package com.conrad.centralserver.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(of = "id")
@Builder
@Entity(name = "penalty_approvals")
@NoArgsConstructor
@AllArgsConstructor
public class PenaltyApproval {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "penalty_approvals_ids_gen")
    @SequenceGenerator(name = "penalty_approvals_ids_gen", sequenceName = "penalty_approvals_id_seq", allocationSize = 1)
    private BigInteger id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "incident_id")
    private IncidentRecord incidentRecord;

    private boolean penaltyApproved;

    private BigInteger approvedBy;

    @UpdateTimestamp
    private LocalDateTime updated;

}
