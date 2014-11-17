package vn.edu.vnu.uet.quannk_56.nlp.journal;

import java.io.IOException;
import java.io.Writer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import vn.edu.vnu.uet.quannk_56.nlp.UnicodeFile;

public class Run {
	public static void main(String args[]) {
		Document doc;
		try {
			doc = Jsoup.connect("http://kenh14.vn/star/dang-thu-thao-tu-tin-khoe-trang-phuc-dan-toc-tai-miss-international-20141105031251783.chn").get();
			System.out.println(doc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * This method get the html code of a URL and save it to a file
	 * 
	 * @param url
	 * @param fileName
	 */
	public static void getHTMLAndWriteToFile(String url, String fileName) {
		Writer out = UnicodeFile.OpenFileForWrite(fileName);
		try {
			out.append(Journal.getUrlSource(url));
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
