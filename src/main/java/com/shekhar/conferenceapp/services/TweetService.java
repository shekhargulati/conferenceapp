package com.shekhar.conferenceapp.services;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import com.shekhar.conferenceapp.domain.Tweet;

@Stateless
@Transactional
public class TweetService {

    @Inject
    private EntityManager entityManager;


    @Asynchronous
    public void save(Tweet tweet) {
        entityManager.persist(tweet);
    }

   }
