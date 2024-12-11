package com.example.logsend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class KafkaProducer {
    //토픽이름
    private static final String TOPIC = "log-topic";


    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;



    //토픽을 전송하는 메서드
    public void sendTopic(String timestamp){
        StringBuilder sb = new StringBuilder("time:");
        sb.append(timestamp);
        kafkaTemplate.send(TOPIC, sb.toString());
    }
}


