# Dungeon Crawl (sprint 2)

## Story

Last week you created a pretty good [Roguelike](https://en.wikipedia.org/wiki/Roguelike) game. It already has some features but the players have no opportunity to save their games. It can be annoying especially when you have to leave the game aside suddenly.

The gamer community bragging for saving functionality and some other new interesting ideas like:

- sharing game with each other
- maps of different sizes
- player tracking camera movement

The management is handing out a **prioritized list** of new user stories that should be appended to the unfinished stories from last week in your product backlog. Try to estimate these new stories as well, and based on the estimations pick the stories your team can finish in this sprint.

> Using database for saving game state feature is a business critical item which overrides every other priority now!

Let's continue this entertaining project, and make our players happier!

## What are you going to learn?

- Serialization of objects
- Communicating with database
- Writing unit tests for your classes
- Design pattern: **Data Access Object**

## Tasks

1. Create a new sprint from the existing backlog. Last week you had a long list of stories, a few new stories this week.
    - The new items are added to the backlog
    - The team has created a new sprint plan based upon the unified backlog
    - The mandatory "Saving game" backlog item is in Sprint 2 and planned in detail

2. As you will work in a new repository but you need the code from the previous sprint, add the `dungeon-crawl-2` repository as a new remote to the previous sprint's repository, then pull (merge) and push your changes into it.
    - There is a merge commit in the project's repository that contains code from the previous sprint

3. Allow the user to save the current state of the game in a database. Extend the given schema if needed.
    - The application uses PostgreSQL database with the given schema: `schema_ddl.sql`
    - The application respects the `PSQL_USER_NAME`, `PSQL_PASSWORD`, `PSQL_DB_NAME` environment variables
    - Students has an Entity Relationship diagram (connections between classes, 1-1, 1-many...) in a digitalized format.
    - When the user hits `Ctrl+s`, a modal window pops up with one text input field (labelled `Name`) and two buttons, `Save` and `Cancel`.
- When the user clicks on `Save`, the game saves the current state (current map, player' position and content of inventory) in the database
  - If the given name is new then it saves the state
  - If the given username already exist in the db the system shows a dialogbox with a question: `Would you like to overwrite the already existing state?`
    - Choosing `Yes`: the already existing state is updated and all modal window closes
    - Choosing `No`: the dialog closes and the name input field content on the saving dialog is selected again
  - In case of clicking on `Cancel` the saving process terminates without any further action
- The modal window is automatically closed after the operation
    - Already discovered maps are also saved in DB.
    - There is a `Load` menu which brings up a modal window, showing the previously saved states with their names as a selectable list. Choosing an element loads the selected game state with the proper map, position and inventory.

4. Allow the user to export (serialize) his game state into a text file, and load (deserialize) the game from the exported file.
    - There is a menu item with a label `Export game` which triggers the export mechanism
    - The exporting process asks the user for the location and the name of the exported file. The file is created in the defined directory using the given name as a JSON file. eg. `<my-fantastic-game>.json`
    - The file stores every necessary game state information in JSON format.
    - There is a menu button labeled `Import` for importing a previously saved game.
- The system shows a file browser to select an exported file
  - If the chosen file isn't in proper format, the application shows a dialog window (OK, Cancel) with the following message: `IMPORT ERROR! Unfortunately the given file is in wrong format. Please try another one!`
    - If the user clicks on `OK` button then the window closes without any further action
    - If the user click on `Cancel` all dialog and modal window closes
  - In case the file is in the required format, the game loads the state, and navigates on the map to the point where the user left the game with its inventory

5. The customer seeks for quality assurance and wants to see that your code is covered by unit tests. It is important that beyond positive test cases also cover negative scenarios.
    - Every unit test method is well arranged and follows the `arrange`-`act`-`assert` structure
    - Unit test classes and methods follow these naming conventions consistently:
- classes: `<The name of the tested class>Test`
- methods: `<the name of the tested method>_<expected input / tested state>_<expected behavior>`
    - Every test class has at least one negative test case (and more if it's plausible)
    - Code coverage of self-created business logic classes is above 90%

## General requirements

None

## Hints

- Break the backlog items into smaller tasks so that you can work in parallel
- The given DB schema is only an example. Probably you need to alter is according to the requirements. For instance it doesn't contain any info about inventory or discovered maps by the player
- Write as many unit tests as possible to cover your business logic
- If a method takes a reference type parameter there should be test for getting `null` as an argument. It is called negative test cases.
- You can read easily an environment variable's value: `System.getenv("VAR_NAME");`
- You can import the sample data file into `psql` with the `\i` command or run it via the Database tool in IntelliJ.
- In IntelliJ language injections let you work with pieces of code in other languages embedded in your code. When you inject a language (such as PostgreSQL) into a string literal, you get comprehensive code assistance for editing that literal.
- Do you remember how to set environment variables for your run configuration? [here](https://www.jetbrains.com/help/objc/add-environment-variables-and-program-arguments.html)
- For serialization you need to add necessary dependency to your `pom.xml` and reload the maven project

## Starting your project

To start your project click [this link](https://journey.code.cool/v2/project/team/blueprint/dungeon-crawl-2/java), and make sure to **select or create your team**.

## Background materials

- <i class="far fa-exclamation"></i> [Software testing](https://learn.code.cool/full-stack/#/../pages/general/software-testing)
- <i class="far fa-open_book"></i> [Positive or negative](https://stackoverflow.com/questions/8162423)
- <i class="far fa-exclamation"></i> [How to design classes](https://learn.code.cool/full-stack/#/../pages/java/how-to-design-classes)
- <i class="far fa-exclamation"></i> [Introduction to jdbc](https://learn.code.cool/full-stack/#/../pages/java/introduction-to-jdbc)
- <i class="far fa-exclamation"></i> [jdbc basics](https://docs.oracle.com/javase/tutorial/jdbc/basics/index.html)
- <i class="far fa-exclamation"></i> [DAO pattern in Java](https://www.baeldung.com/java-dao-pattern)
- <i class="far fa-exclamation"></i> [Serialization in Java](https://learn.code.cool/full-stack/#/../pages/java/serialization-in-java)
- <i class="far fa-exclamation"></i> [Compare two popular serialization framwork](https://www.baeldung.com/jackson-vs-gson)

### References

The tiles used in the game are from [1-Bit Pack by Kenney](https://kenney.nl/assets/bit-pack),
shared on [CC0 1.0 Universal license](https://creativecommons.org/publicdomain/zero/1.0/).
