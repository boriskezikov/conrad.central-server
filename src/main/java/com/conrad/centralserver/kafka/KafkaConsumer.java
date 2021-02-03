package com.conrad.centralserver.kafka;

import com.conrad.centralserver.dto.IncidentRecordDto;
import com.conrad.centralserver.service.IncidentsProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import static com.conrad.centralserver.Utils.RQ_UUID_HEADER;
import static com.conrad.centralserver.Utils.SYSTEM_ID_HEADER;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final IncidentsProcessor processor;

    @KafkaListener(topics = "${os_central.kafka.incidents_topic}", containerFactory = "kafkaMainListenerContainerFactory")
    public void receive(@Validated @Payload IncidentRecordDto dto,
                        @Header(RQ_UUID_HEADER) String rqUuid,
                        @Header(SYSTEM_ID_HEADER) String systemId) {
        log.debug("core() - consuming message from MAIN topic. RqUUID: {}", rqUuid);
        processor.process(dto, rqUuid, systemId);
    }

}
