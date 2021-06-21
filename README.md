# Sudoku
Autor: Assem Hussein, 5312065

This is a Sudoku game written in Java with help of Processing core library. This game was developed within the scope of a final project for the module "Programming interactive systems" at THM — University of Applied Sciences 2021 (See license). Have fun! 

### Sudoku in a nutshell

Sudoku is a logic puzzle with a schematic basic structure: Except for special types of games and variants (such as the rather unknown circle Sudoku or the so-called "Samurai Sudoku"), all number puzzles are structured more or less the same, Sudoku-style: The base consists of 9x9 squares or cells. More or less evenly distributed in the puzzle are already 10 to 20 digits. The more digits are given, the easier is the solution. The goal of the game is to fill up all empty cells with the digits 1 to 9 in such a way that each digit appears only once in a column (vertical), in a row (horizontal) and in a block (3 times 3 cells).

### Screenshots

![](https://s20.directupload.net/images/210618/isa3zups.gif)

#### Prerequisites

- gradle v7
- OpenJDK 16
- Core Processing library

To execute, just run `gradle run`.

### Overview of the files and the lines of code

```
Sudoku/
├── build.gradle
├── core.jar
├── gradle
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradlew
├── gradlew.bat
├── README.md
├── settings.gradle
└── src
    ├── main
    │   ├── java
    │   │   ├── GameBoard.java
    │   │   ├── GameCore.java
    │   │   ├── GameEngine
    │   │   │   └── GameEngine.java
    │   │   ├── GUISettings.java
    │   │   └── Sudoku.java
    │   └── resources
    │       └── bg.png
    └── test
        └── java
            └── GameBoardTest.java

-------------------------------------------------------------------------------
Language                     files          blank        comment           code
-------------------------------------------------------------------------------
Java                             6             81             66            518
Bourne Shell                     1             23             36            126
Markdown                         1             23              0             71
DOS Batch                        1             21              2             66
Gradle                           2              7              5             35
-------------------------------------------------------------------------------
SUM:                            11            155            109            816
-------------------------------------------------------------------------------

---------------------------------------------------------------------------------------------
File                                                      blank        comment           code
---------------------------------------------------------------------------------------------
./src/main/java/GameBoard.java                               28             18            186
./src/main/java/GameCore.java                                11              1             48
./src/main/java/GameEngine/GameEngine.java                    8              0             11
./src/main/java/Sudoku.java                                  23              6            172
./src/main/java/GUISettings.java                              0              0             14
./src/test/java/GameBoardTest.java                           11             41             87
./gradlew                                                    23             36            126
./README.md                                                  23              0             71
./gradlew.bat                                                21              2             66
./build.gradle                                                6              5             34
./settings.gradle                                             1              0              1
---------------------------------------------------------------------------------------------
SUM:                                                        155            109            816
---------------------------------------------------------------------------------------------
```

###  References

- Processing library for Java https://processing.org/
- ControlP5 by Andreas Schlegel. A GUI library to build custom user interfaces for desktop and android mode. More info: http://www.sojamo.de/libraries/controlP5/
- [Baeldung : Create a Sudoku Solver in Java](https://www.baeldung.com/java-sudoku)

### Suggested improvements
- Implementing solving the puzzle using [Dancing Links Donald E. Knuth, Stanford University](https://www.ocf.berkeley.edu/~jchu/publicportal/sudoku/0011047.pdf)
- Find a better distribution of clues.
- Setting input from keyboard as well.

### MIT License

Copyright 2021 Assem Hussein

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the  "Software"), to deal in the Software without restriction, including  without limitation the rights to use, copy, modify, merge, publish,  distribute, sublicense, and/or sell copies of the Software, and to  permit persons to whom the Software is furnished to do so, subject to  the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF  MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY  CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,  TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE  SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
