package com.conrad.centralserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IncidentRecordDto {

    @NotBlank
    private String carNumber;
    @NotNull
    private Integer actualSpeed;
    @NotNull
    private Timestamp incidentTimestamp;
    @NotNull
    private UUID cameraUuid;
    @NotNull
    private CarType carType;

}
