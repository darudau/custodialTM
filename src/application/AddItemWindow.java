package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * Popup window to add an item to the user current task list. This class is not
 * intended for adding office items since there are additional parameters for an
 * office item.
 * 
 * @author Douglas Rudau
 * @version 02-17-2020
 *
 */
public class AddItemWindow extends BorderPane implements EventHandler<ActionEvent>
{
	/** New Item being created */
	private Item newItem;

	private TaskList model;

	/** Name of New Item */
	private TextField nameTextField;

	/** Location of New Item */
	private TextField locationTextField;

	/** Expected Time To Complete Task */
	private TextField expectedTimeToCompleteTextField;

	/** Scheduled Days of the Week */
	private ComboBox<String> scheduleComboBox;

	/** Are student workers permitted Level */
	private CheckBox studentWorkersAllowedCheckBox;

	/** Determines if task is secure */
	private CheckBox secureTaskCheckBox;

	/** VBox that contians the options */
	private VBox options;

	/** Stage from the window that called this pop up window */
	private Stage stage;

	/** Scene from the window that called this popup window */
	private Scene scene;

	/** Ok button */
	private Button ok;

	/** Cancel button */
	private Button cancel;

	/** Help Button */
	private Button help;

	/**
	 * Flag for if the window is an edit window or a new item window. If this
	 * flag is true, the item is being edited, if this flag is false, the item
	 * is a new item
	 */
	private boolean isEditMode;

	private Item oldItem;

