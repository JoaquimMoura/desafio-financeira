package com.solo.financeira.instituicao.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.solo.financeira.instituicao.domain.Pessoa;
import com.solo.financeira.instituicao.repositories.filter.PessoaFilter;

@Mapper
public interface PessoaMapper {

	PessoaMapper INSTANCE = Mappers.getMapper(PessoaMapper.class);

	@Mappings({ @Mapping(target = "nome", source = "nome") })
	Pessoa toDomain(PessoaFilter filter);

}
