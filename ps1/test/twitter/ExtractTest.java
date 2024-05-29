/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class ExtractTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     * getTimespan(tweets)
     * Partition:
     *  tweets.sizes: 0,1,>1
     *  tweets in time order or out of order
     *  tweets contains repeated time or unique time
     * 
     * GetMentionedUsers(tweets)
     * Partition:
     *  tweets includes username-mention @ or not
     *  tweets includes repeated username-mention @ or not
     *  tweets includes username-mention @ preceded by valid char or not
     *  tweets includes username-mention @ at start, middle or end of tweet
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2016-02-17T11:30:00Z");

    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "hahahahaha", "hello #hahahaha", d3);
    private static final Tweet tweet4 = new Tweet(4, "hahahahaha", "hello @hello #hahahaha", d3);
    private static final Tweet tweet5 = new Tweet(5, "hahahahaha", "hello123 @hello #hahahaha", d3);
    private static final Tweet tweet6 = new Tweet(6, "hahahahaha", "@hello123 @hello #hahahaha", d3);
    private static final Tweet tweet7 = new Tweet(7, "hahahahaha", "ewrwere@hello123 @hello #hahahaha", d3);
    private static final Tweet tweet8 = new Tweet(8, "hahahahaha", "ewrwere@hello123 @hello #hahahaha @", d3);
    private static final Tweet tweet9 = new Tweet(9, "hahahahaha", "testing @test", d3);



    private static final List<Tweet> tweets0 = Arrays.asList();
    private static final List<Tweet> tweets1 = Arrays.asList(tweet1);
    private static final List<Tweet> tweets = Arrays.asList(tweet1, tweet2);
    private static final List<Tweet> tweets3 = Arrays.asList(tweet1, tweet2, tweet3);
    private static final List<Tweet> tweets3_ = Arrays.asList(tweet3, tweet2, tweet1);
    private static final List<Tweet> tweets4 = Arrays.asList(tweet1, tweet2, tweet3, tweet4);
    private static final List<Tweet> tweets5 = Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5);
    private static final List<Tweet> tweetsAll = Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5, tweet6, tweet7, tweet8,tweet9);




    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    @Test
    public void testGetTimespan(){


        Timespan timespan0 = Extract.getTimespan(tweets0);
        Timespan timespan1 = Extract.getTimespan(tweets1);
        Timespan timespan = Extract.getTimespan(tweets);
        Timespan timespan3 = Extract.getTimespan(tweets3);
        Timespan timespan3_ = Extract.getTimespan(tweets3_);
        Timespan timespan4 = Extract.getTimespan(tweets4);

        Timespan diff0 = new Timespan(Instant.MIN,Instant.MIN);
        Timespan diff1 = new Timespan(d1,d1);
        Timespan diff = new Timespan(d1,d2);
        Timespan diff3 = new Timespan(d1,d3);
        Timespan diff4 = new Timespan(d1,d3);

        assertTrue("equal timespan0",timespan0.equals(diff0));
        assertTrue("equal timespan1",timespan1.equals(diff1));
        assertTrue("equal timespan",timespan.equals(diff));
        assertTrue("equal timespan3",timespan3.equals(diff3));
        assertTrue("equal timespan3_",timespan3_.equals(diff3));
        assertTrue("equal timespan4",timespan4.equals(diff4));



    }
    
    @Test
    public void testGetTimespanTwoTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }


    @Test
    public void testGetMentionedUsersNoMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1));
        Set<String> mentionedUsers4 = Extract.getMentionedUsers(tweets4);
        Set<String> mentionedUsers5 = Extract.getMentionedUsers(tweets5);
        Set<String> mentionedUsers6 = Extract.getMentionedUsers(Arrays.asList(tweet6));
        Set<String> mentionedUsers7 = Extract.getMentionedUsers(Arrays.asList(tweet7));
        Set<String> mentionedUsers8 = Extract.getMentionedUsers(Arrays.asList(tweet8));
        Set<String> mentionedUsersAll = Extract.getMentionedUsers(tweetsAll);

        Set<String> name4 = new HashSet<>(Arrays.asList("hello"));
        Set<String> name5 = new HashSet<>(Arrays.asList("hello"));
        Set<String> name6 = new HashSet<>(Arrays.asList("hello123","hello"));
        Set<String> name7 = new HashSet<>(Arrays.asList("hello"));
        Set<String> name8 = new HashSet<>(Arrays.asList("hello"));
        Set<String> nameAll = new HashSet<>(Arrays.asList("hello123","hello","test"));

        
        assertTrue("expected empty set", mentionedUsers.isEmpty());
        assertTrue("expected name4", mentionedUsers4.equals(name4));
        assertTrue("expected name5", mentionedUsers5.equals(name5));
        assertTrue("expected name6", mentionedUsers6.equals(name6));
        assertTrue("expected name7", mentionedUsers7.equals(name7));
        assertTrue("expected name8", mentionedUsers8.equals(name8));
        assertTrue("expected nameAll", mentionedUsersAll.equals(nameAll));


    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * Extract class that follows the spec. It will be run against several staff
     * implementations of Extract, which will be done by overwriting
     * (temporarily) your version of Extract with the staff's version.
     * DO NOT strengthen the spec of Extract or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Extract, because that means you're testing a
     * stronger spec than Extract says. If you need such helper methods, define
     * them in a different class. If you only need them in this test class, then
     * keep them in this test class.
     */

}