	/**
	 * Constructor for the AddItem window. This constructor is called when a
	 * window for a new item is to be opened This method only requires the model
	 * whtere the new item is to be added.
	 * 
	 * @param model
	 *            the task list model where the new item is to be added
	 */
	public AddItemWindow(TaskList model)
	{
		this.isEditMode = false;

		this.model = model;
		oldItem = null;
		newItem = new Item(); // initialize with default values will be changed
								// as events happen
		options = new VBox();
		initNameTextField();
		initLocationTextField();
		initExcpectedTimeToCompleteTextField();
		initScheduleComboBox();
		initCheckBoxes();
		initButtons();
		options.setAlignment(Pos.CENTER);

		this.setCenter(options);

		stage = new Stage();
		scene = new Scene(this, 470, 150);
		stage.setResizable(false);
		this.stage.setTitle("Add New Item To Task List");
		scene.getStylesheets()
				.add(getClass().getResource("application.css").toExternalForm());
		scene.getStylesheets()
				.add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Constructor for editing items
	 * 
	 * @param taskListModel
	 *            the model with the item to be edited
	 * @param editItem
	 *            the item to be editted
	 */
	public AddItemWindow(TaskList taskListModel, Item editItem)
	{
		this.isEditMode = true;
		this.model = taskListModel;
		newItem = editItem; // initialize with default values will be changed
							// as events happen
		oldItem = editItem;
		options = new VBox();
		initNameTextField();
		initLocationTextField();
		initExcpectedTimeToCompleteTextField();
		initScheduleComboBox();
		initCheckBoxes();
		initButtons();
		options.setAlignment(Pos.CENTER);

		this.setCenter(options);

		stage = new Stage();
		scene = new Scene(this, 470, 150);
		stage.setResizable(false);
		this.stage.setTitle("Editting: " + editItem.getName());
		scene.getStylesheets()
				.add(getClass().getResource("application.css").toExternalForm());
		scene.getStylesheets()
				.add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Method to initialize the buttons in this pane
	 */
	private void initButtons()
	{
		HBox buttons = new HBox();

		ok = new Button("Ok");
		ok.setTooltip(new Tooltip(
				"Checks if Parameters are valid and adds the item to the task list if they are"));
		ok.setOnAction(this);
		ok.setText("O_k");
		ok.setMnemonicParsing(true);
		ok.setMinSize(150, USE_COMPUTED_SIZE);
		buttons.getChildren().add(ok);

		cancel = new Button("Cancel");
		cancel.setTooltip(new Tooltip("Exits without modifying the task list"));
		cancel.setOnAction(this);
		cancel.setText("_Cancel");
		cancel.setMnemonicParsing(true);
		cancel.setMinSize(150, USE_COMPUTED_SIZE);
		buttons.getChildren().add(cancel);

		help = new Button("Help");
		help.setTooltip(new Tooltip("Opens the help companion"));
		help.setOnAction(this);
		help.setMinSize(150, USE_COMPUTED_SIZE);
		help.setText("_Help");
		help.setMnemonicParsing(true);
		buttons.getChildren().add(help);
		buttons.setSpacing(10);
		buttons.setAlignment(Pos.CENTER);
		this.setBottom(buttons);
	}

	/**
	 * Method to initialize the Name text field
	 */
	private void initNameTextField()
	{
		nameTextField = new TextField();
		nameTextField.setText(newItem.getName());
		nameTextField.setTooltip(new Tooltip(
				"Use a descriptive name such as \"Vacuum Grand Stairs\""));
		nameTextField.setPrefSize(400, USE_COMPUTED_SIZE);
		Label l = new Label("Item name: ");
		HBox hbox = new HBox();
		hbox.getChildren().add(l);
		hbox.getChildren().add(nameTextField);
		options.getChildren().add(hbox);
	}

	/**
	 * method to initialize the location text field
	 */
	private void initLocationTextField()
	{
		locationTextField = new TextField();
		locationTextField.setText(newItem.getLocation());
		locationTextField.setTooltip(new Tooltip(
				"Use the Building Name and Room Number, ex \"ANC 205\""));
		locationTextField.setPrefSize(385, USE_COMPUTED_SIZE);
		Label l = new Label("Item Location: ");
		HBox hbox = new HBox();
		hbox.getChildren().add(l);
		hbox.getChildren().add(locationTextField);
		options.getChildren().add(hbox);
	}

	/**
	 * Method to initialize the expected time to complete text field
	 */
	private void initExcpectedTimeToCompleteTextField()
	{
		expectedTimeToCompleteTextField = new TextField();
		expectedTimeToCompleteTextField.setText(newItem.getExpectedTime() + "");
		expectedTimeToCompleteTextField.setTooltip(new Tooltip(
				"Enter the average time to complete this task in hours"));
		Label l = new Label("Expected Time to Complete Item: ");
		Label hours = new Label("Hours");
		HBox hbox = new HBox();
		hbox.getChildren().add(l);
		hbox.getChildren().add(expectedTimeToCompleteTextField);
		hbox.getChildren().add(hours);
		options.getChildren().add(hbox);
	}

	/**
	 * method to initialize the combo box for the schedule days when a new
	 * window is opened.
	 */
	private void initScheduleComboBox()
	{
		Label l = new Label("Scheduled Days for Task");
		HBox hbox = new HBox();
		hbox.getChildren().add(l);

		this.scheduleComboBox = new ComboBox<String>();
		// this.scheduleComboBox.getItems().add("Not Scheduled");
		for (int i = 0; i < Day.dayNames.length; i++)
		{
			this.scheduleComboBox.getItems().add(Day.dayNames[i]);
		}
		this.scheduleComboBox
				.setValue(Day.getNameFromCode(newItem.getScheduleDaysInt()));
		hbox.getChildren().add(this.scheduleComboBox);
		options.getChildren().add(hbox);
	}

	/**
	 * Method to initialize the check boxes in the gui when a new window is
	 * opened.
	 */
	private void initCheckBoxes()
	{
		String secure = "Task is Secure";
		HBox hbox = new HBox();
		this.secureTaskCheckBox = new CheckBox(secure);
		this.secureTaskCheckBox.setSelected(newItem.isSecure());
		this.secureTaskCheckBox.setTooltip(new Tooltip(
				"Check this box if the item is secure, such as Lorton Data"));
		hbox.getChildren().add(this.secureTaskCheckBox);

		String studentWorkerAllowed = "Student Workers Permitted";
		this.studentWorkersAllowedCheckBox = new CheckBox(studentWorkerAllowed);
		this.studentWorkersAllowedCheckBox
				.setSelected(newItem.isStudentWorkersPermitted());
		this.studentWorkersAllowedCheckBox.setTooltip(new Tooltip(
				"Chheck this box if student workers are allowed to do this task"));
		hbox.getChildren().add(studentWorkersAllowedCheckBox);
		hbox.setSpacing(150);
		options.getChildren().add(hbox);

	}

	/**
	 * Handler method for action events in this window
	 * 
	 * @param event
	 *            the action event that occurred
	 */
	@Override
	public void handle(ActionEvent event)
	{
		if (event.getSource().equals(ok))
		{
			okButtonClicked();
		}

		if (event.getSource().equals(cancel))
		{
			stage.close();
		}

		if (event.getSource().equals(help))
		{
			helpButtonClicked();
		}
	}

	/**
	 * Method to handle when the ok button is clicked. When the okay button is
	 * clicked, the parameters set in the form are checked. If the parameters
	 * are all valid and the object is not already present in the model, the new
	 * item will be added to the model and the add item window will close.
	 */
	private void okButtonClicked()
	{
		// validate name
		if (this.nameTextField.getText().equals(Item.DEFAULT_NAME)
				|| this.nameTextField.getText().equals(""))
		{
			AlertType type = Alert.AlertType.ERROR;
			String title = "Name is a Required Field";
			String header = "Please enter a descriptive name for the item";
			String content = "";
			displayAlert(type, title, header, content);
			return;
		}
		else
		{
			newItem.setName(this.nameTextField.getText());
		}

		// validate location
		if (this.locationTextField.getText().equals(Item.DEFAULT_LOCATION))
		{
			AlertType type = Alert.AlertType.WARNING;
			String title = "Unspecified Location is Discouraged";
			String header = "An unspecified location is discouraged, but may be "
					+ "necessary in some cases";
			String content = "Unspecefied locations may be needed for "
					+ "adminstrative tasks, tasks with "
					+ "no definite location such as light bulb replacment";
			displayAlert(type, title, header, content);
		}
		else if (locationTextField.getText().equals(""))
		{
			AlertType type = AlertType.ERROR;
			String title = "Location is a Required Field";
			String header = "A location must be specified, if no location is needed, please use \""
					+ Item.DEFAULT_LOCATION + "\"";
			String content = "";
			displayAlert(type, title, header, content);
			return;
		}
		else
		{
			this.newItem.setLocation(this.locationTextField.getText());
		}

		try
		{
			newItem.setExpectedTime(Double
					.parseDouble(this.expectedTimeToCompleteTextField.getText()));
		}
		catch (Exception e)
		{
			AlertType type = Alert.AlertType.ERROR;
			String title = "Invalid time has been enetered";
			String header = e.getMessage();
			String content = "Valid times are positive decimal values, for example 1.25, or 0.5";
			displayAlert(type, title, header, content);
		}
		String schedule = this.scheduleComboBox.getValue();
		int index = 0;
		for (int i = 0; i < scheduleComboBox.getItems().size(); i++)
		{
			if (scheduleComboBox.getItems().get(i).equals(schedule))
			{
				index = i;
				break;
			}
		}
		newItem.setScheduleDaysInt(index);
		newItem.setStudentWorkersPermitted(
				this.studentWorkersAllowedCheckBox.isSelected());
		newItem.setSecure(this.secureTaskCheckBox.isSelected());

		if (this.model.isPresent(newItem) && isEditMode == false)
		{
			AlertType type = Alert.AlertType.ERROR;
			String title = "Item is already in Task List";
			String header = "The item is already present in the task list and will not be added";
			String content = "Click cancel on the add item window if you do not want to continue adding this item";
			displayAlert(type, title, header, content);
		}
		else
		{
			if (isEditMode)
			{
				model.remove(oldItem);
				model.addTask(newItem);
				stage.close();
			}
			else
			{
				model.addTask(newItem);
				stage.close();
			}
		}
	}

	/**
	 * Method to display Alerts when errors occur.
	 * 
	 * @param type
	 *            the AlertType
	 * @param title
	 *            the alert window title
	 * @param header
	 *            the alert header
	 * @param content
	 *            the alert's content, such as a an exception's message or a
	 *            stack trace
	 */
	private void displayAlert(AlertType type, String title, String header,
			String content)
	{
		Alert a = new Alert(type);
		a.setTitle(title);
		a.setHeaderText(header);
		a.setContentText(content);
		a.showAndWait();
	}

	/**
	 * When the help button is pressed a help companion window will open up
	 * describing how each of the parameters in the add item window will work.
	 */
	private void helpButtonClicked()
	{
		if (isEditMode == false)
		{
			new HelpCompanionWindow(HelpContent.ADD_ITEM);
		}
		else
		{
			new HelpCompanionWindow(HelpContent.EDIT_ITEM + "\n\n" + HelpContent.ADD_ITEM);

		}
	}

	/**
	 * Method used to get the new item after it is created so it can be added to
	 * the task list
	 * 
	 * @return the newly created item
	 */
	public Item getNewItem()
	{
		return this.newItem;
	}

}
