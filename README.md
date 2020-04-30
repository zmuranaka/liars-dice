# Java Liar's Dice GUI

## What is this Project?

This project is the game Liar's Dice. Liar's Dice is a four player game where every player begins with five dice. Players take turns betting on what has been rolled (they only know their own dice). On their turn, players must either challenge the player before them or make a bet. If the player bets, they must bet a higher face value or quantity of dice than the previous player. If they challenge, all players reveal their dice and you count up the amount of dice from the face the player had bet. If the player was a liar (meaning there was not as many dice of the face that the player had bet), then they lose a die. Otherwise, the player that challenged them loses a die. Then all players roll again and restart betting. Play continues until only one player has any dice left.

## How is this Implemented in Java?

Each player has their own unique array of rolls. The bots bet based on a random generator I coded, but the player is free to bet however they want. The bots will detect if a bet is 100% a lie (i.e. the player said the face value is a 7 on a six-sided die), and if so then they will always challenge. Otherwise, the bots will then detect if the bet is very unlikely to be true. If the player's bet is very unlikely to be true, they will often challenge it (again based on a random number generator). The player can feel free to challenge whatever they like. Play will continue on until the player wins or is eliminated, in which case the game is over.

## Directories:

bin  
&nbsp;&nbsp;&nbsp;&nbsp;This folder contains all of the compiled classes.  
images  
&nbsp;&nbsp;&nbsp;&nbsp;This folder contains the Liar's Dice logo I made.  
lib  
&nbsp;&nbsp;&nbsp;&nbsp;This folder contains the jar (java archive) of the project.  
src  
&nbsp;&nbsp;&nbsp;&nbsp;This folder contains all of the source code.

## Files:

Driver.java  
&nbsp;&nbsp;&nbsp;&nbsp;This is the Driver class that actually runs the program. It is a very small class, since I have offloaded the creation of the GUI and information about the game into the LiarsDiceGUI class and all of the information about a player into the LiarsDicePlayer class.  
LiarsDiceGUI.java  
&nbsp;&nbsp;&nbsp;&nbsp;This class contains the entire GUI for the program. Anything you see on the screen is located in this class. It also contains information about the game that is not related to the information about an individual player. The Driver class creates an object of this class.  
LiarsDicePlayer.java  
&nbsp;&nbsp;&nbsp;&nbsp;This class contains all of the information about an individual player. It allows the bots to bet, detect lies, and challenge bets.  
compileAndRun.cmd  
&nbsp;&nbsp;&nbsp;&nbsp;This is a simple command prompt script I wrote because I was compiling and running the project from the command prompt, and I was often reusing the same commands. Running this script recompiles the project and then runs the Driver class. Note: the Driver src file must be named Driver.java in order for this script to work properly. Similarly, the .class files must be in the bin directory, and the the .java files must be in the src directory.  
runDriver.cmd  
&nbsp;&nbsp;&nbsp;&nbsp;This is a simple command prompt script I wrote that is similar to compileAndRun.cmd, except it does not recompile the program. Running this script simply runs the Driver class. Note: the Driver.class file must be in the bin directory for this script to work properly.

## Sources:

I wrote all of the code myself, and I created the image in PicsArt on iPadOS.  

## Author:

Zachary Muranaka  
&nbsp;&nbsp;&nbsp;&nbsp;zacharymuranaka@mail.weber.edu  
&nbsp;&nbsp;&nbsp;&nbsp;https://zmuranaka.github.io
