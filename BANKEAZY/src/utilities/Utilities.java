package utilities;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import pages.BankEazyApp;

public class Utilities {
	
	private static final Logger logger = BankEazyApp.logger;
	
	public static long getDobInMillis(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
        return zonedDateTime.toInstant().toEpochMilli();
	}

	public static long getCurrentTime() {
		return System.currentTimeMillis();
	}
	
	public static String getDateString(long millis) {
		Instant instant = Instant.ofEpochMilli(millis);
		ZonedDateTime date = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
		String resultDate = date.format(DateTimeFormatter.ofPattern("dd/MMM/yyyy hh:mm:ss"));
		return resultDate;
	}
	
	public static String getDateTimeString(long millis) {
		Instant instant = Instant.ofEpochMilli(millis);
		ZonedDateTime dateTime = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
		String resultDateTime = dateTime.format(DateTimeFormatter.ofPattern("dd/MMM/yyyy - HH-mm-ss"));
		return resultDateTime;
	}
	
	public static <K, V> void printObjects(Map<K, V> map) {
		for(V value : map.values()) {
			logger.fine(value.toString());
		}
	}

	public static <T> void printObjects(List<T> objects) {
		for(T obj : objects) {
			logger.fine(obj.toString());
		}
	}
}

