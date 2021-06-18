# Sudoku

This is a Sudoku game written in Java with help of Processing core library. This game was developed within the scope of a final project for the module "Programming interactive systems" at THM — University of Applied Sciences 2021 (See license). Have fun! 

### Sudoku in nutshell

Sudoku is a logic puzzle with a schematic basic structure: Except for special types of games and variants (such as the rather unknown circle Sudoku or the so-called "Samurai Sudoku"), all number puzzles are structured more or less the same, Sudoku-style: The base consists of 9x9 squares or cells. More or less evenly distributed in the puzzle are already 10 to 20 digits. The more digits are given, the easier is the solution. The goal of the game is to fill up all empty cells with the digits 1 to 9 in such a way that each digit appears only once in a column (vertical), in a row (horizontal) and in a block (3 times 3 cells).

### Screenshots

![](/home/assem/Pictures/Peek 2021-06-18 16-19.gif)

#### Prerequisites

- gradle v7
- OpenJDK 16
- Core Processing library

To execute, just run `gradle run`.

### Übersicht über die Dateien und die Lines of Code

- [Class] GameBoard.java  250 lines (LOG)
- [Class] GameCore.java 60 lines  (LOG)
- [Class] GUISettings.java 25 lines (GUI)
- [Class] Sudoku.java 250 lines (GUI)
- [Interface] GameEngine.java 20 lines (LOG)

###  References

- Processing library for Java https://processing.org/
- ControlP5 by Andreas Schlegel. A GUI library to build custom user interfaces for desktop and android mode. More info: http://www.sojamo.de/libraries/controlP5/
- [Baeldung : Create a Sudoku Solver in Java](https://www.baeldung.com/java-sudoku)

### Suggested improvements
- Implementing solving the puzzle using [Dancing Links Donald E. Knuth, Stanford University](https://www.ocf.berkeley.edu/~jchu/publicportal/sudoku/0011047.pdf)
- Find a better distribution of clues.
- Setting input from keyboard as well.

### License

Copyright 2021 Assem Hussein

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the  "Software"), to deal in the Software without restriction, including  without limitation the rights to use, copy, modify, merge, publish,  distribute, sublicense, and/or sell copies of the Software, and to  permit persons to whom the Software is furnished to do so, subject to  the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF  MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY  CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,  TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE  SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
