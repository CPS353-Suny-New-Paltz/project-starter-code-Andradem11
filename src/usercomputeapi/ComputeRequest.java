package usercomputeapi;

public class ComputeRequest {
    // get input and delimiters for result
    private final DataSource source;
    private final String delimiter;

    public ComputeRequest(DataSource source, String delimiter) {
        this.source = source;
        // assign default delimiter if null or blank
        this.delimiter = (delimiter == null || delimiter.isBlank()) ? ";" : delimiter;
    }

    public DataSource getSource() {
        return source;
    }

    public String getDelimiter() {
        return delimiter;
    }
}
