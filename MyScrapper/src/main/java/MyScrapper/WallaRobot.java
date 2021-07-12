package MyScrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WallaRobot extends BaseRobot {
	private ArrayList<String> urls = new ArrayList<>(); // ArrayList hold all links
	private ArrayList<Story> stories = new ArrayList<>(); // ArrayList hold all stories
	private Map<String, Integer> wordCount = new HashMap<>(); // Map to save the words(don't call function many times)

	public WallaRobot() {
		super("https://www.walla.co.il/");
		try {
			/*
			 * This code take control of main page of mako and find all the stories that
			 * apear. Take the url of the story and save it into urls ArrayList for further
			 * connection Remove outside links which contains https because all the stories
			 * refer to the server side with a get request Remove link the contain nothing
			 * -->("")
			 */
			Document dom = Jsoup.connect(this.getRootWebsiteUrl()).get(); // Connect to web
			for (Element row : dom.select("a")) { // Find all stories
				String temp = row.attr("href"); // Get all links
				if (temp.contains("item") || temp.contains("breaking")) // Select correct stories //////////////
																		// &&temp.contains("article")https://news.walla.co.il/
					if (urls.indexOf(temp) == -1) // Skip over duplicates
						urls.add(temp); // Add links to urls

			}
			/*
			 * This code go over all the urls connect to the page. Take all the text of
			 * title,subtitle and body of story and put it in story class for further work.
			 */
			for (String temp : urls) { // Go over urls
				Story currentStory = null; // Current story to hold the data
				String title = ""; // Initialize title to use in both cases
				String subTitle = ""; // Initialize subtitle to use in both cases
				Document tmpDom = Jsoup.connect(temp).get(); // Connect to story web
				if (!temp.contains("breaking")) { // Check if the url is a full story or just a break(only Title and
													// subTitle)
					title = Jsoup.parse(tmpDom.select("h1").toString()).text(); // Get title
					subTitle = tmpDom.select("header").select("p").text(); // Get subtitle
					Elements bodyElement = tmpDom.select("p"); // Get all the body of the story
					bodyElement.remove(bodyElement.get(0)); // Remove the subTitle(in walla the subtitles is also a <p>
															// element like the body)
					String body = bodyElement.text(); // Get the string of the body
					currentStory = new Story(title, subTitle, body); // Create story instance
					stories.add(currentStory);
				} else { // In case the story is a break we need to handle it differently
					Elements titles = tmpDom.select("h2"); // Take the title(h2 element in walla)
					Elements subTitles = tmpDom.select("p"); // Take the subtitles(p element in walla)
					titles.remove(titles.size() - 1); // Remove other h2 elements that snuck in
					titles.remove(titles.size() - 1); // Remove other h2 elements that snuck in
					for (int i = 0; i < titles.size(); i++) { // Create story out of the title and subtitle with empty
																// body and add it to stories ArrayList
						title = titles.get(i).text();
						subTitle = subTitles.get(i).text();
						currentStory = new Story(title, subTitle, "");
						stories.add(currentStory);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * This function return a map of all the word and their amount in all the
	 * stories
	 */
	@Override
	public Map<String, Integer> getWordsStatistics() {
		for (Story story : stories) { // Going over all the stories
			String[] titleSplit = story.getTitle().replaceAll("\\p{Punct}", "").split(" "); // Split title to words
			String[] subTitleSplit = story.getSubTitle().replaceAll("\\p{Punct}", "").split(" "); // Split subtitle to
																									// words
			String[] bodySplit = story.getBody().replaceAll("\\p{Punct}", "").split(" "); // Split body to words

			countLoop(titleSplit, wordCount); // Call function to count words
			countLoop(subTitleSplit, wordCount); // Call function to count words
			countLoop(bodySplit, wordCount); // Call function to count words

		}
		return wordCount;
	}

	/*
	 * This function recieve text and count the appearances in all the titles and
	 * subtitles in the site
	 */
	@Override
	public int countInArticlesTitles(String text) {
		int counter = 0; // Counter
		for (Story story : stories) { // Go over all the stories
			String title = story.getTitle(); // Take title of the current story
			String subTitle = story.getSubTitle(); // Take the subtitle of the current story
			int fromIndex = 0; // Index to keep track of the counter within the titles and subtitles

			while ((fromIndex = title.indexOf(text, fromIndex)) != -1) { // Go over title and find the appearances of
																			// the text
				counter++; // Count
				fromIndex++; // Go to next word
			}
			fromIndex = 0; // Reset index for second loop
			while ((fromIndex = subTitle.indexOf(text, fromIndex)) != -1) { // Go over subtitle and find the appearances
																			// of the text
				counter++; // Count
				fromIndex++; // Go to next word
			}
		}
		return counter; // Return the final number of appearances
	}

	/*
	 * This function return the title of the story with the longest content(body)
	 */
	@Override
	public String getLongestArticleTitle() {
		Story result = new Story("title", "subTitle", "body"); // Result to store the story with longest content
		int maxLength = 0; // Save max length of body for condition
		for (Story story : stories) { // Go over all the stories
			if (story.getBody().length() > maxLength) { // Check if the lenght of the current story is bigger than the
														// max
				maxLength = story.getBody().length(); // Update the max length
				result = story; // Save the story with the longest content
			}
		}
		return result.getTitle(); // Return the title of the longest story
	}

	/*
	 * Function to add and update the words in the given map
	 * 
	 * @param String[] split - Array of the words splitted from wanted text
	 * 
	 * @param Map <String, Integer> wordCount - map to add and update words
	 */
	private void countLoop(String[] split, Map<String, Integer> wordCount) {

		for (int i = 0; i < split.length; i++) { // Go over the array
			String temp = split[i]; // Take current word
			if (!wordCount.containsKey(temp)) // Check if the word exist in map
				wordCount.put(temp, 1); // Add in case it doesn't with 1 count
			else
				wordCount.put(temp, wordCount.get(temp) + 1); // Update the count of the word in the map
		}
	}
}
