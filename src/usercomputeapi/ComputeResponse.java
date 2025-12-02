package usercomputeapi;

public class ComputeResponse {
    public enum Status {
        SUCCESS,
        FAIL
    }

    // sum of primes
    private final int sum;
    private final Status status;

    public ComputeResponse(int sum, Status status) {
        this.sum = sum;
        this.status = status;
    }

    public int getSum() {
        return sum;
    }

    public Status getStatus() {
        return status;
    }

    public boolean isSuccess() {
        return status == Status.SUCCESS ? true : false;
    }
}
