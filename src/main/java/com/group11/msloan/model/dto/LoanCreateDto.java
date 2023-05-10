package com.group11.msloan.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.group11.msloan.model.enums.LoanType;
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

    @JsonProperty("customer_id")
    private Long customerId;

    @JsonProperty("loan_type")
    private LoanType loanType;

    private BigDecimal amount;
    private String term;
}
