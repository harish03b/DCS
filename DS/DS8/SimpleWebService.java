import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class SimpleWebService {
    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        server.createContext("/add", (HttpExchange exchange) -> {

            String response;

            try {
                String query = exchange.getRequestURI().getQuery();

                if (query == null) {
                    response = "Please provide parameters: ?a=5&b=10";
                } else {
                    String[] params = query.split("&");

                    int a = Integer.parseInt(params[0].split("=")[1]);
                    int b = Integer.parseInt(params[1].split("=")[1]);

                    int result = a + b;
                    response = "Result = " + result;
                }

                exchange.sendResponseHeaders(200, response.length());

            } catch (Exception e) {
                response = "Error: Invalid input";
                exchange.sendResponseHeaders(400, response.length());
            }

            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        });

        server.start();
        System.out.println("Server running at http://localhost:8000/add?a=5&b=10");
    }
}