package application;

import java.io.File;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Pagination;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class ListMenuBar extends MenuBar
		implements EventHandler<ActionEvent>, Observer
{
	private File modelSavePath;

	private TaskList model;

	private Stage stage;

	private TaskViewPane taskView;

	private Menu fileMenu;
	private MenuItem newItem;
	private MenuItem openItem;
	private MenuItem saveItem;
	private MenuItem saveAsItem;
	// private MenuItem printItem;
	private MenuItem sortItem;
	private MenuItem newFromSelectedItem;
	private MenuItem exportTxtItem;
	private MenuItem exportShortTXTItem;
	private MenuItem exportHTMLItem;
	private MenuItem exit;
	private MenuItem exitNoSave;

	private Menu selectMenu;
	private MenuItem selectAll;
	private MenuItem selectNone;
	private MenuItem selectInvert;
	private MenuItem selectFullTimeOnly;
	private MenuItem selectStudentWorkerApproved;
	private MenuItem selectSecure;
	private MenuItem selectNonSecure;
	private MenuItem selectOffices;
	private MenuItem selectNonOffices;

	/** Sub select menu for specific days */
	private Menu selectCommonSchedules;
	private MenuItem selectNotScheduled;
	private MenuItem selectEveryday;
	private MenuItem selectWeekdays;
	private MenuItem selectWeekends;
	private MenuItem selectMWF;
	private MenuItem selectTR;
	private MenuItem selectTRF;
	/** Sub Select Menu for specific days of week */
	private MenuItem selectSunday;
	private MenuItem selectMonday;
	private MenuItem selectTuesday;
	private MenuItem selectWednesday;
	private MenuItem selectThursday;
	private MenuItem selectFriday;
	private MenuItem selectSaturday;

	private Menu helpMenu;
	private MenuItem about;
	private MenuItem helpCompanion;

	private boolean isModelChangedSinceLastSave;

	public ListMenuBar(TaskList model, TaskViewPane taskViewPane, Stage stage)
	{
		if (model == null)
		{
			this.model = new TaskList();
		}
		else
		{
			this.model = model;
		}

		this.model.addObserver(this);
		this.stage = stage;
		this.taskView = taskViewPane;
		initFileMenu();
		initSelectMenu();
		initHelpMenu();

	}

	private void initHelpMenu()
	{
		this.helpMenu = new Menu("Help");
		this.about = new MenuItem("About");
		this.about.setOnAction(this);
		this.helpCompanion = new MenuItem("Help Companion");
		this.helpCompanion.setOnAction(this);

		this.helpMenu.getItems().add(about);
		this.helpMenu.getItems().add(helpCompanion);
		this.getMenus().add(helpMenu);
	}

	private void initSelectMenu()
	{
		this.selectMenu = new Menu("Select");

		this.selectAll = new MenuItem("All");
		this.selectAll.setOnAction(this);
		this.selectAll.setAccelerator(
				new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN));

		this.selectNone = new MenuItem("None");
		this.selectNone.setOnAction(this);
		this.selectNone.setAccelerator(new KeyCodeCombination(KeyCode.A,
				KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN));

		this.selectInvert = new MenuItem("Invert");
		this.selectInvert.setOnAction(this);
		this.selectInvert.setAccelerator(
				new KeyCodeCombination(KeyCode.I, KeyCombination.CONTROL_DOWN));

		this.selectFullTimeOnly = new MenuItem("Full Time Only");
		this.selectFullTimeOnly.setOnAction(this);
		this.selectFullTimeOnly.setAccelerator(
				new KeyCodeCombination(KeyCode.F, KeyCombination.ALT_DOWN));

		this.selectStudentWorkerApproved = new MenuItem("Student Workers Permitted");
		this.selectStudentWorkerApproved.setOnAction(this);
		this.selectStudentWorkerApproved.setAccelerator(new KeyCodeCombination(
				KeyCode.F, KeyCombination.SHIFT_DOWN, KeyCombination.ALT_DOWN));

		this.selectSecure = new MenuItem("Secure");
		this.selectSecure.setOnAction(this);
		this.selectSecure.setAccelerator(
				new KeyCodeCombination(KeyCode.S, KeyCombination.ALT_DOWN));

		this.selectNonSecure = new MenuItem("Non Secure");
		this.selectNonSecure.setOnAction(this);
		this.selectNonSecure.setAccelerator(new KeyCodeCombination(KeyCode.S,
				KeyCombination.ALT_DOWN, KeyCombination.SHIFT_DOWN));

		this.selectOffices = new MenuItem("Offices");
		this.selectOffices.setOnAction(this);
		this.selectOffices.setAccelerator(
				new KeyCodeCombination(KeyCode.O, KeyCombination.ALT_DOWN));

		this.selectNonOffices = new MenuItem("Non Offices");
		this.selectNonOffices.setOnAction(this);
		this.selectNonOffices.setAccelerator(new KeyCodeCombination(KeyCode.O,
				KeyCombination.ALT_DOWN, KeyCombination.SHIFT_DOWN));

		this.selectMenu.getItems().add(this.selectAll);
		this.selectMenu.getItems().add(this.selectNone);
		this.selectMenu.getItems().add(this.selectInvert);
		this.selectMenu.getItems().add(new SeparatorMenuItem());
		this.selectMenu.getItems().add(this.selectFullTimeOnly);
		this.selectMenu.getItems().add(this.selectStudentWorkerApproved);
		this.selectMenu.getItems().add(new SeparatorMenuItem());
		this.selectMenu.getItems().add(this.selectSecure);
		this.selectMenu.getItems().add(this.selectNonSecure);
		this.selectMenu.getItems().add(new SeparatorMenuItem());
		this.selectMenu.getItems().add(this.selectOffices);
		this.selectMenu.getItems().add(this.selectNonOffices);
		this.getMenus().add(selectMenu);

		this.selectMenu.getItems().add(new SeparatorMenuItem());

		/** Sub select menu for specific days */
		selectNotScheduled = new MenuItem("Not Scheduled");
		selectNotScheduled.setOnAction(this);
		selectMenu.getItems().add(this.selectNotScheduled);
		this.selectNotScheduled.setAccelerator(new KeyCodeCombination(KeyCode.F1));

		selectEveryday = new MenuItem("Everyday");
		selectEveryday.setOnAction(this);
		selectMenu.getItems().add(selectEveryday);
		selectEveryday.setAccelerator(new KeyCodeCombination(KeyCode.F2));

		selectWeekdays = new MenuItem("Weekdays");
		selectWeekdays.setOnAction(this);
		this.selectMenu.getItems().add(selectWeekdays);
		this.selectWeekdays.setAccelerator(new KeyCodeCombination(KeyCode.F3));

		selectWeekends = new MenuItem("Weekends");
		selectWeekends.setOnAction(this);
		selectMenu.getItems().add(this.selectWeekends);
		selectWeekends.setAccelerator(new KeyCodeCombination(KeyCode.F4));

		selectMWF = new MenuItem("Mondays, Wednesdays, and Fridays");
		selectMWF.setOnAction(this);
		selectMenu.getItems().add(selectMWF);
		selectMWF.setAccelerator(new KeyCodeCombination(KeyCode.F5));

		selectTR = new MenuItem("Tuesdays and Thursdays");
		selectTR.setOnAction(this);
		selectMenu.getItems().add(selectTR);
		selectTR.setAccelerator(new KeyCodeCombination(KeyCode.F6));

		selectTRF = new MenuItem("Tuesdays, Thursdays, and Fridays");
		selectTRF.setOnAction(this);
		selectMenu.getItems().add(selectTRF);
		selectTRF.setAccelerator(new KeyCodeCombination(KeyCode.F7));

		selectMenu.getItems().add(new SeparatorMenuItem());

		selectSunday = new MenuItem("Sunday");
		selectSunday.setOnAction(this);
		selectMenu.getItems().add(this.selectSunday);
		selectSunday.setAccelerator(new KeyCodeCombination(KeyCode.S));

		selectMonday = new MenuItem("Monday");
		selectMonday.setOnAction(this);
		selectMenu.getItems().add(this.selectMonday);
		selectMonday.setAccelerator(new KeyCodeCombination(KeyCode.M));

		selectTuesday = new MenuItem("Tuesday");
		selectTuesday.setOnAction(this);
		selectMenu.getItems().add(this.selectTuesday);
		selectTuesday.setAccelerator(new KeyCodeCombination(KeyCode.T));

		selectWednesday = new MenuItem("Wednesday");
		selectWednesday.setOnAction(this);
		selectMenu.getItems().add(this.selectWednesday);
		selectWednesday.setAccelerator(new KeyCodeCombination(KeyCode.W));

		selectThursday = new MenuItem("Thursday");
		selectThursday.setOnAction(this);
		selectMenu.getItems().add(this.selectThursday);
		selectThursday.setAccelerator(new KeyCodeCombination(KeyCode.R));

		selectFriday = new MenuItem("Friday");
		selectFriday.setOnAction(this);
		selectMenu.getItems().add(this.selectFriday);
		selectFriday.setAccelerator(new KeyCodeCombination(KeyCode.F));

		selectSaturday = new MenuItem("Saturday");
		selectSaturday.setOnAction(this);
		selectMenu.getItems().add(this.selectSaturday);
		selectSaturday.setAccelerator(new KeyCodeCombination(KeyCode.A));
	}

	private void initFileMenu()
	{
		this.fileMenu = new Menu("File");
		this.newItem = new MenuItem("New...");
		this.newItem.setOnAction(this);
		this.newItem.setAccelerator(
				new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));

		this.newFromSelectedItem = new MenuItem("New From Selected");
		this.newFromSelectedItem.setOnAction(this);
		this.newFromSelectedItem.setAccelerator(new KeyCodeCombination(KeyCode.N,
				KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN));

		this.openItem = new MenuItem("Open..");
		this.openItem.setOnAction(this);
		this.openItem.setAccelerator(
				new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));

		this.saveItem = new MenuItem("Save");
		this.saveItem.setOnAction(this);
		this.saveItem.setAccelerator(
				new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));

		this.saveAsItem = new MenuItem("Save As...");
		this.saveAsItem.setOnAction(this);
		this.saveAsItem.setAccelerator(new KeyCodeCombination(KeyCode.S,
				KeyCombination.SHIFT_DOWN, KeyCombination.CONTROL_DOWN));

		// this.printItem = new MenuItem("Print");
		// this.printItem.setOnAction(this);

		this.exportHTMLItem = new MenuItem("Export To HTML File");
		this.exportHTMLItem.setOnAction(this);

		this.exportTxtItem = new MenuItem("Export to Text File");
		this.exportTxtItem.setOnAction(this);
		this.exportTxtItem.setAccelerator(
				new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN));

		this.exportShortTXTItem = new MenuItem("Export to Abbrevated Text File");
		this.exportShortTXTItem.setOnAction(this);
		this.exportShortTXTItem.setAccelerator(new KeyCodeCombination(KeyCode.E,
				KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN));

		this.exit = new MenuItem("Exit");
		this.exit.setOnAction(this);
		this.exit.setAccelerator(
				new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN));

		this.exitNoSave = new MenuItem("Exit Without Saving");
		this.exitNoSave.setOnAction(this);
		this.exitNoSave.setAccelerator(new KeyCodeCombination(KeyCode.X,
				KeyCombination.SHIFT_DOWN, KeyCombination.CONTROL_DOWN));

		this.sortItem = new MenuItem("Sort List");
		this.sortItem.setOnAction(this);
		this.sortItem.setAccelerator(
				new KeyCodeCombination(KeyCode.S, KeyCombination.SHIFT_DOWN));

		this.fileMenu.getItems().add(newItem);
		this.fileMenu.getItems().add(newFromSelectedItem);
		this.fileMenu.getItems().add(openItem);
		this.fileMenu.getItems().add(new SeparatorMenuItem());
		this.fileMenu.getItems().add(saveItem);
		this.fileMenu.getItems().add(saveAsItem);
		this.fileMenu.getItems().add(new SeparatorMenuItem());
		this.fileMenu.getItems().add(sortItem);
		this.fileMenu.getItems().add(new SeparatorMenuItem());
		this.fileMenu.getItems().add(exportHTMLItem);
		this.fileMenu.getItems().add(exportTxtItem);
		this.fileMenu.getItems().add(exportShortTXTItem);
		this.fileMenu.getItems().add(new SeparatorMenuItem());
		this.fileMenu.getItems().add(exit);
		this.fileMenu.getItems().add(exitNoSave);

		this.getMenus().add(fileMenu);
	}

	@Override
	public void handle(ActionEvent event)
	{
		/* File Menu items */
		if (event.getSource().equals(newItem))
		{
			newItemClicked();
		}

		if (event.getSource().equals(newFromSelectedItem))
		{
			newFromSelectedClicked();
		}

		if (event.getSource().equals(saveItem))
		{
			saveClicked();
		}

		if (event.getSource().equals(this.openItem))
		{
			openClicked();

		}

		if (event.getSource().equals(this.saveAsItem))
		{
			saveAsClicked();
		}

		if (event.getSource().equals(this.sortItem))
		{
			model.sort();
		}

		if (event.getSource().equals(this.exportTxtItem))
		{
			exportTXTClicked();
		}

		if (event.getSource().equals(this.exportShortTXTItem))
		{
			exportShortTXTClicked();
		}

		if (event.getSource().equals(this.exportHTMLItem))
		{
			exportHTMLClicked();
		}

		if (event.getSource().equals(exit))
		{
			exitClicked();
		}

		if (event.getSource().equals(exitNoSave))
		{
			stage.close();
		}

		/* Select menu Items */
		if (event.getSource().equals(this.selectNone))
		{
			taskView.selectCheckBoxes(TaskViewPane.NONE);
		}

		if (event.getSource().equals(this.selectAll))
		{
			this.taskView.selectCheckBoxes(TaskViewPane.ALL);
		}

		if (event.getSource().equals(this.selectInvert))
		{
			taskView.selectCheckBoxes(TaskViewPane.INVERT);
		}

		if (event.getSource().equals(this.selectFullTimeOnly))
		{
			taskView.selectCheckBoxes(TaskViewPane.FULL_TIME);
		}

		if (event.getSource().equals(this.selectStudentWorkerApproved))
		{
			taskView.selectCheckBoxes(TaskViewPane.STUDENT_WORKERS);
		}

		if (event.getSource().equals(this.selectSecure))
		{
			taskView.selectCheckBoxes(TaskViewPane.SECURE);
		}

		if (event.getSource().equals(this.selectNonSecure))
		{
			taskView.selectCheckBoxes(TaskViewPane.NON_SECURE);
		}

		if (event.getSource().equals(this.selectOffices))
		{
			taskView.selectCheckBoxes(TaskViewPane.OFFICES);
		}

		if (event.getSource().equals(this.selectNonOffices))
		{
			taskView.selectCheckBoxes(TaskViewPane.NON_OFFICES);
		}

		if (event.getSource().equals(this.selectMonday))
		{
			taskView.selectCheckBoxesByDay(Day.MONDAY);
		}
		if (event.getSource().equals(this.selectTuesday))
		{
			taskView.selectCheckBoxesByDay(Day.TUESDAY);
		}
		if (event.getSource().equals(this.selectWednesday))
		{
			taskView.selectCheckBoxesByDay(Day.WEDNESDAY);
		}
		if (event.getSource().equals(this.selectThursday))
		{
			taskView.selectCheckBoxesByDay(Day.THURSDAY);
		}
		if (event.getSource().equals(this.selectFriday))
		{
			taskView.selectCheckBoxesByDay(Day.FRIDAY);
		}
		if (event.getSource().equals(this.selectSaturday))
		{
			taskView.selectCheckBoxesByDay(Day.SATURDAY);
		}
		if (event.getSource().equals(this.selectSunday))
		{
			taskView.selectCheckBoxesByDay(Day.SUNDAY);
		}
		if (event.getSource().equals(this.selectNotScheduled))
		{
			taskView.selectCheckBoxesByDay(Day.NOT_SCHEDULED);
		}
		if (event.getSource().equals(this.selectEveryday))
		{
			taskView.selectCheckBoxesByDay(Day.EVERYDAY);
		}
		if (event.getSource().equals(this.selectWeekdays))
		{
			taskView.selectCheckBoxesByDay(Day.WEEKDAYS);
		}
		if (event.getSource().equals(this.selectWeekends))
		{
			taskView.selectCheckBoxesByDay(Day.WEEKENDS);
		}
		if (event.getSource().equals(this.selectMWF))
		{
			taskView.selectCheckBoxesByDay(Day.MWF);
		}
		if (event.getSource().equals(this.selectTR))
		{
			taskView.selectCheckBoxesByDay(Day.TR);
		}
		if (event.getSource().equals(this.selectTRF))
		{
			taskView.selectCheckBoxesByDay(Day.TRF);
		}

		/* Help Menu items */
		if (event.getSource().equals(this.about))
		{
			aboutClicked();
		}

		if (event.getSource().equals(helpCompanion))
		{
			helpCompanionClicked();
		}

	}

	private void exportHTMLClicked()
	{
		FileChooser fc = new FileChooser();
		fc.setTitle("Select Export Location for HTML File");
		FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter(
				"Hyper Text Markup Language", "*.html");
		fc.getExtensionFilters().add(filter);
		File txtPath = fc.showSaveDialog(stage);
		model.exportHTML(txtPath.toString());
	}

	private void newFromSelectedClicked()
	{
		ArrayList<Item> selected = this.taskView.getSelectedItems();
		this.modelSavePath = null;
		this.model.clearList();
		for (int i = 0; i < selected.size(); i++)
		{
			this.model.addTask(selected.get(i));
		}
		this.stage.setTitle("Unnamed List");
	}

	private void helpCompanionClicked()
	{
		new HelpCompanionWindow(HelpContent.getMasterHelp());
	}

	private void aboutClicked()
	{

	}

	private void newItemClicked()
	{
		if (this.model.hasChanged())
		{
			saveClicked();
		}
		this.modelSavePath = null;
		this.model.clearList();
		this.stage.setTitle("Unnamed List");
	}

	private void exitClicked()
	{
		if (this.isModelChangedSinceLastSave)
		{
			this.saveClicked();
			stage.close();
		}
		else
		{
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Exit without Saving");
			alert.setHeaderText("Are you sure you want to exit without saving?");
			alert.setContentText(
					"You work will be lost if the file is not saved as a list manager file *.list");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK)
			{
				this.saveAsClicked();
			}
			else
			{
				stage.close();
			}
		}
	}

	/**
	 * Method to handle exporting the current list to a txt file.
	 */
	private void exportTXTClicked()
	{
		FileChooser fc = new FileChooser();
		fc.setTitle("Select Export Location for Text File");
		FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter(
				"Text Files (*.txt)", "*.txt");
		fc.getExtensionFilters().add(filter);
		File txtPath = fc.showSaveDialog(stage);
		model.saveListTextToFile(txtPath.toString());
	}

	private void exportShortTXTClicked()
	{
		FileChooser fc = new FileChooser();
		fc.setTitle("Select Export Location for Short Text File");
		FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter(
				"Text Files", "*.txt");
		fc.getExtensionFilters().add(filter);
		File txtPath = fc.showSaveDialog(stage);
		model.saveListShortTextFile(txtPath.toString());
	}

	/**
	 * Method to handle when the save as menu item is clicked. This allows the
	 * user to save the file as a new .list file.
	 */
	private void saveAsClicked()
	{
		FileChooser fc = new FileChooser();
		fc.setTitle("Select Save Location");
		FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter(
				"List Manager Files (*.list)", "*.list");
		fc.getExtensionFilters().add(filter);
		modelSavePath = fc.showSaveDialog(stage);
		model.save(modelSavePath.toString());
		model.setName(modelSavePath.getName());
		this.stage.setTitle(modelSavePath.toString());
	}

	/**
	 * This method handles when save is selected. If save is selected and the
	 * file does not have a .list file associated with it, the choose save file
	 * chooser will pop up. If the file has been saved already, the model.save
	 * method will be used instead.
	 */
	private void saveClicked()
	{
		if (this.modelSavePath != null)
		{
			model.save(modelSavePath.toString());
		}
		else
		{
			FileChooser fc = new FileChooser();
			fc.setTitle("Select Save Location");
			FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter(
					"List Manager Files (*.list)", "*.list");
			fc.getExtensionFilters().add(filter);
			modelSavePath = fc.showSaveDialog(stage);
			model.save(modelSavePath.toString());
			model.setName(modelSavePath.getName());
			this.stage.setTitle(modelSavePath.toString());
			this.isModelChangedSinceLastSave = false;
		}
	}

	/**
	 * Handles when open is clicked. A file chooser opens up with an extension
	 * filter for .list files, which are list manager files.
	 */
	private void openClicked()
	{
		FileChooser fc = new FileChooser();
		fc.setTitle("Choose Resource File *.list");
		FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter(
				"List Manager Files (*.list)", "*.list");
		fc.getExtensionFilters().add(filter);
		File selectedFile = fc.showOpenDialog(stage);
		if (selectedFile != null)
		{
			try
			{
				this.model.open(selectedFile.toString());
				this.modelSavePath = selectedFile;
				this.model.setName(modelSavePath.getName());
				this.stage.setTitle(modelSavePath.toString());
			}
			catch (ClassNotFoundException e)
			{
				Alert a = new Alert(AlertType.ERROR);
				a.setTitle("Invalid File has been Selected");
				a.setHeaderText(
						selectedFile.toString() + " Is not a valid .list file");
				a.setContentText(e.getMessage());
				a.showAndWait();
			}
		}
	}

	@Override
	public void update(Observable arg0, Object arg1)
	{
		this.isModelChangedSinceLastSave = true;
	}

}
