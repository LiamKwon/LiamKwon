package stocktaking.master;

import stocktaking.master.DB;

import java.util.ArrayList;

import javafx.application.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.*;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.ActionEvent;
import javafx.event.Event;
import java.awt.Toolkit;
import java.io.File;

public class GUI {
	
	static String bms_Col="";
	static String location_Col="";
	static DB db = new DB();
	final Runnable runnable = (Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.default");
	
	
    //when bms text box of StockIn page got data
    public void bmsTextboxGD_Input(TextField bmsInput, TextField locationInput, String userName, TableView<Product> table){
    	
    	int i = 0;
    	//boolean nothing = true;
    	for(i=0 ; i<table.getItems().size(); i++) {
    		bms_Col=table.getColumns().get(0).getCellObservableValue(i).getValue().toString();
    		if(bmsInput.getText().equals(bms_Col)) {
    		
    			BeepSound();
    			//nothing = false;
    			break;
            }
    			
    	}
    	//if(nothing){
    			
    		db.InsertDb(table,bmsInput.getText(),locationInput.getText(),userName);
    		
    		db.SelectDb(bmsInput.getText(),table,true);
	      
    	//}
    		
    }
    
    
  
    
    //when bms text box of StockOut page got data
    public void Showing_Input_Data(TextField bmsInput, TableView<Product> table,boolean bmsOrLocation){
    	
    	int i = 0;
    	boolean nothing = true;
    	for(i=0 ; i<table.getItems().size(); i++) {
    		
    		bms_Col=table.getColumns().get(0).getCellObservableValue(i).getValue().toString();
    		location_Col=table.getColumns().get(1).getCellObservableValue(i).getValue().toString();
    		if(bmsInput.getText().equals(bms_Col)||bmsInput.getText().equals(location_Col)) {
    			runnable.run();
    			nothing = false;
    			break;
            	}
    		
    			else {
    				
    				System.out.println("what is else inside of bmsinput "+bmsInput.getText());
    				
    			}
    		
    	}
    	if(nothing){
    		
    		db.SelectDb(bmsInput.getText(),table,bmsOrLocation);
    		
    	}
    		
    }
    
    

  
    
    // delete data from db in StockOut is clicked
    
    public void DelDBBtn_Find_Clicked(TableView<Product> table,TextField bmsInput,String userName,Label labelC){
    	
    	for(int i=0 ; i<table.getItems().size(); i++){
    		bms_Col=table.getColumns().get(0).getCellObservableValue(i).getValue().toString(); 
    		location_Col=table.getColumns().get(1).getCellObservableValue(i).getValue().toString();
    		
    		db.DeleteDb(table,bms_Col,userName);
        }
    	
    	if(table.getItems().size()==0) {BeepSound();}
    	else {
    		runnable.run();
    	Stage window = new Stage();

        //Block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Delete data success");
        window.setMinWidth(250);

        Label label = new Label();
        label.setText("total "+table.getItems().size()+" assets are deleted");
        Button closeButton = new Button("Close this window");
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
        
        bmsInput.clear();
        table.getItems().clear();
        labelC.setText(table.getItems().size()+" Assets ");
    	}
  }
    
    // delete all data from db in main page is clicked
    
    public void DelAllDBBtn_Main_Clicked(String id){
    	
    	
    	if(id.equals("ADMIN")) {
    	Stage window = new Stage();

        //Block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Delete all data success");
        window.setMinWidth(250);

        Label label = new Label();
        label.setText("successfully Delete");
        Button closeButton = new Button("Close this window");
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
        db.DeleteAllDb();
        
    	}
    	else{
        	Stage window = new Stage();

            //Block events to other windows
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("Wrong ID or PW");
            window.setMinWidth(250);

            Label label = new Label();
            label.setText("Wrong ID or PW");
            Button closeButton = new Button("Close this window");
            closeButton.setOnAction(e -> window.close());

            VBox layout = new VBox(10);
            layout.getChildren().addAll(label, closeButton);
            layout.setAlignment(Pos.CENTER);

            //Display window and wait for it to be closed before returning
            Scene scene = new Scene(layout);
            window.setScene(scene);
            window.showAndWait();
            
        	}
       
  }
    
    
    // when move btn in Move Page clicked
   
    public void MoveBtn_Move_Clicked(TableView<Product> table, TextField totxtbox, TextField fromtxtbox,String userName, Label labelC){
    	
    	for(int i=0 ; i<table.getItems().size(); i++){
    		bms_Col=table.getColumns().get(0).getCellObservableValue(i).getValue().toString(); 
    		
    		db.UpdateDb(table,bms_Col,totxtbox.getText(),userName);
        }
    	if(table.getItems().size()==0) {BeepSound();}
    	else {
    		runnable.run();
    	Stage window = new Stage();

        //Block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Move Assets success");
        window.setMinWidth(250);

        Label label = new Label();
        label.setText("total "+table.getItems().size()+" assets are Moved");
        Button closeButton = new Button("Close this window");
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
        
        labelC.setText(totxtbox.getText()+" -> "+db.locationSearchDB(totxtbox.getText(),table));
        totxtbox.clear();
        fromtxtbox.clear();
        table.getItems().clear();
        totxtbox.requestFocus();
    	}
    	
  }
    
    //Delete button clicked
    public void deleteButtonClicked(TableView<Product> table,Label label){
        ObservableList<Product> productSelected, allProducts;
        allProducts = table.getItems();
        productSelected = table.getSelectionModel().getSelectedItems();

        productSelected.forEach(allProducts::remove);
        label.setText(table.getItems().size()+" Assets ");
    }
    
  //mainpage btn for go back to mainpage clicked
  public void mainPageBtn_Input_Clicked(TableView<Product> mainTable,Scene mainp){
	  mainTable.getItems().clear();
	  Main.window.setScene(mainp);
	  db.SelectAllDb(mainTable,false);
  }
  public void BeepSound() {
  	Media sound = new Media(new File("/c:/beep/beepS.wav").toURI().toString());
  	MediaPlayer mediaPlayer = new MediaPlayer(sound);
  	mediaPlayer.play();
  }

}



////upload btn of input clicked
//public void uploadBtn_Input_Clicked(){
//	
//	for(int i=0 ; i<inputTable.getItems().size(); i++){
//		bms_Col=inputTable.getColumns().get(0).getCellObservableValue(i).getValue().toString(); 
//		location_Col=inputTable.getColumns().get(1).getCellObservableValue(i).getValue().toString();
//		db.InsertDb(inputTable,bms_Col,location_Col);
//  }
//	Stage window = new Stage();
//
//  //Block events to other windows
//  window.initModality(Modality.APPLICATION_MODAL);
//  window.setTitle("Input data success");
//  window.setMinWidth(250);
//
//  Label label = new Label();
//  label.setText("total "+inputTable.getItems().size()+" assets are stored");
//  Button closeButton = new Button("Close this window");
//  closeButton.setOnAction(e -> window.close());
//
//  VBox layout = new VBox(10);
//  layout.getChildren().addAll(label, closeButton);
//  layout.setAlignment(Pos.CENTER);
//
//  //Display window and wait for it to be closed before returning
//  Scene scene = new Scene(layout);
//  window.setScene(scene);
//window.showAndWait();
//
//	
//}
