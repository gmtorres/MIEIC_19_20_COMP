import java.io.*;

public class RegisterAllocationException extends Exception {

	String description;
	int k;
	
	RegisterAllocationException(String description, int k){
		super();
		this.description = description;
		this.k = k;
	}
	
	public String toString() {
		return this.description;
	}
	
}
