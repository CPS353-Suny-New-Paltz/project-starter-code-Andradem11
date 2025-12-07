package webfrontend;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class HomeHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange ex) throws java.io.IOException {

        String html = """
        <html>
        <head>
            <link rel="stylesheet" href="/static/style.css">
        </head>
        <body>
        <div class="page">

            <h1>Prime Sum Calculator</h1>

            <!-- Manual Input Card -->
            <div class="card">
              <h2>Compute Prime Sums (Manual Input)</h2>

              <form action="/compute" class="form-grid">
                <label>Enter numbers:</label>
                <input name="n" type="text" placeholder="10 or 5,20,3">

                <button type="submit" class="btn">Compute</button>
              </form>
            </div>

            <!-- File Upload Card -->
            <div class="card">
              <h2>Process a Text File</h2>

              <form action="/upload" method="POST" enctype="multipart/form-data" class="form-grid">
                <label>Select a text file:</label>
                <input type="file" name="file">

                <button type="submit" class="btn">Upload & Process</button>
              </form>
            </div>

        </div>
        </body>
        </html>
        """;

        HtmlRenderer.send(ex, html);
    }
}
