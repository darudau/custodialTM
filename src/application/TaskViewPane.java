package application;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.Mnemonic;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * The task view pane stores the user select able task view. The task view pane
 * allows the user to add new tasks with the Add button, remove selected tasks
 * with the remove button, and edit selected tasks with the edit button.
 * 
 * When multiple tasks are selected, if the removed button is clicked, it will
 * remove all the tasks that were selected (if more than one task was selected a
 * window will pop up alerting the user that this will delete multiple tasks and
 * ask if they wish to continue.
 * 
 * When multiple tasks are selected and the edit button is selected, batch
 * editing will be allowed. Batch editing allows the user to change: security
 * clearances required and the scheduled days. Other attributes can only be
 * changed one at a time. //TODO batch may not be implemnted right away
 * 
 * @author Douglas Rudau
 * @version 02-12-2020
 *
 */
public class TaskViewPane extends BorderPane
		implements Observer, EventHandler<ActionEvent>
{
	/** Add task button */
	private Button addTaskButton;

	private Button addOfficebutton;

	/** Remove Task Button */
	private Button removeTaskButton;

	/** Edit Task Button */
	private Button editTaskButton;

	/** The check boxes for each task that was read in from the saved file */
	private ArrayList<CheckBox> taskCheckBoxesArrayList;

	/** Pointer to the task list model */
	private TaskList taskListModel;

	/** Scroll pane for the checkboxes for each item */
	private ScrollPane tasksScrollPane;

	/** HBox contianing the buttons on the control bar */
	private HBox buttonControlBar;

	/** Select menu integer command constants */
	public static final int NONE = 0;
	public static final int ALL = 1;
	public static final int INVERT = 2;
	public static final int FULL_TIME = 3;
	public static final int STUDENT_WORKERS = 4;
	public static final int SECURE = 5;
	public static final int NON_SECURE = 6;
	public static final int OFFICES = 7;
	public static final int NON_OFFICES = 8;

	/**
	 * Constructor for the TaskviewPane
	 * 
	 * @param taskList
	 *            the task list model that is to be respresented
	 */
	public TaskViewPane(TaskList taskList)
	{
		this.taskListModel = taskList;
		this.tasksScrollPane = new ScrollPane();
		this.taskListModel.addObserver(this);
		this.taskCheckBoxesArrayList = new ArrayList<CheckBox>();
		this.updateCheckBoxes();
		this.buttonControlBar = new HBox();
		this.initializeAddTaskButton();
		this.initializeAddOfficeButton();
		this.initializeEditTaskButton();
		this.initializeRemoveTaskButton();
		this.setTop(buttonControlBar);

		this.getStylesheets()
				.add(getClass().getResource("application.css").toExternalForm());
	}

	/**
	 * Method to allow for update with the observer pattern
	 * 
	 * @param arg0
	 * @param arg1
	 */
	public void update(Observable arg0, Object arg1)
	{
		updateCheckBoxes();
	}

	/**
	 * Method to update the checkboxes after the model being observed is set to
	 * changed and the observers are notified.
	 */
	private void updateCheckBoxes()
	{
		this.taskCheckBoxesArrayList.clear();
		for (int i = 0; i < this.taskListModel.getNumberOfTasks(); i++)
		{
			this.taskCheckBoxesArrayList
					.add(new CheckBox(this.taskListModel.get(i).getName()));
		}

		addCheckBoxesToWindow();
	}

	/**
	 * Method to add the checkboxes to window for each item that is within the
	 * task list
	 */
	private void addCheckBoxesToWindow()
	{
		this.setLeft(null);
		VBox checkboxes = new VBox();

		for (int i = 0; i < this.taskCheckBoxesArrayList.size(); i++)
		{
			checkboxes.getChildren().add(this.taskCheckBoxesArrayList.get(i));
		}

		this.tasksScrollPane.setContent(checkboxes);
		this.tasksScrollPane.setPrefWidth(260);
		this.setLeft(tasksScrollPane);

	}

	/**
	 * method to initialize the add task button and its associated tooltips
	 */
	private void initializeAddTaskButton()
	{
		this.addTaskButton = new Button("Add Task");
		this.addTaskButton.setText("A_dd Task");
		this.addTaskButton
				.setTooltip(new Tooltip("Opens up the add new task window"));
		this.addTaskButton.setOnAction(this);
		this.addTaskButton.setMnemonicParsing(true);
		this.buttonControlBar.getChildren().add(addTaskButton);
	}

	/**
	 * Method to intiailize the add office button in the TaskViewPane
	 */
	private void initializeAddOfficeButton()
	{
		this.addOfficebutton = new Button("Add Office");
		this.addOfficebutton.setText("Add Off_ice");
		this.addOfficebutton.setMnemonicParsing(true);
		this.addOfficebutton
				.setTooltip(new Tooltip("Opens the add new Office Window"));
		this.addOfficebutton.setOnAction(this);
		this.buttonControlBar.getChildren().add(addOfficebutton);
	}

	/**
	 * method to initialize the remove task button and its associated tooltips
	 */
	private void initializeRemoveTaskButton()
	{
		this.removeTaskButton = new Button("Remove");
		this.removeTaskButton.setText("Remo_ve");
		this.removeTaskButton
				.setTooltip(new Tooltip("Removes the selected task(s)"));
		this.removeTaskButton.setOnAction(this);
		this.removeTaskButton.setMnemonicParsing(true);
		this.buttonControlBar.getChildren().add(removeTaskButton);
	}

	/**
	 * method to initialize the edit task button and its associated tooltips
	 */
	private void initializeEditTaskButton()
	{
		this.editTaskButton = new Button("Edit Task");
		this.editTaskButton.setText("_Edit");
		this.editTaskButton.setMnemonicParsing(true);
		this.editTaskButton.setTooltip(
				new Tooltip("Opens up the edit task window for the selected task"));
		this.editTaskButton.setOnAction(this);
		this.buttonControlBar.getChildren().add(editTaskButton);
	}

	/**
	 * Handler method for action events in this class
	 */
	@Override
	public void handle(ActionEvent event)
	{
		/* Add task button brings up add task window */
		if (event.getSource().equals(addTaskButton))
		{
			AddItemWindow newTaskWindow = new AddItemWindow(this.taskListModel);
			this.updateCheckBoxes();
		}

		if (event.getSource().equals(this.addOfficebutton))
		{
			AddOfficeWindow window = new AddOfficeWindow(this.taskListModel);
			this.updateCheckBoxes();
		}

		/* Remove Task Button */
		if (event.getSource().equals(removeTaskButton))
		{
			ArrayList<String> tasksToRemove = new ArrayList<String>();
			for (int i = 0; i < this.taskCheckBoxesArrayList.size(); i++)
			{
				if (this.taskCheckBoxesArrayList.get(i).isSelected())
				{
					tasksToRemove.add(this.taskCheckBoxesArrayList.get(i).getText());
				}
			}

			for (int i = 0; i < tasksToRemove.size(); i++)
			{
				this.taskListModel.remove(tasksToRemove.get(i));
			}

		}

		/* Edit Task Button brings up edit window */
		if (event.getSource().equals(editTaskButton))
		{
			Item editItem = null;
			int selectedCount = 0;
			for (int i = 0; i < this.taskCheckBoxesArrayList.size(); i++)
			{
				if (this.taskCheckBoxesArrayList.get(i).isSelected())
				{
					selectedCount++;
					editItem = taskListModel.get(i);
				}
			}

			if (selectedCount > 1)
			{
				// multiple tasks cannot be edited at once
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Multiple Items Selected Error");
				alert.setHeaderText("Attempting to edit multiple items");
				alert.setContentText(
						"Multiple items cannot be edited at the same time.  "
								+ "Please select only one item to edit");
			}

			else if (selectedCount < 1)
			{
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error: No Items Selected");
				alert.setHeaderText("Please select an item to edit");
				alert.setContentText(
						"No item to edit has been selected.  Please select an item to edit");
			}
			else
			{
				if (editItem.getClass().equals(OfficeItem.class))
				{
					AddOfficeWindow w = new AddOfficeWindow(this.taskListModel,
							(OfficeItem) editItem);
				}
				else
				{
					AddItemWindow w = new AddItemWindow(this.taskListModel,
							editItem);
				}
			}
		}

	}

	/**
	 * Method to select check boxes for the select menu This method uses the
	 * select codes that are defined as constants
	 * 
	 * @param selectCode
	 *            integer select code
	 */
	public void selectCheckBoxes(int selectCode)
	{
		if (selectCode == ALL)
		{
			for (int i = 0; i < this.taskCheckBoxesArrayList.size(); i++)
			{
				this.taskCheckBoxesArrayList.get(i).setSelected(true);
			}
		}
		else if (selectCode == NONE)
		{
			for (int i = 0; i < this.taskCheckBoxesArrayList.size(); i++)
			{
				this.taskCheckBoxesArrayList.get(i).setSelected(false);
			}
		}
		else if (selectCode == INVERT)
		{
			for (int i = 0; i < this.taskCheckBoxesArrayList.size(); i++)
			{
				this.taskCheckBoxesArrayList.get(i).setSelected(
						!this.taskCheckBoxesArrayList.get(i).isSelected());
			}
		}
		else if (selectCode == FULL_TIME)
		{
			for (int i = 0; i < this.taskListModel.getNumberOfTasks(); i++)
			{
				if (!taskListModel.get(i).isStudentWorkersPermitted())
				{
					this.taskCheckBoxesArrayList.get(i).setSelected(true);
				}
				else
				{
					this.taskCheckBoxesArrayList.get(i).setSelected(false);
				}
			}
		}
		else if (selectCode == STUDENT_WORKERS)
		{
			for (int i = 0; i < this.taskListModel.getNumberOfTasks(); i++)
			{
				if (taskListModel.get(i).isStudentWorkersPermitted())
				{
					this.taskCheckBoxesArrayList.get(i).setSelected(true);
				}
				else
				{
					this.taskCheckBoxesArrayList.get(i).setSelected(false);
				}
			}
		}
		else if (selectCode == SECURE)
		{
			for (int i = 0; i < taskListModel.getNumberOfTasks(); i++)
			{
				if (this.taskListModel.get(i).isSecure())
				{
					this.taskCheckBoxesArrayList.get(i).setSelected(true);
				}
				else
				{
					this.taskCheckBoxesArrayList.get(i).setSelected(false);
				}
			}
		}
		else if (selectCode == NON_SECURE)
		{
			for (int i = 0; i < taskListModel.getNumberOfTasks(); i++)
			{
				if (this.taskListModel.get(i).isSecure())
				{
					this.taskCheckBoxesArrayList.get(i).setSelected(false);
				}
				else
				{
					this.taskCheckBoxesArrayList.get(i).setSelected(true);
				}
			}
		}
		else if (selectCode == OFFICES)
		{
			for (int i = 0; i < this.taskListModel.getNumberOfTasks(); i++)
			{
				if (this.taskListModel.get(i).getClass().equals(OfficeItem.class))
				{
					this.taskCheckBoxesArrayList.get(i).setSelected(true);
				}
				else
				{
					this.taskCheckBoxesArrayList.get(i).setSelected(false);
				}
			}
		}
		else if (selectCode == NON_OFFICES)
		{
			for (int i = 0; i < this.taskListModel.getNumberOfTasks(); i++)
			{
				if (this.taskListModel.get(i).getClass().equals(OfficeItem.class))
				{
					this.taskCheckBoxesArrayList.get(i).setSelected(false);
				}
				else
				{
					this.taskCheckBoxesArrayList.get(i).setSelected(true);
				}
			}
		}
	}

	/**
	 * Method to select the checkboxes based on a scheduled day This method uses
	 * the integer day codes defined in the Day class
	 * 
	 * @param dayCode
	 *            integer day code
	 */
	public void selectCheckBoxesByDay(int dayCode)
	{
		int dayCodeForI = -10;

		if (dayCode == Day.MONDAY)
		{
			for (int i = 0; i < this.taskListModel.getNumberOfTasks(); i++)
			{
				dayCodeForI = this.taskListModel.get(i).getScheduleDaysInt();
				if (dayCodeForI == dayCode || dayCodeForI == Day.MWF
						|| dayCodeForI == Day.EVERYDAY
						|| dayCodeForI == Day.WEEKDAYS)
				{
					this.taskCheckBoxesArrayList.get(i).setSelected(true);
				}
				else
				{
					this.taskCheckBoxesArrayList.get(i).setSelected(false);
				}
			}
		}
		else if (dayCode == Day.WEDNESDAY)
		{
			for (int i = 0; i < this.taskListModel.getNumberOfTasks(); i++)
			{
				dayCodeForI = this.taskListModel.get(i).getScheduleDaysInt();
				if (dayCodeForI == dayCode || dayCodeForI == Day.MWF
						|| dayCodeForI == Day.EVERYDAY
						|| dayCodeForI == Day.WEEKDAYS)
				{
					this.taskCheckBoxesArrayList.get(i).setSelected(true);
				}
				else
				{
					this.taskCheckBoxesArrayList.get(i).setSelected(false);
				}
			}
		}
		else if (dayCode == Day.FRIDAY)
		{
			for (int i = 0; i < this.taskListModel.getNumberOfTasks(); i++)
			{
				dayCodeForI = this.taskListModel.get(i).getScheduleDaysInt();
				if (dayCodeForI == dayCode || dayCodeForI == Day.MWF
						|| dayCodeForI == Day.TRF || dayCodeForI == Day.EVERYDAY
						|| dayCodeForI == Day.WEEKDAYS)
				{
					this.taskCheckBoxesArrayList.get(i).setSelected(true);
				}
				else
				{
					this.taskCheckBoxesArrayList.get(i).setSelected(false);
				}
			}
		}
		else if (dayCode == Day.TUESDAY)
		{
			for (int i = 0; i < this.taskListModel.getNumberOfTasks(); i++)
			{
				dayCodeForI = this.taskListModel.get(i).getScheduleDaysInt();
				if (dayCodeForI == dayCode || dayCodeForI == Day.TR
						|| dayCodeForI == Day.TRF || dayCodeForI == Day.EVERYDAY
						|| dayCodeForI == Day.WEEKDAYS)
				{
					this.taskCheckBoxesArrayList.get(i).setSelected(true);
				}
				else
				{
					this.taskCheckBoxesArrayList.get(i).setSelected(false);
				}
			}
		}
		else if (dayCode == Day.THURSDAY)
		{
			for (int i = 0; i < this.taskListModel.getNumberOfTasks(); i++)
			{
				dayCodeForI = this.taskListModel.get(i).getScheduleDaysInt();
				if (dayCodeForI == dayCode || dayCodeForI == Day.TR
						|| dayCodeForI == Day.TRF || dayCodeForI == Day.EVERYDAY
						|| dayCodeForI == Day.WEEKDAYS)
				{
					this.taskCheckBoxesArrayList.get(i).setSelected(true);
				}
				else
				{
					this.taskCheckBoxesArrayList.get(i).setSelected(false);
				}
			}
		}
		else if (dayCode == Day.SATURDAY)
		{
			for (int i = 0; i < this.taskListModel.getNumberOfTasks(); i++)
			{
				dayCodeForI = this.taskListModel.get(i).getScheduleDaysInt();
				if (dayCodeForI == dayCode || dayCodeForI == Day.EVERYDAY
						|| dayCodeForI == Day.WEEKENDS)
				{
					this.taskCheckBoxesArrayList.get(i).setSelected(true);
				}
				else
				{
					this.taskCheckBoxesArrayList.get(i).setSelected(false);
				}
			}
		}
		else if (dayCode == Day.SUNDAY)
		{
			for (int i = 0; i < this.taskListModel.getNumberOfTasks(); i++)
			{
				dayCodeForI = this.taskListModel.get(i).getScheduleDaysInt();
				if (dayCodeForI == dayCode || dayCodeForI == Day.EVERYDAY
						|| dayCodeForI == Day.WEEKENDS)
				{
					this.taskCheckBoxesArrayList.get(i).setSelected(true);
				}
				else
				{
					this.taskCheckBoxesArrayList.get(i).setSelected(false);
				}
			}
		}
		else
		{
			for (int i = 0; i < taskListModel.getNumberOfTasks(); i++)
			{
				if (dayCode == this.taskListModel.get(i).getScheduleDaysInt())
				{
					this.taskCheckBoxesArrayList.get(i).setSelected(true);
				}
				else
				{
					this.taskCheckBoxesArrayList.get(i).setSelected(false);
				}
			}
		}

	}

	public ArrayList<Item> getSelectedItems()
	{
		ArrayList<Item> selected = new ArrayList<Item>();
		for (int i = 0; i < this.taskCheckBoxesArrayList.size(); i++)
		{
			if (this.taskCheckBoxesArrayList.get(i).isSelected())
			{
				selected.add(this.taskListModel.get(i));
			}
		}

		return selected;

	}
}
