package webfrontend;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import computeengineapi.ComputeEngineAPI;
import computeengineapi.ComputeEngineFastImpl;
import storagecomputeapi.StorageComputeAPI;
import storagecomputeapi.StorageComputeImpl;
import usercomputeapi.UserComputeAPI;
import usercomputeapi.UserComputeImpl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
/**
 * Handles POST requests sent to /upload.
 * This endpoint accepts a MULTIPART FORM (file upload) from the HTML page.
 * The uploaded file contains *one number per line*.
 */

public class FileUploadHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange ex) throws IOException {
		
		// Browsers upload files using POST multipart/form-data
	    if (!ex.getRequestMethod().equalsIgnoreCase("POST")) {
	        HtmlRenderer.send(ex, "Use POST to upload files.");
	        return;
	    }

	    String body = new String(ex.getRequestBody().readAllBytes());
	    String fileContent = MultipartParser.extractFileContent(body);

	    // If file content missing → error
	    if (fileContent == null || fileContent.isBlank()) {
	        HtmlRenderer.send(ex, "No file content received.");
	        return;
	    }

	    // Save input file
	    File in = new File("frontend-input.txt");
	    Files.writeString(in.toPath(), fileContent);

	    // Prepare output file
	    File out = new File("frontend-output.txt");
	    
	    // Run the backend compute engine on the file
	    StorageComputeAPI storage = new StorageComputeImpl();
	    ComputeEngineAPI engine = new ComputeEngineFastImpl();
	    UserComputeAPI compute = new UserComputeImpl(storage, engine);

	    compute.processFile(in.getAbsolutePath(), out.getAbsolutePath());

	    // If output file exists, read contents
	    String output = Files.exists(out.toPath()) 
	            ? Files.readString(out.toPath()) 
	            : "";

	    // Split input lines & output values so we can pair them in a table
	    String[] inputLines = fileContent.split("\\R");
	    String[] outputValues = output.isBlank() ? new String[0] : output.split(",");

	    // Displays:
        //   Input | Sum | Status
	    StringBuilder table = new StringBuilder();

	    table.append("""
	        <html>
	        <head>
	           <link rel="stylesheet" href="/static/style.css">
	        </head>
	        <body>
	        <div class="page">
	        <div class="card">
	          <h2>File Results</h2>
	          <table class="result-table">
	            <tr>
	              <th>Input</th>
	              <th>Sum</th>
	              <th>Status</th>
	            </tr>
	        """);

	    int idx = 0;
	    for (String line : inputLines) {
	    	// Skip empty lines (maybe file had blanks)
	        String trimmed = line.trim();
	        if (trimmed.isBlank()) continue;

	        // Get corresponding output result, or default "0"
	        String outVal = (idx < outputValues.length) ? outputValues[idx].trim() : "0";
	        idx++;

	        // Check if input line is a valid integer >= 2
	        int n;
	        boolean valid = true;

	        try {
	            n = Integer.parseInt(trimmed);
	            if (n < 2) valid = false;
	        } catch (Exception e) {
	            valid = false;
	        }

	        // Add a row to the HTML table
	        table.append("<tr>")
	             .append("<td>").append(trimmed).append("</td>")
	             .append("<td>").append(outVal).append("</td>")
	             .append("<td>").append(valid ? "OK" : "Invalid").append("</td>")
	             .append("</tr>");
	    }

	    table.append("""
	          </table>
	          <p class="small">Input file saved as: frontend-input.txt</p>
	          <p class="small">Output file saved as: frontend-output.txt</p>
	          <a class="btn" href="/">Back</a>
	        </div>
	        </div>
	        </body>
	        </html>
	        """);
	    // Send final HTML page to browser
	    HtmlRenderer.send(ex, table.toString());
	}
}