import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

import org.json.JSONArray;
import org.json.JSONObject;

public class WeatherWebApp {

    static String apiKey =
    "ba37401375a8eb399791045f03cd534e";

    public static void main(String[] args)
            throws Exception {

        // Create server on port 8000
        HttpServer server =
                HttpServer.create(
                        new InetSocketAddress(8000),
                        0
                );

        // Create route
        server.createContext("/weather",
                (HttpExchange exchange) -> {

            String query =
                    exchange.getRequestURI().getQuery();

            String city = "Nagpur";

            // Extract city from URL
            if (query != null &&
                    query.startsWith("city=")) {

                city = query.substring(5);
            }

            String response =
                    getWeather(city);

            exchange.sendResponseHeaders(
                    200,
                    response.getBytes().length
            );

            OutputStream os =
                    exchange.getResponseBody();

            os.write(response.getBytes());

            os.close();
        });

        server.setExecutor(null);

        server.start();

        System.out.println(
        "Server running at:");
        System.out.println(
        "http://localhost:8000/weather?city=Nagpur");
    }

    public static String getWeather(String cityName) {

        try {

            String baseUrl =
            "https://api.openweathermap.org/data/2.5/weather";

            String urlString =
                    baseUrl
                    + "?q="
                    + URLEncoder.encode(
                            cityName,
                            "UTF-8"
                    )
                    + "&appid="
                    + apiKey
                    + "&units=metric";

            URL url = new URL(urlString);

            HttpURLConnection connection =
                    (HttpURLConnection)
                            url.openConnection();

            connection.setRequestMethod("GET");

            int responseCode =
                    connection.getResponseCode();

            if (responseCode == 200) {

                BufferedReader reader =
                        new BufferedReader(
                        new InputStreamReader(
                                connection.getInputStream()
                        )
                );

                StringBuilder response =
                        new StringBuilder();

                String line;

                while ((line =
                        reader.readLine()) != null) {

                    response.append(line);
                }

                reader.close();

                JSONObject data =
                        new JSONObject(
                                response.toString()
                        );

                JSONObject main =
                        data.getJSONObject("main");

                double temperature =
                        main.getDouble("temp");

                JSONObject wind =
                        data.getJSONObject("wind");

                double windSpeed =
                        wind.getDouble("speed");

                JSONArray weatherArray =
                        data.getJSONArray("weather");

                JSONObject weather =
                        weatherArray.getJSONObject(0);

                String description =
                        weather.getString(
                                "description"
                        );

                // HTML Response
                return "<html>"
                        + "<body>"
                        + "<h1>Weather in "
                        + cityName
                        + "</h1>"

                        + "<h2>Temperature: "
                        + temperature
                        + " °C</h2>"

                        + "<h2>Wind Speed: "
                        + windSpeed
                        + " m/s</h2>"

                        + "<h2>Description: "
                        + description
                        + "</h2>"

                        + "</body>"
                        + "</html>";

            } else {

                return "<h1>City not found!</h1>";
            }

        } catch (Exception e) {

            return "<h1>Error occurred</h1>";
        }
    }
}