package utilities;

import java.util.Scanner;

public class ScannerHelper {
	private static Scanner scanner = null;
	
	public static Scanner getScanner() {
		if(scanner == null) {
			openScanner();
		}
		return scanner;
	}
	
	private static void openScanner() {
		scanner = new Scanner(System.in);
	}
	
	public static void closeScanner() {
		if(scanner == null) {
			scanner.close();
		}
	}
}
