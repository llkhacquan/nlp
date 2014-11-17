package vn.edu.vnu.uet.quannk_56.nlp.journal;

import java.io.IOException;
import java.io.Writer;
import java.util.Vector;

import vn.edu.vnu.uet.quannk_56.nlp.UnicodeFile;

public class JournalThongBaoUET extends Journal {

	@Override
	public Vector<String> getLinksFromMainPage(String _mainpage) {
		// <a href="/coltech/taxonomy/term/53/3188">Xem chi tiết...</a>
		Vector<String> result = new Vector<String>();
		final String preLink = "http://www2.uet.vnu.edu.vn/coltech/taxonomy/term/53/";
		final String specialToken = "\">Xem chi tiết...</a>";
		for (int i = 0; i < 10; i++) {
			int index = _mainpage.indexOf(specialToken);
			if (index < 0)
				break;
			{
				String sub = _mainpage.substring(index - 5, index);
				StringBuilder t = new StringBuilder();
				for (int i1 = sub.length() - 1; i1 >= 0; i1--) {
					if (sub.charAt(i1) >= '0' && sub.charAt(i1) <= '9')
						t.append(sub.charAt(i1));
					else
						break;
				}
				sub = t.reverse().toString();
				result.add(preLink + sub);
				_mainpage = _mainpage.substring(index + 1);
			}
		}

		return result;
	}

	@Override
	public String getMainContentFromPost(String pageHtml) {

		return "";
	}

	public static void main(String[] args) {
		String url = "http://www2.uet.vnu.edu.vn/coltech/taxonomy/term/53?page=";
		Vector<String> links = new Vector<String>();

		// BufferedReader in = UnicodeFile.OpenFileForRead("test.html");
		// String str;
		// while ((str = in.readLine()) != null) {
		// content.append(str);
		// }
		// in.close();
		Writer out = UnicodeFile.OpenFileForWrite("ThongBaoUET links.txt");

		Journal thongBaoUET = new JournalThongBaoUET();

		try {
			for (int i = 0; i <= 100; i++) {
				Vector<String> t;
				String url2 = url + Integer.toString(i);
				System.out.print("Get html source from " + url2 + " ... ");
				String urlSource = Journal.getUrlSource(url2);
				if (urlSource == "")
					continue;
				t = (thongBaoUET.getLinksFromMainPage(urlSource));
				links.addAll(t);
				System.out.println("Done!");
				for (String link : t) {
					out.write(link + "\r\n");
					System.out.println("\t" + link);
				}
			}
			out.flush();
			out.close();
		} catch (IOException o) {
			o.printStackTrace();
		}
	}
}
