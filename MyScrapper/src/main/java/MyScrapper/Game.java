package MyScrapper;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Game {
		public static void main(String[] args) {
			try {
				// Here we create a document object and use JSoup to fetch the website
				Document doc = Jsoup.connect("https://www.codetriage.com/?language=Java").get();
		  
				// With the document fetched, we use JSoup's title() method to fetch the title
				System.out.printf("Title: %s\n", doc.title());
		  
				// Get the list of repositories
				Elements repositories = doc.getElementsByClass("repo-item");
		  
				/**
				 * For each repository, extract the following information:
				 * 1. Title
				 * 2. Number of issues
				 * 3. Description
				 * 4. Full name on github
				 */
				for (Element repository : repositories) {
				  // Extract the title
				  String repositoryTitle = repository.getElementsByClass("repo-item-title").text();
		  
				  // Extract the number of issues on the repository
				  String repositoryIssues = repository.getElementsByClass("repo-item-issues").text();
		  
				  // Extract the description of the repository
				  String repositoryDescription = repository.getElementsByClass("repo-item-description").text();
		  
				  // Get the full name of the repository
				  String repositoryGithubName = repository.getElementsByClass("repo-item-full-name").text();
		  
				  // The reposiory full name contains brackets that we remove first before generating the valid Github link.
				  String repositoryGithubLink = "https://github.com/" + repositoryGithubName.replaceAll("[()]", "");
		  
				  // Format and print the information to the console
				  System.out.println(repositoryTitle + " - " + repositoryIssues);
				  System.out.println("\t" + repositoryDescription);
				  System.out.println("\t" + repositoryGithubLink);
				  System.out.println("\n");
				}
		  
			  // In case of any IO errors, we want the messages written to the console
			  } catch (IOException e) {
				e.printStackTrace();
			  }
		}
//		int choice = 0;
//		System.out.println("Please choose which website to scrap:\n1.Mako\n2.Walla\n3.Ynet");
//
//		while (true) {
//
//			choice = input.nextInt();
//			
//			switch (choice) {
//			case 0:
//				try {
//					Document dom=Jsoup.connect("https://www.walla.co.il/").get();
//					System.out.println(dom.outerHtml());
//				} catch (IOException e) {
//					
//					e.printStackTrace();
//				}
//				break;
//			case 1:
//				System.out.println("You have chosen Mako");
//				bot=new MakoRobot();
//				break;
//			case 2:
//				System.out.println("You have chosen Walla");
//				bot=new WallaRobot();
//				break;
//			case 3:
//				System.out.println("You have chosen Ynet");
//				bot=new YnetRobot();
//				break;
//			default:
//				System.out.println("Your choice doesn't exist");
//				break;
//
//			}
//		}
	}


