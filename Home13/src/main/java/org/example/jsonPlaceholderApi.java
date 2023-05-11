package org.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class jsonPlaceholderApi {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    public static void main(String[] args) throws IOException {
        createUser("{\"name\": \"John Doe\", \"username\": \"johndoe\", \"email\": \"johndoe@example.com\"}");
        updateUser(1, "{\"id\": 1, \"name\": \"John Doe\", \"username\": \"johndoe\", \"email\": \"johndoe@example.com\"}");
        deleteUser(1);
        getAllUsers();
        getUserById(2);
        //getUserByUsername("Bret");
    }

    public static void createUser(String json) throws IOException {
        URL url = new URL(BASE_URL + "/users");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        outputStream.writeBytes(json);
        outputStream.flush();
        outputStream.close();

        BufferedReader inputReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = inputReader.readLine()) != null) {
            response.append(inputLine);
        }
        inputReader.close();

        System.out.println("New user created with response: " + response.toString());
    }

    public static void updateUser(int userId, String json) throws IOException {
        URL url = new URL(BASE_URL + "/users/" + userId);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        outputStream.writeBytes(json);
        outputStream.flush();
        outputStream.close();

        BufferedReader inputReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = inputReader.readLine()) != null) {
            response.append(inputLine);
        }
        inputReader.close();

        System.out.println("User updated with response: " + response.toString());
    }

    public static void deleteUser(int userId) throws IOException {
        URL url = new URL(BASE_URL + "/users/" + userId);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("DELETE");

        int responseCode = connection.getResponseCode();
        if (responseCode >= 200 && responseCode < 300) {
            System.out.println("User with id " + userId + " deleted successfully."+ responseCode);
        } else {
            System.out.println("Error deleting user with id " + userId + ". Response code: " + responseCode);
        }
    }

    public static void getAllUsers() throws IOException {
        URL url = new URL(BASE_URL + "/users");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader inputReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = inputReader.readLine()) != null) {
            response.append(inputLine);
        }
        inputReader.close();

        System.out.println("All users: " + response.toString());
    }

    public static void getUserById(int userId) throws IOException {
        URL url = new URL(BASE_URL + "/users/" + userId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                System.out.println(response.toString());
            } else {
                System.out.println("Error: " + responseCode);
            }
        }
    public static void getUserByUsername(String username) throws IOException {
        String url = BASE_URL + "?username=" + URLEncoder.encode(username, "UTF-8");
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println(response.toString());
        } else {
            System.out.println("Error: " + responseCode);
        }
    }
}


