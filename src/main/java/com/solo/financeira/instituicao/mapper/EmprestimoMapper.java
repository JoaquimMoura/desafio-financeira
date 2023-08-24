package com.solo.financeira.instituicao.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.solo.financeira.instituicao.domain.Emprestimo;
import com.solo.financeira.instituicao.repositories.filter.EmprestimoFilter;

@Mapper
public interface EmprestimoMapper {

	EmprestimoMapper INSTANCE = Mappers.getMapper(EmprestimoMapper.class);

	@Mappings({ 
		@Mapping(target = "parcelas", source = "parcelas"),
		@Mapping(target = "valorContratado", source = "valorDesejado"), 
		@Mapping(target = "valorParcela", source = "valorPago"), 
		@Mapping(target = "pessoa.identificador", source = "identificador")})
	Emprestimo toDomain(EmprestimoFilter filter);
}
