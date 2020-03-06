package application;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Item class is for items on the list.
 * 
 * @author Douglas Rudau
 * @version 02-04-2020
 *
 */
public class Item implements Comparable<Item>, java.io.Serializable, Cloneable
{
	private static final long serialVersionUID = 1L;

	/** Name of item on list */
	private String name;

	/** Description of item on list */
	private String description;

	/** Location where item is to be done */
	private String location;

	/** Expected Time to Complete Item in Hours */
	private double expectedTime;

	/** Array list of subtasks for the item */
	private ArrayList<Item> subTasks;

	/**
	 * days item is scheduled for based off week 0 = Sunday and 6 = Saturday,
	 * true is scheduled, false is unscheduled
	 */
	private boolean scheduledDays[];

	/** Day Schedule Integer Representation */
	private int scheduleDaysInt;

	/**
	 * is this task a secure task, if a task is secure it only full time staff
	 * are permited to do the task
	 */
	private boolean isSecure;

	/** Are student workers permitted to do the task */
	private boolean studentWorkersPermitted;

	/** Default item parameters */
	public final static String DEFAULT_NAME = "unnamed";
	public final static String DEFAULT_LOCATION = "unspecified location";
	public final static int DEFAULT_SCHEDULE = Day.NOT_SCHEDULED;
	public final static double DEFAULT_EXPECTED_TIME = 0.0;

	/**
	 * Default constructor for the item, creates an item using the default
	 * parameters
	 */
	public Item()
	{
		name = DEFAULT_NAME;
		location = DEFAULT_LOCATION;
		scheduleDaysInt = DEFAULT_SCHEDULE;
		expectedTime = DEFAULT_EXPECTED_TIME;
		subTasks = new ArrayList<Item>();

	}

	/**
	 * Item constructor
	 * 
	 * @param name
	 *            the name of the item
	 * @param description
	 *            the description of the item
	 * @param location
	 *            the location of the item
	 * @param scheduledDays
	 *            integer represeantion of the schedule from the Day class
	 * @param expectedTime
	 *            hours to complete the item
	 * @param subTasks
	 *            an array list of items, that are the subtasks for the item
	 */
	public Item(String name, String description, String location, int scheduledDays,
			double expectedTime, ArrayList<Item> subTasks)
	{
		this.name = name;
		this.description = description;
		this.location = location;
		this.expectedTime = expectedTime;
		this.isSecure = false;
		this.studentWorkersPermitted = true;

		if (subTasks == null)
		{
			this.subTasks = new ArrayList<Item>();
		}
		else
		{
			this.subTasks = subTasks;
		}
		this.scheduledDays = new boolean[7];
		this.schduleTask(scheduledDays, true);
	}

	/**
	 * Constructor for a new item
	 * 
	 * @param name
	 *            for the item
	 * @param location
	 *            for the item
	 * @param scheduledDays
	 *            integer represeantion of schedule fromt the Day class
	 * @param expectedTime
	 *            hours required to complete the item
	 */
	public Item(String name, String location, int scheduledDays, double expectedTime)
	{
		this.subTasks = new ArrayList<Item>();
		this.name = name;
		this.description = ""; // no description given
		this.location = location;
		this.expectedTime = expectedTime;
		this.scheduledDays = new boolean[7];
		this.isSecure = false;
		this.studentWorkersPermitted = true;
		this.schduleTask(scheduledDays, true);

	}

	/**
	 * Constructor for a new item
	 * 
	 * @param name
	 *            the name of the item
	 * @param location
	 *            the location of the item
	 * @param scheduledDays
	 *            integer day represenation of schedule
	 * @param expectedTime
	 *            hours required to complete the item
	 * @param isSecure
	 *            is the task considered secure
	 * @param studentWorkersPermitted
	 *            are students allowed
	 */
	public Item(String name, String location, int scheduledDays, double expectedTime,
			boolean isSecure, boolean studentWorkersPermitted)
	{
		this.subTasks = new ArrayList<Item>();
		this.name = name;
		this.description = ""; // no description given
		this.location = location;
		this.expectedTime = expectedTime;
		this.scheduledDays = new boolean[7];
		this.isSecure = false;
		this.studentWorkersPermitted = true;
		this.schduleTask(scheduledDays, true);
		this.isSecure = isSecure;
		this.studentWorkersPermitted = studentWorkersPermitted;

	}

	/**
	 * Method to get the anme of the item
	 * 
	 * @return string containing the name of the item
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Method to set hte name of the item
	 * 
	 * @param name
	 *            string for the name of item
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Method to get the description for the item
	 * 
	 * @return the description for the item as a string
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Method to set the description for the item
	 * 
	 * @param description
	 *            a description for the item
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * Method to get the location of the item
	 * 
	 * @return string describing the location of the item
	 */
	public String getLocation()
	{
		return location;
	}

	/**
	 * Method to set the location of the item
	 * 
	 * @param location
	 *            string describing the location of the item.
	 */
	public void setLocation(String location)
	{
		this.location = location;
	}

	/**
	 * Gets the expected time in hours
	 * 
	 * @return the expected time in hours
	 */
	public double getExpectedTime()
	{
		return expectedTime;
	}

