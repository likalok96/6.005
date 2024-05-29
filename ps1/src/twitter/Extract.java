/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.time.Instant;

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

        if(tweets.size()==0){
            return new Timespan(Instant.MIN,Instant.MIN);
        }

        Instant min=null,min2=null;
        for(int i=0;i<tweets.size();i++){
            Instant time = tweets.get(i).getTimestamp();
            if(min==null){
                min = time;
            }
            if(min2==null){
                min2 = time;
                continue;
            }
            if(time.isBefore(min)){
                min = time;
            } else if(time.isAfter(min2)){
                min2 = time;
            }
        }
        Timespan result = new Timespan(min,min2);
        return result;

        //throw new RuntimeException("not implemented");
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

        Pattern pattern = Pattern.compile("[A-Za-z0-9-_]+");
        
        for(int i=0;i<tweets.size();i++){

            String content = tweets.get(i).getText();
            
            for(int loc=content.indexOf('@'); loc!=-1; loc=content.indexOf('@',loc+1) ){

                boolean checkBefore;
                boolean checkAfter;

                if(loc!=0){
                    Matcher matcherBefore = pattern.matcher(String.valueOf(content.charAt(loc-1)));
                    checkBefore = !matcherBefore.find();
                }else{
                    checkBefore = true ;
                }

                if(loc!=content.length()-1){
                    Matcher matcherAfter = pattern.matcher(String.valueOf(content.charAt(loc+1)));
                    checkAfter = matcherAfter.find();
                }else{
                    checkAfter = false ;
                }


                if(checkBefore && checkAfter ){

                    Matcher matcherName = pattern.matcher(content.substring(loc+1));
                    if(matcherName.find()){
                        String name = matcherName.group(0);
                        users.add(name.toLowerCase());
                    }
                    
                }
            }
        }

        return users;
        //throw new RuntimeException("not implemented");
    }

}
