package utilities;

import java.util.InputMismatchException;
import java.util.Scanner;

import exception.CustomBankException;

public class InputHelper {
	static Scanner scanner = ScannerHelper.getScanner();

	public static int getInt() throws CustomBankException {
		try {
			int integer = scanner.nextInt();
			scanner.nextLine();
			return integer;
		} catch (InputMismatchException ex) {
			throw new CustomBankException(CustomBankException.GIVE_PROPER_INPUT);
		}
	}

	public static double getDouble() {
		double doubleValue = scanner.nextDouble();
		scanner.nextLine();
		return doubleValue;
	}

	public static String getString() {
		String inputString = scanner.nextLine();
		return inputString;
	}
}
