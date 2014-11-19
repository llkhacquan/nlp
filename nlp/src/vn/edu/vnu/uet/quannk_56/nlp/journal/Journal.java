package vn.edu.vnu.uet.quannk_56.nlp.journal;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;
import java.util.Vector;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import vn.edu.vnu.uet.quannk_56.nlp.UnicodeFile;

/**
 * This base class define some method to get posts from some journal with
 * specify pattern in html code like : dantri, kenh14, genk ..v..v The derived
 * classes should implement the 'getLinksFromMainPage' and 'getDocumentFromPage'
 * method
 * 
 * @author wind
 *
 */

public abstract class Journal implements Runnable {
	public Journal(String folder, String url1, String url2, int start, int end) {
		this.folder = folder;
		this.url1 = url1;
		this.url2 = url2;
		this.iStart = start;
		this.iEnd = end;
	}

	public Journal() {
	}

	/**
	 * Get url source from the URL
	 * 
	 * @param url
	 * @return
	 */
	public static String getUrlSource(String url) {
		URL yahoo;
		StringBuilder a = new StringBuilder();
		try {
			yahoo = new URL(url);
			URLConnection yc = yahoo.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					yc.getInputStream(), "UTF-8"));
			String inputLine;
			while ((inputLine = in.readLine()) != null)
				a.append(inputLine + "\n");
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return a.toString();
	}

	public String folder;

	/**
	 * Get main content from all links in file of links
	 * 
	 * @param fileLinks
	 *            the files contains links, each line is a link
	 */
	public void getSourceFromFileLinks(String folder, String fileLinks) {
		System.out.println("Reading file: " + fileLinks);
		String[] lines = UnicodeFile.getLinesFromFile(fileLinks);
		for (String link : lines) {
			getContentFromLink(folder, link);
		}
	}

	/**
	 * Get main content from all links and store to files
	 * 
	 * @param links
	 */
	public void getContentFromLinks(Vector<String> links) {
		for (String link : links) {
			getContentFromLink("", link);
		}
	}

	/**
	 * This get the link and extract the main content and save this to a file
	 * The file is put in the input folder and named as the link (remove html://
	 * and /
	 * 
	 * @param folder
	 * @param link
	 */
	private void getContentFromLink(String folder, String link) {
		System.out.println("\tGet main content from " + link.substring(0, 50)
				+ " and save to file.");
		try {
			String fileName = folder + "/"
					+ link.replace("http://", "").replace("/", " ") + ".txt";
			File f = new File(fileName);
			if (f.exists() && !f.isDirectory()) {
				// System.out.println("File exists, skip!!!");
				return;
			}
			Document doc = Jsoup.connect(link).get();
			String title = doc.title();
			Writer out = UnicodeFile.OpenFileForWrite(fileName);
			//out.append("Link:" + link + "\t");
			out.append(title + "\r\n");
			out.append(getMainContentFromPost(doc.toString()));
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * From a full html code, restore the links to specify posts
	 * 
	 * @param mainpageHTML
	 * @return
	 */
	abstract public Vector<String> getLinksFromMainPage(String mainpageHTML);

	/**
	 * From a full html code of a specify post, return a Document
	 * 
	 * @param htmlCode
	 * @return
	 */
	abstract public String getMainContentFromPost(String htmlCode);

	/**
	 * This method get html source from a 100 base URLs and store to file links.
	 * The base URL should be url1 + i + fileLinks, i run from 0 to 99. For
	 * examples: "http://genk.vn/dien-thoai/page-2.chn".
	 * 
	 * 
	 * @param url1
	 *            Example "http://genk.vn/dien-thoai/page-"
	 * @param fileLinks
	 *            Example ".chn"
	 */
	public void getLinksFromMainPage(Journal html, String url1, String url2,
			int start, int end) {
		for (int i = start; i <= end; i++) {
			Vector<String> t;
			String url = url1 + Integer.toString(i) + url2;
			System.out.println("Get html source from " + url + " ... ");
			String urlSource = Journal.getUrlSource(url);
			if (urlSource == "")
				continue;
			t = (html.getLinksFromMainPage(urlSource));
			for (String link : t) {
				getContentFromLink(folder, link);
			}
		}
	}

	public String url1, url2;
	public int iStart, iEnd;

	@Override
	public void run() {
		getLinksFromMainPage(this, url1, url2, iStart, iEnd);
	}
}
