package ru.fizteh.fivt.students.evlinkov.twitterstream;
/**
 * Created by evlinkov on 17.12.15.
 */
import com.beust.jcommander.Parameter;

public class Parameters {
    static final int USUAL_TWEETS_LIMIT = 555;
    @Parameter(names = {"--query", "-q"},
            description = "Search query")
    private String query = "";
    @Parameter(names = {"--place", "-p"},
            description = "Search place")
    private String place = "";
    @Parameter(names = {"--stream", "-s"},
            description = "Stream mode")
    private boolean stream = false;
    @Parameter(names = {"--hideRetweets"},
            description = "Hide retweets")
    private boolean hideRetweets = false;
    @Parameter(names = {"--limit", "-l"},
            description = "Limit tweets")
    private int limitTweets = USUAL_TWEETS_LIMIT;
    @Parameter(names = {"--help", "-h"},
            description = "Help mode")
    private boolean helpMode = false;
    public String getQuery() {
        return query;
    }
    public String getPlace() {
        return place;
    }
    public boolean checkStream() {
        return stream;
    }
    public boolean checkHideRetweets() {
        return hideRetweets;
    }
    public int getLimitTweets() {
        return limitTweets;
    }
    public boolean checkHelpMode() {
        return helpMode;
    }
}
