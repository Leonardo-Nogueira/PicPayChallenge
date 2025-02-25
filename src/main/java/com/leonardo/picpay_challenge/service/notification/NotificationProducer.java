package com.leonardo.picpay_challenge.service.notification;

import com.leonardo.picpay_challenge.domain.notification.Notification;
import com.leonardo.picpay_challenge.domain.transaction.Transaction;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationProducer {

    private final KafkaTemplate<String, Transaction> kafkaTemplate;

    public NotificationProducer(KafkaTemplate<String,Transaction> kafkaTemplate){
        this.kafkaTemplate=kafkaTemplate;
    }

    public void sendNotification(Transaction transaction){
        kafkaTemplate.send("transaction-notification", transaction);
    }
}
