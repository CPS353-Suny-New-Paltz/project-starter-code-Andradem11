package userComputeAPI;
import project.annotations.NetworkAPIPrototype;

public class UserComputeAPIPrototype {
	
	@NetworkAPIPrototype
	public void prototype(UserComputeAPI user) {
//		the input source
		user.setInputSource("input text");
		
//		the output destination
		user.setOutputDestination("output text");
		
//		set delimiters for parsing input and output
		user.setDelimiters(";", ";");
		
	}
	

}
