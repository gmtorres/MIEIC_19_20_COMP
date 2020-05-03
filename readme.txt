Em frente a cada tópico terá um [YES] ou [NO], para o caso de ter sido implementado ou não, respetivamente.

-= Semantic Analysis =-
todas as verificações feitas na análise semantica devem reportar erro excepto a verificação de inicialização de variáveis que deverá apenas dar um warning:
- Symbol Table
    . global: inclui info de imports e a classe declarada --> [YES]
    . classe-specific: inclui info de extends, fields e methods --> [YES]
    . method-specific: inclui info dos arguments e local variables --> [YES]
    . sub topics:
       + tem de permitir method overload (i.e. métodos com mesmo nome mas assinatura de parâmetros diferente) --> [YES]
       + tem de permitir consulta da tabela por parte da análise semantica (e geração de código) --> [YES]
       + tem de permitir ligar e desligar a sua impressão para fins de debug (neste caso para fins de avaliação) --> [YES]
- Type Verification
    . verificar se operações são efetuadas com o mesmo tipo (e.g. int + boolean tem de dar erro) --> [YES]
    . não é possível utilizar arrays diretamente para operações aritmeticas (e.g. array1 + array2) --> [YES]
    . verificar se um array access é de facto feito sobre um array --> [YES]
    . verificar se o indice do array access é um inteiro --> [YES]
    . verificar se valor do assignee é igual ao do assigned (a_int = b_boolean não é permitido!) --> [YES]
    . verificar se operação booleana é efetuada só com booleanos --> [YES]
    . verificar se conditional expressions (if e while) resulta num booleano --> [YES]
    . verificar se variáveis são inicializadas, dando um WARNING em vez de ERRO
       + parametros são assumidos como inicializados --> [YES]
       + devem fazer uma análise através do control flow, i.e., se há um if e a variável só é inicializada dentro de ou o then ou o else, deve-se dar um warning a indicar que poderá não estar inicializada --> [YES]
       + será considerado bónus a quem resolver esta verificação usando erros em vez de warning.
            - cuidado que se a analise não estiver bem feita os erros vão fazer com que o vosso compilador não passe para a geração de código!
			- caso pretendam fazer esta abordagem com erros adicionem uma forma de ativar/desativar o erro para facilitar no caso de haver problemas.
			
- Function Verification
	* verificar se o "target" do método existe, e se este contém o método (e.g. a.foo, ver se 'a' existe e se tem um método 'foo') --> [YES]
	    - caso seja do tipo da classe declarada (e.g. a usar o this), verificar se é método do extends olhando para o que foi importado (isto se a classe fizer extends de outra classe importada)
	* caso o método não seja da classe declarada, isto é importada, verificar se método foi importado
	* verificar se o número de argumentos na invocação é igual ao número de parâmetros da declaração --> [YES]
	* verificar se o tipo dos parâmetros coincide com o tipo dos argumentos --> [YES]
	    - não esquecer que existe method overloading
-= Code Generation =-
    * estrutura básica de classe (incluindo construtor <init>) --> [YES]
	* estrutura básica de fields --> [YES]
	* estrutura básica de métodos (podem desconsiderar os limites neste checkpoint: limit_stack 99, limit_locals 99) --> [YES, limit_stack and limit_locals implemented]
	* assignments --> [YES]
	* operações aritméticas (com prioridade de operações correta) --> [YES]
		- neste checkpoint não é necessário a seleção das operações mais eficientes mas isto será considerado no CP3 e versão final
	* invocação de métodos --> [YES]