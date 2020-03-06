package application;

import java.util.Observable;
import java.util.Observer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

/**
 * ListDisplayPane creates a display for the current model as a string. This
 * display is updated when the model changes as the observer pattern is
 * Implemented.
 * 
 * @author Douglas Rudau
 * @version 02-24-2020
 *
 */
public class ListDisplayPane extends BorderPane
		implements EventHandler<ActionEvent>, Observer
{
	/** The task list model that is being represented */
	private TaskList model;

	/** text area where the list is displayed */
	private TextArea listTextArea;

	/**
	 * Constructor for the List Display Pane
	 * 
	 * @param model
	 */
	public ListDisplayPane(TaskList model)
	{
		this.model = model;
		this.model.addObserver(this);

		this.listTextArea = new TextArea();
		Font consolas = Font.font("Console", FontWeight.NORMAL, FontPosture.REGULAR,
				12);
		this.listTextArea.setEditable(false);
		this.listTextArea.setFont(consolas);
		this.listTextArea.autosize();
		this.listTextArea.prefWidth(getLayoutX());
		this.setCenter(listTextArea);
		updateTextArea();
	}

	/**
	 * Handle method for mouse events in the task list editor
	 * 
	 * @arg0 the Action Event to be handled
	 */
	@Override
	public void handle(ActionEvent arg0)
	{

	}

	/**
	 * Update method for the observer pattern
	 */
	@Override
	public void update(Observable arg0, Object arg1)
	{
		updateTextArea();
	}

	/**
	 * Method to update the displayed task list when the model changes.
	 */
	private void updateTextArea()
	{
		this.listTextArea.setText(model.toString());
	}

}
