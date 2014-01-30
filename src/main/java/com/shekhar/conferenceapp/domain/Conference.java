package com.shekhar.conferenceapp.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

@Entity
public class Conference {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String name;

    @Size(max = 4000)
    private String description;

    @Column(updatable = false)
    private Date createdOn = new Date();

    private boolean track = false;

    @URL
    private String bannerImgUrl;

    @Future
    private Date startDate;

    @Future
    private Date endDate;

    @NotNull
    @URL
    private String conferenceUrl;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "confHashtags", joinColumns = @JoinColumn(name = "conference_id"))
    @Column(name = "hashtag")
    @NotNull
    @Size(max = 5, min = 1)
    private Set<String> hashtags = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "confSpeakers", joinColumns = @JoinColumn(name = "conference_id"))
    @Column(name = "speaker")
    @NotNull
    @Size(max = 100, min = 1)
    private Set<String> speakers = new HashSet<>();

    public Conference() {
    }

    public Conference(String name, Date startDate, Date endDate, String conferenceUrl) {
        super();
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.conferenceUrl = conferenceUrl;
    }

    public Conference(Long id, String name, Set<String> hashtags) {
        this.id = id;
        this.name = name;
        this.hashtags = hashtags;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public boolean isTrack() {
        return track;
    }

    public void setTrack(boolean track) {
        this.track = track;
    }

    public String getBannerImgUrl() {
        return bannerImgUrl;
    }

    public void setBannerImgUrl(String bannerImgUrl) {
        this.bannerImgUrl = bannerImgUrl;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getConferenceUrl() {
        return conferenceUrl;
    }

    public void setConferenceUrl(String conferenceUrl) {
        this.conferenceUrl = conferenceUrl;
    }

    public void setHashtags(Set<String> hashtags) {
        this.hashtags = hashtags;
    }

    public Set<String> getHashtags() {
        return hashtags;
    }

    public void setSpeakers(Set<String> speakers) {
        this.speakers = speakers;
    }

    public Set<String> getSpeakers() {
        return speakers;
    }

    @Override
    public String toString() {
        return "Conference [id=" + id + ", name=" + name + ", description=" + description + ", createdOn=" + createdOn
                + ", track=" + track + ", bannerImgUrl=" + bannerImgUrl + ", startDate=" + startDate + ", endDate="
                + endDate + ", conferenceUrl=" + conferenceUrl + ", hashtags=" + hashtags + ", speakers=" + speakers
                + "]";
    }

}
