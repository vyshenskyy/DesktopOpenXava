# DesktopOpenXava

## Running OpenXava web application as a standalone desktop program

**OpenXava** ([https://www.openxava.org/](https://www.openxava.org/)) is a powerful tool for the development of data-oriented web applications using Java with minimal coding.

OpenXava applications are designed to run on servers with database management system (e.g. MySQL or HSQLDB) and web application server (e.g. Apache Tomcat). But for small systems that do not require access of multiple users, it would be useful to have the possibility to run this system on a standalone computer, like desktop application, with embedded database and Tomcat. The benefit of OpenXava in this situation is that it has many features like extensive filtering, plots, charts, control of input data, export and import etc. right “out of the box”. The users of OpenXava do not need to develop them. 

## Project description

**DesktopOpenXava** is a Maven project that has two modules: **OpenXavaSample** and **DesktopOpenXavaJar**. 

**OpenXavaSample** is a regular OpenXava project that produces .war file for the deployment on a web application server. You can develop  this project in a usual way - just leave HSQLDB as the database.

**DesktopOpenXavaJar** is the module that takes the `OpenXavaSample.war` file generated by `OpenXavaSample`, and creates executable .jar file with .war inside. The main() method of this .jar copies the .war to the working folder (if .war file already exists, it does not copy it again). Then it runs embedded HSQLDB, runs embedded Tomcat, and opens the application in the default browser.

### Run Maven

Run `mvn clean package` or `mvn clean install` from the directory of the parent project `DesktopOpenJava`. You can also run Maven from your IDE. The result will be written to the `target` folder of this parent project. You will find `RunWebApp-jar-with-dependencies.jar`,   `start.bat` that can be used to run the executable .jar on Windows, and `start.sh` to run the executable .jar on Linux.

#### Notes: 

- Maven takes `start.bat` file from `DesktopOpenJava\DesktopOpenXavaJar\src\main\resources`.
- Correct name of the web application must be specified for the main() method
``` java
private static final String APP_NAME = "OpenXavaSample";
```

### How to use (on Windows)

* Put two files (`RunWebApp-jar-with-dependencies.jar` and `start.bat`) in some empty folder and run `start.bat`.

`start.bat` opens a separate command prompt window and runs the application. To stop the application, just close this window. To run the system again, run `start.bat` again. The system will use  all files and folders generated in previous run.

### How to use (on Linux)

* Put two files (`RunWebApp-jar-with-dependencies.jar` and `start.sh`) in some empty folder and run `start.sh`.

`start.sh` opens a separate gnome terminal window and runs the application. To stop the application, just close this window. To run the system again, run `start.sh` again. The system will use  all files and folders generated in previous run. If you have a different terminal emulator, edit `start.sh` file and replace `gnome-terminal -- ` with the appropriate command, e.g. `xterm -e ` or `konsole -e `.


### How to insert an existing .war into the executable .jar

We can insert existing .war file into the executable .jar using **DesktopOpenXavaJar**. 

- Specify correct name of the web application in the main() method.
- Copy existing .war file into `DesktopOpenXavaJar\src\main\resources`
- In the pom.xml of the **DesktopOpenXavaJar** project, comment out the whole `maven-dependency-plugin`, because we do not want to take .war file from another project.
- From **DesktopOpenXavaJar** folder, run `mvn clean package` (or run Maven for this module from IDE).


 
  