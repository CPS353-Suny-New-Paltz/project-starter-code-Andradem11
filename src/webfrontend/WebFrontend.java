package webfrontend;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebFrontend {

	// File path to your CSS file inside the project directory.
    public static final File CSS_FILE = new File("src/webfrontend/static/style.css");

    public static void main(String[] args) throws Exception {

        // create HTTP server on port 8080
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // routes
        
        // (home page)
        server.createContext("/", new HomeHandler());
        
        // (manual numbers)
        server.createContext("/compute", new ComputeHandler());
        
        // (file upload)
        server.createContext("/upload", new FileUploadHandler());
        
        // (save text to file)
        server.createContext("/save", new SaveHandler());

        // static CSS file route
        server.createContext("/static/style.css", ex ->
                HtmlRenderer.sendFile(ex, CSS_FILE, "text/css"));

        // use fixed thread pool (4 threads)
        ExecutorService pool = Executors.newFixedThreadPool(4);
        server.setExecutor(pool);

        // Start the HTTP server
        server.start();
        System.out.println("Frontend running at http://localhost:8080");
    }
}
