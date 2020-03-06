package application;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class HelpCompanionWindow extends BorderPane
		implements EventHandler<ActionEvent>
{
	private Label helpView;
	
	private String content;
	
	private double labelHeight;
	
	public HelpCompanionWindow(String content)
	{
		this.content = content;
		this.labelHeight = 500; 
		initHelpView();
		ScrollPane sp = new ScrollPane();
		sp.setContent(helpView);
		sp.setMaxWidth(370);
		sp.setMaxHeight(labelHeight - 5);
		this.setCenter(sp);
		displayHelpCompanion();
	}

	public void displayHelpCompanion()
	{
		Stage primaryStage = new Stage();
		primaryStage.setTitle("List Manager Help Companion");
		Scene scene = new Scene(this, 400, labelHeight);
		scene.getStylesheets()
				.add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void initHelpView()
	{
		this.helpView = new Label();
		this.helpView.setMaxWidth(350);
		this.helpView.setText(content);
		this.helpView.setWrapText(true);
	}

	@Override
	public void handle(ActionEvent arg0)
	{
		// TODO Auto-generated method stub

	}

}
