package com.crash.br.kafka;


import com.crash.br.kafka.demo.Cliente;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.BatchInterceptor;
import org.springframework.kafka.listener.RecordInterceptor;

import java.util.HashMap;

import static org.springframework.kafka.listener.ContainerProperties.AckMode;

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
        configs.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, "65536");
        configs.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");
        configs.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, "group-1");
        configs.put(ConsumerConfig.CLIENT_ID_CONFIG, "group-1");
        configs.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, "true");
        configs.put("schema.registry.url", "http://localhost:8081");
        return new DefaultKafkaConsumerFactory<>(configs);
    }

    @Bean
    public ConsumerFactory<String, Object> consumerFactoryAtMostOnce() {
        var configs = new HashMap<String, Object>();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "io.confluent.kafka.serializers.KafkaAvroDeserializer");
        configs.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        configs.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "101");
        configs.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, "135");
        configs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
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
        factory.getContainerProperties().setMissingTopicsFatal(false);
        factory.getContainerProperties().setAckMode(AckMode.MANUAL);
        factory.getContainerProperties().setSyncCommits(true);
        factory.setRecordInterceptor(recordInterceptor());
        return factory;
    }

    private RecordInterceptor<String, Object> recordInterceptor() {
        return new RecordInterceptor<>() {
            @Override
            public ConsumerRecord<String, Object> intercept(ConsumerRecord<String, Object> record) {
                if (record.value() instanceof Cliente payload) {
                    if (payload.getIdade() >= 18) {
                        return record;
                    }
                }
                return null;
            }

            @Override
            public void success(ConsumerRecord<String, Object> record, Consumer<String, Object> consumer) {
                LOGGER.info("Sucesso ao consumir mensagem do topico kafka {}", record.value());
            }

            @Override
            public void failure(ConsumerRecord<String, Object> record, Exception exception, Consumer<String, Object> consumer) {
                LOGGER.error("Falha ao consumir mensagem {}", exception, exception.getCause());
            }
        };
    }
}

