package com.group11.msloan.repository;

import com.group11.msloan.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanRepository  extends JpaRepository<Loan,Long> {
Optional<Loan> findByCustomerIdAndDocumentId(Long customerId, Long DocumentId);
}
