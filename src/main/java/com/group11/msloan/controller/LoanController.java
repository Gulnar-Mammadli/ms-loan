package com.group11.msloan.controller;

import com.group11.msloan.model.Loan;
import com.group11.msloan.model.dto.LoanCreateDto;
import com.group11.msloan.model.dto.LoanUpdateDto;
import com.group11.msloan.model.enums.LoanType;
import com.group11.msloan.model.enums.Status;
import com.group11.msloan.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @PostMapping
    Loan createLoan(@RequestBody LoanCreateDto loanDto){
        return loanService.createLoan(loanDto);
    }


    @PutMapping("/{loanId}")
    Loan updateLoan(@RequestBody LoanUpdateDto dto, @PathVariable Long loanId){
        return loanService.updateLoan(dto,loanId);
    }

    @GetMapping("/{loanId}")
    Loan getLoanById(@PathVariable Long loanId){
        return loanService.getLoanById(loanId);
    }

    @GetMapping()
    List<Loan> getLoansByCustomer(@RequestParam(name = "customerId",required = false) Long customerId,
                                  @RequestParam(name = "loanType",required = false) LoanType loanType,
                                  @RequestParam(name = "status",required = false) Status status,
                                  @RequestParam(name = "amount",required = false) BigDecimal amount){
        return loanService.getLoansByCustomer(customerId,loanType,status,amount);
    }

}
