package webfrontend;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class SaveHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange ex) throws IOException {

    	// Saving files always requires an explicit POST.
        if (!ex.getRequestMethod().equalsIgnoreCase("POST")) {
            HtmlRenderer.send(ex, "Use POST.");
            return;
        }

        // Read ALL bytes from request body into a String.
        String body = new String(ex.getRequestBody().readAllBytes());
        
        // write into the output file.
        String data = MultipartParser.extractField(body, "data");
        
        // Extract the filename for the output
        String outFile = MultipartParser.extractField(body, "out");

        // If the user did NOT give a file name, use "output.txt".
        if (outFile == null || outFile.isBlank()) outFile = "output.txt";

        // If data is null, save an empty string.
        Files.writeString(new File(outFile).toPath(), data == null ? "" : data);

        // confirmation
        HtmlRenderer.send(ex, """
        <html><head><link rel="stylesheet" href="/static/style.css"></head>
        <body><div class="page"><div class="card">
        <h2>Saved</h2>
        <p>File written: %s</p>
        <a href="/"><button class="btn">Back</button></a>
        </div></div></body></html>
        """.formatted(outFile));
    }
}
