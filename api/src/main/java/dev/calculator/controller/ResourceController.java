package dev.calculator.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class ResourceController {

    private final ObjectMapper objectMapper;

    public ResourceController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @RequestMapping("/static-resource")
    public Map<String, Object> resource() {
        Map<String, Object> model = new HashMap<>();
        model.put("id", UUID.randomUUID().toString());
        model.put("content", "Hello world");
        return model;
    }

    @RequestMapping("/auth-resource")
    public ResponseEntity<JsonNode> authResource(Authentication authentication) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("id", UUID.randomUUID().toString());
        objectNode.put("content",
                String.format("Oh, hey %s, you are authenticated!", authentication.getName()));
        return ResponseEntity.ok(objectNode);
    }

}
