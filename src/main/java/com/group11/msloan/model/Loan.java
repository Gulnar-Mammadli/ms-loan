package com.group11.msloan.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.group11.msloan.model.enums.LoanType;
import com.group11.msloan.model.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "loans")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("customer_id")
    @Column(nullable = false,name = "customer_id")
    private Long customerId;

    @Enumerated(EnumType.STRING)
    @JsonProperty("loan_type")
    @Column(nullable = false,name = "loan_type")
    private LoanType loanType;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String term;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @JsonProperty("decided_amount")
    @Column(name = "decided_amount")
    private BigDecimal decidedAmount;

    @JsonProperty("created_at")
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
