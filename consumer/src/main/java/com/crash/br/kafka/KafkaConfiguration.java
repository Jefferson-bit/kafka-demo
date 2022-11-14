package com.crash.br.kafka;


import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;

import static org.springframework.kafka.listener.ContainerProperties.AckMode;

@EnableKafka
@Configuration
public class KafkaConfiguration {

    private final KafkaProperties kafkaProperties;

    public KafkaConfiguration(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        var configs = new HashMap<String, Object>();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "io.confluent.kafka.serializers.KafkaAvroDeserializer");
        configs.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, "65536");
        configs.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");
        configs.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, "group-1");
        configs.put(ConsumerConfig.CLIENT_ID_CONFIG, "group-1");
        configs.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, "true");
        configs.put("schema.registry.url", "http://localhost:8081");

        return new DefaultKafkaConsumerFactory<>(configs);
    }
    @Bean(name = "concurrentKafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(){
        var factory = new ConcurrentKafkaListenerContainerFactory<String, String>();
        factory.setConsumerFactory(consumerFactory());
        factory.getContainerProperties().setMissingTopicsFatal(false);
        factory.getContainerProperties().setAckMode(AckMode.MANUAL);
        factory.getContainerProperties().setSyncCommits(true);
        return factory;
    }

}

