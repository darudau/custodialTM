package application;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

/**
 * Popup window that allows for a new office item to be added or an existing
 * office item to be edited. This option is supported with the two constructors
 * for the system
 * 
 * @author Douglas Rudau
 * @version 02-17-2020
 *
 */
public class AddOfficeWindow extends BorderPane implements EventHandler<ActionEvent>
{
	/** New Item being created */
	private OfficeItem newItem;

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

	/** Square footage text area */
	private TextField squareFootageTextField;

	/** Worker count text area */
	private TextField workerCountTextField;

	/** checkbox for if the office has a breakroom */
	private CheckBox isBreakroomCheckBox;

	/** Vbox that contians the options for this item */
	private VBox options;

	/** Stage from where this AdddOfficeWindow was constructed from */
	private Stage stage;

	/** Scene from the window that called for this window */
	private Scene scene;

	/** Ok button */
	private Button ok;

	/** Cancel button */
	private Button cancel;

	/** Help Button */
	private Button help;

	/**
	 * Edit mode flag, if true this task is being edited, if false a new office
	 * item is being created
	 */
	private boolean isEditMode;

	/** The previous item that has been loaded */
	private OfficeItem oldItem;

	/**
	 * This constructor is for creating a new Office Item object in the task
	 * list model
	 * 
	 * @param model
	 *            pointer to the task list model
	 */
	public AddOfficeWindow(TaskList model)
	{
		this.model = model;
		this.oldItem = null;
		this.isEditMode = false;
		newItem = new OfficeItem(); // initialize with default values will be
									// changed
		// as events happen
		options = new VBox();
		initNameTextField();
		initLocationTextField();
		initScheduleComboBox();
		initCheckBoxes();
		initWorkerCountTextField();
		initSquareFootageTextField();
		initBreakroomCheckBox();
		// initExcpectedTimeToCompleteTextField();
		initButtons();
		options.setSpacing(2.5);
		options.setAlignment(Pos.CENTER);

		this.setCenter(options);

		stage = new Stage();
		scene = new Scene(this, 470, 250);
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
	 * This constructor is for editing an existing item
	 * 
	 * @param model
	 *            the task list model where the item is located
	 * @param editItem
	 *            the item to be edited
	 */
	public AddOfficeWindow(TaskList model, OfficeItem editItem)
	{
		this.isEditMode = true;
		this.model = model;
		newItem = editItem;
		this.oldItem = editItem;
		options = new VBox();
		initNameTextField();
		initLocationTextField();
		initScheduleComboBox();
		initCheckBoxes();
		initWorkerCountTextField();
		initSquareFootageTextField();
		initBreakroomCheckBox();
		initButtons();
		options.setSpacing(2.5);
		options.setAlignment(Pos.CENTER);

		this.setCenter(options);

		stage = new Stage();
		scene = new Scene(this, 470, 250);
		stage.setResizable(false);
		this.stage.setTitle("Editing Item: " + newItem.getName());
		scene.getStylesheets()
				.add(getClass().getResource("application.css").toExternalForm());
		scene.getStylesheets()
				.add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Method to intialize the breakroom check box
	 */
	private void initBreakroomCheckBox()
	{
		String breakroom = "Breakroom in Office";
		HBox hbox = new HBox();
		this.isBreakroomCheckBox = new CheckBox(breakroom);
		this.isBreakroomCheckBox.setTooltip(
				new Tooltip("Check this box if there is a breakroom in the office"));
		this.isBreakroomCheckBox.setSelected(newItem.hasBreakroom());
		hbox.getChildren().add(this.isBreakroomCheckBox);
		options.getChildren().add(hbox);
	}

	/**
	 * Method to initialize the square footage text field
	 */
	private void initSquareFootageTextField()
	{
		squareFootageTextField = new TextField();
		squareFootageTextField.setText(newItem.getSquareFeet() + "");
		squareFootageTextField.setTooltip(new Tooltip(
				"Enter the square footage of the office, this number is used to calculate time"));
		squareFootageTextField.setPrefSize(300, USE_COMPUTED_SIZE);
		Label l = new Label("Square Footage: ");
		Label ft = new Label("Square Feet");
		HBox hbox = new HBox();
		hbox.getChildren().add(l);
		hbox.getChildren().add(squareFootageTextField);
		hbox.getChildren().add(ft);
		options.getChildren().add(hbox);
	}

	/**
	 * Method to initialize the worker count text field
	 */
	private void initWorkerCountTextField()
	{
		workerCountTextField = new TextField();
		workerCountTextField.setText(newItem.getWorkerCount() + "");
		workerCountTextField.setTooltip(new Tooltip(
				"Enter the number of workers in the office, this number is used to calculate time"));
		workerCountTextField.setPrefSize(300, USE_COMPUTED_SIZE);
		Label l = new Label("Worker Count: ");
		Label ft = new Label("Workers");
		HBox hbox = new HBox();
		hbox.getChildren().add(l);
		hbox.getChildren().add(workerCountTextField);
		hbox.getChildren().add(ft);
		options.getChildren().add(hbox);
	}

	/**
	 * Method to initialize the buttons
	 *
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
	 * method to initialize the name text field
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
	 * Method to initialize the location text field
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
	 * Method to intialize the schedule selection combo box
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
	 * Method to initialze the security clearance check boxes
	 */
	private void initCheckBoxes()
	{
		String secure = "Task is Secure";
		HBox hbox = new HBox();
		this.secureTaskCheckBox = new CheckBox(secure);
		this.secureTaskCheckBox.setTooltip(new Tooltip(
				"Check this box if the item is secure, such as Lorton Data"));
		this.secureTaskCheckBox.setSelected(newItem.isSecure());
		hbox.getChildren().add(this.secureTaskCheckBox);

		String studentWorkerAllowed = "Student Workers Permitted";
		this.studentWorkersAllowedCheckBox = new CheckBox(studentWorkerAllowed);
		this.studentWorkersAllowedCheckBox.setTooltip(new Tooltip(
				"Chheck this box if student workers are allowed to do this task"));
		this.studentWorkersAllowedCheckBox
				.setSelected(newItem.isStudentWorkersPermitted());
		hbox.getChildren().add(studentWorkersAllowedCheckBox);
		hbox.setSpacing(150);
		options.getChildren().add(hbox);

	}

	/**
	 * Handler method for action events in this window
	 *
	 * @param event
	 *            the action event that triggered the call to handle
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
	 * Method to handle when the ok button is clicked. When this button is
	 * clicked, the fields and their imputs are validated using the model if the
	 * fields all contain valid input data, the new office item is created, or
	 * the changes that have been requrested via the edit mode are done to the
	 * office item within the task list model.
	 *
	 * If the items within the fields are invalid, error messages or warnings
	 * are displayed based on the severity of the error
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
			AlertType type = Alert.AlertType.CONFIRMATION;
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
		newItem.setHasBreakroom(this.isBreakroomCheckBox.isSelected());

		try
		{
			newItem.setSquareFeet(
					Integer.parseInt(this.squareFootageTextField.getText()));
		}
		catch (Exception e)
		{
			AlertType type = Alert.AlertType.ERROR;
			String title = "Square Footage Error";
			String header = "An invalid square footage has been enetered for the Office";
			String content = e.getMessage();
			displayAlert(type, title, header, content);
			return;
		}

		try
		{
			int count = Integer.parseInt(this.workerCountTextField.getText());

			newItem.setWorkerCount(count);
		}
		catch (Exception e)
		{
			AlertType type = Alert.AlertType.ERROR;
			String title = "Worker Count Error";
			String header = "An invalid worker count has been enetered for the Office";
			String content = e.getMessage();
			displayAlert(type, title, header, content);
			e.printStackTrace();
			return;
		}

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
				// System.out.println(oldItem.equals(newItem));
				// System.out.println("OLD\n" + oldItem.toString());
				// System.out.println("New\n" + newItem.toString());
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
	 * Method to display an alert using the JavaFX alert class.
	 *
	 * @param type
	 *            the type of alert to be displayed
	 * @param title
	 *            the title of the Alert window
	 * @param header
	 *            the header text for the alert
	 * @param content
	 *            the content string for the alert, this could be a stack trace
	 *            or similar content
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
		if(isEditMode == false){
		new HelpCompanionWindow(HelpContent.ADD_OFFICE);
		}else{
			new HelpCompanionWindow(HelpContent.EDIT_ITEM);
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
