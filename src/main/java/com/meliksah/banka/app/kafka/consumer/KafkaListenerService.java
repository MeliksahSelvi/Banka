package com.meliksah.banka.app.kafka.consumer;

import com.meliksah.banka.app.kafka.dto.LogMessage;
import com.meliksah.banka.app.log.entity.LogDetail;
import com.meliksah.banka.app.log.mapper.LogMapper;
import com.meliksah.banka.app.log.service.entityservice.LogDetailEntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaListenerService {

    private final LogDetailEntityService logDetailEntityService;

    /*
    * gönderilen kafka message'lerini karşıladığımız yer.
    * */
    @KafkaListener(
            topics = "${banka.kafka.topic}",
            groupId = "${banka.kafka.group-id}"
    )
    public void listen(@Payload LogMessage logMessage) {
        log.info("Message received by consumer --> " + logMessage.getMessage());

        //tüm kafka loglarını dbye kaydediyoruz.
        saveLogToDb(logMessage);
    }

    /*
    * method sonlandığında transactional bitecek ve kayıt atmayacak.
    * */
    @Transactional
    public void saveLogToDb(LogMessage logMessage) {
        LogDetail logDetail = LogMapper.INSTANCE.LogMessagetoLogDetail(logMessage);
        logDetail = logDetailEntityService.save(logDetail);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("===============================");
        System.out.println("end");
    }
}
