package vn.edu.vnu.uet.quannk_56.nlp.journal;

import java.util.Vector;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JournalKenh14 extends Journal implements Runnable {

	@Override
	public Vector<String> getLinksFromMainPage(String mainpageHtml) {
		Vector<String> result = new Vector<String>();
		String preLink = "http://m.kenh14.vn/";
		String specialToken = "</span><a href=\"";
		String sub;
		for (int i = 0; i < 10; i++) {
			int index = mainpageHtml.indexOf(specialToken);
			if (index < 0)
				break;
			index += specialToken.length();
			mainpageHtml = mainpageHtml.substring(index);
			index = mainpageHtml.indexOf("\"");
			if (index < 0)
				break;
			sub = mainpageHtml.substring(1, index);
			result.add(preLink + sub);
		}
		return result;
	}

	@Override
	public String getMainContentFromPost(String pageHtml) {
		String result = "";
		Document doc;
		doc = Jsoup.parse(pageHtml);
		Elements mainContents = doc.getElementsByClass("fea_content");
		for (Element content : mainContents) {
			result += content.text() + "\n";
		}
		result = result.substring(0, result.length() - 1);
		return result;
	}

	public JournalKenh14(String folder, String url1, String url2, int start,
			int end) {
		super(folder, url1, url2, start, end);
	}

	public JournalKenh14() {
		super();
	}

	public static void main(String args[]) {
		getLinksAndPost();
	}

	public static void getLinksAndPost() {
		new Thread(new JournalKenh14("Data/Kenh14/",
				"http://m.kenh14.vn/star/trang-", ".chn", 1, 100)).start();
		new Thread(new JournalKenh14("Data/Kenh14/",
				"http://m.kenh14.vn/musik/trang-", ".chn", 1, 100)).start();
		new Thread(new JournalKenh14("Data/Kenh14/",
				"http://m.kenh14.vn/cine/trang-", ".chn", 1, 100)).start();
		new Thread(new JournalKenh14("Data/Kenh14/",
				"http://m.kenh14.vn/fashion/trang-", ".chn", 1, 100)).start();
		new Thread(new JournalKenh14("Data/Kenh14/",
				"http://m.kenh14.vn/doi-song/trang-", ".chn", 1, 100)).start();
		new Thread(new JournalKenh14("Data/Kenh14/",
				"http://m.kenh14.vn/xa-hoi/trang-", ".chn", 1, 100)).start();
		new Thread(new JournalKenh14("Data/Kenh14/",
				"http://m.kenh14.vn/sport/trang-", ".chn", 1, 100)).start();
		new Thread(new JournalKenh14("Data/Kenh14/",
				"http://m.kenh14.vn/2-tek/trang-", ".chn", 1, 100)).start();
	}
}