	/**
	 * Method to set the expected time for the item. The expected time is to be
	 * set in hours.
	 * 
	 * @param expectedTime
	 *            the time required to complete an item in hours
	 */
	public void setExpectedTime(double expectedTime)
	{
		this.expectedTime = expectedTime;
	}

	/**
	 * Method to get the array list of subtasks
	 * 
	 * @return the array list of subtasks
	 */
	public ArrayList<Item> getSubTasks()
	{
		return subTasks;
	}

	/**
	 * Method to set an array list of subtasks as the the subtasks for a
	 * specific task.
	 * 
	 * @param subTasks
	 */
	public void setSubTasks(ArrayList<Item> subTasks)
	{
		this.subTasks = subTasks;
	}

	/**
	 * Method to allow for easier addition of subtasks
	 * 
	 * @param task
	 *            the new Item to be added to the subtask lists
	 */
	public void addSubTask(Item task)
	{
		this.subTasks.add(task);
	}

	/**
	 * Method to add a sub task to a task, note that this method only requires a
	 * name, and will use default values for all other field.
	 * 
	 * @param taskName
	 *            the name of the subtask
	 */
	public void addSubTask(String taskName)
	{
		this.subTasks.add(new Item(taskName, null, this.location,
				this.scheduleDaysInt, 0, null));
	}

	/**
	 * Schedules the item to be done on the day or days entered using the
	 * integer codes provided as static final integers in this class. An item
	 * can be scheudled or unshceulded using this method for a specific day.
	 * True is scheduled and false is not-scheduled. If an invalid day code is
	 * entered, an IllegalArgumentException is thrown.
	 * 
	 * Day Specific Codes: 0 == Sunday; 1 == Monday; 2 == Tuesday; 3 ==
	 * Wednesday; 4 == Thursday; 5 == Friday; 6 == Saturday;
	 * 
	 * Common Schedule Codes: -1 == Not Scheduled; 7 == Everyday; 8 == Weekdays;
	 * 9 == Weekends; 10 == Monday / Wednesday / Friday; 11 == Tuesday /
	 * Thursday; 12 == Tuesday / Thursday / Friday;
	 * 
	 * @param day
	 *            integer code to determine what days are to be changed
	 * @param isScheduled
	 *            true -> item is scheduled, false -> item is not scheduled
	 */
	public void schduleTask(int day, boolean isScheduled)
	{
		this.scheduleDaysInt = day;
	}

	/**
	 * Method to unscheduled the event for everyday
	 */
	public void unscheduleItem()
	{
		for (int i = 0; i < scheduledDays.length; i++)
		{
			scheduledDays[i] = false;
		}

		this.scheduleDaysInt = Day.NOT_SCHEDULED;
	}

	/**
	 * Creates a string representation of the item including all the dtails
	 * about the item
	 * 
	 * @return a string representation of the item.
	 */
	@Override
	public String toString()
	{
		DecimalFormat df = new DecimalFormat("###.##");
		return name + ": " + location + "\nClearance Level: "
				+ getWorkerPermissions() + Day.dayShortNames[this.scheduleDaysInt]
				+ "\tExpect Time " + df.format(expectedTime) + " Hours"
				+ subTasksToString();
	}

	/**
	 * Converts the item to a basic html represenation, including the item's
	 * name and any subtasks the item has
	 * 
	 * @return basic HTML represeantion of the item.
	 */
	public String toHTML()
	{
		DecimalFormat df = new DecimalFormat("###.##");
		return name + subTasksToHTML();
	}

	/**
	 * Converts the task with all the information about the task to an HTML
	 * represenation. This representation includes all details about thet ask
	 * such as name, location, schudle, expected time, and subtasks.
	 * 
	 * @return detialed html represenation of the item
	 */
	public String toHTMLLong()
	{
		DecimalFormat df = new DecimalFormat("###.##");
		return name + ": " + location + "<br>Clearance Level: "
				+ getWorkerPermissions() + Day.dayShortNames[this.scheduleDaysInt]
				+ "<br>Expect Time " + df.format(expectedTime) + " Hours"
				+ subTasksToHTML();
	}

	/**
	 * Method to convert subtasks to an HTML string as an unordered list.
	 * 
	 * @return html representation of the subtasks of the item
	 */
	private String subTasksToHTML()
	{
		if (this.subTasks.size() > 0)
		{
			String s = "<ul>";

			for (int i = 0; i < this.subTasks.size(); i++)
			{
				s += "<li>" + this.subTasks.get(i).getName() + "</li>";
			}

			s += "</ul>";
			return s;
		}
		else
		{
			return "";
		}

	}

	public String getWorkerPermissions()
	{
		if (isSecure)
		{
			if (studentWorkersPermitted)
			{
				return "Staff with Students";
			}
			return "Staff Only";
		}

		return "Anyone";
	}

	private String subTasksToString()
	{
		String s = "";

		if (subTasks.size() == 0)
		{
			return s;
		}

		for (int i = 0; i < subTasks.size(); i++)
		{
			if (subTasks.get(i) != null)
			{
				s += "\n\t" + subTasks.get(i).getName();
			}
		}

		return s;
	}

