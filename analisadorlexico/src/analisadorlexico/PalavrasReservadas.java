package analisadorlexico;

import java.util.ArrayList;

public class PalavrasReservadas {
	
	private ArrayList<String> palavrasReservadas;

	public PalavrasReservadas() {
		palavrasReservadas = new ArrayList<>();
		
		palavrasReservadas.add("do");
		palavrasReservadas.add("while");		
		palavrasReservadas.add("if");
		palavrasReservadas.add("else");
		palavrasReservadas.add("switch");		
		palavrasReservadas.add("for");
		palavrasReservadas.add("return");
		palavrasReservadas.add("null");		
		palavrasReservadas.add("int");
		palavrasReservadas.add("float");
		palavrasReservadas.add("double");		
		palavrasReservadas.add("string");
		palavrasReservadas.add("bool");
		palavrasReservadas.add("break");		
		palavrasReservadas.add("case");
		palavrasReservadas.add("void");
		palavrasReservadas.add("NULL");
		palavrasReservadas.add("include");
	}
	
	public ArrayList<String> get(){
		return this.palavrasReservadas;
	}
}
