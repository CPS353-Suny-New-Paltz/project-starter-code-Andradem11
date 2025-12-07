package webfrontend;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import computeengineapi.ComputeEngineAPI;
import computeengineapi.ComputeEngineFastImpl;
import storagecomputeapi.StorageComputeAPI;
import storagecomputeapi.StorageComputeImpl;
import usercomputeapi.ComputeRequest;
import usercomputeapi.ComputeResponse;
import usercomputeapi.UserComputeAPI;
import usercomputeapi.UserComputeImpl;
import usercomputeapi.UserComputeMultiThreaded;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * ComputeHandler
 * --------------
 * Handles requests sent to /compute.
 *
 * These requests come from the HTML form where the user enters:
 *     1, 5, 20, 50
 *
 * This handler:
 *   - Reads the numbers from the URL query
 *   - Converts them into ComputeRequest objects
 *   - Runs your backend compute engine (multi-threaded)
 *   - Builds an HTML results table and sends it back to the browser
 */
public class ComputeHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange ex) throws IOException {

        // get query string
    	// If the query is missing or malformed, show an error.
        String query = ex.getRequestURI().getQuery();
        if (query == null || !query.contains("=")) {
            HtmlRenderer.send(ex, "Invalid input.");
            return;
        }
        

        // Extract the numbers from the query string
        String nums = query.split("=", 2)[1];
        // Split by commas → ["5", "10", "15"]
        String[] tokens = nums.split(",");

        // Construct backend components
        StorageComputeAPI storage = new StorageComputeImpl();
        ComputeEngineAPI engine = new ComputeEngineFastImpl();
        UserComputeAPI base = new UserComputeImpl(storage, engine);
        
        // Multi-threaded wrapper around the base compute API
        UserComputeMultiThreaded multi = new UserComputeMultiThreaded(base, 4);

        List<String> inputs = new ArrayList<>();
        List<ComputeRequest> requests = new ArrayList<>();

        // parse numbers
        for (String t : tokens) {
            t = t.trim();  		// remove spaces
            inputs.add(t);  	// store original input for HTML later
            try {
                int n = Integer.parseInt(t);
                requests.add(new ComputeRequest(() -> n, ";"));
            } catch (Exception e) {
                requests.add(null);
            }
        }

        // filter valid requests
        List<ComputeRequest> valid = new ArrayList<>();
        for (ComputeRequest r : requests) {
            if (r != null) valid.add(r);
        }

        // compute for valid inputs
        List<ComputeResponse> validResponses = multi.computeMultiRequest(valid);

        // map back to original order
        List<ComputeResponse> responses = new ArrayList<>();
        int idx = 0;			// pointer for validResponses
        for (ComputeRequest r : requests) {
            if (r == null) {
            	// Invalid input → create a failure response manually
                responses.add(new ComputeResponse(0, ComputeResponse.Status.FAIL));
            } else {
            	// Valid input → take the next response from validResponses
                responses.add(validResponses.get(idx++));
            }
        }

        // build results table
        StringBuilder html = new StringBuilder("""
        <html>
        <head><link rel="stylesheet" href="/static/style.css"></head>
        <body><div class="page"><div class="card">
        <h2>Results</h2>
        <table>
        <tr><th>Input</th><th>Sum</th><th>Status</th></tr>
        """);
        
        // Add each row to the table
        for (int i = 0; i < inputs.size(); i++) {
            ComputeResponse r = responses.get(i);
            html.append("<tr><td>")
                .append(inputs.get(i))		// original input string
                .append("</td><td>")
                .append(r.getSum())			// sum computed by backend
                .append("</td><td>")
                .append(r.isSuccess() ? "OK" : "Invalid")	// success or failure
                .append("</td></tr>");
        }
        
        // Close the table and add a back button
        html.append("""
        </table><br>
        <a href="/"><button class="btn">Back</button></a>
        </div></div></body></html>
        """);

        // Send the HTML back to the client
        HtmlRenderer.send(ex, html.toString());
    }
}
