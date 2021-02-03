package com.conrad.centralserver.controller;

import com.conrad.centralserver.domain.PenaltyApproval;
import com.conrad.centralserver.service.PenaltyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("api/penalty")
@RequiredArgsConstructor
public class ApprovalController {

    private final PenaltyService service;

    @GetMapping
    public List<PenaltyApproval> loadRecords() {
        return service.findAll();
    }

    @PostMapping("/approve")
    public void approvePenalty(@RequestBody BigInteger penaltyId, @RequestHeader("user_id") BigInteger userId) {
        service.approvePenalty(penaltyId, userId);
    }

}
