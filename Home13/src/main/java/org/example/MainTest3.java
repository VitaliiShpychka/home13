package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

    public class MainTest3 {

        private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

        public static void main(String[] args) {
            int userId = 1;
            List<Todo> todos = getOpenTodosForUser(userId);
            System.out.println("Open todos for user with ID " + userId + ":");
            System.out.println(Arrays.toString(todos.toArray()));
        }

        public static List<Todo> getOpenTodosForUser(int userId) {
            String json = sendGetRequest(BASE_URL + "/users/" + userId + "/todos");
            Gson gson = new Gson();
            List<Todo> todos = gson.fromJson(json, new TypeToken<List<Todo>>(){}.getType());
            todos.removeIf(todo -> todo.isCompleted());
            return todos;
        }

        private static String sendGetRequest(String url) {
            StringBuilder response = new StringBuilder();
            try {
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("GET");
                int responseCode = con.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                } else {
                    System.out.println("GET request failed: " + responseCode);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response.toString();
        }

        public static class Todo {
            private int userId;
            private int id;
            private String title;
            private boolean completed;

            public int getUserId() {
                return userId;
            }
            public void setUserId(int userId) {
                this.userId = userId;
            }
            public int getId() {
                return id;
            }
            public void setId(int id) {
                this.id = id;
            }
            public String getTitle() {
                return title;
            }
            public void setTitle(String title) {
                this.title = title;
            }
            public boolean isCompleted() {
                return completed;
            }
            public void setCompleted(boolean completed) {
                this.completed = completed;
            }
            @Override
            public String toString() {
                return "Todo [userId=" + userId + ", id=" + id + ", title=" + title + ", completed=" + completed + "]";
            }
        }
    }


