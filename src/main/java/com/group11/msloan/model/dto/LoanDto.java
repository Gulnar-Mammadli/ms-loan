package com.group11.msloan.model.dto;

import com.group11.msloan.model.enums.LoanType;
import com.group11.msloan.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanDto {

    private Long customerId;
    private Long documentId;
    private LoanType loanType;
    private BigDecimal amount;
    private BigDecimal paymentAmount;
    private String term;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private BigDecimal interestRate;
}
