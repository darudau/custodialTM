package application;

/**
 * Static representation of Day and DayCodes. This class contains methods used
 * with the Day and DayCodes.
 * 
 * @author Douglas Rudau
 * @version 02-24-2020
 *
 */
public class Day
{
	/** Int codes for each day */
	public static final int SUNDAY = 1;
	public static final int MONDAY = 2;
	public static final int TUESDAY = 3;
	public static final int WEDNESDAY = 4;
	public static final int THURSDAY = 5;
	public static final int FRIDAY = 6;
	public static final int SATURDAY = 7;

	/** Int codes for common schedules */
	public static final int NOT_SCHEDULED = 0;
	public static final int EVERYDAY = 8;
	public static final int WEEKDAYS = 9;
	public static final int WEEKENDS = 10;
	public static final int MWF = 11; // Monday, Wednesday, and Friday
	public static final int TR = 12; // Tuesday and Thursday
	public static final int TRF = 13; // Tuesday, Thursday, and Friday

	/** Array of Strings for the names of each day or code respectively */
	public static String[] dayNames =
	{ "Not Scheduled", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday",
			"Friday", "Saturday", "Everyday", "Weekdays", "Weekends",
			"Monday, Wednesday, and Friday", "Tuesday and Thursday",
			"Tuesday, Thursday, and Friday" };

	public static String[] dayShortNames =
	{ "       ", "S      ", " M     ", "  T    ", "   W   ", "    R  ", "     F ",
			"      A", "SMTWRFA", " MTWRF ", "S     A", " M W F ", "  T R  ",
			"  T RF" };

	/** Array of Chars for each day of the week */
	public static char[] dayChar =
	{ 'S', 'M', 'T', 'W', 'R', 'F', 'A' };

	/**
	 * Gets the name or code's name from a specified day code
	 * 
	 * @param dayCode
	 *            specified code
	 * @return string containing the Day's name
	 */
	public static String getNameFromCode(int dayCode)
	{
		if (dayCode < SUNDAY && dayCode > TRF)
		{
			return "Not Scheduled";
		}

		return dayNames[dayCode];
	}

	/**
	 * Gets a char representation of the day from Sunday through Saturday. Other
	 * date codes are currently not supported
	 * 
	 * @param dayCode
	 *            integer code representing the day
	 * @return single char representation of the day
	 */
	public static char getDayChar(int dayCode)
	{
		if (dayCode < SUNDAY && dayCode >= SATURDAY)
		{
			throw new IllegalArgumentException(
					"Invalid day code, valid codes are 1 thru 7 for days");
		}

		return dayChar[dayCode - 1]; // one is subtracted due to change daycode representation
	}

	/**
	 * Method to get the char representation key
	 * 
	 * @return string containing the single character key for each day
	 */
	public static String getCharRepresentationKey()
	{
		String key = "CHARACTER\tDAY'S NAME\n";

		for (int i = SUNDAY; i <= SATURDAY; i++)
		{
			key += dayChar[i] + "\t->\t" + dayNames[i] + "\n";
		}

		return key;
	}

	/**
	 * Method to get the integer representation key as a string
	 * 
	 * @return string containing the integer day representations
	 */
	public static String getIntRepresenationKey()
	{
		String key = "-1\t->\tNot Scheduled\n";
		for (int i = NOT_SCHEDULED; i <= TRF; i++)
		{
			key += " " + i + "\t->\t" + dayNames[i] + "\n";
		}
		return key;
	}
	
	public static String getShortWeekRepresentation(int dayCode){
		if(dayCode < NOT_SCHEDULED || dayCode > TRF){
			return dayShortNames[0];
		}
		
		return dayShortNames[dayCode];
	}

}
