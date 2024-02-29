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
			scanner.nextLine();
			throw new CustomBankException(CustomBankException.GIVE_PROPER_INPUT);
		}
	}

	public static long getLong() throws CustomBankException{
		try {
			long longValue = scanner.nextLong();
			scanner.nextLine();
			return longValue;
		}catch(InputMismatchException ex) {
			scanner.nextLine();
			throw new CustomBankException(CustomBankException.GIVE_PROPER_INPUT);
		}
	}
	
	
	public static double getDouble() throws CustomBankException{
		try {
			double doubleValue = scanner.nextDouble();
			scanner.nextLine();
			return doubleValue;
		}catch(InputMismatchException ex) {
			scanner.nextLine();
			throw new CustomBankException(CustomBankException.GIVE_PROPER_INPUT);
		}
	}

	public static String getString() {
		String inputString = scanner.nextLine();
		return inputString;
	}
}
