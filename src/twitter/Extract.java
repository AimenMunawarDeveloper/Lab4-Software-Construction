/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.util.List;
import java.time.Instant;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.HashSet;
import java.util.regex.Matcher;
/**
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {

    /**
     * Get the time period spanned by tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return a minimum-length time interval that contains the timestamp of
     *         every tweet in the list.
     */
	public static Timespan getTimespan(List<Tweet> tweets) {
	    if (tweets.isEmpty()) {
	        throw new IllegalArgumentException("list of tweets can't be empty");
	    }
	    Instant start = tweets.get(0).getTimestamp();
	    Instant end = start;
	    for (int i = 1; i < tweets.size(); i++) {
	        Instant time = tweets.get(i).getTimestamp();
	        start = time.isBefore(start) ? time : start;
	        end = time.isAfter(end) ? time : end;
	    }
	    return new Timespan(start, end);
	}
    /**
     * Get usernames mentioned in a list of tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return the set of usernames who are mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a Twitter username (as
     *         defined by Tweet.getAuthor()'s spec).
     *         The username-mention cannot be immediately preceded or followed by any
     *         character valid in a Twitter username.
     *         For this reason, an email address like bitdiddle@mit.edu does NOT 
     *         contain a mention of the username mit.
     *         Twitter usernames are case-insensitive, and the returned set may
     *         include a username at most once.
     */
	public static Set<String> getMentionedUsers(List<Tweet> tweets) {
	    Set<String> users = new HashSet<>();
	    for (Tweet tweet : tweets) {
	        String text = tweet.getText();
	        int index = 0;
	        while ((index = text.indexOf("@", index)) != -1) {
	            int endIndex = index + 1;
	            while (endIndex < text.length() && Character.isLetterOrDigit(text.charAt(endIndex))) {
	                endIndex++;
	            }
	            if (endIndex > index + 1) { 
	                String username = text.substring(index + 1, endIndex).toLowerCase();
	                users.add(username);
	            }
	            index = endIndex;
	        }
	    }
	    return users;
	}




}
