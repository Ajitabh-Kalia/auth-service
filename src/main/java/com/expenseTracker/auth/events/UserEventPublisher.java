package com.expenseTracker.auth.events;

import org.springframework.data.redis.core.StreamOperations;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.connection.RedisStreamCommands.XAddOptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserEventPublisher {

    private final StreamOperations<String, String, Object> streamOps;
    private static final String STREAM_KEY = "user-events";
    private final ObjectMapper objectMapper;

    public UserEventPublisher(StreamOperations<String, String, Object> streamOps, ObjectMapper objectMapper) {
        this.streamOps = streamOps;
        this.objectMapper = objectMapper;
    }


    public void publishUserEvent(UserEvent event) {
        Map<String, Object> body = new HashMap<>();
        body.put("id", event.getId());
        body.put("username", event.getUsername());
        body.put("passwordHash", event.getPasswordHash());
        body.put("role", event.getRole());

        String jsonBody;
        try {
            jsonBody = objectMapper.writeValueAsString(body);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return;
        }
        Map<String, Object> eventMap = new HashMap<>();
        eventMap.put("json", jsonBody);
        XAddOptions options = XAddOptions.maxlen(50).approximateTrimming(true);

        streamOps.add(STREAM_KEY, eventMap, options);
    }
}
