import java.util.Scanner;

public class MyProgram {
    
    private static String code = "";
    private static int turnNum = 0;
    private static boolean gameOver = false;

    // Scanner name must be SCAN! Not SCANNY!
    private static final Scanner SCAN = new Scanner(System.in);

    public static void main(String[] args) 
    {
        //This method must work with your code as is. A backup is provided.
        while (true)
        {
            //These 2 lines clear the console.
            System.out.print("\033[H\033[2J");  
            System.out.flush(); 
            
            //We need to get the code for the opponent to break. This will ask a player for a code and then check to make sure it follows the rules.
            boolean validCode = false;
            while (!validCode)
            {
                System.out.println(getCodeDirections());
                code = SCAN.nextLine();
                validCode = checkCode(code);
            }
            
            System.out.print("\033[H\033[2J");  
            System.out.flush();  

            System.out.println(getDirections());
            System.out.println();
            System.out.println();

            //This is the game loop. It will keep repeating until the game is over.
            while (! gameOver)
            {
                //Print the turn directions, get a guess, process that guess, and give feedback to the user.
                System.out.print(getTurnDirections());
                String input = SCAN.nextLine();
                String results = check(input);

                System.out.println();
                System.out.println(results);
                System.out.println();
            }

            //Check if they want to play again
            System.out.println("\nWould you like to play again? Enter Y to play again.");
            String again = SCAN.nextLine();
            if (! again.equalsIgnoreCase("y"))
                return;
            code = "";
            turnNum = 0;
            gameOver = false;
        }
    }

    //Show how to enter the secret code
    public static String getCodeDirections()
    {
        return "Enter the 4 character code your opponent will be guessing\n"
             + "It can only use the letters A-F, upper or lower case, and the letters cannot repeat\n";
    }
    
    //check the secret code
    public static boolean checkCode(String s)
    {
        s = s.toUpperCase();

        //length must be exactly 4
        if (s.length() != 4) 
        {
            System.out.println("Your code must be exactly 4 characters long");
            return false;
        }

        //only A–F
        String valid = "ABCDEF";
        for (int i = 0; i < 4; i++) 
        {
            String letter = s.substring(i, i + 1);
            if (valid.indexOf(letter) == -1) 
            {
                System.out.println("Only letters A-F are allowed");
                return false;
            }
        }

        //no repeats
        for (int i = 0; i < 4; i++) 
        {
            String a = s.substring(i, i + 1);
            for (int j = i + 1; j < 4; j++) 
            {
                String b = s.substring(j, j + 1);
                if (a.equals(b)) 
                {
                    System.out.println("Letters cannot repeat");
                    return false;
                }
            }
        }

        //store the normalized code so comparisons are easy later
        code = s;
        return true;
    }

    //One-time directions shown before guessing starts
    public static String getDirections()
    {
        return "Welcome to Mastermind. You are the codebreaker. Your job is to guess the pattern in both letter and order.\n"
             + "There are 6 available letters, A-F upper or lower case, and they will not repeat.\n"
             + "You will have 8 guesses. After each guess you will learn the number of letters that are correct and in the right spot\n"
             + "as well as the number of letters that are correct but in the wrong spot. Guess by entering 4 letters between A and F with no spaces. Good luck!\n";
    }

    //Prompt for the next guess
    public static String getTurnDirections()
    {
        return "Please make guess number " + (turnNum + 1) + ": ";
    }

    //Check one guess: validate, count, update turnNum and gameOver, and return feedback
    public static String check(String guess)
    {
        guess = guess.toUpperCase();
        String valid = "ABCDEF";
        String invalidcode = "Your guess does not match the rules, only 4 letters between A and F without repeats. You have wasted a guess";

        //validate length
        if (guess.length() != 4) 
        {
            turnNum++;
            if (turnNum >= 8) gameOver = true;
            return invalidcode;
        }

        //validate A–F only
        for (int i = 0; i < 4; i++) 
        {
            String letter = guess.substring(i, i + 1);
            if (valid.indexOf(letter) == -1) 
            {
                turnNum++;
                if (turnNum >= 8) gameOver = true;
                return invalidcode;
            }
        }

        //validate no repeats
        for (int i = 0; i < 4; i++) 
        {
            String a = guess.substring(i, i + 1);
            for (int j = i + 1; j < 4; j++) 
            {
                String b = guess.substring(j, j + 1);
                if (a.equals(b)) 
                {
                    turnNum++;
                    if (turnNum >= 8) gameOver = true;
                    return invalidcode;
                }
            }
        }

        //valid guess is a turn
        turnNum++;

        //count exact matches and misplaced matches
        String upperCode = code; //already uppercase
        int correctSpot = 0;
        int correctLetter = 0;

        //exact matches
        for (int i = 0; i < 4; i++) 
        {
            String g = guess.substring(i, i + 1);
            String c = upperCode.substring(i, i + 1);
            if (g.equals(c)) 
            {
                correctSpot++;
            }
        }

        //correct letters in wrong spots
        for (int i = 0; i < 4; i++) 
        {
            String g = guess.substring(i, i + 1);
            String c = upperCode.substring(i, i + 1);
            if (!g.equals(c) && upperCode.indexOf(g) != -1) 
            {
                correctLetter++;
            }
        }

        //win
        if (correctSpot == 4) 
        {
            gameOver = true;
            return "Congratulations, you are correct!";
        }

        //out of turns
        if (turnNum >= 8) 
        {
            gameOver = true;
            return "You are out of guesses. Better luck next time.";
        }

        //normal feedback
        return "You had " + correctSpot + " letter(s) in the correct spot and "
             + correctLetter + " correct letter(s) in the wrong spot.";
    }
}