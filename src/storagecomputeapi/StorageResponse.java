package storagecomputeapi;

public class StorageResponse {
	public enum Status {
		SUCCESS,
		FAIL
	}
	private final Status status;
	private final String message;
	
	public StorageResponse(Status status, String message) {
		this.status = status;
		this.message = message;
	
	}
	
	public Status getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return status == Status.SUCCESS;
    }


}
