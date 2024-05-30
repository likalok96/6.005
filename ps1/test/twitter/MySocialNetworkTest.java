/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class MySocialNetworkTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     * 
     * Partition:
     *  guessFollowsGraph(tweets)
     *  tweets.size: 0,1,>1
     *  tweets contains user-mention @ or not
     *  tweets contains self user-mention @ or not
     *  tweets contains repeated user-mention @ or not
     * 
     *  tweets contains user-mention # or not
     *  tweets contains self user-mention # or not
     *  tweets contains repeated user-mention # or not
     * 
     *  tweets contains repeated author or not
     * 
     * influencers(followsGraph) 
     *  followsGraph.size: 0,1,>1
     *  followGraph contains same number of followers or not
     *  result contains non followGraph key or not
     * 
     */

    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2016-02-17T11:30:00Z");

    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest @so much @hello @hahahahaha ?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype #bbitdiddle", d2);
    private static final Tweet tweet3 = new Tweet(3, "hahahahaha", "hello", d3);
    private static final Tweet tweet4 = new Tweet(4, "hahahahaha", "hello @hello #hahahaha", d3);
    private static final Tweet tweet5 = new Tweet(5, "hahahahaha", "hello123 @Hello #hahahaha", d3);
    private static final Tweet tweet6 = new Tweet(6, "hahahahaha", "@hello123 @hello #hahahaha", d3);
    private static final Tweet tweet7 = new Tweet(7, "hahahahaha", "ewrwere@hello123 @hello #hahahaha", d3);
    private static final Tweet tweet8 = new Tweet(8, "hahahahaha", "ewrwere@hello123 @hello #hahahaha @", d3);
    private static final Tweet tweet9 = new Tweet(9, "hahahahaha", "testing @test @hahahahaha ", d3);

    private static final List<Tweet> tweets0 = Arrays.asList();
    private static final List<Tweet> tweets1 = Arrays.asList(tweet1);
    private static final List<Tweet> tweets = Arrays.asList(tweet1, tweet2);
    private static final List<Tweet> tweets3 = Arrays.asList(tweet1, tweet2, tweet3);
    private static final List<Tweet> tweets3_ = Arrays.asList(tweet3, tweet2, tweet1);
    private static final List<Tweet> tweets4 = Arrays.asList(tweet1, tweet2, tweet3, tweet4);
    private static final List<Tweet> tweets5 = Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5);
    private static final List<Tweet> tweetsEx1 = Arrays.asList(tweet2, tweet3, tweet4, tweet5, tweet6, tweet7, tweet8,tweet9);

    private static final List<Tweet> tweetsAll = Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5, tweet6, tweet7, tweet8,tweet9);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testGuessFollowsGraphEmpty() {
        Map<String, Set<String>> followsGraph = MySocialNetwork.guessFollowsGraph(new ArrayList<>());

        Map<String, Set<String>> followsGraph2 = MySocialNetwork.guessFollowsGraph(Arrays.asList(tweet3));

        
        assertTrue("expected empty graph", followsGraph.isEmpty());
        assertTrue("expected empty graph", followsGraph2.isEmpty());

    }

    @Test
    public void testGuessFollowsGraph1Result() {
        Map<String, Set<String>> followsGraph = MySocialNetwork.guessFollowsGraph(Arrays.asList(tweet4));
        Set<String> expectedUsers = new HashSet<>();
        expectedUsers.addAll(Arrays.asList("hahahahaha"));
        Set<String> expectedFollowers = new HashSet<>();
        expectedFollowers.addAll(Arrays.asList("hello","hahahaha"));

        assertFalse("expected empty graph", followsGraph.isEmpty());
        assertTrue("expected empty graph", followsGraph.keySet().containsAll(expectedUsers));
        assertTrue("expected empty graph", followsGraph.get("hahahahaha").containsAll(expectedFollowers));
    }

    @Test
    public void testGuessFollowsGraph3Result() {
        Map<String, Set<String>> followsGraph = MySocialNetwork.guessFollowsGraph(tweetsEx1);
        Set<String> expectedUsers = new HashSet<>();
        expectedUsers.addAll(Arrays.asList("hahahahaha"));
        Set<String> expectedFollowers = new HashSet<>();
        expectedFollowers.addAll(Arrays.asList("hello","hello123","test","hahahaha"));

        assertFalse("expected empty graph", followsGraph.isEmpty());
        assertTrue("expected empty graph", followsGraph.keySet().containsAll(expectedUsers));
        assertTrue("expected empty graph", followsGraph.get("hahahahaha").containsAll(expectedFollowers));
    }

    @Test
    public void testGuessFollowsGraph2Users() {
        Map<String, Set<String>> followsGraph = MySocialNetwork.guessFollowsGraph(tweetsAll);
        Set<String> expectedUsers = new HashSet<>();
        expectedUsers.addAll(Arrays.asList("hahahahaha","alyssa","bbitdiddle"));
        Set<String> expectedFollowers = new HashSet<>();
        expectedFollowers.addAll(Arrays.asList("hello","hello123","test","hahahaha"));
        Set<String> expectedFollowers2 = new HashSet<>();
        expectedFollowers2.addAll(Arrays.asList("so","hello","hahahahaha"));
        Set<String> expectedFollowers3 = new HashSet<>();
        expectedFollowers3.addAll(Arrays.asList("hype"));

        assertFalse("expected empty graph", followsGraph.isEmpty());
        assertTrue("expected empty graph", followsGraph.keySet().containsAll(expectedUsers));
        assertTrue("expected empty graph", followsGraph.get("hahahahaha").containsAll(expectedFollowers));
        assertTrue("expected empty graph", followsGraph.get("alyssa").containsAll(expectedFollowers2));
        assertTrue("expected empty graph", followsGraph.get("bbitdiddle").containsAll(expectedFollowers3));
    }


    @Test
    public void testInfluencersEmpty() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertTrue("expected empty list", influencers.isEmpty());
    }

    @Test
    public void testInfluencers1() {
        //Map<String, Set<String>> followsGraph = new HashMap<>();
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet4));
        List<String> influencers = SocialNetwork.influencers(followsGraph);

        List<String> expectedInfluencers = Arrays.asList("hello","hahahahaha");
        
        assertFalse("expected empty list", influencers.isEmpty());
        assertTrue("expected list",influencers.containsAll(expectedInfluencers));
        assertEquals("expected order","hello", influencers.get(0));

    }

    @Test
    public void testInfluencers2() {
        //Map<String, Set<String>> followsGraph = new HashMap<>();
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet5));
        List<String> influencers = SocialNetwork.influencers(followsGraph);

        List<String> expectedInfluencers = Arrays.asList("hello","hahahahaha");
        
        assertFalse("expected empty list", influencers.isEmpty());
        assertTrue("expected list",influencers.containsAll(expectedInfluencers));
        assertEquals("expected order","hello", influencers.get(0));

    }

    @Test
    public void testInfluencers() {
        //Map<String, Set<String>> followsGraph = new HashMap<>();
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweetsAll);
        List<String> influencers = SocialNetwork.influencers(followsGraph);

        List<String> expectedInfluencers = Arrays.asList("hello","so","test","hello123","hahahahaha","alyssa");
        System.out.println(followsGraph);

        assertFalse("expected empty list", influencers.isEmpty());
        assertTrue("expected list",influencers.containsAll(expectedInfluencers));
        assertEquals("expected order","hello", influencers.get(0));

    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * SocialNetwork class that follows the spec. It will be run against several
     * staff implementations of SocialNetwork, which will be done by overwriting
     * (temporarily) your version of SocialNetwork with the staff's version.
     * DO NOT strengthen the spec of SocialNetwork or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in SocialNetwork, because that means you're testing a
     * stronger spec than SocialNetwork says. If you need such helper methods,
     * define them in a different class. If you only need them in this test
     * class, then keep them in this test class.
     */

}
