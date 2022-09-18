Analisador Léxico implementado em Java para o trabalho do GA da disciplina de Tradutores.

Analisa o arquivo de código "input.txt", dentro da pasta [src/input/](https://github.com/augustostahlschmidt/analisador_lexico/blob/main/analisadorlexico/src/input/input.txt), gerando como saída os tokens no arquivo [tokens.txt](https://github.com/augustostahlschmidt/analisador_lexico/blob/main/analisadorlexico/src/output/tokens.txt), dentro da pasta "src/output/".

O trabalho utiliza a classe [Java.io.StreamTokenizer](https://docs.oracle.com/javase/7/docs/api/java/io/StreamTokenizer.html) para fazer o parse do arquivo de entrada em tokens, utilizando os métodos e atributos da classe
para identificar comentários de estilos diferentes, palavras, números e indicadores de fim de linha e fim de arquivo.

O arquivo de tokens de saída utiliza o modelo de tokens presentes no enunciado do trabalho, com uma pequena exceção, onde os tokens de identificadores também contém o nome do identificador ao invés de apenas seu número.

