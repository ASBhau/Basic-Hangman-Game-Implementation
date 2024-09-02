#include <iostream>
#include <string>
#include <vector>
#include <cstdlib>
#include <ctime>

using namespace std;

string getRandomWord() {
    vector<string> words = {"example", "hangman", "computer", "programming", "cplusplus", "random", "word"};
    srand(time(0)); 
    int randomIndex = rand() % words.size(); 
    return words[randomIndex]; 
}

bool isAlreadyGuessed(vector<char>& guessedLetters, char letter) {
    for (char guessedLetter : guessedLetters) {
        if (guessedLetter == letter) {
            return true; 
        }
    }
    return false; 
}

class Word {
private:
    string hiddenWord; 
    string guessedWord; 
    vector<char> guessedLetters; 

public:
    Word(string hiddenWord) : hiddenWord(hiddenWord) {
        guessedWord = string(hiddenWord.length(), '_'); 
    }

    bool guessLetter(char letter) {
        if (isAlreadyGuessed(guessedLetters, letter)) {
            return false; 
        }
        guessedLetters.push_back(letter); 

        bool correctGuess = false; 
        for (int i = 0; i < hiddenWord.length(); ++i) {
            if (hiddenWord[i] == letter) {
                guessedWord[i] = letter; 
                correctGuess = true;
            }
        }
        return correctGuess; 
    }

    
    string getGuessedWord() {
        return guessedWord;
    }

    
    string getHiddenWord() {
        return hiddenWord;
    }

    
    bool isFullyGuessed() {
        return guessedWord == hiddenWord;
    }
};


class Hangman {
private:
    Word word; 
    int maxGuesses; 
    int incorrectGuesses; 

public:
    
    Hangman(string hiddenWord, int maxGuesses) : word(hiddenWord), maxGuesses(maxGuesses), incorrectGuesses(0) {}

    
    void play() {
        while (!isGameOver() && !isGameWon()) {
            displayCurrentState();
            char guess = getGuess(); 
            updateGameState(guess); 
        }
        displayCurrentState(); 
        if (isGameWon()) {
            cout << "Congratulations! You've guessed the word!" << endl; 
        } else {
            cout << "Game Over! The word was: " << word.getHiddenWord() << endl; 
        }
    }

private:
    
    void displayCurrentState() {
        cout << "Current word: " << word.getGuessedWord() << endl; 
        cout << "Incorrect guesses left: " << maxGuesses - incorrectGuesses << endl; 
    }

   
    char getGuess() {
        char guess;
        cout << "Enter your guess: ";
        cin >> guess; 
        return guess;
    }

  
    void updateGameState(char guess) {
        if (!word.guessLetter(guess)) {
            incorrectGuesses++; 
        }
    }

    
    bool isGameWon() {
        return word.isFullyGuessed();
    }

    
    bool isGameOver() {
        return incorrectGuesses >= maxGuesses;
    }
};


int main() {
    string word = getRandomWord(); 
    int maxGuesses = 6; 
    Hangman game(word, maxGuesses); 
    game.play(); 
    return 0;
}
