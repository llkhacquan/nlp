package vn.edu.vnu.uet.quannk_56.nlp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Vector;

/**
 * This class help open file for write and read
 * @author wind
 *
 */
public class UnicodeFile {
	public static Writer OpenFileForWrite(String filePath) {
		try {
			File fileDir = new File(filePath);

			Writer out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(fileDir), "UTF8"));
			return out;

		} catch (UnsupportedEncodingException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public static BufferedReader OpenFileForRead(String filePath) {
		try {
			File fileDir = new File(filePath);

			BufferedReader in = new BufferedReader(new InputStreamReader(
					new FileInputStream(fileDir), "UTF8"));
			return in;
		} catch (UnsupportedEncodingException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;

	}
	
	/**
	 * Read the content of a file
	 * @param fileName
	 * @return
	 */
	public static String getContentFromFile(String fileName) {
		BufferedReader in = UnicodeFile.OpenFileForRead(fileName);
		StringBuilder content = new StringBuilder();
		String str;
		try {
			while ((str = in.readLine()) != null) {
				content.append(str + "\n");
			}
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return content.toString();
	}

	public static String[] getLinesFromFile(String fileName){
		return getContentFromFile(fileName).split("\\r?\\n");
	}
}
