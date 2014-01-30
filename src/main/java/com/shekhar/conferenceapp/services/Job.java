package com.shekhar.conferenceapp.services;

import java.util.Set;

public class Job {

    private Long id;
    private String name;
    private Set<String> hashtags;

    public Job(Long id, String name, Set<String> hashtags) {
        this.id = id;
        this.name = name;
        this.hashtags = hashtags;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<String> getHashtags() {
        return hashtags;
    }

    @Override
    public String toString() {
        return "Job [id=" + id + ", name=" + name + ", hashtags=" + hashtags + "]";
    }
    
    

    

}
