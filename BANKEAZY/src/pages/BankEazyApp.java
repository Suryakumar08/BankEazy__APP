package pages;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import exception.CustomBankException;
import utilities.InputHelper;
import utilities.ScannerHelper;
import utilities.customConsoleLogFormatter;

public class BankEazyApp {

	public static Logger logger = Logger.getGlobal();

	public static void main(String[] args) {
		setLoggerProperties();

		boolean continueProgram = true;

		while (continueProgram) {
			try {
				logger.fine("-----------Welcome to BankEazy Bank------------");
				logger.info("\n1)Login\nAny other digit)Exit\n\nEnter your option to proceed : ");
				int userChoice = InputHelper.getInt();
				switch (userChoice) {
				case 1: {
					LoginPage.run();
					break;
				}
				default: {
					ScannerHelper.closeScanner();
					logger.fine("Thanks you! Come again!!");
					continueProgram = false;
					break;
				}
				}
			} catch (CustomBankException e) {
				logger.log(Level.WARNING, CustomBankException.ERROR_OCCURRED + "\n" + e.getMessage(), e);
			}
		}
	}

	public static void setLoggerProperties() {
		try {
			Path logFolderPath = Paths.get("logFiles");
            if (!Files.exists(logFolderPath)) {
                Files.createDirectories(logFolderPath);
            }
//			FileHandler fileHandler = new FileHandler("logFiles/BankEazyLogs.log", true);
//			fileHandler.setFormatter(new customLogFileFormatter());
//			logger.addHandler(fileHandler);
			ConsoleHandler consoleHandler = new ConsoleHandler();
			consoleHandler.setFormatter(new customConsoleLogFormatter());
			consoleHandler.setLevel(Level.ALL);
			logger.addHandler(consoleHandler);
			logger.setUseParentHandlers(false);
			logger.setLevel(Level.ALL);

		} catch (SecurityException | IOException e) {
			e.printStackTrace();
			System.out.println("Some error occurred : " + e.getMessage() + "\nCause : " + e.getCause());
		}
	}

}
