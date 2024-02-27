package utilities;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Utilities {
	
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
		
		return date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
	}
	
	public static String getDateTimeString(long millis) {
		Instant instant = Instant.ofEpochMilli(millis);
		ZonedDateTime dateTime = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
		
		return dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss"));
	}
}

