package ru.fizteh.fivt.students.evlinkov.twitterstream;
/**
 * Created by evlinkov on 17.12.15.
 */
import com.beust.JCommander.Parameter;
import org.junit.Assert;
import org.junit.Test;

public class TestParameters {
    @Test
    public void testHelp() {
        Parameters commandLine = new Parameters();
        new JCommander(commandLine, new String[]{"-h"});
        Assert.assertEquals(true, commandLine.checkHelpMode());
    }
    @Test
    public void testStream() {
        Parameters commandLine = new Parameters();
        new JCommander(commandLine, new String[]{"--stream"});
        Assert.assertEquals(true, commandLine.checkStream());
        Assert.assertEquals(false, commandLine.checkHideRetweets());
    }
    @Test
    public void testPlace() {
        Parameters commandLine = new Parameters();
        new JCommander(commandLine, new String[]{"--place", "Volgograd"});
        Assert.assertEquals("Volgograd", commandLine.getPlace());
        Assert.assertEquals(555, commandLine.getLimitTweets());
    }
    @Test
    public void testQuery() {
        Parameters commandLine = new Parameters();
        new JCommander(commandLine, new String[]{"--query", "Luntiki", "--help"});
        Assert.assertEquals("Luntiki", commandLine.getQuery());
        Assert.assertEquals(true, commandLine.checkHelpMode());
        Assert.assertEquals("", commandLine.getPlace());
    }
    @Test
    public void testHideRetweets() {
        Parameters commandLine = new Parameters();
        new JCommander(commandLine, new String[]{"--hideRetweets"});
        Assert.assertEquals("", commandLine.getQuery());
        Assert.assertEquals(true, commandLine.checkHideRetweets());
    }
    @Test
    public void testLimitTweets() {
        Parameters commandLine = new Parameters();
        new JCommander(commandLine, new String[]{"--limit", "230"});
        Assert.assertEquals(false, commandLine.checkHelpMode());
        Assert.assertEquals(230, commandLine.getLimitTweets());
    }
}
