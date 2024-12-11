package com.example.logsend;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.GregorianCalendar;
import java.util.Calendar;
@RestController
@RequiredArgsConstructor
public class FrontController {
    private final KafkaProducer kafkaProducer;


    @GetMapping("/health")
    public String health() {
        return "OK";
    }


    @GetMapping("/")
    public String index() {
        Calendar cal = new GregorianCalendar();
        kafkaProducer.sendTopic(cal.toString());
        return "MyWeb";
    }
}
