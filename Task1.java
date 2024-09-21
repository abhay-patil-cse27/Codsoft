import java.util.Random;
import java.util.*;
public class Task1 
{
    private static final int MAX_ATTEMPTS = 10;
    private static final int MIN_RANGE = 1;
    private static final int MAX_RANGE = 100;

    public static void main(String[] args) 
  {
        Scanner scanner = new Scanner(System.in);
        boolean playAgain = true;
        int totalRounds = 0;
        int totalScore = 0;

        while (playAgain) {
            totalRounds++;
            int roundScore = playGame(scanner);
            totalScore += roundScore;
            System.out.println("Your score of this round: " + roundScore);
            System.out.println("Total score after " + totalRounds + " round(s): " + totalScore);
            System.out.print("Type yes if you want to play again? (yes/no): ");
            playAgain = scanner.next().equalsIgnoreCase("yes");
        }

        System.out.println("Thanks for playing! Your final score is: " + totalScore);
        scanner.close();
    }

    private static int playGame(Scanner scanner) 
  {
        Random random = new Random();
        int randomNumber = random.nextInt(MAX_RANGE - MIN_RANGE + 1) + MIN_RANGE;
        int attempts = 0;
        boolean correctGuess = false;

        while (attempts < MAX_ATTEMPTS && !correctGuess) 
        {
            System.out.print("Enter your guess (Attempt " + (attempts + 1) + " of " + MAX_ATTEMPTS + "): ");
            int userGuess = scanner.nextInt();
            attempts++;
            correctGuess = checkGuess(userGuess, randomNumber);

            if (!correctGuess) 
            {
                if (userGuess > randomNumber) 
                {
                    System.out.println("Your guess is higher than the actual number.");
                } 
                else 
                {
                    System.out.println("Your guess is lower than the actual number.");
                }
            }
        }

        if (correctGuess) 
        {
            System.out.println("Congratulations! You've guessed the number in " + attempts + " attempts.");
            return MAX_ATTEMPTS - attempts + 1; // Higher score for fewer attempts
        } 
        else 
        {
            System.out.println("Sorry, you've used all your attempts. The number was: " + randomNumber);
            return 0; // No score for failing to guess
        }
    }

    private static boolean checkGuess(int userGuess, int randomNumber) 
    {
        return userGuess == randomNumber;
    }
}
