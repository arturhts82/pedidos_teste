package br.com.ambev.enumerator;

/**Enum para status de processamento das transações do sistema*/
public enum StatusProcessamento {
	/** Sucesso */
	SU,
	/** Gravado */
	GR,
	/**
	 * Erro no processamento (Barrado) ocorrido dentro do sistema, em transações
	 * inbound ou processos internos
	 */
	EP,
	/** Não processado */
	NP;

}
