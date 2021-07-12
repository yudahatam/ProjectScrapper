package MyScrapper;

import java.util.Map;
import java.util.Scanner;

public class Game {
	private static Scanner input = new Scanner(System.in);

	public static void main(String[] args) {
		System.out.println("Please choose which website to scrap:\n1.Mako\n2.Walla\n3.Ynet");
		int choice = input.nextInt();
		switch (choice) {
			case 1:
				System.out.println("You have chosen Mako please wait a couple of seconds");
				score(new MakoRobot());
				break;
			case 2:
				System.out.println("You have chosen Walla please wait a couple of seconds");
				score(new WallaRobot());
				break;
			case 3:
				System.out.println("You have chosen Ynet please wait a couple of seconds");
				score(new YnetRobot());
				break;
			default:
				System.out.println("Your choice doesn't exist");
				break;
		}
	}

	public static void score(BaseRobot bot) {
		Map<String, Integer> wordMap = bot.getWordsStatistics();
		int guesses = 5;
		int score = 0;
		String guess;
		int numGuess;
		while (guesses > 0) {
			System.out
					.println("The title of the longest story in the website is " + bot.getLongestArticleTitle() + " ");
			System.out.println("Please guess the most common word, you have " + guesses + " guesses ");
			System.out.println("Your current score is " + score);
			guess = input.next();
			if (wordMap.get(guess) != null)
				score += wordMap.get(guess);
			guesses--;
		}
		System.out.println("You ran out of guesses");
		System.out.println("Your current score is " + score);
		System.out.println("Please enter a text that you think will apear in all the titles (upto 20 chars)");
		guess = input.next();
		while (20 < guess.length()) {
			System.out.println("Invalid amount of chars, please enter again");
			guess = input.next();
		}
		System.out.println("Please enter the amount of times you think your text apear in the titles");
		numGuess = input.nextInt();
		if (Math.abs(numGuess - bot.countInArticlesTitles(guess)) <= 2) {
			score += 250;
			System.out.println("Correct your score is " + score);
		} else
			System.out.println(
					"Wrong you were " + Math.abs(numGuess - bot.countInArticlesTitles(guess)) + " steps close");
		System.out.println("Thank you for playing your score is " + score);
	}
}
