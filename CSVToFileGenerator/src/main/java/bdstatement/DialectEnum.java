package bdstatement;

public enum DialectEnum {

	MYSQL(1,"Mysql");
	
	private Integer codigo;
	private String descricao;
	
	private DialectEnum(Integer codigo, String descricao){
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
}
