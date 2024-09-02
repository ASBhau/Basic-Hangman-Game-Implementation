import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Hangman {

    public static String getRandomWord() {
        String[] words = {"example", "hangman", "computer", "programming", "cplusplus", "random", "word"};
        Random random = new Random();
        int randomIndex = random.nextInt(words.length);
        return words[randomIndex];
    }

    public static boolean isAlreadyGuessed(ArrayList<Character> guessedLetters, char letter) {
        for (char guessedLetter : guessedLetters) {
            if (guessedLetter == letter) {
                return true;
            }
        }
        return false;
    }

    public static class Word {
        private String hiddenWord;
        private StringBuilder guessedWord;
        private ArrayList<Character> guessedLetters;

        public Word(String hiddenWord) {
            this.hiddenWord = hiddenWord;
            this.guessedWord = new StringBuilder("_".repeat(hiddenWord.length()));
            this.guessedLetters = new ArrayList<>();
        }

        public boolean guessLetter(char letter) {
            if (isAlreadyGuessed(guessedLetters, letter)) {
                return false;
            }
            guessedLetters.add(letter);

            boolean correctGuess = false;
            for (int i = 0; i < hiddenWord.length(); i++) {
                if (hiddenWord.charAt(i) == letter) {
                    guessedWord.setCharAt(i, letter);
                    correctGuess = true;
                }
            }
            return correctGuess;
        }

        public String getGuessedWord() {
            return guessedWord.toString();
        }

        public String getHiddenWord() {
            return hiddenWord;
        }

        public boolean isFullyGuessed() {
            return guessedWord.toString().equals(hiddenWord);
        }
    }

    public static class HangmanGame {
        private Word word;
        private int maxGuesses;
        private int incorrectGuesses;

        public HangmanGame(String hiddenWord, int maxGuesses) {
            this.word = new Word(hiddenWord);
            this.maxGuesses = maxGuesses;
            this.incorrectGuesses = 0;
        }

        public void play() {
            Scanner scanner = new Scanner(System.in);
            while (!isGameOver() && !isGameWon()) {
                displayCurrentState();
                char guess = getGuess(scanner);
                updateGameState(guess);
            }
            displayCurrentState();
            if (isGameWon()) {
                System.out.println("Congratulations! You've guessed the word!");
            } else {
                System.out.println("Game Over! The word was: " + word.getHiddenWord());
            }
        }

        private void displayCurrentState() {
            System.out.println("Current word: " + word.getGuessedWord());
            System.out.println("Incorrect guesses left: " + (maxGuesses - incorrectGuesses));
        }

        private char getGuess(Scanner scanner) {
            System.out.print("Enter your guess: ");
            return scanner.next().charAt(0);
        }

        private void updateGameState(char guess) {
            if (!word.guessLetter(guess)) {
                incorrectGuesses++;
            }
        }

        private boolean isGameWon() {
            return word.isFullyGuessed();
        }

        private boolean isGameOver() {
            return incorrectGuesses >= maxGuesses;
        }
    }

    public static void main(String[] args) {
        String word = getRandomWord();
        int maxGuesses = 6;
        HangmanGame game = new HangmanGame(word, maxGuesses);
        game.play();
    }
}
