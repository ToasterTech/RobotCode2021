# RobotCode2021
Robot Code For 2021 FRC Season. Based off the default timed robot, with additional gradle configuration to allow style checks and automated unit testing on pushes. 

# Getting Started
Follow instructions on WPIlib documentation to install development environment and required libaries:
https://docs.wpilib.org/en/latest/docs/getting-started/getting-started-frc-control-system/wpilib-setup.html

# Build and deploy:
To build select the build option from vscode or optionally on the commandline run:
```
.\gradlew build
```

To deploy use the built in deploy robot code vscode option.

Additionally you can run tests locally:
```
.\gradlew test
```
or running the built in Test Robot Code option in vscode.


# Reccomended Development Environment
Reccomend using WPIlib FRC 2021 vscode with the following plugins if not already enabled:
  * Checkstyle for java https://github.com/jdneo/vscode-checkstyle
  * Language support for Java https://marketplace.visualstudio.com/items?itemName=redhat.java

Checkstyle should be configured automatically to detect style violations in vscode and make everyone's life much easier. 

# Style Check
To keep the code readable and correct we use Checkstyle to enforce Java style. We are using a slightly modified version of the google style guide referenced here:
https://google.github.io/styleguide/javaguide.html

# Branch and commit rules 
We should follow branch naming and commit naming using tasks defined in the trello board.
Branch name:
```
Task_{task_number}_{description_of_task}
```
Commit Message:
```
Task-{task_number}: {description of changes}
```

No code should be commited to master, and must pass code review on Pull Request. 

# Pull Request
Pull Requests must meet the criteria before being merged:
* Approved by atleast one mentor
* Pass checks (ie. should actually build)

Note style checks are part of the build process.
