package MyScrapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Game {
	public static void main(String[] args) {
		MakoRobot mybot=new MakoRobot();
		System.out.println("testing .........");
		System.out.println(mybot.countInArticlesTitles("קורונה"));
		for (Map.Entry<String, Integer> entry : mybot.getWordsStatistics().entrySet()) {
			System.out.println(entry.getKey() + " = " + entry.getValue()+" Times");
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
}


