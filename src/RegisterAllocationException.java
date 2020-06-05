import java.io.*;

public class RegisterAllocationException extends Exception {

	String description;
	
	RegisterAllocationException(String description){
		super();
		this.description = description;
	}
	
	public String toString() {
		return this.description;
	}
	
}
