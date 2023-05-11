package com.group11.msloan.repository;

import com.group11.msloan.model.Loan;
import com.group11.msloan.model.enums.LoanType;
import com.group11.msloan.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    Optional<Loan> findByCustomerIdAndLoanTypeAndStatus(Long customerId,LoanType loanType,Status status);
    Optional<Loan> findByCustomerIdAndId(Long customerId,Long id);

    Optional<List<Loan>> findAllByCustomerIdOrStatusOrLoanTypeOrAmountLessThanOrAmountGreaterThan(Long customerId,
                                                                                                  Status status,
                                                                                                  LoanType loanType,
                                                                                                  BigDecimal amount,
                                                                                                  BigDecimal quantity);
}

