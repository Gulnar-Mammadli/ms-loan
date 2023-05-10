package com.group11.msloan.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfiguration {

    @Bean
    public NewTopic LoanTopicCreation(){
        return TopicBuilder.name("loanCreatedTopic")
                .build();
    }

    @Bean
    public NewTopic decisionTopicCreation(){
        return TopicBuilder.name("decisionTopic")
                .build();
    }
}
