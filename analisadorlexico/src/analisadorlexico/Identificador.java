package analisadorlexico;

public class Identificador {		
	private int numero;
	private String nome;
	
	public Identificador(int numero, String nome) {
		this.setNumero(numero);
		this.setNome(nome);
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}	
}
