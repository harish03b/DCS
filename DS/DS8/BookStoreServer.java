import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;

public class BookStoreServer {

    public static void main(String[] args)
            throws Exception {

        // Create server
        HttpServer server =
                HttpServer.create(
                        new InetSocketAddress(8000),
                        0
                );

        // Create API endpoint
        server.createContext("/book",
                (HttpExchange exchange) -> {

            String query =
                    exchange.getRequestURI()
                            .getQuery();

            String response =
                    getBookData(query);

            exchange.getResponseHeaders()
                    .set(
                    "Content-Type",
                    "application/json"
            );

            exchange.sendResponseHeaders(
                    200,
                    response.getBytes().length
            );

            OutputStream os =
                    exchange.getResponseBody();

            os.write(response.getBytes());

            os.close();
        });

        server.start();

        System.out.println(
        "BookStore Server Started");
        System.out.println(
        "http://localhost:8000/book?id=1");
    }

    public static String getBookData(
            String query
    ) {

        try {

            int bookId = 1;

            // Extract ID
            if (query != null &&
                    query.startsWith("id=")) {

                bookId =
                        Integer.parseInt(
                                query.substring(3)
                        );
            }

            // Read JSON file
            String content =
                    new String(
                            Files.readAllBytes(
                            Paths.get("books.json")
                    ));

            // Convert to JSONArray
            JSONArray books =
                    new JSONArray(content);

            // Search book
            for (int i = 0;
                 i < books.length();
                 i++) {

                JSONObject book =
                        books.getJSONObject(i);

                if (book.getInt("id")
                        == bookId) {

                    return book.toString(4);
                }
            }

            return "{ \"message\": "
                    + "\"Book not found\" }";

        } catch (Exception e) {

            return "{ \"error\": "
                    + "\"Something went wrong\" }";
        }
    }
}