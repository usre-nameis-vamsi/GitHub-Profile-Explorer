package com.github.search;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.concurrent.Task;
import javafx.application.Platform;

public class GitHubProfileSearchApp extends Application {
    
    private GitHubApiService apiService;
    private UserHistoryService historyService;
    private VBox mainContainer;
    private TextField searchField;
    private Button searchButton;
    private VBox contentArea;
    private VBox sidebarArea;
    private HBox mainLayout;
    
    @Override
    public void start(Stage primaryStage) {
        apiService = new GitHubApiService();
        historyService = new UserHistoryService();
        
        primaryStage.setTitle("GitHub Profile Explorer");
        
        // Create main container
        mainContainer = new VBox(20);
        mainContainer.setPadding(new Insets(30));
        mainContainer.setAlignment(Pos.TOP_CENTER);
        
        // Set gradient background
        String gradientStyle = "-fx-background-color: linear-gradient(to bottom right, #581c87, #1e3a8a, #312e81);";
        mainContainer.setStyle(gradientStyle);
        
        // Create header
        createHeader();
        
        // Create search form
        createSearchForm();
        
        // Create main layout
        mainLayout = new HBox(20);
        
        // Create sidebar
        sidebarArea = new VBox(15);
        sidebarArea.setPrefWidth(250);
        sidebarArea.setVisible(false);
        
        // Create content area
        contentArea = new VBox(20);
        contentArea.setAlignment(Pos.CENTER);
        HBox.setHgrow(contentArea, Priority.ALWAYS);
        
        // Show initial empty state
        showEmptyState();
        
        mainLayout.getChildren().addAll(sidebarArea, contentArea);
        mainContainer.getChildren().add(mainLayout);
        
        // Create scene
        Scene scene = new Scene(new ScrollPane(mainContainer), 1200, 800);
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void createHeader() {
        VBox header = new VBox(10);
        header.setAlignment(Pos.CENTER);
        
        Label title = new Label("🐙 GitHub Profile Explorer");
        title.setFont(Font.font("System", FontWeight.BOLD, 36));
        title.setTextFill(Color.WHITE);
        
        Label subtitle = new Label("Discover and explore GitHub profiles with enhanced features");
        subtitle.setFont(Font.font("System", 16));
        subtitle.setTextFill(Color.web("#c4b5fd"));
        
        header.getChildren().addAll(title, subtitle);
        mainContainer.getChildren().add(header);
    }
    
    private void createSearchForm() {
        HBox searchForm = new HBox(10);
        searchForm.setAlignment(Pos.CENTER);
        searchForm.setMaxWidth(600);
        
        searchField = new TextField();
        searchField.setPromptText("Search GitHub username...");
        searchField.setPrefHeight(40);
        searchField.setStyle("-fx-background-color: rgba(139, 92, 246, 0.5); " +
                           "-fx-text-fill: white; " +
                           "-fx-prompt-text-fill: #c4b5fd; " +
                           "-fx-border-color: #7c3aed; " +
                           "-fx-border-radius: 8; " +
                           "-fx-background-radius: 8;");
        HBox.setHgrow(searchField, Priority.ALWAYS);
        
        searchButton = new Button("Search");
        searchButton.setPrefHeight(40);
        searchButton.setStyle("-fx-background-color: #7c3aed; " +
                            "-fx-text-fill: white; " +
                            "-fx-border-radius: 8; " +
                            "-fx-background-radius: 8; " +
                            "-fx-font-weight: bold;");
        
        // Add search functionality
        searchButton.setOnAction(e -> performSearch());
        searchField.setOnAction(e -> performSearch());
        
        searchForm.getChildren().addAll(searchField, searchButton);
        mainContainer.getChildren().add(searchForm);
    }
    
    private void performSearch() {
        String username = searchField.getText().trim();
        if (username.isEmpty()) {
            showAlert("Please enter a username to search.");
            return;
        }
        
        searchButton.setDisabled(true);
        searchButton.setText("Searching...");
        
        Task<GitHubUser> searchTask = new Task<GitHubUser>() {
            @Override
            protected GitHubUser call() throws Exception {
                return apiService.getUser(username);
            }
            
            @Override
            protected void succeeded() {
                Platform.runLater(() -> {
                    GitHubUser user = getValue();
                    if (user != null) {
                        displayUser(user);
                        historyService.addToHistory(username);
                        updateSidebar();
                    }
                    resetSearchButton();
                });
            }
            
            @Override
            protected void failed() {
                Platform.runLater(() -> {
                    Throwable exception = getException();
                    String message = exception.getMessage();
                    if (message.contains("404")) {
                        showAlert("User not found: " + username);
                    } else {
                        showAlert("Error fetching user data: " + message);
                    }
                    resetSearchButton();
                });
            }
        };
        
        Thread searchThread = new Thread(searchTask);
        searchThread.setDaemon(true);
        searchThread.start();
    }
    
    private void resetSearchButton() {
        searchButton.setDisabled(false);
        searchButton.setText("Search");
    }
    
    private void showEmptyState() {
        contentArea.getChildren().clear();
        
        VBox emptyState = new VBox(20);
        emptyState.setAlignment(Pos.CENTER);
        emptyState.setStyle("-fx-background-color: rgba(139, 92, 246, 0.3); " +
                          "-fx-border-color: #7c3aed; " +
                          "-fx-border-radius: 15; " +
                          "-fx-background-radius: 15; " +
                          "-fx-padding: 60;");
        emptyState.setMaxWidth(400);
        
        Label iconLabel = new Label("🐙");
        iconLabel.setFont(Font.font(64));
        
        Label title = new Label("Search for a GitHub User");
        title.setFont(Font.font("System", FontWeight.BOLD, 20));
        title.setTextFill(Color.WHITE);
        
        Label subtitle = new Label("Enter a username above to get started");
        subtitle.setFont(Font.font("System", 16));
        subtitle.setTextFill(Color.web("#c4b5fd"));
        
        emptyState.getChildren().addAll(iconLabel, title, subtitle);
        contentArea.getChildren().add(emptyState);
    }
    
    private void displayUser(GitHubUser user) {
        contentArea.getChildren().clear();
        sidebarArea.setVisible(true);
        
        VBox userCard = createUserCard(user);
        contentArea.getChildren().add(userCard);
        
        loadUserRepositories(user.getLogin());
    }
    
    private VBox createUserCard(GitHubUser user) {
        VBox card = new VBox(20);
        card.setStyle("-fx-background-color: rgba(139, 92, 246, 0.3); " +
                     "-fx-border-color: #7c3aed; " +
                     "-fx-border-radius: 15; " +
                     "-fx-background-radius: 15; " +
                     "-fx-padding: 30;");
        
        HBox userInfo = new HBox(30);
        userInfo.setAlignment(Pos.CENTER_LEFT);
        
        // Avatar
        VBox avatarBox = new VBox();
        avatarBox.setAlignment(Pos.CENTER);
        ImageView avatar = new ImageView();
        try {
            avatar.setImage(new Image(user.getAvatarUrl()));
            avatar.setFitWidth(120);
            avatar.setFitHeight(120);
            avatar.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 0);");
        } catch (Exception e) {
            // Fallback to emoji if image fails
            Label avatarFallback = new Label("👤");
            avatarFallback.setFont(Font.font(80));
            avatarFallback.setStyle("-fx-background-color: rgba(124, 58, 237, 0.5); " +
                                  "-fx-background-radius: 50; " +
                                  "-fx-padding: 20;");
            avatarBox.getChildren().add(avatarFallback);
        }
        
        if (avatar.getImage() != null) {
            avatarBox.getChildren().add(avatar);
        }
        
        // User details
        VBox details = new VBox(15);
        HBox.setHgrow(details, Priority.ALWAYS);
        
        // Name and username
        Label name = new Label(user.getName() != null ? user.getName() : user.getLogin());
        name.setFont(Font.font("System", FontWeight.BOLD, 24));
        name.setTextFill(Color.WHITE);
        
        Label username = new Label("@" + user.getLogin());
        username.setFont(Font.font("System", 16));
        username.setTextFill(Color.web("#c4b5fd"));
        
        VBox nameBox = new VBox(5);
        nameBox.getChildren().addAll(name, username);
        
        // Bio
        if (user.getBio() != null && !user.getBio().isEmpty()) {
            Label bio = new Label(user.getBio());
            bio.setFont(Font.font("System", 14));
            bio.setTextFill(Color.web("#e5e7eb"));
            bio.setWrapText(true);
            nameBox.getChildren().add(bio);
        }
        
        // Stats
        HBox stats = new HBox(40);
        stats.setAlignment(Pos.CENTER_LEFT);
        
        VBox reposStats = createStatBox("Repositories", String.valueOf(user.getPublicRepos()));
        VBox followersStats = createStatBox("Followers", String.valueOf(user.getFollowers()));
        VBox followingStats = createStatBox("Following", String.valueOf(user.getFollowing()));
        
        stats.getChildren().addAll(reposStats, followersStats, followingStats);
        
        // Additional info
        VBox additionalInfo = new VBox(8);
        if (user.getLocation() != null) {
            Label location = new Label("📍 " + user.getLocation());
            location.setTextFill(Color.web("#c4b5fd"));
            additionalInfo.getChildren().add(location);
        }
        if (user.getCompany() != null) {
            Label company = new Label("🏢 " + user.getCompany());
            company.setTextFill(Color.web("#c4b5fd"));
            additionalInfo.getChildren().add(company);
        }
        if (user.getBlog() != null && !user.getBlog().isEmpty()) {
            Label blog = new Label("🔗 " + user.getBlog());
            blog.setTextFill(Color.web("#c4b5fd"));
            additionalInfo.getChildren().add(blog);
        }
        
        details.getChildren().addAll(nameBox, stats, additionalInfo);
        userInfo.getChildren().addAll(avatarBox, details);
        card.getChildren().add(userInfo);
        
        return card;
    }
    
