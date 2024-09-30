/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.Set;

import org.junit.Test;

public class ExtractTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     * Testing strategy:
     * getTimespan: we are partitioning based on the number of tweets.
     * -testGetTimespanSingleTweet
     * -testGetTimespanTwoTweets
     * getMentionedUsers():Test based on the number of mentions in the tweet
     * -testGetMentionedUsersNoMention
     * -testGetMentionedUsersOneMention
     * -testGetMentionedUsersCaseInsensitive
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; 
    }
    @Test
    /* Test case where the list contains only one tweet. */
    public void testGetTimespanSingleTweet() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d1, timespan.getEnd()); 
    }

    @Test
    /* Test case where the list contains two tweets with different timestamps.*/
    public void testGetTimespanTwoTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }

    /* Tests for "getMentionedUsers()" */
    @Test
    // Test case where a tweet contains no mention
    public void testGetMentionedUsersNoMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1));
        
        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }

    @Test
    // Test case where a tweet contains exactly one mention.
    public void testGetMentionedUsersOneMention() {
        Tweet tweetWithMention = new Tweet(3, "alyssa", "hello @user1", d1);
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweetWithMention));
        
        assertEquals("expected size", 1, mentionedUsers.size());
        assertTrue("Set should contain 'user1'", mentionedUsers.contains("user1"));
    }

    @Test
    // Test case where the mention is written with different casing
    public void testGetMentionedUsersCaseInsensitive() {
        Tweet tweetWithMention = new Tweet(4, "bbitdiddle", "@User1 is here", d2);
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweetWithMention));
        
        assertTrue("Set should contain 'user1' ignoring case", mentionedUsers.contains("user1"));
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
