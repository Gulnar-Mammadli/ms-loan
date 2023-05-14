package com.group11.msloan.service;

import com.group11.msloan.mapper.LoanMapper;
import com.group11.msloan.model.Loan;
import com.group11.msloan.model.dto.DecisionDto;
import com.group11.msloan.model.dto.LoanCreateDto;
import com.group11.msloan.model.dto.LoanDto;
import com.group11.msloan.model.enums.DecisionStatus;
import com.group11.msloan.model.enums.LoanType;
import com.group11.msloan.model.enums.Status;
import com.group11.msloan.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoanService {

    private final LoanRepository loanRepository;

    private final KafkaTemplate<String, LoanDto> kafkaTemplate;

    public Loan createLoan(LoanCreateDto loanCreateDto) {
        Optional<Loan> result = loanRepository.findByCustomerIdAndLoanTypeAndStatus(loanCreateDto.getCustomerId(), loanCreateDto.getLoanType(), Status.INITIALIZED);
        if (result.isEmpty()) {
            Loan loan = LoanMapper.INSTANCE.mapToLoan(loanCreateDto);
            loan.setCreatedAt(LocalDateTime.now());
            loan.setStatus(Status.INITIALIZED);
            return loanRepository.save(loan);
        }
        return null;
    }

    @KafkaListener(topics = "decisionTopic", groupId = "loanDecisionGroup",concurrency = "4")
    public void updateByDecisionInfo(DecisionDto decisionDto) {
        log.info("decision received {}  status updated", decisionDto.toString());

        Optional<Loan> result = loanRepository.findById(decisionDto.getLoanId());
        if (result.isPresent()) {
            Loan loan = result.get();
            loan.setUpdatedAt(LocalDateTime.now());

            int i = decisionDto.getDecidedAmount().compareTo(loan.getAmount());
            if (decisionDto.getDecisionStatus() == DecisionStatus.APPROVED && i == 0) {
                loan.setStatus(Status.ACCEPTED);
                loan.setDecidedAmount(decisionDto.getDecidedAmount());
            } else if (decisionDto.getDecisionStatus() == DecisionStatus.APPROVED && i <0) {
                loan.setStatus(Status.OFFERED);
                loan.setDecidedAmount(decisionDto.getDecidedAmount());

            } else if (decisionDto.getDecisionStatus() == DecisionStatus.APPROVED && i >0) {
                loan.setStatus(Status.OFFERED);
                loan.setDecidedAmount(loan.getAmount());

            } else if (decisionDto.getDecisionStatus() == DecisionStatus.REJECTED) {
                loan.setStatus(Status.REFUSED);

            }

            loanRepository.save(loan);
            log.info("Loan {}  status updated", loan.getId());
        }
    }

    public Loan updateLoan(LoanDto loanDto) {
        Optional<Loan> response = loanRepository.findByCustomerIdAndId(loanDto.getCustomerId(), loanDto.getId());
        if (response.isPresent()) {
            if (response.get().getStatus() == Status.INITIALIZED) {

                if (loanDto.getStatus() == Status.SUBMITTED) {
                    Loan loan = Loan.builder()
                            .id(loanDto.getId())
                            .customerId(loanDto.getCustomerId())
                            .loanType(loanDto.getLoanType())
                            .amount(loanDto.getAmount())
                            .term(loanDto.getTerm())
                            .status(Status.SUBMITTED)
                            .createdAt(response.get().getCreatedAt())
                            .updatedAt(LocalDateTime.now())
                            .build();

                    Loan result = loanRepository.save(loan);

                    loanDto.setStatus(Status.SUBMITTED);

                    // Push the loanDto to the loanCreatedTopic topic
                    kafkaTemplate.send("loanCreatedTopic", loanDto);
                    log.info("An loan id: {} has been updated and sent for decision", loan.getId());
                    return result;

                } else if (loanDto.getStatus() == Status.REFUSED) {
                    response.get().setStatus(Status.REFUSED);
                    response.get().setUpdatedAt(LocalDateTime.now());
                    return loanRepository.save(response.get());
                }

            } else if (response.get().getStatus() == Status.SUBMITTED) {
                if (loanDto.getStatus() == Status.REFUSED) {
                    response.get().setStatus(Status.REFUSED);
                    response.get().setUpdatedAt(LocalDateTime.now());
                    return loanRepository.save(response.get());
                }

            } else if (response.get().getStatus() == Status.REFUSED) {
                response.get().setUpdatedAt(LocalDateTime.now());
                return loanRepository.save(response.get());

            } else if (response.get().getStatus() == Status.OFFERED) {

                if (loanDto.getStatus() == Status.ACCEPTED || loanDto.getStatus() == Status.REFUSED) {
                    response.get().setStatus(loanDto.getStatus());
                    response.get().setUpdatedAt(LocalDateTime.now());
                    return loanRepository.save(response.get());

                }
            }
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
