package com.meliksah.banka.app.kafka.producer;

import com.meliksah.banka.app.kafka.dto.LogMessage;
import com.meliksah.banka.app.log.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("kafka-messages")
@RequiredArgsConstructor
public class KafkaMessageController {

    private final LogService logService;

    @PostMapping
    public void sendMessage(@RequestBody LogMessage logMessage) {
        logService.log(logMessage);
    }
}
