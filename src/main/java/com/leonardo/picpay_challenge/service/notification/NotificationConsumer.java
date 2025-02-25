package com.leonardo.picpay_challenge.service.notification;

import com.leonardo.picpay_challenge.domain.notification.Notification;
import com.leonardo.picpay_challenge.domain.transaction.Transaction;
import com.leonardo.picpay_challenge.exception.NotificationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Slf4j
@Service
public class NotificationConsumer {

    private RestClient restClient;

    @Value("${rest.baseUrl}")
    private String baseUrl;

    public NotificationConsumer(RestClient.Builder builder) {
        this.restClient = builder.baseUrl(
                        "https://run.mocky.io/v3/ff61c66d-caef-49e2-98d4-b842f0da4b91")
                .build();
    }

    @KafkaListener(topics = "${spring.kafka.topicNotification}", groupId = "${spring.kafka.groupId}")
    public void receiveNotification(Transaction transaction){
        log.info("Active Notification {}",transaction);

        var response = restClient.get().retrieve().toEntity(Notification.class);

        if(response.getStatusCode().isError() || !response.getBody().message()){
            throw new NotificationException("Error sending notification");
        }
        log.info("Notification received {}",transaction);

    }




}
