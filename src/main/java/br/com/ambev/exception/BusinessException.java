package br.com.ambev.exception;

import java.text.MessageFormat;

import br.com.ambev.enumerator.MensagemErroEnum;
import br.com.ambev.enumerator.StatusProcessamento;
import lombok.Getter;

public class BusinessException extends Exception {

	private static final long serialVersionUID = 1L;

	public static final String COD_ERRO_INESPERADO = "ERR9999";
	public static final String PARAMETROS_INCORRETOS = "ERR9998";

	@Getter
	protected StatusProcessamento codStatus;
	@Getter
	protected String codErro;
	@Getter
	protected String msgErro;

	protected BusinessException(final StatusProcessamento codStatus, final String codErro, final String msgErro,
			final Throwable cause) {
		super(msgErro, cause);
		this.codStatus = codStatus;
		this.codErro = codErro;
		this.msgErro = msgErro;
	}

	protected BusinessException(final StatusProcessamento codStatus, final String codErro, final String msgErro) {
		super(msgErro);
		this.codStatus = codStatus;
		this.codErro = codErro;
		this.msgErro = msgErro;
	}

	protected BusinessException(final String codErro, final String msgErro, final Throwable cause) {
		this(StatusProcessamento.EP, codErro, msgErro, cause);
	}

	protected BusinessException(final String codErro, final String msgErro) {
		this(StatusProcessamento.EP, codErro, msgErro);
	}

	public BusinessException(MensagemErroEnum mensagemErroEnum, Object... param) {
		this(mensagemErroEnum.getErrorCode(), MessageFormat.format(mensagemErroEnum.getMsg(), param));
	}

	public BusinessException(MensagemErroEnum mensagemErroEnum) {
		this(mensagemErroEnum.getErrorCode(), mensagemErroEnum.getMsg());
	}

	public BusinessException(MensagemErroEnum mensagemErroEnum, Throwable e) {
		this(mensagemErroEnum.getErrorCode(), MessageFormat.format(mensagemErroEnum.getMsg(), e.toString()), e);
	}

	/**
	 * Erro a partir de uma exceção com mensagem parametrizada
	 */
	public static BusinessException ofException(Throwable e, MensagemErroEnum mensagemErroEnum, Object... param) {
		return new BusinessException(mensagemErroEnum.getErrorCode(),
				MessageFormat.format(mensagemErroEnum.getMsg(), param), e);
	}

}
