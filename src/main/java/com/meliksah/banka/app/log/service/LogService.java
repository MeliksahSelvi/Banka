package com.meliksah.banka.app.log.service;

import com.meliksah.banka.app.kafka.dto.LogMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LogService {

    @Value("${banka.kafka.topic}")
    private String topic;

    private final KafkaTemplate<String, LogMessage> kafkaTemplate;

    /*
    * kafka message gönderdiğimiz method
    * */
    public void log(LogMessage logMessage) {

        System.out.println("starting to producer");
        String id = UUID.randomUUID().toString();

        kafkaTemplate.send(topic, id, logMessage);
        System.out.println("message sent from producer");
    }

}
