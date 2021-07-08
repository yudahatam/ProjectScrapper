package MyScrapper;
import java.util.Map;

public class YnetRobot extends BaseRobot {

	public YnetRobot() {
		super("https://www.ynet.co.il/home/0,7340,L-8,00.html");
	}

	@Override
	public Map<String, Integer> getWordsStatistics() {
		return null;
	}

	@Override
	public int countInArticlesTitles(String text) {
		return 0;
	}

	@Override
	public String getLongestArticleTitle() {
		return null;
	}

}
