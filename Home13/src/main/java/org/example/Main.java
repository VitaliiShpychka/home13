package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Main {
    public static void main(String[] args) {
        int userId = 1; // id користувача
        String urlUserPosts = "https://jsonplaceholder.typicode.com/users/" + userId + "/posts";
        String urlPostComments = null;
        int maxPostId = -1;
        int lastPostId = -1;
        String lastPostTitle = null;

        try {

            URL url = new URL(urlUserPosts);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            JsonArray userPosts = JsonParser.parseReader(inputStreamReader).getAsJsonArray();
            connection.disconnect();

            // Пошук останнього поста
            for (JsonElement post : userPosts) {
                JsonObject postObject = post.getAsJsonObject();
                int postId = postObject.get("id").getAsInt();
                if (postId > maxPostId) {
                    maxPostId = postId;
                    lastPostId = postId;
                    lastPostTitle = postObject.get("title").getAsString();
                }
            }

            if (lastPostId != -1) {
                // Отримання коментарів до останнього поста
                urlPostComments = "https://jsonplaceholder.typicode.com/posts/" + lastPostId + "/comments";
                url = new URL(urlPostComments);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                inputStream = connection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                JsonArray postComments = JsonParser.parseReader(inputStreamReader).getAsJsonArray();
                connection.disconnect();

                // Запис коментарів у файл
                String fileName = "user-" + userId + "-post-" + lastPostId + "-comments.json";
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                    writer.write(postComments.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println("Last posts  \"" + lastPostTitle + "\" of user with id " + userId + " was writing in file " + fileName);
            } else {
                System.out.println("User with id " + userId + " don't have posts");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



