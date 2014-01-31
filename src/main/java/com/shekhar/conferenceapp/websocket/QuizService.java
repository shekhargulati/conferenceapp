package com.shekhar.conferenceapp.websocket;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class QuizService {

    private static final Map<String, String> CACHE = new ConcurrentHashMap<String, String>() {

        private static final long serialVersionUID = 1L;

        {
            put("Is OpenShift open source?", "yes");
            put("Is OpenShift an auto-scalable PaaS?", "yes");
            put("Does OpenShift supports Oracle database?", "no");
            put("Is OpenShift is the best platform as a service?", "yes");
            put("Is OpenShift extensible?", "yes");
        }

    };

    private Random random = new Random();
    private List<String> keys = new ArrayList<String>(CACHE.keySet());

    public String getRandomQuestion() {
        return keys.get(random.nextInt(CACHE.size()));
    }

    public String getAnswer(String question) {
        return CACHE.get(question);
    }

}
