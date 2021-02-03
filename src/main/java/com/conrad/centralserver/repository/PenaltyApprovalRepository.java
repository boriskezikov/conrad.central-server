package com.conrad.centralserver.repository;

import com.conrad.centralserver.domain.PenaltyApproval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface PenaltyApprovalRepository extends JpaRepository<PenaltyApproval, BigInteger> {
}
