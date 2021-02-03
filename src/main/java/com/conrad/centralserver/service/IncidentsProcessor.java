package com.conrad.centralserver.service;

import com.conrad.centralserver.domain.IncidentRecord;
import com.conrad.centralserver.domain.PenaltyApproval;
import com.conrad.centralserver.dto.IncidentRecordDto;
import com.conrad.centralserver.repository.PenaltyApprovalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class IncidentsProcessor {

    private final PenaltyApprovalRepository repository;

    @Value("${os_central.max_speed_allowed}")
    private Integer MAX_SPEED_ALLOWED;

    public void process(IncidentRecordDto dto, String rqUuid, String systemId) {
        if (dto.getActualSpeed() > MAX_SPEED_ALLOWED) {
            var speedOffset = dto.getActualSpeed() - MAX_SPEED_ALLOWED;
            IncidentRecord incidentRecord = IncidentRecord.builder()
                    .incidentRegistered(LocalDateTime.now())
                    .carLicense(dto.getCarNumber())
                    .actualSpeed(dto.getActualSpeed())
                    .speedExceededBy(speedOffset)
                    .rqUuid(UUID.fromString(rqUuid))
                    .incidentTimestamp(dto.getIncidentTimestamp())
                    .carType(dto.getCarType())
                    .systemUuid(UUID.fromString(systemId)).build();
            repository.save(PenaltyApproval.builder().incidentRecord(incidentRecord).build());
            log.info("core() - Incident {} registered", rqUuid);
        }
    }
}
