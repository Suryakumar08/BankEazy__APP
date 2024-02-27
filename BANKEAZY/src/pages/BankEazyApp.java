package pages;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import exception.CustomBankException;
import jdbc.JDBCConnector;
import utilities.InputHelper;
import utilities.ScannerHelper;
import utilities.customConsoleLogFormatter;
import utilities.customLogFileFormatter;

public class BankEazyApp {

	public static Logger logger = Logger.getGlobal();

	public static void main(String[] args) {
		setLoggerProperties();
		try {
			boolean continueProgram = true;
			try {
				while (continueProgram) {
					logger.fine("-----------Welcome to BankEazy Bank------------");
					logger.info(
							"\n1)Login\nAny other digit)Exit\n\nEnter your option to proceed : ");
					int userChoice = InputHelper.getInt();
					switch (userChoice) {
					case 1: {
						JDBCConnector.establishConnection("BankEazy");
						LoginPage.run();
						break;
					}
					default: {
						JDBCConnector.closeConnection();
						ScannerHelper.closeScanner();
						logger.fine("Thanks you! Come again!!");
						continueProgram = false;
						break;
					}
					}
				}
			} catch (CustomBankException e) {
				e.printStackTrace();
				logger.log(Level.WARNING, CustomBankException.ERROR_OCCURRED + "\n" + e.getCause().getMessage(), e);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Level.SEVERE, "Exception Occurred ", e);
		}
	}

	public static void setLoggerProperties() {
		try {
			
			FileHandler fileHandler = new FileHandler("BankEazyLogs.log", true);
			fileHandler.setFormatter(new customLogFileFormatter());
			logger.addHandler(fileHandler);
			ConsoleHandler consoleHandler = new ConsoleHandler();
			consoleHandler.setFormatter(new customConsoleLogFormatter());
			consoleHandler.setLevel(Level.ALL);
			logger.addHandler(consoleHandler);
			logger.setUseParentHandlers(false);
			logger.setLevel(Level.ALL);

		} catch (SecurityException | IOException e) {
			System.out.println("Some error occurred : " + e.getMessage() + "\nCause : " + e.getCause());
		}
	}

}
