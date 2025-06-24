package com.github.search;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GitHubApiService {
    
    private static final String GITHUB_API_BASE = "https://api.github.com";
    private final Gson gson;
    
    public GitHubApiService() {
        this.gson = new Gson();
    }
    
    public GitHubUser getUser(String username) throws Exception {
        String url = GITHUB_API_BASE + "/users/" + username;
        String response = makeHttpRequest(url);
        
        JsonObject userJson = gson.fromJson(response, JsonObject.class);
        
        return new GitHubUser(
            userJson.get("login").getAsString(),
            userJson.has("name") && !userJson.get("name").isJsonNull() ? 
                userJson.get("name").getAsString() : null,
            userJson.get("avatar_url").getAsString(),
            userJson.has("bio") && !userJson.get("bio").isJsonNull() ? 
                userJson.get("bio").getAsString() : null,
            userJson.get("public_repos").getAsInt(),
            userJson.get("followers").getAsInt(),
            userJson.get("following").getAsInt(),
            userJson.has("location") && !userJson.get("location").isJsonNull() ? 
                userJson.get("location").getAsString() : null,
            userJson.has("blog") && !userJson.get("blog").isJsonNull() ? 
                userJson.get("blog").getAsString() : null,
            userJson.has("company") && !userJson.get("company").isJsonNull() ? 
                userJson.get("company").getAsString() : null,
            userJson.has("email") && !userJson.get("email").isJsonNull() ? 
                userJson.get("email").getAsString() : null,
            userJson.get("created_at").getAsString(),
            userJson.get("html_url").getAsString()
        );
    }
    
    public List<GitHubRepository> getUserRepositories(String username, int limit) throws Exception {
        String url = GITHUB_API_BASE + "/users/" + username + "/repos?sort=updated&per_page=" + limit;
        String response = makeHttpRequest(url);
        
        JsonArray reposArray = gson.fromJson(response, JsonArray.class);
        List<GitHubRepository> repositories = new ArrayList<>();
        
        for (int i = 0; i < reposArray.size(); i++) {
            JsonObject repoJson = reposArray.get(i).getAsJsonObject();
            
            GitHubRepository repo = new GitHubRepository(
                repoJson.get("id").getAsInt(),
                repoJson.get("name").getAsString(),
                repoJson.has("description") && !repoJson.get("description").isJsonNull() ? 
                    repoJson.get("description").getAsString() : null,
                repoJson.get("html_url").getAsString(),
                repoJson.get("stargazers_count").getAsInt(),
                repoJson.get("forks_count").getAsInt(),
                repoJson.has("language") && !repoJson.get("language").isJsonNull() ? 
                    repoJson.get("language").getAsString() : null,
                repoJson.get("updated_at").getAsString()
            );
            
            repositories.add(repo);
        }
        
        return repositories;
    }
    
    private String makeHttpRequest(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/vnd.github.v3+json");
        connection.setRequestProperty("User-Agent", "GitHub-Profile-Explorer");
        
        int responseCode = connection.getResponseCode();
        
        if (responseCode == 404) {
            throw new Exception("404 - User not found");
        } else if (responseCode != 200) {
            throw new Exception("HTTP Error: " + responseCode);
        }
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        
        reader.close();
        connection.disconnect();
        
        return response.toString();
    }
}
