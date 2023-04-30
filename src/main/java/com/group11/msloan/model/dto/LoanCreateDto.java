package com.group11.msloan.model.dto;

import com.group11.msloan.model.enums.LoanType;
import com.group11.msloan.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanCreateDto {

    private Long customerId;
    private Long documentId;
    private LoanType loanType;
    private BigDecimal amount;
    private BigDecimal paymentAmount;
    private String term;
    private Status status;
    private BigDecimal interestRate;
}
