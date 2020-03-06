package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class Main extends Application
{
	@Override
	public void start(Stage primaryStage)
	{
		try
		{

			BorderPane root = new BorderPane();

			TaskList master = new TaskList();
//			OfficeItem lorton = new OfficeItem("Lorton Data", "ANC 310", 8, true,
//					false, 50, 10000, true, 2);
//			OfficeItem alerusCC = new OfficeItem("Alerus Call Center", "ANC 350",
//					Day.WEEKDAYS, 10, 800, false, 0);
//			OfficeItem alerus = new OfficeItem("Alerus", "ANC 450", Day.WEEKDAYS,
//					true, true, 200, 100000, true, 4);
//			OfficeItem country = new OfficeItem("Closed Country", "ANC 400",
//					Day.NOT_SCHEDULED, 0, 100000, false, 4);
//			OfficeItem gradAthletics = new OfficeItem("Graduate Atheltics", "ANC306",
//					Day.MWF, 4, 600, false, 0);
//			OfficeItem metro = new OfficeItem("Metro Suite", "ANC 110", Day.WEEKDAYS,
//					50, 10000, true, 2);
//
//			Item metroTrainingRoom = new Item("Metro Training Room", "ANC 120",
//					Day.WEEKDAYS, .5);
//			Item communityRoom = new Item("Community Room", "ANC 100", Day.WEEKDAYS,
//					.75);
//			Item starbucks = new Item("Starbucks Lounge", "ANC 304E", Day.WEEKDAYS,
//					.3);
//			Item grandStairs = new Item("Vacuum Grand Stairs", "ANC Grand Stairs",
//					Day.MWF, 1.0);
//
//			master.addTask(lorton);
//			master.addTask(alerusCC);
//			master.addTask(alerus);
//			master.addTask(country);
//			master.addTask(grandStairs);
//			master.addTask(gradAthletics);
//			master.addTask(metroTrainingRoom);
//			master.addTask(metro);
//			master.addTask(communityRoom);
//			master.addTask(starbucks);
//			
//
//			master.save("C:\\users\\Douglas\\Desktop\\master.list");

//			try
//			{
//				master.open("C:\\users\\Douglas\\Desktop\\master.list");
//			}
//			catch (ClassNotFoundException e)
//			{
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
			TaskViewPane tp = new TaskViewPane(master);
			ListMenuBar menus = new ListMenuBar(master, tp, primaryStage);
			ListDisplayPane listDisplay = new ListDisplayPane(master);
			root.setTop(menus);
			root.setLeft(tp);
			root.setCenter(listDisplay);
			Scene scene = new Scene(root, 800, 600);
			scene.getStylesheets()
					.add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}
