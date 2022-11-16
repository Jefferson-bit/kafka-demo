package com.crash.br.kafka;


import com.crash.br.kafka.demo.Cliente;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KafkaConsumer {

    private static Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "kafka-demo", groupId = "group-1", containerFactory = "concurrentKafkaListenerContainerFactory")
    public void listener(@Payload List<ConsumerRecord<String, Cliente>> payload) {
        try {
            LOGGER.info("payload {}", payload);
            payload.forEach(obj -> System.out.println(obj.value()));
        } catch (Exception e) {
            LOGGER.error("Erro ao consumir mensagem do kafka {}", e.getMessage());
        }

    }
}
