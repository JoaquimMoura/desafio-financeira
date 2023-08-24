package com.solo.financeira.instituicao.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;
import org.hibernate.validator.group.GroupSequenceProvider;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.solo.financeira.instituicao.domain.enumerations.TipoIdentificadorEnum;
import com.solo.financeira.instituicao.domain.validation.PessoaGroupSequenceProvider;
import com.solo.financeira.instituicao.domain.validation.group.PessoaFisica;
import com.solo.financeira.instituicao.domain.validation.group.PessoaJuridica;

import lombok.Data;

/**
 * @author Joaquim de Castro
 *
 */
@Entity
@Table(name = "tb_pessoa")
@Data
@GroupSequenceProvider(value = PessoaGroupSequenceProvider.class)
public class Pessoa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(max = 50, message = "Só é pertido 50 caracteres")
	@NotNull(message = "Nome é obrigatório")
	@Column(name = "nome")
	private String nome;

	@CPF(groups = PessoaFisica.class)
	@CNPJ(groups = PessoaJuridica.class)
	@NotNull(message = "Identificador é obrigatório")
	@Size(max = 50, message = "Só é pertido 50 caracteres")
	@Column(name = "identificador", unique = true)
	private String identificador;

	@NotNull(message = "Data de Nascimento é obrigatório")
	@Column(name = "data_nascimento")
	private String dataNascimento;

	@NotNull(message = "Tipo Identificador é obrigatório")
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_identificador")
	private TipoIdentificadorEnum tipoIdentificador;

	@Digits(integer = 18, fraction = 2)
	@NotNull(message = "Valor minimo é obrigatório")
	@Column(name = "valor_min_mensal")
	private BigDecimal vlMinimo;

	@Digits(integer = 18, fraction = 2)
	@NotNull(message = "Valor maximo de emprestimo é obrigatório")
	@Column(name = "valor_max_emprestimo")
	private BigDecimal vlMaximo;

	@OneToMany(mappedBy = "pessoa", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Emprestimo> emprestimo;

	@JsonIgnore
	public String getSemFormatacao() {
		return TipoIdentificadorEnum.removerFormatacao(this.identificador);
	}

	@PrePersist
	@PreUpdate
	private void prePersistPreUpdate() {
		this.identificador = getSemFormatacao();
	}

	@PostLoad
	private void postLoad() {
		this.identificador = this.tipoIdentificador.formatar(this.identificador);
	}

	public static Pessoa tipoIdentificador(Pessoa pessoa, Integer length) {

		switch (length) {

		case 11:
			pessoa.setTipoIdentificador(TipoIdentificadorEnum.PF);
			pessoa.setVlMinimo(new BigDecimal("300.00"));
			pessoa.setVlMaximo(new BigDecimal("10000.00"));
			break;

		case 14:
			pessoa.setTipoIdentificador(TipoIdentificadorEnum.PJ);
			pessoa.setVlMinimo(new BigDecimal("1000.00"));
			pessoa.setVlMaximo(new BigDecimal("100000.00"));
			break;
		case 8:
			pessoa.setTipoIdentificador(TipoIdentificadorEnum.EU);
			pessoa.setVlMinimo(new BigDecimal("100.00"));
			pessoa.setVlMaximo(new BigDecimal("10000.00"));
			break;
		case 10:
			pessoa.setTipoIdentificador(TipoIdentificadorEnum.AP);
			pessoa.setVlMinimo(new BigDecimal("400.00"));
			pessoa.setVlMaximo(new BigDecimal("25000.00"));
			break;

		default:
			break;
		}
		return pessoa;
	}

	public static String formataDados(String dado) {
		return dado.replaceAll("[^0-9]+", "");
	}

}
