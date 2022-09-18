package analisadorlexico;

import java.util.ArrayList;

public class Saida {
	private ArrayList<String> saida;
	private String linha;
	
	public Saida() {
		this.saida = new ArrayList<>();
		this.linha = null;
	}
	
	public void adicionarToken(String token) {
		if(this.linha != null)
			this.linha += " " + token;
		else
			this.linha = token;
	}
	
	public void terminarLinha() {
		if(this.linha != null) {
			this.saida.add(linha);
			this.linha = null;
		}
		else {
			this.saida.add(" ");
		}
	}

	public ArrayList<String> getSaida() {
		return this.saida;
	}
}
