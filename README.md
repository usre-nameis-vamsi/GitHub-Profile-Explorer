
 ## GitHub Profile Explorer.
 
 * A JavaFX desktop application for exploring GitHub profiles with enhanced features.
   
 🌐 Live Demo: https://githubprofileexplore.netlify.app/
##  Features:
   
  - 🔍 Search GitHub Users: Find any GitHub user by username
  - 👤 User Profiles: View detailed user information including bio, stats, and location
  - 📚 Repository Display: Browse user's recent repositories with stats
  - 📝 Search History: Keep track of your recent searches
  - 🎨 Modern UI: Beautiful gradient interface with smooth interactions
  - 🖱️ Clickable Repos: Click on repositories to open them in your browser
 ##  Requirements:
 - Java 21 or higher
  - Maven 3.6 or higher
  - Internet connection for GitHub API access
 
 ##   Setup Instructions for Eclipse:
  1. Import Project
     - Open Eclipse IDE
     - Go to File → Import
     - Select "Existing Maven Projects"
     - Browse to the project folder and click Finish
 
  2. Configure JavaFX
     - Right-click on the project → Properties
     - Go to Java Build Path → Modulepath
     - Add External JARs and include JavaFX runtime JARs if needed
 
  3. Run Configuration
     - Right-click on GitHubProfileSearchApp.java
     - Select Run As → Java Application
     - If module issues occur, use VM arguments:
       --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml
 
  4. Alternative: Maven Run
     - Right-click on project → Run As → Maven build...
     - In Goals field, enter: clean javafx:run
     - Click Run
 
  ##  Building the Application:
 - Using Maven CLI:
  mvn clean compile
    mvn javafx:run
     mvn clean package
 
  - Using Eclipse:
     - Right-click project → Run As → Maven build...
     - Goals: clean package
     - JAR will be in the target/ directory
 
 ##  📁 Project Structure:
  src/
     ├── main/
 * │   └── java/com/github/search/
 * │       ├── GitHubProfileSearchApp.java                       # Main application class
 * │       ├── GitHubApiService.java          # GitHub API integration
 * │       ├── GitHubUser.java                # User data model
 * │       ├── GitHubRepository.java          # Repository data model
 * │       └── UserHistoryService.java        # History management
 * ├── pom.xml                                # Maven configuration
 * └── README.md                              # This file
 
  ##  Usage:
  - Enter a GitHub username in the search field and click "Search"
  - View user details including avatar, bio, stats, and location
  - Browse recent repositories
  - Click on any repository to open it in the browser
  - Use search history for quick access to past queries
 
 ##   Dependencies:
  - JavaFX 21.0.1
  - Gson 2.10.1
  - Java HTTP Client
 
  ##  Troubleshooting:
  - JavaFX Runtime Issues:
    * Download SDK from https://openjfx.io/
     * Add VM args: --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml
 
  - API Rate Limits:
     * Unauthenticated requests: 60/hour
     * Add GitHub token in GitHubApiService.java for higher limits
 
  - Network Issues:
     * Check your internet connection
     * Ensure firewall allows GitHub API requests
 
  ## 📄 License:
  This project is open-source and licensed under the MIT License.
