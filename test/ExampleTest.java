import static org.junit.Assert.*;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ExampleTest {


    @Test
    public void testExpressions() {
		assertEquals(5, evalString("2+3\n"));
		assertEquals(2, evalString("6-4\n"));		
    }


    @Test
    public void testParseError() {
		try {
			// This should fail
			evalString("6-");
			// If no exception is thrown, fail test
			fail();
		} catch(Exception e) {
			// Success
		}
    }


	public int evalString(String input) {
		
		try {
			SimpleNode root = new Calculator(Utils.toInputStream(input)).Expression(); // returns reference to root node
			return Main.eval(root);
		} catch(ParseException e) {
			// Convert checked exception into runtime exception
			throw new RuntimeException(e);
		}		
	}
}
