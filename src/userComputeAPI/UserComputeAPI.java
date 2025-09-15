package userComputeAPI;
import project.annotations.NetworkAPI;
@NetworkAPI
public interface UserComputeAPI {
//	set where the system should read from
	void setInputSource(String source);
	
//	set where the system should write the result
	void setOutputDestination(String destiantion);
	
	
	void setDelimiters(String pairDelimiter,String resultDelimiter);
	
	

}
