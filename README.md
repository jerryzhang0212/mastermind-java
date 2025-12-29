# Mastermind – Java Console Game

A Java implementation of the classic **Mastermind** deduction game.  
The player attempts to guess a secret 4-letter code (A–F, no repeats) within 8 turns.  
After each guess, the program provides feedback on:

- Letters in the **correct position**
- Correct letters in the **wrong position**

This project was written as part of my computer science practice in Java, focusing on:
- Input validation  
- String processing  
- Game loop design  
- Logical comparison algorithms  
- Console UI interaction  

## How to Play
1. Player 1 enters a 4-letter secret code using letters A–F.
2. Player 2 has 8 attempts to guess the code.
3. After each guess, feedback is displayed.
4. If the guess is correct (all letters in correct position), the game ends.

## Features
- Full input validation (length, characters, duplicates)
- Accurate checking of exact and partial matches
- Clean console interface
- Replay option
- Case-insensitive input
