package vn.edu.vnu.uet.quannk_56.nlp.facebook;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import vn.edu.vnu.uet.quannk_56.nlp.UnicodeFile;

public class FacebookGetPost implements Runnable {
	private final static String accessToken = "CAACEdEose0cBAOd2Xg0jaStpTvNThkNjWmDwCiRZAf77wfxx4mRZA0PRZAyE5V3N8A23zTMbjwMPp6ZAqSSbJ1L8uP9mZCFH5B1TJxtzfNt86QE1kKLNVejYCQJenb7dS3OdJVZAwLYwfQfjblb63OKPGiL2ZB6ZAyq3Nqd4awuZCKdJXZAlZBtXF8nrx70KYYo8dl6Pl0FPEgL76zZAioZB7CcUAZBefZBUKaTKywZD";
	private final static String edge = "feed";
	private final static String host = "https://graph.facebook.com"; 
	private final static int NUMBER_OF_POSTS = 2000;
	private final static String Group_DonNhaChoDoChat_ID = "239188916260047";
	private final static String version = "v2.2";

	private static void CreateFolder(String folder) {
		File theDir = new File(folder);
		// if the directory does not exist, create it
		if (!theDir.exists()) {
			System.out.println("creating directory: " + folder);
			boolean result = false;
			try {
				theDir.mkdir();
				result = true;
			} catch (SecurityException se) {
			}
			if (result) {
				System.out.println("DIR created");
			}
		}
	}
	public static String getFullURL(String objectID) {
		return host + "/" + version + "/" + objectID + "/" + edge
				+ "?access_token=" + accessToken;
	}

	public static void GetPosts(String objectID, String folder,
			int maxNumberOfPost) throws Exception {
		CreateFolder(folder);

		int numberOfPost = 0;
		Writer outputFile = UnicodeFile.OpenFileForWrite("Posts.txt");
		String htmlSource, url = getFullURL(objectID);
		JSONParser parser = new JSONParser();
		// s = ReadFromFile("feed.txt");
		while (numberOfPost < maxNumberOfPost) {
			System.out.print("Get html source...");
			htmlSource = getUrlSource(url);
			System.out.println("\t\tDone!");
			JSONObject obj = (JSONObject) parser.parse(htmlSource);
			JSONArray data = (JSONArray) obj.get("data");
			for (int i = 0; i < data.size(); i++) {
				numberOfPost++;
				String message = (String) ((JSONObject) data.get(i))
						.get("message");
				String id = (String) ((JSONObject) data.get(i)).get("id");
				if (message == null)
					continue;
				Writer outFile = UnicodeFile.OpenFileForWrite(folder + "\\"
						+ id + ".txt");

				System.out.println(folder
						+ "\t"
						+ numberOfPost
						+ "\t"
						+ message.substring(0, message.length() > 50 ? 50
								: message.length()));
				outFile.append(message);
				outFile.flush();
				outFile.close();
				outputFile.append(message + "\n-------------------------\n");
			}

			JSONObject data2 = (JSONObject) obj.get("paging");
			if (data2 == null)
				break;
			url = data2.get("next").toString();
			if (url == null)
				break;
		}
		outputFile.flush();
		outputFile.close();
	}
	// HTTP GET request
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

	public static void main(String args[]) throws Exception {
		String objectID;
		String folderName;

		objectID = Group_DonNhaChoDoChat_ID;
		folderName = "Data\\DonNhaChoDoChat";

		new Thread(new FacebookGetPost(objectID, folderName,
				NUMBER_OF_POSTS)).start();
		
	}

	public static String ReadFromFile(String fileName) {
		BufferedReader in = UnicodeFile.OpenFileForRead(fileName);
		StringBuilder content = new StringBuilder();
		String str;
		try {
			while ((str = in.readLine()) != null) {
				content.append(str + "\n");
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content.toString();
	}

	private int maxPosts;

	private String objectID, folder;

	public FacebookGetPost(String _objectID, String _folder,
			int _maxPosts) {
		this.objectID = _objectID;
		this.folder = _folder;
		this.maxPosts = _maxPosts;
	}

	@Override
	public void run() {
		try {
			GetPosts(objectID, folder, maxPosts);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
