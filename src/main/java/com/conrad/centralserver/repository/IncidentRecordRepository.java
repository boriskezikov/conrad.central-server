package com.conrad.centralserver.repository;

import com.conrad.centralserver.domain.IncidentRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface IncidentRecordRepository extends JpaRepository<IncidentRecord, BigInteger> {
}
