package com.shekhar.conferenceapp.rest;

import java.util.logging.Logger;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNewConference(@Valid final Conference conference) {
        logger.info("Saving conference .. " + conference);
        Conference persitedConference = conferenceService.save(conference);
        logger.info("Saved conference ..");
        return Response.status(Status.CREATED).entity(persitedConference).build();

    }

    
}
