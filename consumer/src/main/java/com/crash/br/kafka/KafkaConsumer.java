package com.crash.br.kafka;


import com.crash.br.kafka.demo.Cliente;
import com.crash.br.service.BuscaClienteNoKafka;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    private static Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    private final BuscaClienteNoKafka buscaCliente;

    public KafkaConsumer(BuscaClienteNoKafka buscaCliente) {
        this.buscaCliente = buscaCliente;
    }

    @KafkaListener(topics = "kafka-demo", groupId = "group-1", containerFactory = "concurrentKafkaListenerContainerFactory")
    public void listener(@Payload ConsumerRecord<String, Cliente> payload, Acknowledgment acknowledgment) {
        try {
            Cliente cliente = payload.value();
            LOGGER.info("Buscando informações do cliente no consumidor do kafka {}", cliente);
            buscaCliente.buscaCliente(cliente);
            acknowledgment.acknowledge();
        } catch (Exception e) {
            LOGGER.error("Erro ao consumir mensagem do kafka {}", e.getMessage());
        }

    }
}
