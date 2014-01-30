package com.shekhar.conferenceapp.rest;

import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.shekhar.conferenceapp.domain.Conference;
import com.shekhar.conferenceapp.services.ConferenceService;

@Path("/conferences")
public class ConferenceResource {

    @Inject
    private ConferenceService conferenceService;

    @Inject
    private Logger logger;
    
    @Resource
    private ManagedExecutorService mes;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void createNewConference(@Suspended final AsyncResponse asyncResponse, @Valid final Conference conference) {
        mes.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    // ignoring it
                }
                logger.info("Saving conference .. " + conference);
                Conference persitedConference = conferenceService.save(conference);
                logger.info("Saved conference ..");
                asyncResponse.resume(Response.status(Status.CREATED).entity(persitedConference).build());
            }
        });

    }

}
