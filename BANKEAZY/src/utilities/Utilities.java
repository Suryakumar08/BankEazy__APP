package utilities;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Utilities {
	public static String getDateString(long millis) {
		Instant instant = Instant.ofEpochMilli(millis);
		ZonedDateTime date = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
		
		return date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
	}
	
	public static String getDateTimeString(long millis) {
		Instant instant = Instant.ofEpochMilli(millis);
		ZonedDateTime dateTime = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
		
		return dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss"));
	}
}

