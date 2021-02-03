package com.conrad.centralserver.service;

import com.conrad.centralserver.domain.PenaltyApproval;
import com.conrad.centralserver.repository.PenaltyApprovalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PenaltyService {

    private final PenaltyApprovalRepository penaltyApprovalRepository;

    public void approvePenalty(BigInteger penaltyId, BigInteger approvedBy) {
        penaltyApprovalRepository.findById(penaltyId).ifPresent(incident -> {
            incident.setPenaltyApproved(true);
            incident.setApprovedBy(approvedBy);
            penaltyApprovalRepository.save(incident);
        });
    }

    public List<PenaltyApproval> findAll() {
        return penaltyApprovalRepository.findAll();
    }
}
