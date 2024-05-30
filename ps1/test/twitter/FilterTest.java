/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class FilterTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     * Partition:
     * 
     *  writtenBy(tweets, username)
     *  tweets.size: 0,>0
     *  result.size: 0,1,>1
     *  username: author with same case or different case
     * 
     *  inTimespan(tweets, timespan)
     *  tweets.size: 0,>0
     *  result.size: 0,1,>1
     *  timespan: start> tweet.getTimestamp() < end,
     *            start>= tweet.getTimestamp() <= end,
     *            tweet.getTimestamp() > start < end,
     *            start > end < tweet.getTimestamp(),
     * 
     *  containing(tweets, words)
     *  tweets.size: 0,>0
     *  words.size: 0,1,>1
     *  result.size: 0,1,>1
     *  word: tweet text with same case or different case
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
    public void testWrittenByMultipleTweetsSingleResult() {
        List<Tweet> writtenBynone = Filter.writtenBy(Arrays.asList(), "alyssa");

        List<Tweet> writtenBy0 = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "alyssa123");
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "alyssa");
        List<Tweet> writtenBy2 = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "alYssa");
        List<Tweet> writtenBy3 = Filter.writtenBy(tweets3, "alyssa");
        List<Tweet> writtenByAll1 = Filter.writtenBy(tweetsAll, "alyssa");
        List<Tweet> writtenByAll2 = Filter.writtenBy(tweetsAll, "hahahahaha");

        assertTrue("expected non-empty list", writtenBynone.isEmpty());
        assertTrue("expected non-empty list", writtenBy0.isEmpty());


        assertEquals("expected singleton list", 1, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet1));

        assertEquals("expected singleton list", 1, writtenBy2.size());
        assertTrue("expected list to contain tweet", writtenBy2.contains(tweet1));

        assertEquals("expected singleton list", 1, writtenBy3.size());
        assertTrue("expected list to contain tweet", writtenBy3.contains(tweet1));

        assertEquals("expected singleton list", 1, writtenByAll1.size());
        assertTrue("expected list to contain tweet", writtenByAll1.contains(tweet1));

        assertEquals("expected singleton list", 7, writtenByAll2.size());
        assertTrue("expected list to contain tweet", writtenByAll2.containsAll(Arrays.asList(tweet3, tweet4, tweet5, tweet6, tweet7, tweet8,tweet9)));
        assertEquals("expected order",0,writtenByAll2.indexOf(tweet3));
        assertEquals("expected order",3,writtenByAll2.indexOf(tweet6));

    }
    
    @Test
    public void testInTimespanMultipleTweetsMultipleResults() {
        Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T12:00:00Z");

        Instant testStart2 = Instant.parse("2016-02-18T09:00:00Z");
        Instant testEnd2 = Instant.parse("2016-02-18T12:00:00Z");

        Instant testStart3 = Instant.parse("2016-02-17T11:10:00Z");
        Instant testEnd3 = Instant.parse("2016-02-17T11:31:00Z");

        Instant testStart4 = Instant.parse("2016-02-17T11:30:00Z");
        Instant testEnd4 = Instant.parse("2016-02-17T11:30:00Z");

        Instant testStart5 = Instant.parse("2016-02-16T11:00:00Z");
        Instant testEnd5 = Instant.parse("2016-02-16T11:30:00Z");
        
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(testStart, testEnd));
        List<Tweet> inTimespan2 = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(testStart2, testEnd2));
        List<Tweet> inTimespan3 = Filter.inTimespan(tweetsAll, new Timespan(testStart3, testEnd3));
        List<Tweet> inTimespan4 = Filter.inTimespan(tweetsAll, new Timespan(testStart4, testEnd4));
        List<Tweet> inTimespan5 = Filter.inTimespan(tweetsAll, new Timespan(testStart5, testEnd5));
        
        assertFalse("expected non-empty list", inTimespan.isEmpty());
        assertTrue("expected list to contain tweets", inTimespan.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, inTimespan.indexOf(tweet1));

        assertTrue("expected empty list", inTimespan2.isEmpty());
        //assertTrue("expected list to contain tweets", inTimespan.containsAll(Arrays.asList(tweet1, tweet2)));
        //assertEquals("expected same order", 0, inTimespan.indexOf(tweet1));

        assertFalse("expected non-empty list", inTimespan3.isEmpty());
        assertTrue("expected list to contain tweets", inTimespan3.containsAll(Arrays.asList(tweet3, tweet4, tweet5, tweet6, tweet7, tweet8,tweet9)));
        assertEquals("expected same order", 0, inTimespan3.indexOf(tweet3));
        assertEquals("expected same order",3,inTimespan3.indexOf(tweet6));

        assertFalse("expected non-empty list", inTimespan4.isEmpty());
        assertTrue("expected list to contain tweets", inTimespan4.containsAll(Arrays.asList(tweet3, tweet4, tweet5, tweet6, tweet7, tweet8,tweet9)));
        assertEquals("expected same order", 0, inTimespan4.indexOf(tweet3));
        assertEquals("expected same order",3,inTimespan4.indexOf(tweet6));

        assertTrue("expected empty list", inTimespan5.isEmpty());

    }
    
    @Test
    public void testContaining() {
        List<Tweet> containingNone = Filter.containing(tweetsAll, Arrays.asList());

        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("talk"));
        List<Tweet> containing2 = Filter.containing(tweetsAll, Arrays.asList("ha"));
        List<Tweet> containing3 = Filter.containing(tweetsAll, Arrays.asList("Test"));
        List<Tweet> containing4 = Filter.containing(tweetsAll, Arrays.asList("ha","test"));
        List<Tweet> containing5 = Filter.containing(tweetsAll, Arrays.asList("gfdgdfgdfgdfg"));
        List<Tweet> containing6 = Filter.containing(tweetsAll, Arrays.asList("gfdgdfgdfgdfg","test"));

        assertTrue("expected empty list", containingNone.isEmpty());

        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, containing.indexOf(tweet1));

        assertFalse("expected non-empty list", containing2.isEmpty());
        assertTrue("expected list to contain tweets", containing2.containsAll(Arrays.asList(tweet3, tweet4, tweet5, tweet6, tweet7, tweet8)));
        assertEquals("expected same order", 0, containing2.indexOf(tweet3));

        assertFalse("expected non-empty list", containing3.isEmpty());
        assertTrue("expected list to contain tweets", containing3.contains(tweet9));

        assertFalse("expected non-empty list", containing4.isEmpty());
        assertTrue("expected list to contain tweets", containing4.containsAll(Arrays.asList(tweet3, tweet4, tweet5, tweet6, tweet7, tweet8,tweet9)));
        assertEquals("expected same order", 0, containing4.indexOf(tweet3));

        assertTrue("expected non-empty list", containing5.isEmpty());
        //assertTrue("expected list to contain tweets", containing5.containsAll(Arrays.asList(tweet3, tweet4, tweet5, tweet6, tweet7, tweet8,tweet9)));
        //assertEquals("expected same order", 0, containing5.indexOf(tweet3));

        assertFalse("expected non-empty list", containing6.isEmpty());
        assertTrue("expected list to contain tweets", containing6.contains(tweet9));
        //assertEquals("expected same order", 0, containing6.indexOf(tweet3));

    }

    /*
     * Warning: all the tests you write here must be runnable against any Filter
     * class that follows the spec. It will be run against several staff
     * implementations of Filter, which will be done by overwriting
     * (temporarily) your version of Filter with the staff's version.
     * DO NOT strengthen the spec of Filter or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Filter, because that means you're testing a stronger
     * spec than Filter says. If you need such helper methods, define them in a
     * different class. If you only need them in this test class, then keep them
     * in this test class.
     */

}
