package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Observable;

/**
 * Task List class generates a task list that items can be added to and removed
 * from. A task list can be saved to a specified file as well and loaded froma
 * file using the java.io.serializable interface.
 * 
 * @author Douglas Rudau
 * @version 03-05-2020
 *
 */
public class TaskList extends Observable implements java.io.Serializable
{
	private static final long serialVersionUID = 794048107822769859L;

	/** Array list containing the tasks */
	private ArrayList<Item> taskList;

	/** name of the task list */
	private String name;

	/**
	 * Constructor for the task list class
	 * 
	 * @param name
	 *            the name of the task list
	 */
	public TaskList(String name)
	{
		taskList = new ArrayList<Item>();
		this.name = name;
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * Default constructor for unnamed list
	 */
	public TaskList()
	{
		taskList = new ArrayList<Item>();
		this.name = "Unnamed List";
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * Clears the task list of all items
	 */
	public void clearList()
	{
		this.taskList.clear();
		this.name = "Unnamed List";
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * Gets the tasks that are scheduled on a specific day or set of days
	 * described by the day code and returns as an array list of the items. if
	 * there are no tasks scheduled on a particular setting, null is returned.
	 * 
	 * @param dayCode
	 * @return ArrayList of items that are scheduled on that specific day. Null
	 *         is returned if no tasks are scheduled
	 */
	public ArrayList<Item> tasksOnDay(int dayCode)
	{
		if (dayCode < Day.NOT_SCHEDULED && dayCode > Day.TRF)
		{
			ArrayList<Item> tasks = new ArrayList<Item>();
			for (int i = 0; i < taskList.size(); i++)
			{
				if (taskList.get(i).getScheduleDaysInt() == dayCode)
				{
					tasks.add(taskList.get(i));
				}
			}
			return tasks;
		}
		else
		{
			return null; // no tasks scheduled
		}
	}

	/**
	 * Adds the task if the task is not already present in the list. if the task
	 * is already present in the list, false is returned and the duplicate task
	 * is not added to the list.
	 * 
	 * @param task
	 * @return true if the task was added, false if the task was not added
	 */
	public boolean addTask(Item task)
	{
		System.out.println("task list size: " + this.taskList.size());
		// check if item is already in task list
		if (isPresent(task))
		{
			return false;
		}
		else
		{
			taskList.add(task);
			this.setChanged();
			this.notifyObservers();
			return true;
		}

	}

	/**
	 * Removes a task passed in by an Item object. If removal is successful,
	 * true is return. If the task was not present, false is returned.
	 * 
	 * @param task
	 * @return true if Item was removed, false if not
	 */
	public boolean remove(Item task)
	{
		if (taskList.remove(task))
		{
			this.setChanged();
			this.notifyObservers();
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Removes a task by selecting tasks based on name
	 * 
	 * @param taskName
	 * @return true if task was removed from the list, false if task was not
	 *         removed from the list.
	 */
	public boolean remove(String taskName)
	{
		for (int i = 0; i < taskList.size(); i++)
		{
			if (taskList.get(i).getName().equals(taskName))
			{
				taskList.remove(i);
				this.setChanged();
				this.notifyObservers();
				return true;
			}
		}

		return false;
	}

	/**
	 * Checks if the task is already present in the taskList.
	 * 
	 * @param task
	 *            Item to check for
	 * @return True if the task is present in the list, false if the task is not
	 *         present
	 */
	public boolean isPresent(Item task)
	{
		for (int i = 0; i < taskList.size(); i++)
		{
			if (task.equals(taskList.get(i)))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns the task list as string
	 */
	public String toString()
	{
		Date today = Calendar.getInstance().getTime();
		Locale loc = new Locale("en", "US");
		DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG, loc);
		SimpleDateFormat sdf2 = new SimpleDateFormat("hh.mm aa");
		String s = "-----------------------------------------------------------------------------\n";
		s += "| " + this.name + "\n";
		s += "| Version: " + dateFormat.format(today) + "\t" + sdf2.format(today)
				+ "\n";
		s += "------------------------------------------------------------------------------\n";
		for (int i = 0; i < taskList.size(); i++)
		{
			s += taskList.get(i).toString() + "\n\n";
		}

		return s;
	}

	/**
	 * Sorts the task list based on the compare to method for the item class.
	 * This method uses the Java collections.sort method.
	 */
	public void sort()
	{
		Collections.sort(taskList);
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * Gets tasks that have a specified security clearance.
	 * 
	 * If the item is a secure item (ie a secure office or other secure area)
	 * the isSecure parameter will be true. If this parameter is false, the task
	 * is not secure.
	 * 
	 * Student workers are permitted to work on some secure tasks, therefore if
	 * the student workers permitted and isSecure values are both set to true,
	 * student workers that are directly supervised may work on the tasks.
	 * 
	 * Some tasks are not secure, but student workers are not permitted to do
	 * these tasks, such as auto scrubbing, operating the riding vacuum, and
	 * filling out work orders. These tasks are for full time staff only.
	 * 
	 * @param isSecure
	 *            true if the item is a secure item, false if the item is not
	 *            secure
	 * @param studentWorkersPermitted
	 *            true if student workers are allowed to work on item, false if
	 *            student workers are not permitted to work on the item
	 * @return
	 */
	public TaskList getItemsWithSecurityClearance(boolean isSecure,
			boolean studentWorkersPermitted)
	{
		String newName = "";
		if (isSecure)
		{
			if (studentWorkersPermitted)
			{
				newName = name + ": Directly Supervised Student Worker Tasks";
			}
			else
			{
				newName = name + ": Full Time Approved Staff Only Tasks";
			}
		}
		else
		{
			if (studentWorkersPermitted)
			{
				newName = name + ": Student Worker Tasks";
			}
			else
			{
				newName = name += "Full Time Staff Only Tasks";
			}
		}

		TaskList list = new TaskList(newName);
		for (int i = 0; i < this.taskList.size(); i++)
		{
			if (taskList.get(i).isSecure() == isSecure && taskList.get(i)
					.isStudentWorkersPermitted() == studentWorkersPermitted)
			{
				list.addTask(taskList.get(i));
			}
		}

		return list;
	}

	/**
	 * Save routine for task list class
	 * 
	 * @param saveLocation
	 *            String path of where the file is to be saved
	 * @return true if saving was successful, false if saving was not
	 */
	public boolean save(String saveLocation)
	{
		try
		{
			FileOutputStream fileOut = new FileOutputStream(saveLocation);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(taskList);
			out.close();
			fileOut.close();
			System.out.println("Success data is saved serilzied in " + saveLocation);
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
		return true;
	}

	/**
	 * Open routine for loading a Task List
	 * 
	 * @param filePath
	 *            path of where the file is being saved to
	 * @return true if loading was sucessful, false if loading was unsucessful
	 * @throws ClassNotFoundException
	 */
	public boolean open(String filePath) throws ClassNotFoundException
	{
		ArrayList inList = null;
		try
		{
			FileInputStream fileIn = new FileInputStream(filePath);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			inList = (ArrayList) in.readObject();
			in.close();
			fileIn.close();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
			return false;
		}

		this.taskList = inList;
		this.setChanged();
		this.notifyObservers();
		return true;
	}

	/**
	 * Saves the task list a text file for printing and human readability
	 * 
	 * @param filePath
	 *            the string contianing the file path
	 * @return true if sucessfull, false if not
	 */
	public boolean saveListTextToFile(String filePath)
	{
		PrintWriter out;
		try
		{
			out = new PrintWriter(filePath);
			out.println(this.toString());
			out.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			return false;
		}

		return true;

	}

	/**
	 * Gets an item from the task list at a specific index
	 * 
	 * @param index
	 *            the index of the item
	 * @return the item at index
	 */
	public Item get(int index)
	{
		return this.taskList.get(index);
	}

	/**
	 * Method to get the number of tasks in the task list
	 * 
	 * @return the number of tasks in the task list
	 */
	public int getNumberOfTasks()
	{
		return this.taskList.size();
	}

	/**
	 * method to get the name of the task list
	 * 
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * method to set the name of the task list
	 * 
	 * @param name
	 *            the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * method to save the task list as a shortened text file. A shorted text
	 * file only shows the names of each item in the task list and does not show
	 * each subtask of each task. This method allows for a shorter text file to
	 * be created.
	 * 
	 * @param filePath
	 *            the location where this file will be saved
	 * @return true if saving was sucessful, false if saving was unsucessful
	 */
	public boolean saveListShortTextFile(String filePath)
	{
		PrintWriter out;
		try
		{
			out = new PrintWriter(filePath);
			out.println(this.toShortList());
			out.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * Generates a string contianing the list in a shortened format. This format
	 * only includes the name of the list the date it was printed (list version)
	 * and the names of each task. Subtasks are not shown, nor are other task
	 * detials shown.
	 * 
	 * @return shortened represenation of the task list
	 */
	public String toShortList()
	{
		Date today = Calendar.getInstance().getTime();
		Locale loc = new Locale("en", "US");
		DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG, loc);
		SimpleDateFormat sdf2 = new SimpleDateFormat("hh.mm aa");
		String s = "-----------------------------------------------------------------------------\n";
		s += "| " + this.name + " Shortened Version\n";
		s += "| Version: " + dateFormat.format(today) + "\t" + sdf2.format(today)
				+ "\n";
		s += "------------------------------------------------------------------------------\n";
		for (int i = 0; i < taskList.size(); i++)
		{
			s += taskList.get(i).getName() + "\n\n";
		}

		return s;
	}

	/**
	 * Exports the task list to an HTML document. The task list displays each
	 * task by name, and includes the version of the list as well as the name.
	 * The HTML document allows for a list to have a nicer overall appearance
	 * when printed out and can be read without any extra software.
	 * 
	 * @param filePath
	 *            the location where the HTML document is to be saved
	 * @return true if sucessful, false if failure
	 */
	public boolean exportHTML(String filePath)
	{
		PrintWriter out;
		try
		{
			out = new PrintWriter(filePath);
			out.println(this.toHTML());
			out.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * Converts the task list to an HTML representation, similar to the
	 * toShortedString() method. Extra detials about the tasks are omited to
	 * reduce the amout of visual information the reader must process and the
	 * task list is displayed using an unordered list format.
	 * 
	 * @return HTML representation of the task list
	 */
	public String toHTML()
	{
		Date today = Calendar.getInstance().getTime();
		Locale loc = new Locale("en", "US");
		DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG, loc);
		SimpleDateFormat sdf2 = new SimpleDateFormat("hh.mm aa");

		String s = "<!DOCTYPE html>\n<html><body>\n";
		s += "<style>article {-webkit-columns: 2 200px;-moz-column: 2 200px;columns: 2 200px;}</style>";

		s += "<h3>" + this.name + ": " + today.toString() + "</h3>\n";
		s += "<article>";
		s += listItemsToHTML();
		s += "</article></body>\n</html>";
		return s;

	}

	/**
	 * Method to convert each list item to an HTML for the task list. This
	 * method is a helper method to the toHTML() method. Tasks are returned as
	 * an unordered list with subtasks being represented as sub items within the
	 * list.
	 * 
	 * @return HTML string representing the items in the task list
	 */
	private String listItemsToHTML()
	{
		String s = "<ul style=\"list-style-type:circle\">";
		for (int i = 0; i < taskList.size(); i++)
		{
			s += "<li>" + taskList.get(i).toHTML() + "</li>";
			if (taskList.get(i).getSubTasks().size() != 0)
			{
				s += "<br>";
			}
		}
		s += "</ul>";

		return s;
	}

}
