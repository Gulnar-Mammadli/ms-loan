package com.group11.msloan.mapper;


import com.group11.msloan.model.Loan;
import com.group11.msloan.model.dto.LoanCreateDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LoanMapper {

    LoanMapper INSTANCE = Mappers.getMapper(LoanMapper.class);
    Loan mapToCustomer(LoanCreateDto loanCreateDto);
}


