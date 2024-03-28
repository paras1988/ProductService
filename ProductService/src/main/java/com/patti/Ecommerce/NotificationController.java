package com.patti.Ecommerce;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

@RestController
@RequestMapping("/notify")
public class NotificationController {
    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;

    @Autowired
    ThreadPoolExecutor kafkaExecutor;

    @GetMapping("/send/{message}")
    public void sendNotification(@PathVariable("message") String message) {
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send("mytopic", message);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("Sent message=[" + message +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                System.out.println("Unable to send message=[" +
                        message + "] due to : " + ex.getMessage());
            }
        });
    }

    @GetMapping("/sendBulkWithTransaction/{count}")
    public void sendBulKNotification(@PathVariable("count") String count) {
        kafkaExecutor.submit(new Runnable() {
            @Override
            public void run() {
                kafkaTemplate.executeInTransaction(t -> {
                    System.out.println("Inside executeInTransaction");
                    try {
                        List<CompletableFuture<SendResult<String, String>>> futures = new ArrayList<>();
                        for (int i = 0; i < Integer.parseInt(count); i++) {
                            ProducerRecord<String,String> producerRecord = new ProducerRecord("mytopic", "jj"+i, "00"+i);
                            CompletableFuture<SendResult<String, String>> future = t.send(producerRecord);
                            futures.add(future);
                            Thread.sleep(1000);
                        }
                        for(CompletableFuture<SendResult<String, String>> future:futures){
                            future.whenComplete((result, ex) -> {
                                try {
                                if (ex == null) {
                                        System.out.println("Sent message=[" + future.get().getProducerRecord().key() +
                                                "] with offset=[" + future.get().getRecordMetadata().offset() + "]");

                                } else {
                                    System.out.println("Unable to send message=[" +
                                            future.get().getProducerRecord().key() + "] due to : " + ex.getMessage());
                                }
                                }  catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            });
                        }
                    } catch (Exception e) {
                        System.out.println("Exception: "+e);
                        throw new RuntimeException(e);
                    }
                    System.out.println("Returning true ");
                    return true;
                });
                System.out.println("Published All great: ");
            }
        });
    }


    @GetMapping("/sendWithHeader/{message}")
    public void sendWithHeader(@PathVariable("message") String message) {

        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send("mytopic", message);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("Sent message=[" + message +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                System.out.println("Unable to send message=[" +
                        message + "] due to : " + ex.getMessage());
            }
        });
    }

}