	/**
	 * Deprecated as of version 02-24-2020. Use
	 * Day.getShortWeekRepresentation(int dayCode)
	 * 
	 * Method to convert the days to a single char representation. This method
	 * has been deprecated as of version 02-24-2020
	 * 
	 * @return string representing the char array
	 */
	@Deprecated
	private String daysToChar()
	{
		String s = "";

		for (int i = 0; i < Day.dayChar.length; i++)
		{
			if (scheduledDays[i] == true)
			{
				s += Day.dayChar[i];
			}
			else
			{
				s += " ";
			}
		}

		return s;
	}

	/**
	 * Returns the schedule as a string
	 * 
	 * @return string containing which days of the week the item is scheduled
	 *         for
	 */
	public String scheduleToString()
	{

		if (this.scheduleDaysInt == Day.NOT_SCHEDULED)
		{
			return "Not Scheduled";
		}
		else if (this.scheduleDaysInt == Day.EVERYDAY)
		{
			return "Everyday";
		}
		else if (this.scheduleDaysInt == Day.WEEKDAYS)
		{
			return "Weekdays";
		}
		else if (this.scheduleDaysInt == Day.WEEKENDS)
		{
			return "Weekends";
		}
		else
		{
			String s = "";
			int count = 0;

			for (int i = 0; i < scheduledDays.length; i++)
			{
				if (scheduledDays[i])
				{
					count++;
					s += Day.dayNames[i] + " ";
				}
			}

			if (count == 0)
			{
				return "Not Scheduled";
			}
			else
			{
				return s;
			}
		}
	}

	/**
	 * Items are compared based on if they are the same class first. If an this
	 * item is the parent class of item and the otherItem is a child class of
	 * item, this item will be considered smaller than the otherItem. If
	 * this.item is an OfficeItem, then the compare to method will return a
	 * positive, indicating that this Item is larger than the other item. If
	 * both this item and otherItem are from the same class (or child class) the
	 * items will be compared based on their names, then their expected time to
	 * complete. Two items are equal if the items are both from the same class,
	 * have the same, the same expected time, and the same schedule.
	 * 
	 * @param otherItem
	 *            the item to compare this item to
	 * @return 0 if both items are equal, positive if otherItem is smaller,
	 *         negative if otherItem is larger
	 */
	@Override
	public int compareTo(Item otherItem)
	{
		if (this.getClass().equals(otherItem.getClass()))
		{
			if (this.name.equals(otherItem.name))
			{

				if (this.expectedTime > otherItem.expectedTime)
				{
					return 1;
				}
				else if (this.expectedTime < otherItem.expectedTime)
				{
					return -1;
				}
				else
				{
					if (this.scheduleDaysInt > otherItem.scheduleDaysInt)
					{
						return 1;
					}
					else if (this.scheduleDaysInt < otherItem.scheduleDaysInt)
					{
						return -1;
					}
					else
					{
						return 0;
					}
				}
			}
			else
			{
				return this.name.compareTo(otherItem.name);
			}
		}
		else if (this.getClass().equals(Item.class)
				&& otherItem.getClass().equals(OfficeItem.class))
		{
			return -1;
		}
		else
		{
			return 1;
		}
	}

	/**
	 * @return the scheduledDays
	 */
	public boolean[] getScheduledDays()
	{
		return scheduledDays;
	}

	/**
	 * @param scheduledDays
	 *            the scheduledDays to set
	 */
	public void setScheduledDays(boolean[] scheduledDays)
	{
		this.scheduledDays = scheduledDays;
	}

	/**
	 * @return the scheduleDaysInt
	 */
	public int getScheduleDaysInt()
	{
		return scheduleDaysInt;
	}

	/**
	 * @param scheduleDaysInt
	 *            the scheduleDaysInt to set
	 */
	public void setScheduleDaysInt(int scheduleDaysInt)
	{
		this.scheduleDaysInt = scheduleDaysInt;
	}

	/**
	 * Equals method for the item. Two items are equal if they have the same
	 * name, same scheduled days, and same location.
	 * 
	 * @param other
	 * @return true if the items are equal, false if the items are not equal
	 */
	public boolean equals(Item other)
	{
		if (this.name.equals(other.name) && this.location.equals(other.location)
				&& this.scheduleDaysInt == other.scheduleDaysInt)
		{
			return true;
		}

		return false;
	}

	/**
	 * @return the isSecure
	 */
	public boolean isSecure()
	{
		return isSecure;
	}

	/**
	 * @param isSecure
	 *            the isSecure to set
	 */
	public void setSecure(boolean isSecure)
	{
		this.isSecure = isSecure;
	}

	/**
	 * @return the studentWorkersPermitted
	 */
	public boolean isStudentWorkersPermitted()
	{
		return studentWorkersPermitted;
	}

	/**
	 * @param studentWorkersPermitted
	 *            the studentWorkersPermitted to set
	 */
	public void setStudentWorkersPermitted(boolean studentWorkersPermitted)
	{
		this.studentWorkersPermitted = studentWorkersPermitted;
	}
}
