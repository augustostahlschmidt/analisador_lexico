package analisadorlexico;

import java.io.IOException;

public class AnalisadorLexicoTest {

	public static void main(String[] args) {
		
		AnalisadorLexico analisadorLexico = new AnalisadorLexico();
		
		try {
			analisadorLexico.analisar("src/input/input.txt");
			analisadorLexico.imprimirTokens("src/output/tokens.txt");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
