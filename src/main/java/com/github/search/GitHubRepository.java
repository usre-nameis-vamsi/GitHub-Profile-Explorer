package com.github.search;

public class GitHubRepository {
    private int id;
    private String name;
    private String description;
    private String htmlUrl;
    private int stargazersCount;
    private int forksCount;
    private String language;
    private String updatedAt;
    
    public GitHubRepository(int id, String name, String description, String htmlUrl,
                           int stargazersCount, int forksCount, String language, String updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.htmlUrl = htmlUrl;
        this.stargazersCount = stargazersCount;
        this.forksCount = forksCount;
        this.language = language;
        this.updatedAt = updatedAt;
    }
    
    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getHtmlUrl() { return htmlUrl; }
    public int getStargazersCount() { return stargazersCount; }
    public int getForksCount() { return forksCount; }
    public String getLanguage() { return language; }
    public String getUpdatedAt() { return updatedAt; }
    
    // Setters
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setHtmlUrl(String htmlUrl) { this.htmlUrl = htmlUrl; }
    public void setStargazersCount(int stargazersCount) { this.stargazersCount = stargazersCount; }
    public void setForksCount(int forksCount) { this.forksCount = forksCount; }
    public void setLanguage(String language) { this.language = language; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
}
