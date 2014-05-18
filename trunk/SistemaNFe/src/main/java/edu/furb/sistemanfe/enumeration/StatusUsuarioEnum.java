package edu.furb.sistemanfe.enumeration;

public enum StatusUsuarioEnum {
	ATIVO("A", "Ativo"), BLOQUEADO("B", "Bloqueado"), EXCLUIDO("E", "Excluído");

	private final String valor;
	private final String descricao;

	private StatusUsuarioEnum(String _valor, String _descricao) {
		this.valor = _valor;
		this.descricao = _descricao;
	}
	
	public String getDescricao(){
		return this.descricao;
	}

}
