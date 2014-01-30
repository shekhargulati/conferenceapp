package com.shekhar.conferenceapp.services;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import com.shekhar.conferenceapp.twitter.TwitterFilterStream;
import com.twitter.hbc.httpclient.BasicClient;

@ApplicationScoped
public class TwitterStreamingService {

    @Inject
    private TwitterFilterStream twitterFilterStream;
    @Inject
    private Logger logger;

    public void submitJob(@Observes Job job) {
        logger.info("Recieved job: " + job);
        BlockingQueue<String> queue = new LinkedBlockingQueue<String>(10000);
        BasicClient client = twitterFilterStream.createClient(job.getName(), job.getHashtags(), queue);
        twitterFilterStream.readTweets(job.getId(), client, queue);
    }

}
