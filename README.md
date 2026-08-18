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
1. Locate `src/main/java/yapBot.java`, right-click it, and choose `Run yapBot.main()`. If the code editor shows compile errors, try restarting IntelliJ.

Expected output:

```
yapBot ASCII-art banner
```

**Warning:** Keep the `src/main/java` folder as the root folder for Java files. Tools such as Gradle expect Java source files there.
