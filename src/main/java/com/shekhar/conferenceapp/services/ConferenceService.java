package com.shekhar.conferenceapp.services;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import com.shekhar.conferenceapp.domain.Conference;

@ApplicationScoped
@Transactional
public class ConferenceService {

    @Inject
    private EntityManager entityManager;

    public Conference save(Conference conference) {
        entityManager.persist(conference);
        return conference;
    }

}
