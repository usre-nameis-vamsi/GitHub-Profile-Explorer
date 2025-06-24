import { spawn } from "child_process"

console.log("🚀 Starting GitHub Profile Explorer with Maven...")
console.log("📁 Running: mvn javafx:run")

// Execute mvn javafx:run command
const mvnProcess = spawn("mvn", ["javafx:run"], {
  stdio: "inherit",
  shell: true,
})

mvnProcess.on("error", (error) => {
  console.error("❌ Error starting the application:")
  console.error(error.message)

  if (error.code === "ENOENT") {
    console.log("\n💡 Troubleshooting tips:")
    console.log("1. Make sure Maven is installed and in your PATH")
    console.log("2. Verify you're in the correct project directory")
    console.log("3. Check that Java 21+ is installed")
    console.log("4. Ensure JavaFX is properly configured")
    console.log("\n🔧 Alternative commands to try:")
    console.log("   mvn clean compile")
    console.log("   mvn clean javafx:run")
  }
})

mvnProcess.on("close", (code) => {
  if (code === 0) {
    console.log("✅ Application closed successfully")
  } else {
    console.log(`❌ Application exited with code ${code}`)
    console.log("\n🔧 If you encountered issues, try these steps:")
    console.log("1. Clean and compile: mvn clean compile")
    console.log("2. Check dependencies: mvn dependency:tree")
    console.log("3. Verify JavaFX installation")
    console.log("4. Check Java version: java --version")
  }
})
