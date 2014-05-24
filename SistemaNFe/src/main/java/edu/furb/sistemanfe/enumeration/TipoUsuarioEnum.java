package edu.furb.sistemanfe.enumeration;

public enum TipoUsuarioEnum {
	ADMIN("A", "Admin"), CLIENTE("C", "Cliente");

	private final String valor;
	private final String descricao;

	private TipoUsuarioEnum(String _valor, String _descricao) {
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
