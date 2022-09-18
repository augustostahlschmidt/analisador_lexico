package analisadorlexico;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ArrayList;

public class AnalisadorLexico {
	ArrayList<String> palavrasReservadas;
	ArrayList<Identificador> identificadores;
	Saida saida;
	int qtdeIdentificadores = 0;
	
	public AnalisadorLexico() {
		this.palavrasReservadas = new PalavrasReservadas().get();
		this.identificadores = new ArrayList<>();
		this.saida = new Saida();
	}
	
	public void analisar(String arquivo) throws IOException {		
		BufferedReader br = new BufferedReader(new FileReader(arquivo));
		ArrayList<String> conteudoArquivo = new ArrayList<>();
		
		String linhaArquivo;
		while ((linhaArquivo = br.readLine()) != null)
			conteudoArquivo.add(linhaArquivo);

		BufferedReader tokenBuffer = new BufferedReader(new StringReader(String.join("\n", conteudoArquivo)));
		StreamTokenizer tokenizer = new StreamTokenizer(tokenBuffer);
		tokenizer.slashSlashComments(true);
		tokenizer.slashStarComments(true);
		tokenizer.eolIsSignificant(true);
		tokenizer.ordinaryChar('/');
		int linhaAtual = 1;
		int token = tokenizer.nextToken();
		
		while (token != StreamTokenizer.TT_EOF) {			
			String caracteres = tokenizer.sval;

			switch(token) {
				case StreamTokenizer.TT_WORD: 
					if(this.palavrasReservadas.contains(caracteres))
						saida.adicionarToken("[reserved_word, " + tokenizer.sval + "]");
					else
						analisarIdentificador(tokenizer, token, saida);
					break;
				
				case StreamTokenizer.TT_NUMBER: saida.adicionarToken("[num, " + tokenizer.nval + "]"); break;			
				case '"': processarLiteral(tokenizer, token, saida); break;
				case '+': saida.adicionarToken("[Arith_Op, +]"); break;
				case '-': saida.adicionarToken("[Arith_Op, -]"); break;
				case '/': saida.adicionarToken("[Arith_Op, /]"); break;		
				case '*': saida.adicionarToken("[Arith_Op, *]"); break;
				case '{': saida.adicionarToken("[l_bracket, {]"); break;
				case '}': saida.adicionarToken("[r_bracket, }]"); break;
				case '(': saida.adicionarToken("[l_paren, (]"); break;
				case ')': saida.adicionarToken("[r_paren, )]"); break;
				case ',': saida.adicionarToken("[comma, ,]"); break;
				case ';': saida.adicionarToken("[semicolon, ;]"); break;
				case '<': analisarOperadorRelacional(tokenizer, token, saida); break;
				case '>': analisarOperadorRelacional(tokenizer, token, saida); break;
				case '=': analisarOperadorRelacional(tokenizer, token, saida); break;
				case '&': analisarOperadorLogico(tokenizer, token, saida); break;
				case '|': analisarOperadorLogico(tokenizer, token, saida); break;
				case '!': analisarOperadorLogico(tokenizer, token, saida); break;
			}
			
			// Fim da linha
			if(token == StreamTokenizer.TT_EOL) {
				linhaAtual++;
				saida.terminarLinha();
			}
			token = tokenizer.nextToken();
		}
	}

	private void processarLiteral(StreamTokenizer tokenizer, int token, Saida saida) {
		String literal = tokenizer.sval;	
		String stringLimpa = literal.replaceAll("\n","");
		saida.adicionarToken("[string_literal, " + stringLimpa + "]");
	}

	private void analisarOperadorLogico(StreamTokenizer tokenizer, int token, Saida saida) throws IOException {
		switch(token) {					
			case '&': {
				int proximoToken = tokenizer.nextToken();
				switch (proximoToken) {
					case '&': saida.adicionarToken("[logic_op, &&]"); break;
					default:
						tokenizer.pushBack();
						saida.adicionarToken("[logic_op, &]"); break;
				} break;
			}
			case '|': {
				int proximoToken = tokenizer.nextToken();
				switch (proximoToken) {
					case '|': saida.adicionarToken("[logic_op, ||]"); break;
					default:
						tokenizer.pushBack(); break;
				} break;
			}
			case '!': {
				int proximoToken = tokenizer.nextToken();
				switch (proximoToken) {
					case '=': saida.adicionarToken("[logic_op, !=]"); break;
					default: tokenizer.pushBack(); break;
				} break;
			}
		}
	}

	private void analisarOperadorRelacional(StreamTokenizer tokenizer, int token, Saida saida) throws IOException {
		switch(token) {
			case '<': {
				int proximoToken = tokenizer.nextToken();
				switch (proximoToken) {
					case '=': saida.adicionarToken("[Relational_Op, <=]"); break;
					case '<': saida.adicionarToken("[Relational_Op, <<]"); break;
					default:
						tokenizer.pushBack();
						saida.adicionarToken("[Relational_Op, <]"); break;
				} break;			
			}		
			case '>': {
				int proximoToken = tokenizer.nextToken();
				switch (proximoToken) {
					case '=': saida.adicionarToken("[Relational_Op, >=]"); break;
					case '>': saida.adicionarToken("[Relational_Op, >>]"); break;
					default:
						tokenizer.pushBack();
						saida.adicionarToken("[Relational_Op, >]"); break;
				} break;
			}
			case '=': {
				int proximoToken = tokenizer.nextToken();
				switch (proximoToken) {
					case '=': saida.adicionarToken("[Relational_Op, ==]"); break;
					default:
						tokenizer.pushBack();
						saida.adicionarToken("[equal, =]"); break;
				} break;
			}

		}	
	}

	private void analisarIdentificador(StreamTokenizer tokenizer, int token, Saida saida) {		
		for(Identificador identificador : this.identificadores) {
			String nomeLista = identificador.getNome();
			String nomeTeste = tokenizer.sval;
			
			if(nomeLista.equals(nomeTeste)) {
				saida.adicionarToken("[Id, " + identificador.getNumero() + ", " + identificador.getNome() + "]");
				return;
			}
		}
		
		qtdeIdentificadores++;
		Identificador novoIdentificador = new Identificador(qtdeIdentificadores, tokenizer.sval);
		this.identificadores.add(novoIdentificador);
		saida.adicionarToken("[Id, " + qtdeIdentificadores + ", " + tokenizer.sval + "]");
	}	
	
	public void imprimirTokens(String path) throws IOException {
		ArrayList<String> saida = this.saida.getSaida();
		
        File fout = new File(path);       
		FileOutputStream fos = new FileOutputStream(fout);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        
		for(String stringSaida : saida) {
//			System.out.println(stringSaida);
			bw.write(stringSaida);
			bw.newLine();
		}
		
		bw.close();
	}
}
