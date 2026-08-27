# yapBot

This is a chatbot project. Given below are instructions on how to use it.

## Setting up in IntelliJ

Prerequisites: JDK 25, update IntelliJ to the most recent version.

1. Open IntelliJ (if you are not in the welcome screen, click `File` > `Close Project` to close the existing project first).
1. Open the project into IntelliJ as follows:
   1. Click `Open`.
   1. Select the project directory and click `OK`.
   1. If there are any further prompts, accept the defaults.
1. Configure the project to use **JDK 25** (not another version) as explained [in IntelliJ's documentation](https://www.jetbrains.com/help/idea/sdk.html#set-up-jdk).
   In the same dialog, set **Project language level** to the `SDK default` option.
1. Locate `src/main/java/yapbot/YapBot.java`, right-click it, and choose `Run YapBot.main()`. If the code editor shows compile errors, try restarting IntelliJ.

Expected output:

```
yapBot ASCII-art banner
```

**Warning:** Keep the `src/main/java` folder as the root folder for Java files. Tools such as Gradle expect Java source files there.

## Packaging as a JAR file

The project uses the [Shadow](https://gradleup.com/shadow/) Gradle plugin (configured in `build.gradle`) to package the app, together with all its dependencies, into a single executable "fat" JAR.

**To create the JAR:**

```
./gradlew clean shadowJar
```

(On Windows, use `gradlew.bat clean shadowJar` instead of `./gradlew clean shadowJar`.)

**To locate it:** the JAR is created at `build/libs/yapBot.jar`.

**To run it:**

1. Copy `yapBot.jar` into an empty folder.
2. Open a command window in that folder.
3. Run:
   ```
   java -jar "yapBot.jar"
   ```

The JAR is fully self-contained (no other files or classpath setup needed), and creates its own `data/yapBot.txt` save file in the folder it's run from, the same way running the app from source does.

Note: the generated JAR file is not committed to this repository (see `.gitignore`) since it's a build artifact, not source code. If distributing a release, publish it as a [GitHub release](https://docs.github.com/en/repositories/releasing-projects-on-github/managing-releases-in-a-repository) instead, attaching the JAR as a release binary.
