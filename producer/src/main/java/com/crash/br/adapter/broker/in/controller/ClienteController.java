package com.crash.br.adapter.broker.in.controller;

import com.crash.br.adapter.broker.in.ProducerKafka;
import com.crash.br.adapter.broker.in.request.ClienteRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
public class ClienteController {

    @Autowired
    private ProducerKafka producerKafka;

    @PostMapping
    public ResponseEntity<?> sendMessageTopic(@RequestBody ClienteRequest request) throws ExecutionException, InterruptedException {
        producerKafka.send(request);
        return ResponseEntity.ok().build();
    }
}
