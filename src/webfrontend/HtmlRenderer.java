package webfrontend;

import com.sun.net.httpserver.HttpExchange;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

// contains helper methods for sending HTTP responses.
public class HtmlRenderer {

    // send HTML
    public static void send(HttpExchange ex, String html) throws IOException {
    	
    	// Convert HTML string to bytes for sending over HTTP.
        byte[] bytes = html.getBytes();
        
        // Inform the browser this is HTML using UTF-8 encoding.
        ex.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");
        
        // Send HTTP status 200 OK and total byte length.
        ex.sendResponseHeaders(200, bytes.length);
        
        // Write the bytes into the output stream.
        try (OutputStream os = ex.getResponseBody()) {
            os.write(bytes);
        }
    }

    // send static files
    public static void sendFile(HttpExchange ex, File file, String type) throws IOException {
    	
    	// Read entire file into byte array.
        byte[] bytes = Files.readAllBytes(file.toPath());
        
        // Set Content-Type based on file type passed in.
        ex.getResponseHeaders().add("Content-Type", type);
        
        // Send 200 OK with content length.
        ex.sendResponseHeaders(200, bytes.length);
        
        // Write file bytes to output stream.
        try (OutputStream os = ex.getResponseBody()) {
            os.write(bytes);
        }
    }
}
