package webfrontend;

public class MultipartParser {

    // extract file content from multipart
    public static String extractFileContent(String body) {
    	
    	// Find the end of the headers
        int start = body.indexOf("\r\n\r\n");
        
        // Find the last boundary marker
        int end = body.lastIndexOf("\r\n------");
        
        if (start < 0 || end < 0) return "";
        return body.substring(start + 4, end).trim();
    }

    // extract text field from multipart
    public static String extractField(String body, String field) {
        if (!body.contains("name=\"" + field + "\"")) return null;
        
        // part[0] = everything before the field
        // part[1] = everything starting at the field
        String part = body.split("name=\"" + field + "\"", 2)[1];
        
        // Skip forward to the first empty line
        part = part.substring(part.indexOf("\r\n\r\n") + 4);
        return part.split("\r\n")[0].trim();
    }
}
