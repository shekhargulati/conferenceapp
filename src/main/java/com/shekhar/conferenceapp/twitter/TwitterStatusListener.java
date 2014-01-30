package com.shekhar.conferenceapp.twitter;

import java.util.List;
import java.util.logging.Logger;

import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.Twitter;

import com.shekhar.conferenceapp.domain.Tweet;
import com.shekhar.conferenceapp.services.TweetService;
import com.twitter.Extractor;

public class TwitterStatusListener implements StatusListener {

    private final long conferenceId;
    private final Twitter twitter;
    private TweetService tweetService;
    private Logger logger = Logger.getLogger(this.getClass().getName());

    public TwitterStatusListener(Twitter twitter, Long conferenceId, TweetService tweetService) {
        this.conferenceId = conferenceId;
        this.twitter = twitter;
        this.tweetService = tweetService;
    }

    @Override
    public void onStatus(Status status) {
        logger.info("Status: " + status.getText());
        Status retweetedStatus = findRootRetweet(status);
        Tweet tweetDoc = statusToTweet(status, retweetedStatus);
        tweetService.save(tweetDoc);
    }

    private Tweet statusToTweet(Status status, Status retweetedStatus) {
        Tweet tweetDoc = new Tweet();
        String text = status.getText();
        tweetDoc.setText(text);
        tweetDoc.setCreatedAt(status.getCreatedAt());
        tweetDoc.setTweetedBy(status.getUser().getScreenName());
        tweetDoc.setTweetId(status.getId());
        Extractor extractor = new Extractor();
        List<String> hashtags = extractor.extractHashtags(text);
        tweetDoc.setHashtags(hashtags);
        List<String> mentionedScreennames = extractor.extractMentionedScreennames(text);
        tweetDoc.setMentions(mentionedScreennames);
        tweetDoc.setUrls(extractor.extractURLs(text));
        tweetDoc.setConferenceId(conferenceId);
        if (retweetedStatus != null) {
            tweetDoc.setRetweetCount(retweetedStatus.getRetweetCount());
            tweetDoc.setRetweetId(retweetedStatus.getId());
        }

        return tweetDoc;
    }

    private Status findRootRetweet(Status status) {
        Status retweet = status.getRetweetedStatus();
        Status rootRetweet = retweet;
        while (retweet != null) {
            retweet = retweet.getRetweetedStatus();
            if (retweet != null) {
                rootRetweet = retweet;
            }
        }
        return rootRetweet;
    }

    @Override
    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
    }

    @Override
    public void onTrackLimitationNotice(int limit) {
    }

    @Override
    public void onScrubGeo(long user, long upToStatus) {
    }

    @Override
    public void onException(Exception e) {
        logger.info("Exception .. " + e.getMessage());
    }
}