    private VBox createStatBox(String label, String value) {
        VBox statBox = new VBox(5);
        statBox.setAlignment(Pos.CENTER);
        
        Label valueLabel = new Label(value);
        valueLabel.setFont(Font.font("System", FontWeight.BOLD, 20));
        valueLabel.setTextFill(Color.WHITE);
        
        Label labelText = new Label(label);
        labelText.setFont(Font.font("System", 12));
        labelText.setTextFill(Color.web("#c4b5fd"));
        
        statBox.getChildren().addAll(valueLabel, labelText);
        return statBox;
    }
    
    private void loadUserRepositories(String username) {
        Task<java.util.List<GitHubRepository>> repoTask = new Task<java.util.List<GitHubRepository>>() {
            @Override
            protected java.util.List<GitHubRepository> call() throws Exception {
                return apiService.getUserRepositories(username, 6);
            }
            
            @Override
            protected void succeeded() {
                Platform.runLater(() -> {
                    java.util.List<GitHubRepository> repos = getValue();
                    displayRepositories(repos);
                });
            }
            
            @Override
            protected void failed() {
                Platform.runLater(() -> {
                    System.err.println("Failed to load repositories: " + getException().getMessage());
                });
            }
        };
        
        Thread repoThread = new Thread(repoTask);
        repoThread.setDaemon(true);
        repoThread.start();
    }
    
