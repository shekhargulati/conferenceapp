package com.shekhar.conferenceapp.services;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

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
    
    public Conference read(@NotNull Long id) {
        return entityManager.find(Conference.class, id);
    }

    public List<Conference> findAll(int start, int max) {
        TypedQuery<Conference> query = entityManager.createQuery(
                "SELECT c from Conference c ORDER BY c.createdOn DESC", Conference.class);
        query.setFirstResult(start);
        query.setMaxResults(max);
        return query.getResultList();
    }

}
