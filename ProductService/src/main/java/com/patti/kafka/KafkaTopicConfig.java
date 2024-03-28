package com.patti.kafka;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class KafkaTopicConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }
/*
    @Bean
    public NewTopic topic1() {
        return new NewTopic("notification", 1, (short) 1);
    }*/

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapAddress);
        configProps.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        configProps.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        DefaultKafkaProducerFactory factory = (DefaultKafkaProducerFactory) producerFactory();
        factory.setTransactionIdPrefix(UUID.randomUUID().toString());
        KafkaTemplate kafkaTemplate =  new KafkaTemplate<>(factory);
        return kafkaTemplate;
    }


    @Bean
    public ThreadPoolExecutor kafkaExecutor(){
        ThreadPoolExecutor k = new ThreadPoolExecutor(4,4,0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(100));
        k.setRejectedExecutionHandler(new RejectedExecutionHandler() {

            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                executor.getMaximumPoolSize();
            }
        });
        return k;
    }
}
