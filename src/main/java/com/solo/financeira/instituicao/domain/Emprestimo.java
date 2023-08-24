package com.solo.financeira.instituicao.domain;

import static java.lang.Integer.valueOf;
import static org.apache.commons.lang3.StringUtils.isNumeric;
import static org.apache.commons.lang3.StringUtils.left;
import static org.apache.commons.lang3.StringUtils.right;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.solo.financeira.instituicao.domain.enumerations.StatusPagamentoEnum;

import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.CPFValidator;
import lombok.Data;

/**
 * @author Joaquim de Castro
 *
 */
@Entity
@Table(name = "tb_emprestimo")
@Data
public class Emprestimo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "Valor do empretimo é obrigatório")
	@Column(name = "valor_contratado")
	private BigDecimal valorContratado;

	@GeneratedValue
	private BigDecimal juros;

	@NotNull(message = "Numeros de parcelas é obrigatório")
	private Long parcelas;

	@NotNull(message = "Numeros de Quantide de Parcelas é obrigatório")
	private Long qtdParcelas;

	@GeneratedValue
	private BigDecimal valorTotal;

	@GeneratedValue
	private BigDecimal valorParcela;

	@NotNull(message = "Status pagamento é obrigatório")
	private StatusPagamentoEnum status;

	@NotNull(message = "Data do emprestimo é obrigatório")
	private String dataEmprestimo;
	
	private String dataPagamento;

	@JsonIgnore
	@ManyToOne(optional = true)
	@JoinColumn(name = "pessoa_id", referencedColumnName = "id", nullable = true)
	private Pessoa pessoa;

	public static boolean validaCpf(String numero) {

		var cpf = new CPFValidator();

		var erros = cpf.invalidMessagesFor(numero);

		if (erros.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean validaCnpj(String numero) {

		var cnpj = new CNPJValidator();

		var erros = cnpj.invalidMessagesFor(numero);

		if (erros.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	public static Boolean validaDadosEstudante(String identificador) {

		var tamanho = identificador.length();

		if (tamanho == 8) {

			var firstChar = left(identificador, 1);

			if (firstChar != null) {

				var lastChar = right(identificador, 1);

				if (isNumeric(firstChar) && isNumeric(lastChar)) {
					var base = valueOf(firstChar);
					var altura = valueOf(lastChar);

					base = base += altura;

					if (base == 9) {
						return true;
					}
				}
			}

		}
		return false;
	}

	public static Boolean validaDadosAposentado(String identificador) {

		var tamanho = identificador.length();

		if (tamanho == 10) {

			String lastChar = right(identificador, 1);

			if (lastChar != null) {

				var nineFisrtChar = identificador.substring(0, 8);

				if (nineFisrtChar.contains(lastChar)) {
					return false;
				}
			}

		}
		return true;
	}

}
