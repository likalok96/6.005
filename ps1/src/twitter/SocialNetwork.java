/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javafx.print.Collation;

/**
 * SocialNetwork provides methods that operate on a social network.
 * 
 * A social network is represented by a Map<String, Set<String>> where map[A] is
 * the set of people that person A follows on Twitter, and all people are
 * represented by their Twitter usernames. Users can't follow themselves. If A
 * doesn't follow anybody, then map[A] may be the empty set, or A may not even exist
 * as a key in the map; this is true even if A is followed by other people in the network.
 * Twitter usernames are not case sensitive, so "ernie" is the same as "ERNie".
 * A username should appear at most once as a key in the map or in any given
 * map[A] set.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class SocialNetwork {

    /**
     * Guess who might follow whom, from evidence found in tweets.
     * 
     * @param tweets
     *            a list of tweets providing the evidence, not modified by this
     *            method.
     * @return a social network (as defined above) in which Ernie follows Bert
     *         if and only if there is evidence for it in the given list of
     *         tweets.
     *         One kind of evidence that Ernie follows Bert is if Ernie
     *         @-mentions Bert in a tweet. This must be implemented. Other kinds
     *         of evidence may be used at the implementor's discretion.
     *         All the Twitter usernames in the returned social network must be
     *         either authors or @-mentions in the list of tweets.
     */
    public static Map<String, Set<String>> guessFollowsGraph(List<Tweet> tweets) {
        Map<String, Set<String>> result = new HashMap<>();

        Pattern pattern = Pattern.compile("[A-Za-z0-9-_]+");

        for(int i=0;i<tweets.size();i++){
            Tweet tweet = tweets.get(i);
            String content = tweet.getText();   
            
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
                        if(!name.equals(tweets.get(i).getAuthor().toLowerCase())){
                            result.putIfAbsent(tweet.getAuthor().toLowerCase(), new HashSet<>());
                            result.get(tweet.getAuthor().toLowerCase()).add(name.toLowerCase());
                        }


                    }
                    
                }
            }
        }

        return result;
        //throw new RuntimeException("not implemented");
    }

    /**
     * Find the people in a social network who have the greatest influence, in
     * the sense that they have the most followers.
     * 
     * @param followsGraph
     *            a social network (as defined above)
     * @return a list of all distinct Twitter usernames in followsGraph, in
     *         descending order of follower count.
     */
    public static List<String> influencers(Map<String, Set<String>> followsGraph) {
        //TreeMap<String, Integer> countMap = new TreeMap<>();
        Map<String, Integer> countMap = new HashMap<>();

        for(String follower: followsGraph.keySet()){
            follower = follower.toLowerCase();
            countMap.putIfAbsent(follower, 0);
            for(String influencer: followsGraph.get(follower)){
                influencer = influencer.toLowerCase();
                countMap.putIfAbsent(influencer, 0);
                countMap.replace(influencer, countMap.get(influencer)+1);
            }
            //countMap.put(username, count);
        }
        List<String> result = countMap.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()) ).map(Map.Entry::getKey).collect(Collectors.toList());

        return result;
        //Map<String, Integer> sortedcountMap = sortByComparator(countMap);


        //throw new RuntimeException("not implemented");
    }

}
