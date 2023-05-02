package com.group11.msloan.model.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.group11.msloan.model.enums.LoanType;
import com.group11.msloan.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LoanUpdateDto {

    @JsonProperty("customer_id")
    private Long customerId;

    @JsonProperty("loan_type")
    private LoanType loanType;

    private BigDecimal amount;
    private String term;
    private Status status;
}
