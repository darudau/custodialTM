package application;

import java.util.ArrayList;

/**
 * An OfficeItem is a representation of an office suite that needs to be
 * cleaned. Each office has a total number of workers, a total squarefootage, a
 * number of conference rooms (can be zero). An office may or may not have a
 * breakroom. Each office item has similar cleaning tasks for the subtasks,
 * based on the input parameters, including workerCount, squareFeet,
 * hasBreakroom, and conference room count. Thees attributes are used to
 * determine how long the office will take ot clean.
 * 
 * A breakroom typically requries approixamtetly 30 minutes to clean completely.
 * A conference room typically requires about five minutes to clean completely.
 * 
 * @author Douglas Rudau
 * @version 02-04-2020
 *
 */
public class OfficeItem extends Item
		implements java.io.Serializable, Comparable<Item>, Cloneable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** number of workers in office */
	private int workerCount;

	/** square footage of Office space to the nearest square foot */
	private int squareFeet;

	/** Does the office have a breakroom */
	private boolean hasBreakroom;

	/** Number of conference rooms in the office */
	private int conferenceRoomCount;

	private Item conferenceRoom;
	private Item breakroom;
	private Item emptyOfficeTrash;
	private Item cleanGlass;
	private Item vacuum;

	private final double HOURS_TO_VACUUM_SQ_FOOT = .00002;
	private final double HOURS_TO_EMPTY_ONE_TRASH = .00001;
	private final double HOURS_TO_CLEAN_ONE_CONFERENCE_ROOM = .05;

	public static final int DEFAULT_SQ_FEET = 0;
	public static final int DEFAULT_WORKER_COUNT = 0;
	public static final boolean DEFAULT_BREAKROOM = false;
	public static final int DEFAULT_CONFERENCE_ROOM_COUNT = 0;

	public OfficeItem()
	{
		super();
		this.hasBreakroom = DEFAULT_BREAKROOM;
		this.squareFeet = DEFAULT_SQ_FEET;
		this.workerCount = DEFAULT_WORKER_COUNT;
		this.conferenceRoomCount = DEFAULT_CONFERENCE_ROOM_COUNT;

		setupVacuum();
		setupConferenceRoom();
		setupEmptyOfficeTrash();
		this.addSubTask(new Item("Clean Front Door Glass", this.getLocation(),
				this.getScheduleDaysInt(), .02));

		this.addSubTask(vacuum);
		this.addSubTask(cleanGlass);
		this.addSubTask(emptyOfficeTrash);
	}

	private void setupVacuum()
	{
		double time = (double) (squareFeet) * this.HOURS_TO_VACUUM_SQ_FOOT;
		this.vacuum = new Item("Vacuum Office Carept", this.getLocation(),
				this.getScheduleDaysInt(), time);
		this.setExpectedTime(this.officeExpectedTime());
		this.getSubTasks().add(vacuum);
	}

	private void setupConferenceRoom()
	{
		double time = (double) (conferenceRoomCount)
				* this.HOURS_TO_CLEAN_ONE_CONFERENCE_ROOM;
		this.conferenceRoom = new Item("Clean Conference Rooms", this.getLocation(),
				this.getScheduleDaysInt(), time);
		this.setExpectedTime(this.officeExpectedTime());
		this.getSubTasks().add(conferenceRoom);
	}

	private void setupEmptyOfficeTrash()
	{
		double time = (double) this.workerCount * this.HOURS_TO_EMPTY_ONE_TRASH;
		this.emptyOfficeTrash = new Item("Empty Office Trashes and Recyclings",
				this.getLocation(), this.getScheduleDaysInt(), time);
		this.setExpectedTime(this.officeExpectedTime());
		this.getSubTasks().add(emptyOfficeTrash);
	}

	private void setupCleanGlass()
	{
		this.getSubTasks().add(new Item("Clean Front Door Glass", this.getLocation(),
				this.getScheduleDaysInt(), .01));
	}

	private void setupBreakroom()
	{
		if (hasBreakroom)
		{
			this.getSubTasks().add(new Item("Clean Breakroom", this.getLocation(),
					this.getScheduleDaysInt(), .5));
		}
	}

	/**
	 * Constructor for an office item
	 * 
	 * @param name
	 *            name of the office
	 * @param description
	 *            short description of the office
	 * @param location
	 *            building name and number of the office
	 * @param expectedTime
	 *            Expected time to finish cleaning on an average in hours
	 * @param subTasks
	 *            items that need to be completed within each office
	 * @param workerCount
	 * @param squareFeet
	 */
	public OfficeItem(String name, String description, String location,
			int scheduledDays, double expectedTime, ArrayList<Item> subTasks,
			boolean isSecure, boolean studentsPermitted, int workerCount,
			int squareFeet, boolean hasBreakroom, int numConferenceRooms)
	{
		super(name, description, scheduledDays, expectedTime, isSecure,
				studentsPermitted);
		setWorkerCount(workerCount);
		setSquareFeet(squareFeet);
		setHasBreakroom(hasBreakroom);
		setConferenceRoomCount(numConferenceRooms);

		vacuum = new Item("Vacuum Office Carpet", location, scheduledDays,
				(double) (squareFeet) * this.HOURS_TO_VACUUM_SQ_FOOT);
		cleanGlass = new Item("Clean Glass", location, scheduledDays, .008);
		emptyOfficeTrash = new Item("Empty Office Trash", location, scheduledDays,
				(double) (workerCount * this.HOURS_TO_EMPTY_ONE_TRASH));
		breakroom = new Item("Breakroom", location, scheduledDays, .5);

		this.addSubTasks(hasBreakroom, numConferenceRooms);
	}

	public OfficeItem(String name, String location, int days, int workerCount,
			int squareFeet, boolean hasBreakroom, int numConferenceRooms)
	{
		super(name, location, days, 0.0);
		// this.setSubTasks(new ArrayList<Item>()); //rm if fixed
		vacuum = new Item("Vacuum Office Carpet", location, days,
				(double) (squareFeet) * this.HOURS_TO_VACUUM_SQ_FOOT);
		cleanGlass = new Item("Clean Glass", location, days, .008);
		emptyOfficeTrash = new Item("Empty Office Trash", location, days,
				(double) (workerCount * this.HOURS_TO_EMPTY_ONE_TRASH));
		this.setWorkerCount(workerCount);
		this.hasBreakroom = hasBreakroom;
		this.setConferenceRoomCount(numConferenceRooms);
		this.setSquareFeet(squareFeet);
		this.addSubTasks(hasBreakroom, numConferenceRooms);
		conferenceRoom = new Item("Clean Conference Room", location, days,
				(double) (conferenceRoomCount * HOURS_TO_CLEAN_ONE_CONFERENCE_ROOM));
	}

	public OfficeItem(String name, String location, int days, boolean isSecure,
			boolean studentWorkersPermitted, int workerCount, int squareFeet,
			boolean hasBreakroom, int numConferenceRooms)
	{
		super(name, location, days, 0.0, isSecure, studentWorkersPermitted);
		// this.setSubTasks(new ArrayList<Item>()); //rm if fixed
		vacuum = new Item("Vacuum Office Carpet", location, days,
				(double) (squareFeet) * this.HOURS_TO_VACUUM_SQ_FOOT);
		cleanGlass = new Item("Clean Glass", location, days, .008);
		emptyOfficeTrash = new Item("Empty Office Trash", location, days,
				(double) (workerCount * this.HOURS_TO_EMPTY_ONE_TRASH));
		this.setWorkerCount(workerCount);
		this.hasBreakroom = hasBreakroom;
		this.setConferenceRoomCount(numConferenceRooms);
		this.setSquareFeet(squareFeet);
		this.addSubTasks(hasBreakroom, numConferenceRooms);
		conferenceRoom = new Item("Clean Conference Room", location, days,
				(double) (conferenceRoomCount * HOURS_TO_CLEAN_ONE_CONFERENCE_ROOM));
	}

	/**
	 * Method to add the subtasks to the office item based off what items are in
	 * the office, including office size and if the office has a breakroom or
	 * not.
	 * 
	 * @param hasBreakroom
	 * @param numConferenceRooms
	 */
	private void addSubTasks(boolean hasBreakroom, int numConferenceRooms)
	{
		if (hasBreakroom)
		{
			this.getSubTasks().add(breakroom);
		}
		if (numConferenceRooms > 0)
		{
			this.getSubTasks().add(conferenceRoom);
		}

		this.addSubTask(emptyOfficeTrash);
		this.addSubTask(vacuum);
		this.addSubTask(cleanGlass);

		double totalTime = 0;

		if (this.getSubTasks().size() > 0)
		{
			for (int i = 0; i < this.getSubTasks().size(); i++)
			{
				if (this.getSubTasks().get(i) != null)
				{
					totalTime += this.getSubTasks().get(i).getExpectedTime();
				}
			}
		}

		this.setExpectedTime(totalTime);
	}

	/**
	 * Gets the total count of workers in the office suite
	 * 
	 * @return total number of workers in office suite
	 */
	public int getWorkerCount()
	{
		return workerCount;
	}

	/**
	 * Sets the number of workers in the office suite.
	 * 
	 * @param workerCount
	 */
	public void setWorkerCount(int workerCount)
	{
		if (workerCount >= 0)
		{
			this.getSubTasks().clear();
			this.workerCount = workerCount;
			this.setupVacuum();
			this.setupConferenceRoom();
			this.setupEmptyOfficeTrash();
			this.setupBreakroom();
			this.setupCleanGlass();
		}
		else
		{
			throw new IllegalArgumentException("Worker Count cannot be negative");
		}
	}

	/**
	 * Gets the total square footage of an office item
	 * 
	 * @return number of square feet in office
	 */
	public int getSquareFeet()
	{
		return squareFeet;
	}

	/**
	 * Sets the total square footage of an office item
	 * 
	 * @param squareFeet
	 */
	public void setSquareFeet(int squareFeet)
	{
		if (squareFeet >= 0)
		{
			this.getSubTasks().clear();
			this.squareFeet = squareFeet;
			this.setupVacuum();
			this.setupConferenceRoom();
			this.setupEmptyOfficeTrash();
			this.setupBreakroom();
			this.setupCleanGlass();
		}
		else
		{
			throw new IllegalArgumentException("Square Footage cannot be negative");
		}
	}

	/**
	 * @return the hasBreakroom
	 */
	public boolean hasBreakroom()
	{
		return hasBreakroom;
	}

	/**
	 * @param hasBreakroom
	 *            the hasBreakroom to set
	 */
	public void setHasBreakroom(boolean hasBreakroom)
	{
		this.hasBreakroom = hasBreakroom;
		this.getSubTasks().clear();
		this.setupVacuum();
		this.setupConferenceRoom();
		this.setupEmptyOfficeTrash();
		this.setupBreakroom();
		this.setupCleanGlass();
	}

	/**
	 * @return the conferenceRoomCount
	 */
	public int getConferenceRoomCount()
	{
		return conferenceRoomCount;
	}

	/**
	 * @param conferenceRoomCount
	 *            the conferenceRoomCount to set
	 */
	public void setConferenceRoomCount(int conferenceRoomCount)
	{
		if (conferenceRoomCount >= 0)
		{
			this.getSubTasks().clear();
			this.conferenceRoomCount = conferenceRoomCount;
			this.setupVacuum();
			this.setupConferenceRoom();
			this.setupEmptyOfficeTrash();
			this.setupBreakroom();
			this.setupCleanGlass();
		}
		else
		{
			throw new IllegalArgumentException(
					"Conference Room Count cannot be smaller than zero");
		}
	}

	public boolean equals(OfficeItem otherItem)
	{
		if (this.compareTo(otherItem) != 0)
		{
			return false;
		}
		else
		{
			if (this.workerCount != otherItem.workerCount)
			{
				return false;
			}

			if (this.hasBreakroom != otherItem.hasBreakroom)
			{
				return false;
			}

			if (this.squareFeet != otherItem.squareFeet)
			{
				return false;
			}

			return true;
		}
	}

	public double officeExpectedTime()
	{
		double time = 0;
		for (int i = 0; i < this.getSubTasks().size(); i++)
		{
			time += this.getSubTasks().get(i).getExpectedTime();
		}

		return time;
	}
}
