package com.conrad.centralserver.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.ErrorHandler;

@Slf4j
@RequiredArgsConstructor
public class KafkaErrorHandler implements ErrorHandler {


    @Override
    public void handle(Exception thrownException, ConsumerRecord<?, ?> data) {
        log.error("Error while processing record occurred: {} ", thrownException.getMessage());
    }
}
