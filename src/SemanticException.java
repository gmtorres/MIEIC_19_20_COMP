import java.io.*;


public class SemanticException extends Exception {
	
	String description;
	
	SemanticException(String description){
		super();
		this.description = description;
	}
	
	public String toString() {
		return this.description;
	}
	
}
