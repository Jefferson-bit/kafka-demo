package com.crash.br.kafka;


import com.crash.br.kafka.demo.Cliente;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.RecordInterceptor;
import org.springframework.kafka.support.converter.BatchMessagingMessageConverter;
import org.springframework.kafka.support.converter.JsonMessageConverter;

import java.util.HashMap;


@EnableKafka
@Configuration
public class KafkaConfiguration {

    private final KafkaProperties kafkaProperties;
    private static Logger LOGGER = LoggerFactory.getLogger(KafkaConfiguration.class);

    public KafkaConfiguration(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        var configs = new HashMap<String, Object>();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "io.confluent.kafka.serializers.KafkaAvroDeserializer");
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, "group-1");
        configs.put(ConsumerConfig.CLIENT_ID_CONFIG, "group-1");
        configs.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, "true");
        configs.put("schema.registry.url", "http://localhost:8081");

        return new DefaultKafkaConsumerFactory<>(configs);
    }

    @Bean(name = "concurrentKafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, Object>();
        factory.setConsumerFactory(consumerFactory());
        factory.setMessageConverter(new BatchMessagingMessageConverter(new JsonMessageConverter()));
        factory.setBatchListener(true);
        factory.setRecordInterceptor(recordInterceptor());
        return factory;
    }

    private RecordInterceptor<String, Object> recordInterceptor() {
        return record -> {
            LOGGER.info("Record {}", record);
            if (record.value() instanceof Cliente payload) {
                System.out.println("Dentro do instance of");
                if (payload.getIdade() >= 18) {
                    System.out.println("Dentro da condicional");
                    return record;
                }
            }
            return null;
        };

    }
}