    private void displayRepositories(java.util.List<GitHubRepository> repositories) {
        VBox repoSection = new VBox(15);
        repoSection.setStyle("-fx-background-color: rgba(139, 92, 246, 0.3); " +
                           "-fx-border-color: #7c3aed; " +
                           "-fx-border-radius: 15; " +
                           "-fx-background-radius: 15; " +
                           "-fx-padding: 30;");
        
        Label repoTitle = new Label("📚 Recent Repositories");
        repoTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        repoTitle.setTextFill(Color.WHITE);
        
        GridPane repoGrid = new GridPane();
        repoGrid.setHgap(15);
        repoGrid.setVgap(15);
        
        int col = 0, row = 0;
        for (GitHubRepository repo : repositories) {
            VBox repoCard = createRepositoryCard(repo);
            repoGrid.add(repoCard, col, row);
            
            col++;
            if (col >= 2) {
                col = 0;
                row++;
            }
        }
        
        repoSection.getChildren().addAll(repoTitle, repoGrid);
        contentArea.getChildren().add(repoSection);
    }
    
    private VBox createRepositoryCard(GitHubRepository repo) {
        VBox card = new VBox(10);
        card.setStyle("-fx-background-color: rgba(124, 58, 237, 0.3); " +
                     "-fx-border-color: #8b5cf6; " +
                     "-fx-border-radius: 10; " +
                     "-fx-background-radius: 10; " +
                     "-fx-padding: 15;");
        card.setPrefWidth(250);
        
        Label name = new Label(repo.getName());
        name.setFont(Font.font("System", FontWeight.BOLD, 16));
        name.setTextFill(Color.WHITE);
        
        if (repo.getDescription() != null && !repo.getDescription().isEmpty()) {
            Label description = new Label(repo.getDescription());
            description.setFont(Font.font("System", 12));
            description.setTextFill(Color.web("#c4b5fd"));
            description.setWrapText(true);
            description.setMaxHeight(40);
            card.getChildren().add(description);
        }
        
        HBox stats = new HBox(15);
        stats.setAlignment(Pos.CENTER_LEFT);
        
        if (repo.getLanguage() != null) {
            Label language = new Label(repo.getLanguage());
            language.setStyle("-fx-background-color: rgba(124, 58, 237, 0.5); " +
                            "-fx-text-fill: #e5e7eb; " +
                            "-fx-padding: 3 8; " +
                            "-fx-background-radius: 12;");
            stats.getChildren().add(language);
        }
        
        Label stars = new Label("⭐ " + repo.getStargazersCount());
        stars.setTextFill(Color.web("#c4b5fd"));
        stars.setFont(Font.font("System", 12));
        
        Label forks = new Label("🍴 " + repo.getForksCount());
        forks.setTextFill(Color.web("#c4b5fd"));
        forks.setFont(Font.font("System", 12));
        
        stats.getChildren().addAll(stars, forks);
        
        card.getChildren().addAll(name, stats);
        
        // Make clickable
        card.setOnMouseClicked(e -> {
            try {
                java.awt.Desktop.getDesktop().browse(java.net.URI.create(repo.getHtmlUrl()));
            } catch (Exception ex) {
                showAlert("Could not open repository URL");
            }
        });
        
        card.setOnMouseEntered(e -> card.setStyle(card.getStyle() + "-fx-cursor: hand;"));
        
        return card;
    }
    
