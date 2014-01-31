package com.shekhar.conferenceapp.websocket;

import java.io.IOException;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.CloseReason;
import javax.websocket.CloseReason.CloseCodes;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/quiz")
public class QuizServerEndpoint {

    @Inject
    private Logger logger;

    @Inject
    private QuizService quizService;

    @OnOpen
    public void onOpen(Session session) {
        logger.info("Connected ... " + session.getId());
    }

    @OnMessage
    public String onMessage(String answer, Session session) {
        switch (answer) {
        case "start":
            logger.info("Starting the game by sending first question");
            String question = quizService.getRandomQuestion();
            logger.info("Question: " + question);
            session.getUserProperties().put("question", question);
            JsonObject jsonObject = Json.createObjectBuilder().add("question", question)
                    .add("message", "Here's your first question").add("style", "info").build();
            return jsonObject.toString();
        case "end":
            logger.info("Quitting the game");
            try {
                session.close(new CloseReason(CloseCodes.NORMAL_CLOSURE, "Game finished"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        String question = (String) session.getUserProperties().get("question");
        return checkLastQuestionAndSendANewQuestion(question, answer, session);
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        logger.info(String.format("Session %s closed because of %s", session.getId(), closeReason));
    }

    private String checkLastQuestionAndSendANewQuestion(String question, String answer, Session session) {
        String correctAnswer = quizService.getAnswer(question);

        String nextQuestion = quizService.getRandomQuestion();

        session.getUserProperties().put("question", nextQuestion);

        String message = "Congratulations, your answer is correct";
        String style = "success";
        if (answer == null || !correctAnswer.equals(answer)) {
            message = "Sorry, your answer is wrong. Better luck next time.";
            style = "error";
        }

        return Json.createObjectBuilder().add("question", nextQuestion).add("message", message).add("style", style)
                .build().toString();
    }
}