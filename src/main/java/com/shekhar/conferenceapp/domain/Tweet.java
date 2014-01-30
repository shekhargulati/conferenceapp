package com.shekhar.conferenceapp.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

@Entity
public class Tweet {

    @Id
    @GeneratedValue
    private Long id;

    private String text;
    private Date createdAt;
    private String tweetedBy;
    private long tweetId;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "hashtags", joinColumns = @JoinColumn(name = "tweet_id"))
    @Column(name = "hashtag")
    private List<String> hashtags;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "mentions", joinColumns = @JoinColumn(name = "tweet_id"))
    @Column(name = "mention")
    private List<String> mentions;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "urls", joinColumns = @JoinColumn(name = "tweet_id"))
    @Column(name = "url")
    private List<String> urls;

    private long retweetCount;

    private long retweetId;

    private long conferenceId;

    public Tweet() {
        // TODO Auto-generated constructor stub
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getTweetedBy() {
        return tweetedBy;
    }

    public void setTweetedBy(String tweetedBy) {
        this.tweetedBy = tweetedBy;
    }

    public long getTweetId() {
        return tweetId;
    }

    public void setTweetId(long tweetId) {
        this.tweetId = tweetId;
    }

    public List<String> getHashtags() {
        return hashtags;
    }

    public void setHashtags(List<String> hashtags) {
        this.hashtags = hashtags;
    }

    public List<String> getMentions() {
        return mentions;
    }

    public void setMentions(List<String> mentions) {
        this.mentions = mentions;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public long getRetweetCount() {
        return retweetCount;
    }

    public void setRetweetCount(long retweetCount) {
        this.retweetCount = retweetCount;
    }

    public long getRetweetId() {
        return retweetId;
    }

    public void setRetweetId(long retweetId) {
        this.retweetId = retweetId;
    }

    public long getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(long conferenceId) {
        this.conferenceId = conferenceId;
    }

}
