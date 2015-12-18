package ru.fizteh.fivt.students.evlinkov.twitterstream;
/**
 * Created by evlinkov on 17.12.15.
 */
import com.beust.jcommander.JCommander;
import twitter4j.*;

import java.util.List;

public class TwitterStream {
    public static final long PAUSE = 1000;
    public static void main(String[] args) throws Exception {
        Parameters parameters = new Parameters();
        JCommander jCommander;
        try {
            jCommander = new JCommander(parameters, args);
        } catch (Exception ex) {
            jCommander = new JCommander(parameters, new String[] {"-h"}); //ok, if args is wrong, let's show HelpMode
            jCommander.usage();
            return;
        }
        if (parameters.checkHelpMode()) {
            jCommander.usage();
            return;
        }
        if (parameters.checkStream()) {
            streamPrint(parameters);
        } else {
            print(parameters);
        }
    }
    public static void printTime(Status status) {
        Time printer = new Time();
        System.out.print(new StringBuilder().append("[")
                .append(printer.printTime(status.getCreatedAt().getTime(), System.currentTimeMillis())).append("]"));
    }
    public static void printRetweetsCount(Status status) {
        System.out.println(new StringBuilder().append("(").append(status.getRetweetCount())
                .append(" ретвитов)").toString());
    }
    public static void printTweet(Status status) {
        System.out.print(new StringBuilder().append("@").append(status.getUser().getName()).append(": ")
                .append(status.getText()));
    }
    public static void printRetweet(Status status) {
        System.out.println(new StringBuilder().append("@").append(status.getUser().getName())
                .append(" ретвитнул: @").append(status.getRetweetedStatus().getUser().getName()).append(": ")
                .append(status.getRetweetedStatus().getText()).toString());
        System.out.println();
    }
    public static void print(Parameters parameters) {
        Twitter twitter = new TwitterFactory().getInstance();
        try {
            Query query = new Query(parameters.getQuery());
            if (!parameters.getPlace().equals("")) {
                if (!parameters.getPlace().equals("nearby")) {
                    Location googleFindPlace;
                    googleFindPlace = new Location(parameters.getPlace());
                    GeoLocation geoLocation;
                    geoLocation = new GeoLocation(googleFindPlace.getLocation().lat, googleFindPlace.getLocation().lng);
                    query.setGeoCode(geoLocation, googleFindPlace.getRadius(), Query.KILOMETERS);
                }
            }
            query.setCount(parameters.getLimitTweets());
            QueryResult answer;
            answer = twitter.search(query);
            List<Status> tweets = answer.getTweets();
            if (tweets.isEmpty()) {
                System.out.println("Nothing find");
                return;
            }
            for (Status tweet : tweets) {
                Thread.sleep(PAUSE);
                if (!tweet.isRetweet()) {
                    printTime(tweet);
                    printTweet(tweet);
                    printRetweetsCount(tweet);
                    System.out.println();
                } else {
                    if (!parameters.checkHideRetweets()) {
                        printTime(tweet);
                        printRetweet(tweet);
                    }
                }
            }
        } catch (TwitterException te) {
            te.printStackTrace();
            System.err.println("Failed to search tweets: " + te.getMessage());
            System.exit(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void streamPrint(Parameters parameters) throws Exception {
        twitter4j.TwitterStream twitterStream;
        twitterStream = new TwitterStreamFactory().getInstance();
        StatusAdapter listener = new StatusAdapter() {
            @Override
            public void onStatus(Status status) {
                try {
                    Thread.sleep(PAUSE);
                } catch (InterruptedException e) {
                    System.out.print(e.getMessage());
                }
                if (!status.isRetweet()) {
                    printTweet(status);
                    System.out.println();
                } else {
                    if (!parameters.checkHideRetweets()) {
                        printRetweet(status);
                    }
                }
            }
        };
        twitterStream.addListener(listener);
        if (parameters.getQuery() == "" && parameters.getPlace() == "") {
            twitterStream.sample();
        } else {
            FilterQuery filter;
            String[] track = new String[1];
            track[0] = parameters.getQuery();
            filter = new FilterQuery(0, new long[0], track);
            if (!parameters.getPlace().equals("")) {
                Location findPlace;
                findPlace = new Location((parameters.getPlace()));
                double[][] bounds = {{findPlace.getBounds().southwest.lng,
                        findPlace.getBounds().southwest.lat},
                        {findPlace.getBounds().northeast.lng,
                                findPlace.getBounds().northeast.lat}};
                filter.locations(bounds);
            }
            twitterStream.filter(filter);
        }
    }
}
