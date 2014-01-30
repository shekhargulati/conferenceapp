package com.shekhar.conferenceapp.twitter;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import com.google.common.collect.Lists;
import com.shekhar.conferenceapp.services.TweetService;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.BasicClient;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import com.twitter.hbc.twitter4j.Twitter4jStatusClient;

@ApplicationScoped
public class TwitterFilterStream {

    private static final String consumerKey = System.getenv("TWITTER_CONSUMER_KEY");
    private static final String consumerSecret = System.getenv("TWITTER_CONSUMER_SECRET");
    private static final String accessToken = System.getenv("TWITTER_ACCESS_TOKEN");
    private static final String accessSecret = System.getenv("TWITTER_ACCESS_TOKEN_SECRET");

    @Inject
    private Logger logger;
    @Inject
    private TweetService tweetService;

    public TwitterFilterStream() {
    }

    public BasicClient createClient(String jobName, Set<String> searchTerms, BlockingQueue<String> queue) {

        StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();
        endpoint.trackTerms(new ArrayList<>(searchTerms));
        endpoint.stallWarnings(false);

        Authentication auth = new OAuth1(consumerKey, consumerSecret, accessToken, accessSecret);

        BasicClient client = new ClientBuilder().name(jobName).hosts(Constants.STREAM_HOST).endpoint(endpoint)
                .authentication(auth).processor(new StringDelimitedProcessor(queue)).build();

        return client;
    }

    public void readTweets(Long conferenceId, BasicClient client, BlockingQueue<String> queue) {
        int numProcessingThreads = 1;
        ExecutorService service = Executors.newFixedThreadPool(numProcessingThreads);

        Twitter twitter = getTwitterInstance();

        Twitter4jStatusClient t4jClient = new Twitter4jStatusClient(client, queue,
                Lists.newArrayList(new TwitterStatusListener(twitter, conferenceId, tweetService)), service);

        t4jClient.connect();
        for (int threads = 0; threads < numProcessingThreads; threads++) {
            t4jClient.process();
        }
    }

    private Twitter getTwitterInstance() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true).setOAuthConsumerKey(consumerKey).setOAuthConsumerSecret(consumerSecret)
                .setOAuthAccessToken(accessToken).setOAuthAccessTokenSecret(accessSecret);
        cb.setRestBaseURL("https://api.twitter.com/1.1/");
        TwitterFactory tf = new TwitterFactory(cb.build());
        return tf.getInstance();
    }

    public void shutdown(BasicClient client) {
        client.stop();
    }

    public long numberOfMessagesCollected(BasicClient client) {
        return client.getStatsTracker().getNumMessages();
    }

}