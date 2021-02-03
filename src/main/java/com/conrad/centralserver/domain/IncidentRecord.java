package com.conrad.centralserver.domain;

import com.conrad.centralserver.dto.CarType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(of = "id")
@Builder
@Entity(name = "incident_records")
@NoArgsConstructor
@AllArgsConstructor
public class IncidentRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "incident_records_ids_gen")
    @SequenceGenerator(name = "incident_records_ids_gen", sequenceName = "incident_records_id_seq", allocationSize = 1)
    private BigInteger id;

    /*Unique incident uuid*/
    private UUID rqUuid;

    /*External camera or incident supplier uuid*/
    private UUID systemUuid;

    private String carLicense;

    private Integer actualSpeed;

    private Integer speedExceededBy;

    @Enumerated(EnumType.STRING)
    private CarType carType;

    private Timestamp incidentTimestamp;

    private LocalDateTime incidentRegistered;
}
