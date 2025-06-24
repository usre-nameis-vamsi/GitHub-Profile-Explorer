package com.github.search;

import java.io.*;
import java.util.*;

public class UserHistoryService {
    private static final String HISTORY_FILE = "search_history.txt";
    private static final String FAVORITES_FILE = "favorites.txt";
    private static final int MAX_HISTORY_SIZE = 10;
    
    private List<String> searchHistory;
    private List<String> favorites;
    
    public UserHistoryService() {
        this.searchHistory = new ArrayList<>();
        this.favorites = new ArrayList<>();
        loadHistory();
        loadFavorites();
    }
    
    public void addToHistory(String username) {
        // Remove if already exists
        searchHistory.remove(username);
        // Add to beginning
        searchHistory.add(0, username);
        // Keep only last MAX_HISTORY_SIZE items
        if (searchHistory.size() > MAX_HISTORY_SIZE) {
            searchHistory = searchHistory.subList(0, MAX_HISTORY_SIZE);
        }
        saveHistory();
    }
    
    public void addToFavorites(String username) {
        if (!favorites.contains(username)) {
            favorites.add(username);
            saveFavorites();
        }
    }
    
    public void removeFromFavorites(String username) {
        favorites.remove(username);
        saveFavorites();
    }
    
    public List<String> getHistory() {
        return new ArrayList<>(searchHistory);
    }
    
    public List<String> getFavorites() {
        return new ArrayList<>(favorites);
    }
    
    public boolean isFavorite(String username) {
        return favorites.contains(username);
    }
    
    public void clearHistory() {
        searchHistory.clear();
        saveHistory();
    }
    
    private void loadHistory() {
        try (BufferedReader reader = new BufferedReader(new FileReader(HISTORY_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                searchHistory.add(line.trim());
            }
        } catch (IOException e) {
            // File doesn't exist yet, that's okay
        }
    }
    
    private void saveHistory() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(HISTORY_FILE))) {
            for (String username : searchHistory) {
                writer.println(username);
            }
        } catch (IOException e) {
            System.err.println("Failed to save search history: " + e.getMessage());
        }
    }
    
    private void loadFavorites() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FAVORITES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                favorites.add(line.trim());
            }
        } catch (IOException e) {
            // File doesn't exist yet, that's okay
        }
    }
    
    private void saveFavorites() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FAVORITES_FILE))) {
            for (String username : favorites) {
                writer.println(username);
            }
        } catch (IOException e) {
            System.err.println("Failed to save favorites: " + e.getMessage());
        }
    }
}
