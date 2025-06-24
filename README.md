# GitHub Profile Explorer - Java Edition

A JavaFX desktop application for exploring GitHub profiles with enhanced features.

## 🚀 Features

- 🔍 **Search GitHub Users** - Find any GitHub user by username
- 👤 **User Profiles** - View detailed user information including bio, stats, and location
- 📚 **Repository Display** - Browse user's recent repositories with stats
- 📝 **Search History** - Keep track of your recent searches
- 🎨 **Modern UI** - Beautiful gradient interface with smooth interactions
- 🖱️ **Clickable Repos** - Click on repositories to open them in your browser

## 📋 Requirements

- Java 21 or higher
- Maven 3.6 or higher
- Internet connection for GitHub API access

## 🛠️ Setup Instructions for Eclipse

### 1. Import Project
1. Open Eclipse IDE
2. Go to `File` → `Import`
3. Select `Existing Maven Projects`
4. Browse to the project folder and click `Finish`

### 2. Configure JavaFX
1. Right-click on the project → `Properties`
2. Go to `Java Build Path` → `Modulepath`
3. Add External JARs and include JavaFX runtime JARs if needed

### 3. Run Configuration
1. Right-click on `GitHubProfileSearchApp.java`
2. Select `Run As` → `Java Application`
3. If you encounter module issues, add these VM arguments:
   \`\`\`
   --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml
   \`\`\`

### 4. Alternative: Maven Run
1. Right-click on project → `Run As` → `Maven build...`
2. In Goals field, enter: `clean javafx:run`
3. Click `Run`

## 🏗️ Building the Application

### Using Maven Command Line:
\`\`\`bash
# Compile the project
mvn clean compile

# Run the application
mvn javafx:run

# Create executable JAR
mvn clean package
\`\`\`

### Using Eclipse:
1. Right-click project → `Run As` → `Maven build...`
2. Goals: `clean package`
3. The executable JAR will be in the `target` folder

## 📁 Project Structure

\`\`\`
src/
├── main/
│   └── java/com/github/search/
│       ├── GitHubProfileSearchApp.java    # Main application class
│       ├── GitHubApiService.java          # GitHub API integration
│       ├── GitHubUser.java                # User data model
│       ├── GitHubRepository.java          # Repository data model
│       └── UserHistoryService.java        # History management
├── pom.xml                                # Maven configuration
└── README.md                              # This file
\`\`\`

## 🎯 Usage

1. **Search for Users**: Enter a GitHub username in the search field and click "Search"
2. **View Profile**: See user details including avatar, bio, stats, and location
3. **Browse Repositories**: Scroll through the user's recent repositories
4. **Click Repositories**: Click on any repository card to open it in your browser
5. **Use History**: Click on recent searches in the sidebar to quickly revisit users

## 📦 Dependencies

- **JavaFX 21.0.1** - Modern UI framework
- **Gson 2.10.1** - JSON parsing for GitHub API responses
- **Java HTTP Client** - Built-in HTTP client for API requests

## 🔧 Troubleshooting

### JavaFX Runtime Issues
If you get JavaFX runtime errors:
1. Download JavaFX SDK from [openjfx.io](https://openjfx.io/)
2. Add VM arguments: `--module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml`

### API Rate Limiting
- GitHub API allows 60 requests per hour for unauthenticated requests
- For higher limits, you can add GitHub token authentication to `GitHubApiService.java`

### Network Issues
- Ensure you have internet connection
- Check if your firewall allows the application to access GitHub API

## 📄 License

This project is open source and available under the MIT License.
