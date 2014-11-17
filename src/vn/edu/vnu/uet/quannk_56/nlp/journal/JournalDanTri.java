package vn.edu.vnu.uet.quannk_56.nlp.journal;

import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JournalDanTri extends Journal {

	@Override
	public Vector<String> getLinksFromMainPage(String mainpageHtml) {
		Vector<String> result = new Vector<String>();
		String preLink = "http://dantri.com.vn/";
		String pattern = "href=.*Xem tiếp</a>";

		// Create a Pattern object
		Pattern r = Pattern.compile(pattern);

		// Now create matcher object.
		Matcher m = r.matcher(mainpageHtml);
		String sub;
		while (m.find()) {
			sub = m.group(0);
			sub = sub.substring(7, sub.length() - 14);
			result.add(preLink + sub);
		}
		return result;
	}

	@Override
	public String getMainContentFromPost(String pageHtml) {
		return null;
	}

	public static void main(String args[]) {
		Journal dantri = new JournalDanTri();
		dantri.getLinksFromMainPage(dantri,
				"http://dantri.com.vn/xa-hoi/trang-", ".htm", 1, 100);
		dantri.getLinksFromMainPage(dantri,
				"http://dantri.com.vn/the-gioi/trang-", ".htm", 1, 100);
		dantri.getLinksFromMainPage(dantri,
				"http://dantri.com.vn/giao-duc-khuyen-hoc/trang-", ".htm", 1,
				100);
	}

}
