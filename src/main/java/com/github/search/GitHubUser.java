package com.github.search;

public class GitHubUser {
    private String login;
    private String name;
    private String avatarUrl;
    private String bio;
    private int publicRepos;
    private int followers;
    private int following;
    private String location;
    private String blog;
    private String company;
    private String email;
    private String createdAt;
    private String htmlUrl;
    
    public GitHubUser(String login, String name, String avatarUrl, String bio, 
                     int publicRepos, int followers, int following, String location,
                     String blog, String company, String email, String createdAt, String htmlUrl) {
        this.login = login;
        this.name = name;
        this.avatarUrl = avatarUrl;
        this.bio = bio;
        this.publicRepos = publicRepos;
        this.followers = followers;
        this.following = following;
        this.location = location;
        this.blog = blog;
        this.company = company;
        this.email = email;
        this.createdAt = createdAt;
        this.htmlUrl = htmlUrl;
    }
    
    // Getters
    public String getLogin() { return login; }
    public String getName() { return name; }
    public String getAvatarUrl() { return avatarUrl; }
    public String getBio() { return bio; }
    public int getPublicRepos() { return publicRepos; }
    public int getFollowers() { return followers; }
    public int getFollowing() { return following; }
    public String getLocation() { return location; }
    public String getBlog() { return blog; }
    public String getCompany() { return company; }
    public String getEmail() { return email; }
    public String getCreatedAt() { return createdAt; }
    public String getHtmlUrl() { return htmlUrl; }
    
    // Setters
    public void setLogin(String login) { this.login = login; }
    public void setName(String name) { this.name = name; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
    public void setBio(String bio) { this.bio = bio; }
    public void setPublicRepos(int publicRepos) { this.publicRepos = publicRepos; }
    public void setFollowers(int followers) { this.followers = followers; }
    public void setFollowing(int following) { this.following = following; }
    public void setLocation(String location) { this.location = location; }
    public void setBlog(String blog) { this.blog = blog; }
    public void setCompany(String company) { this.company = company; }
    public void setEmail(String email) { this.email = email; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    public void setHtmlUrl(String htmlUrl) { this.htmlUrl = htmlUrl; }
}
