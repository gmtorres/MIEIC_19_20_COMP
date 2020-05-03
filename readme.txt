Em frente a cada t�pico ter� um [YES] ou [NO], para o caso de ter sido implementado ou n�o, respetivamente.

-= Semantic Analysis =-
todas as verifica��es feitas na an�lise semantica devem reportar erro excepto a verifica��o de inicializa��o de vari�veis que dever� apenas dar um warning:
- Symbol Table
    . global: inclui info de imports e a classe declarada --> [YES]
    . classe-specific: inclui info de extends, fields e methods --> [YES]
    . method-specific: inclui info dos arguments e local variables --> [YES]
    . sub topics:
       + tem de permitir method overload (i.e. m�todos com mesmo nome mas assinatura de par�metros diferente) --> [YES]
       + tem de permitir consulta da tabela por parte da an�lise semantica (e gera��o de c�digo) --> [YES]
       + tem de permitir ligar e desligar a sua impress�o para fins de debug (neste caso para fins de avalia��o) --> [YES]
- Type Verification
    . verificar se opera��es s�o efetuadas com o mesmo tipo (e.g. int + boolean tem de dar erro) --> [YES]
    . n�o � poss�vel utilizar arrays diretamente para opera��es aritmeticas (e.g. array1 + array2) --> [YES]
    . verificar se um array access � de facto feito sobre um array --> [YES]
    . verificar se o indice do array access � um inteiro --> [YES]
    . verificar se valor do assignee � igual ao do assigned (a_int = b_boolean n�o � permitido!) --> [YES]
    . verificar se opera��o booleana � efetuada s� com booleanos --> [YES]
    . verificar se conditional expressions (if e while) resulta num booleano --> [YES]
    . verificar se vari�veis s�o inicializadas, dando um WARNING em vez de ERRO
       + parametros s�o assumidos como inicializados --> [YES]
       + devem fazer uma an�lise atrav�s do control flow, i.e., se h� um if e a vari�vel s� � inicializada dentro de ou o then ou o else, deve-se dar um warning a indicar que poder� n�o estar inicializada --> [YES]
       + ser� considerado b�nus a quem resolver esta verifica��o usando erros em vez de warning.
            - cuidado que se a analise n�o estiver bem feita os erros v�o fazer com que o vosso compilador n�o passe para a gera��o de c�digo!
			- caso pretendam fazer esta abordagem com erros adicionem uma forma de ativar/desativar o erro para facilitar no caso de haver problemas.
			
- Function Verification
	* verificar se o "target" do m�todo existe, e se este cont�m o m�todo (e.g. a.foo, ver se 'a' existe e se tem um m�todo 'foo') --> [YES]
	    - caso seja do tipo da classe declarada (e.g. a usar o this), verificar se � m�todo do extends olhando para o que foi importado (isto se a classe fizer extends de outra classe importada)
	* caso o m�todo n�o seja da classe declarada, isto � importada, verificar se m�todo foi importado
	* verificar se o n�mero de argumentos na invoca��o � igual ao n�mero de par�metros da declara��o --> [YES]
	* verificar se o tipo dos par�metros coincide com o tipo dos argumentos --> [YES]
	    - n�o esquecer que existe method overloading
-= Code Generation =-
    * estrutura b�sica de classe (incluindo construtor <init>) --> [YES]
	* estrutura b�sica de fields --> [YES]
	* estrutura b�sica de m�todos (podem desconsiderar os limites neste checkpoint: limit_stack 99, limit_locals 99) --> [YES, limit_stack and limit_locals implemented]
	* assignments --> [YES]
	* opera��es aritm�ticas (com prioridade de opera��es correta) --> [YES]
		- neste checkpoint n�o � necess�rio a sele��o das opera��es mais eficientes mas isto ser� considerado no CP3 e vers�o final
	* invoca��o de m�todos --> [YES]