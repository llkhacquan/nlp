package vn.edu.vnu.uet.quannk_56.nlp.facebook;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import vn.edu.vnu.uet.quannk_56.nlp.UnicodeFile;

public class LabelingHelper {
	final static String folderPath = "Data\\DonNhaChoDoChat\\";
	final static String folder1 = folderPath + "My_Pham\\"; // nhung thu boi
															// duoc len nguoi,
															// uong duoc
	final static String folder2 = folderPath + "Thuc_An\\"; // nhung thu an
															// duoc, banh keo,
															// thuc pham chuc
															// nang
	final static String folder3 = folderPath + "Quan_ao\\"; // nhung thu mac
															// duoc len nguoi,
															// ca ke dong ho,
															// giay dep, trang
															// suc
	final static String folder4 = folderPath + "Khac\\";

	public static void main(String args[]) {

		listFilesForFolder(folderPath);
	}

	public static void listFilesForFolder(final String folderPath) {
		final File folder = new File(folderPath);
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				if (fileEntry.getAbsolutePath() != folder1
						&& fileEntry.getAbsolutePath() != folder2
						&& fileEntry.getAbsolutePath() != folder3) {

				} else
					listFilesForFolder(fileEntry.getAbsolutePath());
			} else {
				classify(fileEntry);
			}
		}
	}

	private static Scanner in = new Scanner(System.in);

	public static void classify(File file) {
		System.out.println("-------------------------------------");
		System.out.println(file.getAbsolutePath() + "\n");
		String fileContent = UnicodeFile.getContentFromFile(file
				.getAbsolutePath());

		String myPham[] = { "son", "môi ", " nhang", "mỹ phẩm",
				"Mỹ phẩm", "sẹo", "nước hoa", "thiên nhiên" , "mặt nạ"};
		String trangSuc[] = { "vòng", "treo" };
		String quanAo[] = { " áo", "Áo", "quần", "giầy",
				"khoác", "váy", "mặc" };

		boolean contain;
		contain = false;
		for (String term : myPham) {
			fileContent = fileContent.toLowerCase();
			if (fileContent.contains(term)) {
				contain = true;
				fileContent = fileContent.replaceAll(term,
						"____" + term.toUpperCase() + "____");
			}
		}
		if (contain == false)
			return;

		if (fileContent.length() >= 200)
			System.out.println(fileContent.substring(0, 200));
		else
			System.out.println(fileContent);
		System.out.print("\t\tYour classify: ");
		char c = 0;
		try {
			c = (char) in.next().charAt(0);
			switch (c) {
			case '1':
				Files.move(Paths.get(file.getAbsolutePath()),
						Paths.get(folder1 + file.getName()), REPLACE_EXISTING);
				System.out
						.println("Moved " + file.getName() + " to " + folder1);
				break;
			case '2':
				Files.move(Paths.get(file.getAbsolutePath()),
						Paths.get(folder2 + file.getName()), REPLACE_EXISTING);
				System.out
						.println("Moved " + file.getName() + " to " + folder2);
				break;
			case '3':
				Files.move(Paths.get(file.getAbsolutePath()),
						Paths.get(folder3 + file.getName()), REPLACE_EXISTING);
				System.out
						.println("Moved " + file.getName() + " to " + folder3);
				break;
			case '4':
				Files.move(Paths.get(file.getAbsolutePath()),
						Paths.get(folder4 + file.getName()), REPLACE_EXISTING);
				System.out
						.println("Moved " + file.getName() + " to " + folder4);
				break;
			default:
				// System.out.println("You just enter: " + (int) c);
				return;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println();
		}
	}
}
