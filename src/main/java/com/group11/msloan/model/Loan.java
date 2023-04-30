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

    private Long customerId;
    private Long documentId;

    @Enumerated(EnumType.STRING)
    private LoanType loanType;

    private BigDecimal amount;
    private BigDecimal paymentAmount;
    private String term;

    @Enumerated(EnumType.STRING)
    private Status status;

    @JsonProperty("created_at")
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @JsonProperty("interest_rate")
    @Column(name = "interest_rate")
    private BigDecimal interestRate;

}