    private void updateSidebar() {
        sidebarArea.getChildren().clear();
        
        // Search History
        java.util.List<String> history = historyService.getHistory();
        if (!history.isEmpty()) {
            VBox historySection = new VBox(10);
            historySection.setStyle("-fx-background-color: rgba(139, 92, 246, 0.3); " +
                                  "-fx-border-color: #7c3aed; " +
                                  "-fx-border-radius: 10; " +
                                  "-fx-background-radius: 10; " +
                                  "-fx-padding: 15;");
            
            Label historyTitle = new Label("📝 Recent Searches");
            historyTitle.setFont(Font.font("System", FontWeight.BOLD, 16));
            historyTitle.setTextFill(Color.WHITE);
            
            historySection.getChildren().add(historyTitle);
            
            for (String username : history.subList(0, Math.min(5, history.size()))) {
                Button historyButton = new Button(username);
                historyButton.setStyle("-fx-background-color: rgba(124, 58, 237, 0.5); " +
                                     "-fx-text-fill: #e5e7eb; " +
                                     "-fx-border-color: transparent; " +
                                     "-fx-background-radius: 5;");
                historyButton.setPrefWidth(200);
                historyButton.setOnAction(e -> {
                    searchField.setText(username);
                    performSearch();
                });
                historySection.getChildren().add(historyButton);
            }
            
            sidebarArea.getChildren().add(historySection);
        }
    }
    
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("GitHub Profile Explorer");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
