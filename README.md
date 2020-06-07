# Compiler of the Java-- language to Java Bytecodes [GROUP 4B]

|      | Name              | Number    | Self Assessment | Contribution (%) |
| ---- | ----------------- | --------- | --------------- | ---------------- |
| 1    | Inês Marques      | 201605542 | 18 | 23 |
| 2    | Gustavo Torres    | 201706473 | 20 | 30 |
| 3    | Joaquim Rodrigues | 201605219 | 19 | 24 |
| 4    | Vítor Ventuzelos  | 201706403 | 18 | 23 |

GLOBAL Grade of the project: 20

## SUMMARY:
For the Compilers class, the students were asked to develop a tool, named jmm, that compiles Java-- (a subset of the Java language) to Java bytecodes. This compiler follows a well defined compilation flow, capable of performing syntactic and semantic analysis as well as code generation.

## EXECUTE:
To execute the compiler, use the following commands:

```console
java –jar comp2020-4b.jar [-r=<num>] [-o] [-b] [-u] [-cf] [-s] [-l=<num>] <input_file.jmm>
```

## DEALING WITH SYNTACTIC ERRORS:
Any incorrect grammar not accepted by the parser is registered and reported to the user, while compilation is aborted. Within "while" loops, up to 10 grammar errors will be reported before aborting compilation.

## SEMANTIC ANALYSIS:
The semantic analysis performed by the compiler at hands seeks to highlight a wide number of possible errors, some of which are mentioned below and most of which are showcased in the test files included in the project:
- Two variables (within the same scope) cannot have the same identifier;
- A variable must be declared before being used;
- Wrong type of arguments;
- During assignments, the left-hand side and the right-hand side of the statement must be of the same type, except when casting float to integer
- Check if operations are performed with the same type (e.g. int + boolean must give error);
- It is not possible to use arrays directly for arithmetic operations;
- Check if an array access is in fact done over an array;
- Check if the index of the access array is an integer;
- Check if a boolean operation is performed only with boolean variables;
- Check if conditional expressions (if and while) result in a boolean;
- Supports function overload;
- Support inheritance;
- Function type and return type are the same;
- and many more; 

## INTERMEDIATE REPRESENTATIONS (IRs): 
We decided to generate an intermediate representation, a data structure used internally by the compiler, to aid in the analysis of input code. This representation is made after both the semantic and syntax are complete. Also, the IR helps us structure the Java-- code in something more simpler, manageable and closer to the target machine code. It will also help us in the optimizations of the code generation part of the project. We also did the Control Flow Graph for liveness analysis allowing us to do the register allocation optimization. Their implementations can be found in \textit{/src/CFGNode.java}, \textit{/src/IRNode.java} and \textit{/src/IRBuilder.java}.

## CODE GENERATION:
If there aren’t any errors the code for the specified file is generated. This process is done by the Jasmin class that receives the generated IR and a Printstream with the specified file for which the final code will be printed. It can be found in \textit{/src/Jasmin.java}. The generated code can be found in the \textit{jasmin.j} file placed in the root folder of the project.

## OVERVIEW:

The group accomplished all the suggested stages for this project:

1. Developed a parser for Java-- using JavaCC and taking as starting point the Java-- grammar furnished;
2. Included error treatment and recovery mechanisms;
3. Proceeded with the specification of the AST;
4. Included the necessary symbol tables;
5. Semantic Analysis;
6. Generated JVM code accepted by jasmin corresponding to the invocation of functions in Java--;
7. Generated JVM code accepted by jasmin for arithmetic expressions;
8. Generated JVM code accepted by jasmin for conditional instructions (if and if-else);
9. Generated JVM code accepted by jasmin for loops;
10. Generated JVM code accepted by jasmin to deal with arrays.
11. Completed the compiler and test it using a set of Java-- classes;
12. Proceeded with the optimizations related to the code generation.

More than the previous stage the group implemented:

13. Intermediate Representation getting closer to target machine code and optimizations
14. Control Flow Graph for optimizations

## TASK DISTRIBUTION: 
The tasks were distributed between all the peers in this work. The work was passed around to keep everyone interested and it is translated in the assigned contribution.

## PROS:
### Some additional features were added:
- Strings;
- Logical OR, ||;
- Greater than, >;
- For loop;
- Constructor with parameter.
- Floats
- Casting integer to float and vice-versa

### Some additional optimizations were added:
-  -cf Constant fold optimization
-  -s Power of two integer division/multiplication 
-  -u Unused and unnecessary code removal
-  -b If and else branch prediction and removal
-  -l=<n> Loop unrolling

## CONS:
- Conditional expression evaluation optimization - evaluation of a condition can not use the whole conditions
- Expression propagation optimization
- Expression storing optimization
- Loop unswitching optimization
- Object arrays
 

## Test files
The files are in the folder ./files

- ConnectFour - each player chooses the column he wants to place the piece, from 0 to 6, first one to make a line of his pieces made out of four pieces wins
- HSort - heap sort, the users inputs how many numbers we wants to add and then define each number, then the program performs a heap sort
- blackjack - you have your cards (2 numbers displayed) and the dealer card. You can ask for a card by pressing 1 or stand by pressing 2. If you get more than a total of 21 (summing the value of the cards, you lose). When standing, if you get closer to 21 than the dealer, you win, otherwise, you lose (exception made when the dealer sum is above 21).
- sudoku - the player chooses the difficulty and then he selects the number to put and the position
- stressTest - program that shows all features, extras and optimizations in a compact format
