package com.shekhar.conferenceapp.rest;

import java.util.Map;
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
import com.shekhar.conferenceapp.services.GooseExtractorClient;

@Path("/conferences")
public class ConferenceResource {

    @Inject
    private ConferenceService conferenceService;

    @Inject
    private Logger logger;
    
    @Resource
    private ManagedExecutorService mes;
    
    @Inject
    private GooseExtractorClient gooseExtractorClient;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void createNewConference(@Suspended final AsyncResponse asyncResponse, @Valid final Conference conference) {
        mes.submit(new Runnable() {
            @Override
            public void run() {
                Map<String, String> map = gooseExtractorClient.fetchImageAndDescription(conference.getConferenceUrl());
                if (conference.getBannerImgUrl() == null) {
                    conference.setBannerImgUrl(map.get("bannerImgUrl"));
                }
                conference.setDescription(map.get("description"));
                logger.info("Saving conference .. " + conference);
                Conference persitedConference = conferenceService.save(conference);
                logger.info("Saved conference ..");
                asyncResponse.resume(Response.status(Status.CREATED).entity(persitedConference).build());
            }
        });

    }

}
