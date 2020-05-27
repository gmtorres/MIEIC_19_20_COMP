package jasmin;


import static org.junit.Assert.*;

import org.junit.Test;

public class JasminTest {
	

	@Test
    public void testFindMaximum() {
		JasminUtils.testJmm("fixtures/public/FindMaximum.jmm", "Result: 28");
    }

	@Test
    public void testHelloWorld() {
		JasminUtils.testJmm("fixtures/public/HelloWorld.jmm", "Hello, World!");
    }

	@Test
    public void testMonteCarloPi() {
		JasminUtils.testJmm("fixtures/public/MonteCarloPi.jmm", "Insert number: Result: 0", "-1\n");
    }

	@Test
    public void testQuickSort() {
		JasminUtils.testJmm("fixtures/public/QuickSort.jmm", JasminUtils.getResource("fixtures/public/QuickSort.txt"));
    }

	@Test
    public void testSimple() {
		JasminUtils.testJmm("fixtures/public/Simple.jmm", "20");
    }
	

	@Test
    public void testTicTacToe() {
		JasminUtils.testJmm("fixtures/public/TicTacToe.jmm", JasminUtils.getResource("fixtures/public/TicTacToe.txt"), JasminUtils.getResource("fixtures/public/TicTacToe.input"));
	}

	@Test
    public void testWhileAndIF() {
		JasminUtils.testJmm("fixtures/public/WhileAndIF.jmm", JasminUtils.getResource("fixtures/public/WhileAndIF.txt"));
    }


}
