package application;

import javafx.scene.image.Image;

public class HelpContent
{
	public static final String GUI_EXPLANATION = "";

	public static final String NEW_LIST = "CREATING NEW LISTS\n";

	public static final String OPEN_LIST = "OPENING LISTS\nTo open an existing *.list file,"
			+ " use the open command found in the file menu.  A pop up "
			+ "window will appear to allow you to browse for any *.list file"
			+ " to open.  After selecting the file, click the open button.  "
			+ "The list will be opened up for editing.  ";

	public static final String SAVE_LIST = "SAVING LISTS\nLists can be saved using the save "
			+ "options in the File Menu.  If a list has not yet been saved, a "
			+ "file selection window will appear to allow you to select where "
			+ "you want to save the *.list file.  Select a name for the new "
			+ "list and save the file using the permitted extension of *.list."
			+ "  If the file has already been saved, clicking save from the "
			+ "file menu will result in the file's *.list file be updated."
			+ "  \n\nIf you want to create a new version of the *.list file, "
			+ "use the save as function from the file menu.  This function "
			+ "creates a new *.list file with the current list and does not "
			+ "overrite the old *.list file.  The save as function is uesful "
			+ "for creating subsets from a master list.";

	public static final String EXPORT_TXT = "EXPORTING TO TEXT FILES\nExporting the lists to a text file will"
			+ " allow the lists to be saved in a human readable text format.  "
			+ "These files can also be used to create paper printouts of the lists too.";

	public static final String PRINTING_LISTS = "PRINTING LISTS\nList manager at this time does"
			+ " not natively support printing of lists to paper or pdf.  "
			+ "However, a list can be exported as a text file (*.txt), once a "
			+ "list is in this format, it can be printed from the operating "
			+ "system's local text editor.  Notepad, Notepad++, and VIM are "
			+ "some common text editors found on operating systems.  "
			+ "To print the list, open the text file up in your favorite text "
			+ "editor and use the text editor's built in printing functions.";

	public static final String ADD_ITEM = "ADD ITEM\nWhen the add item is clicked, "
			+ "the add item dialog will appear.  When this dialog opens, the"
			+ " required fields to add an item to the list will be displayed."
			+ "  Each feild is required.\n\nTASK PARAMETERS"
			+ "\n-----------------\nNAME: The name of the task, usually a "
			+ "descriptive name is used, such as mop the lobby floors or "
			+ "trash run, etc.\n\nLOCATION:  Where the task takes place "
			+ "A building room number and name abbriavtion are often used,"
			+ " though some tasks may take place in larger areas,"
			+ " such as an entire floor"
			+ "\n\nSCHEDULED DAYS FOR TASK: This is a drop down "
			+ "box to select how often the task occurs on a weekly basis."
			+ "\n\nTASK IS SECURE:  Check this box if the task is considered"
			+ " secure.  Secure tasks usaully require a security agreement"
			+ " and clearance or involve hazardous materials or equipment."
			+ "\n\nSTUDENT WORKERS PERMITTED:  Check this box if"
			+ " student workers are allowed to perform this task.";

	public static final String ADD_OFFICE = "ADD OFFICE\nAn Office is defined as a office"
			+ " space, the space can be as small as a single office or as "
			+ "large as an entire floor.  The time required to clean an office"
			+ " is based off the number of workers and the total square "
			+ "footage for the office.\n\nOFFICE PARAMETERS"
			+ "\n-----------------\nNAME: The name of the office, usually a "
			+ "company name or suite name is used (such as Human Resources "
			+ "or All Data Inc.)\n\nLOCATION:  Where the office is located, "
			+ "usaully the room's number and building's abrevaited name are "
			+ "used (ANC 550)\n\nSCHEDULED DAYS FOR TASK: This is a drop down "
			+ "box to select how often the area is cleaned on a weekly basis."
			+ "\n\nTASK IS SECURE:  Check this box if the office is considered"
			+ " secure.  Secure offices usaully require a security agreement"
			+ " and clearance.\n\nSTUDENT WORKERS PERMITTED:  Check this box if"
			+ " student workers are allowed to clean this office.\n\n"
			+ "WORKER COUNT:  The number of workers in the office ranging from "
			+ "0 (an empty office) to integer limit (though an office with "
			+ "that many workers will likely not exist, and should be broken "
			+ "into seperate sections)\n\nSQUARE FOOTAGE:  The amount of floor"
			+ " space in the office, in square feet.  This number is used to "
			+ "calculate the time required to vacuum an office.\n\nBREAKROOM "
			+ "IN OFFICE:  Check this box if the office contains a breakroom.  "
			+ "Breakrooms are sometimes referred to as lunchrooms, kitchens, "
			+ "or employee lounges.\n\n";

	public static final String EDIT_ITEM = "EDIT ITEM/OFFICE\nWhen editing an item, the item's"
			+ " orginal parameters will be shown in each of the form fields.  "
			+ "Any of these fields can be changed.  These parameters will be "
			+ "changed on the item or office inside the task list after "
			+ "clicking the ok button";

	public static final String REMOVE_ITEM = "REMOVE ITEM/OFFICE\nTo remove items, select the "
			+ "checkbox next to the item(s) that you want to remove.  "
			+ "Note that item removal cannot be undone, so make sure "
			+ "there is a backup copy of the list.";

	public static final String SELECT_MENU = "";

	public static String getMasterHelp()
	{
		String div = "\n=====================================\n";
		String s = "FILE MENU" + div + NEW_LIST + div + OPEN_LIST + div + SAVE_LIST
				+ div + EXPORT_TXT + div + PRINTING_LISTS + div;
		s += "SELECT MENU " + div + SELECT_MENU + div;
		s += "ADDING, EDITING, AND REMOVING TASKS/OFFICES" + div + ADD_ITEM + div
				+ ADD_OFFICE + div + EDIT_ITEM + div + REMOVE_ITEM;
		return s;
	}

	/** Images for the Help Menu */
	// public static final Image IMG_GUI_EXPLANATION = new Image();
	// public static final Image IMG_FILE_OPEN = new Image();
	// public static final Image IMG_FILE_SAVE = new Image();
	// public static final Image IMG_EXPORT_TXT = new Image();
	// public static final Image IMG_ADD_ITEM = new Image();
	// public static final Image IMG_ADD_OFFICE = new Image();
	// public static final Image IMG_REMOVE_ITEM = new Image();
	// public static final Image IMG_EDIT_ITEM = new Image();
}
