package com.leonardo.picpay_challenge;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;
import org.springframework.kafka.config.TopicBuilder;

@EnableJdbcAuditing
@SpringBootApplication
public class PicpayChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(PicpayChallengeApplication.class, args);
	}


	@Bean
	NewTopic notificationTopic(){
		return TopicBuilder.name("${spring.kafka.topicNotification}").build();
	}
}
