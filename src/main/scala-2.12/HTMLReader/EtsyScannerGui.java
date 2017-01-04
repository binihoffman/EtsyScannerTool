package HTMLReader;/**
 * Created by benh on 13/12/2016.
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EtsyScannerGui extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        String[] input = new String[3];
        primaryStage.setTitle("Etsy Search Scanner");

        Image applicationIcon = new Image(getClass().getResourceAsStream("logo.png"));
        primaryStage.getIcons().add(applicationIcon);


        final Text actiontarget = new Text();
        final Text actiontarget2 = new Text();
        actiontarget2.setWrappingWidth(200);

        GridPane grid = new GridPane();
        grid.add(actiontarget, 1, 6);
        grid.add(actiontarget2, 1,7);
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));


        Scene scene = new Scene(grid, 400, 275);
        primaryStage.setScene(scene);

        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label storeName = new Label("Store Name:");
        grid.add(storeName, 0, 1);

        TextField storeTextField = new TextField();
        grid.add(storeTextField, 1, 1);


        Label pw = new Label("Search key:");
        grid.add(pw, 0, 2);

        TextField searchKeyTextField = new TextField();
        grid.add(searchKeyTextField, 1, 2);

        Label nop = new Label("Number of pages:");
        grid.add(nop, 0, 3);

        TextField numOfPagesTextField = new TextField();
        grid.add(numOfPagesTextField, 1, 3);

        Button btn = new Button("Scan");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);

        Runnable r = new Runnable() {
            public void run() {

                String outputFile =  Main.runScanner(input[0], input[1], input[2], 20);
                actiontarget.setFill(Color.GREEN);
                actiontarget.setText("Scan has finished");

                actiontarget2.setFill(Color.GREEN);
                actiontarget2.setText("Check out report - " + outputFile);
            }
        };

        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                actiontarget.setFill(Color.GREEN);
                actiontarget2.setFill(Color.GREEN);
                actiontarget.setText("Scan has started");
                actiontarget2.setText("Generating report.......");
                input[0] = storeTextField.getText();
                input[1] = searchKeyTextField.getText();
                input[2] = numOfPagesTextField.getText();

                Thread th = new Thread(r);
                th.start();


            }


        });








        primaryStage.show();
    }
}
