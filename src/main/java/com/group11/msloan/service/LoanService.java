package com.group11.msloan.service;

import com.group11.msloan.mapper.LoanMapper;
import com.group11.msloan.model.Loan;
import com.group11.msloan.model.dto.LoanDto;
import com.group11.msloan.model.dto.LoanUpdateDto;
import com.group11.msloan.model.enums.LoanType;
import com.group11.msloan.model.enums.Status;
import com.group11.msloan.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;


    //    TODO submit statusu nezere alinmalimi?
    public Loan createLoan(LoanDto loanDto) {
        Optional<Loan> result = loanRepository.findByCustomerIdAndLoanTypeAndStatus(loanDto.getCustomerId(), loanDto.getLoanType(), Status.INITIALIZED);
        if (result.isEmpty()) {
            Loan loan = LoanMapper.INSTANCE.mapToLoan(loanDto);
            loan.setCreatedAt(LocalDateTime.now());
            loan.setStatus(Status.INITIALIZED);
            return loanRepository.save(loan);
        }
        return null;
    }

    //TODO
    public Loan updateLoan(LoanUpdateDto dto, Long loanId) {
        Optional<Loan> result = loanRepository.findAllByCustomerIdAndId(dto.getCustomerId(), loanId);
        if (result.isPresent()) {

            if (result.get().getStatus() == Status.INITIALIZED) {

//                loan type deyismek olar o vaxtki o typeda initizalized basqasi yoxdu
                Loan loan = LoanMapper.INSTANCE.mapFromLoanUpdateDto(dto);
                loan.setId(loanId);
                loan.setCreatedAt(result.get().getCreatedAt());
                loan.setUpdatedAt(LocalDateTime.now());
                if (loan.getStatus() == Status.SUBMITTED || loan.getStatus() == Status.REFUSED) {
                    loan.setUpdatedAt(LocalDateTime.now());
                } else if (loan.getStatus() == Status.OFFERED) {
                    loan.setStatus(result.get().getStatus());
                } else if (loan.getStatus() == Status.ACCEPTED) {
                    loan.setStatus(result.get().getStatus());
                }
                return loanRepository.save(loan);

            } else if (result.get().getStatus() == Status.SUBMITTED) {

                if (dto.getStatus() == Status.REFUSED || dto.getStatus() == Status.OFFERED || dto.getStatus() == Status.ACCEPTED) {
                    result.get().setStatus(dto.getStatus());
                    result.get().setUpdatedAt(LocalDateTime.now());
                    return result.get();
                }

            } else if (result.get().getStatus() == Status.OFFERED) {

                if (dto.getStatus() == Status.ACCEPTED || dto.getStatus() == Status.REFUSED) {
                    result.get().setStatus(dto.getStatus());
                    result.get().setUpdatedAt(LocalDateTime.now());
                    return result.get();
                }}
        }
        return null;
    }

    public List<Loan> getLoansByCustomer(Long customerId, LoanType loanType, Status status, BigDecimal amount) {
        Optional<List<Loan>> result = loanRepository.findAllByCustomerIdOrStatusOrLoanTypeOrAmountLessThanOrAmountGreaterThan(
                customerId, status, loanType, amount, amount);
        return result.orElse(null);
    }

    public Loan getLoanById(Long loanId) {
        Optional<Loan> result = loanRepository.findById(loanId);
        return result.orElse(null);
    }
}
