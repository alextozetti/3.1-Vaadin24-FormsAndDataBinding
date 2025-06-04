# Vaadin 24 Creating Forms training

This is the training material for the Vaadin 24 Creating Forms training. It is a multi-module maven project which contains 2 sub modules: exercises and solutions.
It also contains the instructions for doing the exercises in the [exercises.pdf](exercises.pdf) file, and a pdf version of the slides in the [handout.pdf](handout.pdf) file

### Prerequisites

JDK 17 or later and [Node.js 18](https://nodejs.org/en/download/) or later 

If you plan to run the applications from the command line, you need to install [Maven](https://maven.apache.org)

### Instructions

Import the Maven project to your Favourite IDE. We recommend using [Eclipse](https://eclipse.org/) or [IntelliJ IDEA](https://www.jetbrains.com/idea/).

* [How to import a Maven project in IntelliJ IDEA](https://vaadin.com/tutorials/import-maven-project-intellij-idea)
* [How to import a Maven project in Eclipse](https://vaadin.com/tutorials/import-maven-project-eclipse)
* [How to import a Maven project in NetBeans](https://vaadin.com/tutorials/import-maven-project-netbeans)


You are supposed to do the exercises on the exercises sub-module and follow the the instructions in the [exercises.pdf](exercises.pdf) file.
                              
### Running the applications from command line

To run the exercises, run the Maven goal `spring-boot:run` on the exercises module in your favorite IDE or alternatively in the command line as

```
cd exercises
mvn spring-boot:run
```

To run the solutions, run the Maven goal `spring-boot:run` on the solutions module in your favorite IDE or alternatively in the command line as

```
cd solutions
mvn spring-boot:run
```

By default, the application is running on port 8080, so you can go to [localhost:8080](http://localhost:8080/) to check the result. 

If you want to run the exercises and solutions at the same time, you can specify a different port for the solutions, i.e.

```
cd solutions
mvn spring-boot:run -Dspring-boot.run.arguments=--port=8081
``` 

### Running the applications from IDE

You can run both exercises and solutions as a normal Spring Boot applications directly from IDE. You only have make sure, that your run configuration uses the working directory of the maven module where the Spring Boot application is placed.

To run exercises:
1. Run ExercisesSpringBootApplication.java
2. Make sure that the working directory is set to './exercises'

To run solutions:
1. Run SolutionsSpringBootApplication.java
2. Make sure that the working directory is set to './solutions'

For those who are using IntelliJ Idea, we prepared ready-made run configurations in both exercises and solutions submodules (files ExercisesSpringBootApplication.run.xml and SolutionsSpringBootApplication.run.xml). You can use these to run the applications. Run configurations should be automatically imported when you import this project to IntelliJ Idea.

## License

This is a proprietary project - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

There are other training courses available at [Vaadin Training](https://vaadin.com/training/courses)
