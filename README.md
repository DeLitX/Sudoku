# Sudoku

Main code is located in app/src/main/java.

In "GameLogic" class located logic of sudoku(obviously).It can build sudoku level from affay of arrays of int.
Also,it saves all changes you done during your game.

"ChoosenLevel" is transmitter between "LevelChoose","GameLogic" and screen of device.
It customises GameLogic object with data received from"LevelChoose".

"LevelChoose" provides choosing the level(really?).Also it contains matrixes for each level.

"SolverLogic" defines logic of sudoku solver.

"Solver" is intermediary between screen and "SloverLogic".

"MainActivity" is the main screen.
