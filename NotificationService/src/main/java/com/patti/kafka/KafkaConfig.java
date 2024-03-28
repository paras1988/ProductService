package com.patti.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@EnableKafka
@Configuration
public class KafkaConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;


    @Bean
    KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(10);
        factory.getContainerProperties().setPollTimeout(3000);

        /*
        AckMode.MANUAL_IMMEDIATE will commit the offsets to kafka immediately, without waiting for any
        other kind of events to occur.

        But AckMode.MANUAL will work similar to AckMode.BATCH, which means after the acknowledge() method
        is called on a message, the system will wait till all the messages received by the poll() method have
        been acknowledged. This could take a long time, depending on your setup.
         */
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);

        factory.getContainerProperties().setSyncCommits(true);
        return factory;
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> propsMap = new HashMap();
        propsMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapAddress);
        propsMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        propsMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        /*
        Disabling the auto-commit feature to test the manual commit method.
         */
        propsMap.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        propsMap.put(ConsumerConfig.GROUP_ID_CONFIG, "Test-Consumer-Group");
        return propsMap;
    }


    /**
     * The Acknowledgment object injected into this method is used to "acknowledge"
     * that the consumer/listener received and processed a message on the specified
     * kafka topic.`
     *
     * @param consumerRecord The ConsumerRecord<> object injected
     * @param acknowledgment The Acknowledgement object injected
     */
    @KafkaListener(topics = "mytopic")
    public void listenGroupFoo(ConsumerRecord<?, ?> consumerRecord, Acknowledgment acknowledgment) {
        String message = consumerRecord.value().toString();
          /*
        Declaring a flag to decide if offsets have to be committed.
         */
        boolean commitOffsets = false;

          /*
        Looping till the flag becomes true.
         */
        while (!commitOffsets) {
            /*
            Calling the handleMessage() method to process the message received from Kafka.
             */
            try {
                handleMessage(consumerRecord.offset());

                commitOffsets = true;
            } catch (Exception e) {
                System.out.println("Exception caught. Not committing offset to Kafka.");
                commitOffsets = false;
            }
        }
          /*
        If the flag is set, committing the offsets.
         */
        if (commitOffsets) {
            System.out.println("No exceptions, committing offsets.");
            /*
            Committing the offset to Kafka.
             */
            acknowledgment.acknowledge();
        }
    }

    private void handleMessage(long offset) throws Exception {

        /*
        Printing a random message.
         */
        System.out.println("Busy handling message!");

        /*
        Processing the message => calculating the length of the String.
         */
        //int messageLength = message.length();
        System.out.println("Message offset: " + offset);
         /*
        Generating a random integer. If the integer is odd, a CustomException will be
        thrown, to see if the offset will be committed. If not, how will this be handled?
         */
        Random random = new Random();
        int randomNumber = random.nextInt(100);

        System.out.println("Random number: " + randomNumber);
        if ((randomNumber % 2) != 0) {
            throw new Exception("Odd number generated, so throwing this exception for offset: "+offset);
        }

        System.out.println("Even number generated, committing offsets for offset: "+offset);
    }




}
