package com.conrad.centralserver.controller;

import com.conrad.centralserver.domain.IncidentRecord;
import com.conrad.centralserver.domain.PenaltyApproval;
import com.conrad.centralserver.dto.CarType;
import com.conrad.centralserver.dto.IncidentRecordDto;
import com.conrad.centralserver.repository.IncidentRecordRepository;
import com.conrad.centralserver.repository.PenaltyApprovalRepository;
import com.conrad.centralserver.service.IncidentsProcessor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWebMvc
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Resource
    private IncidentsProcessor incidentsProcessor;

    @Resource
    private IncidentRecordRepository recordRepository;

    @Resource
    private PenaltyApprovalRepository penaltyApprovalRepository;


    @BeforeEach
    public void beforeEach() {
        var res = Stream.generate(() ->
                PenaltyApproval.builder()
                        .incidentRecord(IncidentRecord.builder()
                                .carType(CarType.values()[(int) (Math.random() * CarType.values().length)])
                                .actualSpeed(100)
                                .incidentRegistered(LocalDateTime.now())
                                .incidentTimestamp(Timestamp.valueOf("2021-02-02 00:00:00"))
                                .speedExceededBy(20)
                                .rqUuid(UUID.randomUUID())
                                .carLicense("test")
                                .systemUuid(UUID.randomUUID())
                                .build())
                        .build()).limit(5).collect(toList());
        penaltyApprovalRepository.saveAll(res);
    }

    @Test
    void incidentsProcessorIT() {
        Stream.generate(() -> IncidentRecordDto.builder()
                .carType(CarType.values()[(int) (Math.random() * CarType.values().length)])
                .actualSpeed(85)
                .cameraUuid(UUID.randomUUID())
                .incidentTimestamp(Timestamp.valueOf("2021-02-02 00:00:00"))
                .carNumber("test11")
                .build()).limit(5).forEach(ac ->
                incidentsProcessor.process(ac, UUID.randomUUID().toString(), UUID.randomUUID().toString()));

        var res = recordRepository.findAll();
        assertEquals(10, res.size());
    }

    @Test
    void approvalControllerIT() throws Exception {
        mvc.perform(
                get("/api/penalty/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)));
    }

    @Test
    void approvePenalty() throws Exception {
        mvc.perform(
                post("/api/penalty/approve")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("1")
                        .header("user_id", "224"))
                .andDo(print())
                .andExpect(status().isOk());
        var obj = penaltyApprovalRepository.findById(BigInteger.ONE).get();
        assertTrue(obj.isPenaltyApproved());
    }
}