package com.group11.msloan.service;

import com.group11.msloan.mapper.LoanMapper;
import com.group11.msloan.model.Loan;
import com.group11.msloan.model.dto.LoanCreateDto;
import com.group11.msloan.model.enums.Status;
import com.group11.msloan.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;

    public Loan createLoan(LoanCreateDto loanDto) {

        Optional<Loan> result = loanRepository.findByCustomerIdAndDocumentId(loanDto.getCustomerId(), loanDto.getDocumentId());
        if (result.isEmpty()) {
            Loan loan = LoanMapper.INSTANCE.mapToCustomer(loanDto);
            loan.setCreatedAt(LocalDateTime.now());
            loan.setStatus(Status.INITIALIZED);
            return loanRepository.save(loan);
        }
        return null;
    }

    public Loan getLoan(Long customerId, Long documentId) {
        Optional<Loan> loan = loanRepository.findByCustomerIdAndDocumentId(customerId, documentId);
        return loan.orElse(null);
    }
}
