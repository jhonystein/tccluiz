package edu.furb.sistemanfe.enumeration;

public enum TipoAdministradorEnum {
	SIM("S", "Sim"), NAO("N", "Não");

	private final String valor;
	private final String descricao;

	private TipoAdministradorEnum(String _valor, String _descricao) {
		this.valor = _valor;
		this.descricao = _descricao;
	}
	
	public String getValor(){
		return this.valor;
	}
	
	public String getDescricao(){
		return this.descricao;
	}

}
