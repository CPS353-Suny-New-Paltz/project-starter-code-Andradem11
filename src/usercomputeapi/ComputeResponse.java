package usercomputeapi;

public class ComputeResponse {
	private final int sum;
	private final boolean success;
	
	public ComputeResponse(int sum, boolean success) {
		this.sum=sum;
		this.success=success;
	}
	public int getSum() {
		return sum;
	}
	public boolean isSuccess() {
		return success;
	}
}
