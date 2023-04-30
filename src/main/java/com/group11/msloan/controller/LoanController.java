package com.group11.msloan.controller;

import com.group11.msloan.model.Loan;
import com.group11.msloan.model.dto.LoanCreateDto;
import com.group11.msloan.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @PostMapping
    Loan createLoan(@RequestBody LoanCreateDto loanCreateDto){
        return loanService.createLoan(loanCreateDto);
    }

    @GetMapping("/customerId/documentId/{customerId}/{documentId}")
    Loan getLoan(@PathVariable Long customerId, @PathVariable Long documentId){
        return loanService.getLoan(customerId,documentId);
    }
}
