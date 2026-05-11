import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;

public class StudentServer {

    public static void main(String[] args)
            throws Exception {

        HttpServer server =
                HttpServer.create(
                        new InetSocketAddress(8000),
                        0
                );

        server.createContext("/student",
                (HttpExchange exchange) -> {

            String query =
                    exchange.getRequestURI().getQuery();

            String response =
                    getStudentData(query);

            exchange.getResponseHeaders()
                    .set("Content-Type",
                            "application/json");

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
        "Server started at:");
        System.out.println(
        "http://localhost:8000/student?id=1");
    }

    public static String getStudentData(
            String query
    ) {

        try {

            int studentId = 1;

            if (query != null &&
                    query.startsWith("id=")) {

                studentId =
                        Integer.parseInt(
                                query.substring(3)
                        );
            }

            // Read JSON file
            String content =
                    new String(
                            Files.readAllBytes(
                            Paths.get("student.json")
                    ));

            JSONArray students =
                    new JSONArray(content);

            // Search student
            for (int i = 0;
                 i < students.length();
                 i++) {

                JSONObject student =
                        students.getJSONObject(i);

                if (student.getInt("id")
                        == studentId) {

                    return student.toString(4);
                }
            }

            return "{ \"message\": "
                    + "\"Student not found\" }";

        } catch (Exception e) {

            return "{ \"error\": "
                    + "\"Something went wrong\" }";
        }
    }
}