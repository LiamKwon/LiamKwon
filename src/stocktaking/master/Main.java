package stocktaking.master;

import stocktaking.master.DB;

import java.awt.Toolkit;
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
import javafx.stage.*;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class Main extends Application {
	static String bms_Col="";
	static String location_Col="";
	static String title="";
	static String message="";
	static Stage window;
	//static int counter=0;
	VBox vBoxForMain =new VBox();
	VBox vBoxForInput =new VBox();
	VBox vBoxForFind =new VBox();
	VBox vBoxForMove =new VBox();
	VBox vBoxForHistory =new VBox();
	VBox vBoxForSearch =new VBox();
	VBox vBoxForLogin =new VBox();
	Scene mainp, inputp, findp, moveP,historyP, loginP, searchP;
	static TableView<Product> mainTable;
	TableView<Product> inputTable;
	TableView<Product> findTable;
	TableView<Product> moveTable;
	TableView<Product> historyTable;
	TableView<Product> searchTable;
	TextField idTxt_Login,pwTxt_Login,bmsTextbox_Input, locationTextbox_Input,bmsTextbox_Find, locationTextbox_Find,fromTextbox_Move,toTextbox_Move,historyTextbox_History, searchTextbox_Search;
	static DB db = new DB();
	static GUI gui = new GUI();
	final Runnable runnable = (Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.default");
	static String userName;
	
	
    public static void main(String[] args) {
    	DB.init();
    	launch(args);
    	
        
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
    	
    	Main.window = primaryStage;
    	Label userNameLbl_Main = new Label();
    	userNameLbl_Main.setStyle("-fx-font-size:15");
    	Label userNameLbl_Input = new Label();
    	userNameLbl_Input.setStyle("-fx-font-size:15");
    	Label userNameLbl_Find = new Label();
    	userNameLbl_Find.setStyle("-fx-font-size:15");
    	Label userNameLbl_Move = new Label();
    	userNameLbl_Move.setStyle("-fx-font-size:15");
    	Label userNameLbl_His = new Label();
    	userNameLbl_His.setStyle("-fx-font-size:15");
    	Label userNameLbl_Search = new Label();
    	userNameLbl_Search.setStyle("-fx-font-size:15");
    	
//--------------------Login page---------------------------------
    	
    	//----------------------------login text box------------------------------
    	
    	//bms text box
    	idTxt_Login = new TextField();
    	idTxt_Login.setPromptText("ID");
    	idTxt_Login.setStyle("-fx-font-size:15");
    	idTxt_Login.setMinWidth(100);
    	
    	pwTxt_Login = new TextField();
    	pwTxt_Login.setPromptText("Password");
    	pwTxt_Login.setStyle("-fx-font-size:15");
    	pwTxt_Login.setMinWidth(100);
    	
    	
        //login btn
        Button loginBtn_Login = new Button("Log in");
        loginBtn_Login.setStyle("-fx-font-size:15");
        loginBtn_Login.setMinWidth(150);
        loginBtn_Login.setOnAction(new EventHandler() {
        	
			@Override
			public void handle(Event event) {
				if(db.LoginCheck(idTxt_Login.getText(), pwTxt_Login.getText())) {
					userName= idTxt_Login.getText();
					idTxt_Login.clear();
					pwTxt_Login.clear();
					userNameLbl_Main.setText("user: "+userName);
					userNameLbl_Input.setText("user: "+userName);
					userNameLbl_Find.setText("user: "+userName);
					userNameLbl_Move.setText("user: "+userName);
					userNameLbl_His.setText("user: "+userName);
					runnable.run();
					gui.mainPageBtn_Input_Clicked(mainTable,mainp);	
				}else {
					
					idTxt_Login.clear();
					pwTxt_Login.clear();
					BeepSound();
					
					
				}
				
				
			}
        });
        
        idTxt_Login.setOnKeyPressed(new EventHandler<KeyEvent> () {
            @Override
              public void handle(KeyEvent event) {
            	
            	if(event.getCode().equals(KeyCode.ENTER)){
            		
            		pwTxt_Login.requestFocus();
            		}
            	
            }
              
        });
        pwTxt_Login.setOnKeyPressed(new EventHandler<KeyEvent> () {
            @Override
              public void handle(KeyEvent event) {
            	
            	if(event.getCode().equals(KeyCode.ENTER)){
            		if(db.LoginCheck(idTxt_Login.getText(), pwTxt_Login.getText())) {
    					userName= idTxt_Login.getText();
    					idTxt_Login.clear();
    					pwTxt_Login.clear();
    					userNameLbl_Main.setText("user: "+userName);
    					userNameLbl_Input.setText("user: "+userName);
    					userNameLbl_Find.setText("user: "+userName);
    					userNameLbl_Move.setText("user: "+userName);
    					userNameLbl_His.setText("user: "+userName);
    					userNameLbl_Search.setText("user: "+userName);
    					runnable.run();
    					gui.mainPageBtn_Input_Clicked(mainTable,mainp);	
    					idTxt_Login.requestFocus();
    				}else {
    					
    					idTxt_Login.clear();
    					pwTxt_Login.clear();
    					idTxt_Login.requestFocus();
    					BeepSound();
    					
    				}
            		
            		}
            	
            }
              
        });
        
     
        
        //set main windows size
    	HBox delAll_Main = new HBox();
    	delAll_Main.setPadding(new Insets(100,50,100,50));
    	delAll_Main.setSpacing(50);
    	delAll_Main.setAlignment(Pos.CENTER);
    	//elements of main at the btm
    	delAll_Main.getChildren().addAll(idTxt_Login,pwTxt_Login,loginBtn_Login);
    	
    	// apply elements in main page
    	vBoxForLogin.setPadding(new Insets(100,50,100,50));
    	vBoxForLogin.setSpacing(10);
    	vBoxForLogin.setAlignment(Pos.CENTER);
        vBoxForLogin.getChildren().addAll(idTxt_Login,pwTxt_Login,loginBtn_Login);
        loginP = new Scene(vBoxForLogin);
        
    	
    	//-----------------main page-------------------------------------
    	
    	
        //----------------------------main btn--------------------------------
    	
    	//create excel file
        Button createExcelBtn_Main = new Button("CreateExcel");
        createExcelBtn_Main.setStyle("-fx-font-size:15");
        createExcelBtn_Main.setMinWidth(150);
        createExcelBtn_Main.setOnAction(e -> db.CreateExcel());
    	
        //input btn
        Button mainInputBtn_Main = new Button("Stock In Page");  
        mainInputBtn_Main.setStyle("-fx-font-size:15");
        mainInputBtn_Main.setMinWidth(150);
        mainInputBtn_Main.setOnAction(new EventHandler() {
        	
			@Override
			public void handle(Event event) {
				
				window.setScene(inputp);
				locationTextbox_Input.requestFocus();
			}
        });
        
        //find btn
        Button mainFindBtn_Main = new Button("Stock Out Page");
        mainFindBtn_Main.setStyle("-fx-font-size:15");
        mainFindBtn_Main.setMinWidth(150);
        mainFindBtn_Main.setOnAction(new EventHandler() {
        	
			@Override
			public void handle(Event event) {
				
				window.setScene(findp);
				bmsTextbox_Find.requestFocus();
			}
        });
        
        
        //BMS move btn
        Button mainMoveBtn_Main = new Button("Move Page");
        mainMoveBtn_Main.setStyle("-fx-font-size:15");
        mainMoveBtn_Main.setMinWidth(150);
        mainMoveBtn_Main.setOnAction(new EventHandler() {
        	
			@Override
			public void handle(Event event) {
				
				window.setScene(moveP);
				fromTextbox_Move.requestFocus();
				
			}
        });
        
        //history btn
        Button mainHistoryBtn_Main = new Button("History Page"); 
        mainHistoryBtn_Main.setStyle("-fx-font-size:15");
        mainHistoryBtn_Main.setMinWidth(150);
        mainHistoryBtn_Main.setOnAction(new EventHandler() {
        	
			@Override
			public void handle(Event event) {
				
				window.setScene(historyP);
				historyTextbox_History.requestFocus();
				
			}
        });
        
        //search btn
        Button mainSearchBtn_Main = new Button("Search Page");
        mainSearchBtn_Main.setStyle("-fx-font-size:15");
        mainSearchBtn_Main.setMinWidth(150);
        mainSearchBtn_Main.setOnAction(new EventHandler() {
        	
			@Override
			public void handle(Event event) {
				
				window.setScene(searchP);
				searchTextbox_Search.requestFocus();
				
			}
        });
        
        //Delete data from db btn
        Button deleteAllBtn_Login = new Button("Delete All Data");
        deleteAllBtn_Login.setStyle("-fx-font-size:15");
        deleteAllBtn_Login.setMinWidth(150);
        deleteAllBtn_Login.setOnAction(e -> gui.DelAllDBBtn_Main_Clicked(userName));
        
        //-----------------------main column--------------------------------------
        
        //BMS column
    	TableColumn<Product, String> bmsColumn_Main = new TableColumn<>("BMS");
    	bmsColumn_Main.setMinWidth(200);
    	bmsColumn_Main.setCellValueFactory(new PropertyValueFactory<>("bms"));
    	
    	//Location column
    	TableColumn<Product, String> locationColumn_Main = new TableColumn<>("Location");
    	locationColumn_Main.setMinWidth(100);
    	locationColumn_Main.setCellValueFactory(new PropertyValueFactory<>("location"));
    	
    	//Date column
    	TableColumn<Product, String> dateColumn_Main = new TableColumn<>("Date");
    	dateColumn_Main.setMinWidth(100);
    	dateColumn_Main.setCellValueFactory(new PropertyValueFactory<>("date"));
    	
    	//User column
    	TableColumn<Product, String> userColumn_Main = new TableColumn<>("User");
    	userColumn_Main.setMinWidth(100);
    	userColumn_Main.setCellValueFactory(new PropertyValueFactory<>("User"));
    	
    	//set tableview for main page
    	mainTable = new TableView<>();
    	mainTable.setItems(getProduct());
    	mainTable.getColumns().addAll(bmsColumn_Main, locationColumn_Main,userColumn_Main, dateColumn_Main);
        
    	db.SelectAllDb(mainTable,true);
    	//--------------------------apply for main page-----------------------------
    	
    	
    	
    	//set main windows size
    	HBox goback_Main = new HBox();
    	goback_Main.setPadding(new Insets(10,10,10,10));
    	goback_Main.setSpacing(50);
    	goback_Main.setAlignment(Pos.CENTER);
    	
       //elements of main at the btm
    	goback_Main.getChildren().addAll(mainInputBtn_Main , mainFindBtn_Main, mainMoveBtn_Main);
        // all the elements in main page
    	//set main windows size
    	HBox goback2_Main = new HBox();
    	goback2_Main.setPadding(new Insets(10,10,10,10));
    	goback2_Main.setSpacing(50);
    	goback2_Main.setAlignment(Pos.CENTER);
    	//elements of main at the btm
    	goback2_Main.getChildren().addAll(mainSearchBtn_Main, mainHistoryBtn_Main, createExcelBtn_Main);
        
        // apply elements in main page
    	vBoxForMain.setPadding(new Insets(100,50,100,50));
    	vBoxForMain.setAlignment(Pos.CENTER);
        vBoxForMain.getChildren().addAll(userNameLbl_Main, goback_Main, goback2_Main,deleteAllBtn_Login);
        mainp = new Scene(vBoxForMain);
        
        
        
        
        //-----------------input page-------------------------------------
        
      //----------------------------input label box----------------------------
        
        Label locationCounterLbl_Input = new Label();
        locationCounterLbl_Input.setStyle("-fx-font-size:15");
        locationCounterLbl_Input.setText("Total number of assets in the location ");
        
      //----------------------------input text box----------------------------
        
        //bms text box
        bmsTextbox_Input = new TextField();
        bmsTextbox_Input.setPromptText("BMS");
        bmsTextbox_Input.setMinWidth(100);
        bmsTextbox_Input.setStyle("-fx-font-size:15");
        //lication text box
        locationTextbox_Input = new TextField();
        locationTextbox_Input.setPromptText("Location");
        locationTextbox_Input.setStyle("-fx-font-size:15");
        
    	//----------------------------input btn--------------------------------
        
        //deletebtn from view
        Button deleteBtn_Input = new Button("Clear");
        deleteBtn_Input.setStyle("-fx-font-size:15");
        deleteBtn_Input.setMinWidth(150);
        //deleteBtn_Input.setOnAction(e -> inputTable.getItems().clear());
        deleteBtn_Input.setOnAction(new EventHandler() {
        	
			@Override
			public void handle(Event event) {
				inputTable.getItems().clear();
				locationTextbox_Input.clear();
				bmsTextbox_Input.clear();
				locationCounterLbl_Input.setText("Total number of assets in the location ");
				
			}
        });
        
        //go back to mainp btn
        Button mainPageBtn_Input = new Button("Main Page");
        mainPageBtn_Input.setStyle("-fx-font-size:15");
        mainPageBtn_Input.setMinWidth(150);
        
        //clear all list on view and go to main page when user clicked main page btn
        mainPageBtn_Input.setOnAction(new EventHandler() {
        	
			@Override
			public void handle(Event event) {
				inputTable.getItems().clear();
				locationTextbox_Input.clear();
				locationCounterLbl_Input.setText("Total number of assets in the location ");
				window.setScene(mainp);
				
			}
        });
       
        //go back to find btn
        Button mainFindBtn_Input = new Button("Stock out Page");
        mainFindBtn_Input.setStyle("-fx-font-size:15");
        mainFindBtn_Input.setMinWidth(150);
        //clear all list on view and go to find data page when user clicked find data page btn
        mainFindBtn_Input.setOnAction(new EventHandler() {
        	
			@Override
			public void handle(Event event) {
				inputTable.getItems().clear();
				window.setScene(findp);
				locationTextbox_Input.clear();
				bmsTextbox_Find.requestFocus();
				locationCounterLbl_Input.setText("Total number of assets in the location ");
			}
        });
        
      //go back to find btn
        Button mainMoveBtn_Input = new Button("Move Page");
        mainMoveBtn_Input.setStyle("-fx-font-size:15");
        mainMoveBtn_Input.setMinWidth(150);
        //clear all list on view and go to find data page when user clicked find data page btn
        mainMoveBtn_Input.setOnAction(new EventHandler() {
        	
			@Override
			public void handle(Event event) {
				inputTable.getItems().clear();
				window.setScene(moveP);
				locationTextbox_Input.clear();
				fromTextbox_Move.requestFocus();
				locationCounterLbl_Input.setText("Total number of assets in the location ");
			}
        });
        
        
        //-----------------------input column--------------------------------------
        
        //BMS column
    	TableColumn<Product, String> bmsColumn_Input = new TableColumn<>("BMS");
    	bmsColumn_Input.setMinWidth(200);
    	bmsColumn_Input.setCellValueFactory(new PropertyValueFactory<>("bms"));
    	
    	//Location column
    	TableColumn<Product, String> locationColumn_Input = new TableColumn<>("Location");
    	locationColumn_Input.setMinWidth(100);
    	locationColumn_Input.setCellValueFactory(new PropertyValueFactory<>("location"));
    	
    	//Date column
    	TableColumn<Product, String> dateColumn_Input = new TableColumn<>("Date");
    	dateColumn_Input.setMinWidth(100);
    	dateColumn_Input.setCellValueFactory(new PropertyValueFactory<>("date"));
    	
    	//Location column
    	TableColumn<Product, String> userColumn_Input = new TableColumn<>("User");
    	userColumn_Input.setMinWidth(100);
    	userColumn_Input.setCellValueFactory(new PropertyValueFactory<>("User"));
    	
    	
    	//set tableview for find page
    	inputTable = new TableView<>();
    	inputTable.setItems(getProduct());
    	inputTable.getColumns().addAll(bmsColumn_Input, locationColumn_Input, userColumn_Input , dateColumn_Input);
    	
    	
        //--------------------------apply for input page------------------------------
    	
    	// preventing space data in location textbox
    	locationTextbox_Input.setOnKeyPressed(new EventHandler<KeyEvent> () {
            @Override
              public void handle(KeyEvent event) {
            	
            	if(event.getCode().equals(KeyCode.SPACE)){
            		
            		BeepSound();
            		locationTextbox_Input.clear();
            		
            		}
            	else if(event.getCode().equals(KeyCode.ENTER)){
            		runnable.run();
            		bmsTextbox_Input.requestFocus();
            		locationCounterLbl_Input.setText(locationTextbox_Input.getText()+" -> "+db.locationSearchDB(locationTextbox_Input.getText(),inputTable));
                    
            	}
                
            }
              
        });
    	
    	//bms text box event
    	bmsTextbox_Input.setOnKeyPressed(new EventHandler<KeyEvent> () {
            @Override
              public void handle(KeyEvent event) {
            	if(event.getCode().equals(KeyCode.ENTER)){
            		
                		if(bmsTextbox_Input.getLength() == 10 && locationTextbox_Input.getLength() > 1 ) {
                			gui.bmsTextboxGD_Input(bmsTextbox_Input,locationTextbox_Input,userName,inputTable);
                			
                			locationCounterLbl_Input.setText(locationTextbox_Input.getText()+"->"+db.locationSearchDB(locationTextbox_Input.getText(),inputTable));
            		        bmsTextbox_Input.clear();
                		}else {
                			BeepSound();
                			bmsTextbox_Input.clear();
                		}
            	}
                }
              
        });
    	
        //set view input windows size 
        HBox inputUi_Input = new HBox();
        inputUi_Input.setPadding(new Insets(10,10,10,10));
        inputUi_Input.setAlignment(Pos.CENTER);
        inputUi_Input.setSpacing(10);
        //view elements at the btm
        inputUi_Input.getChildren().addAll(locationTextbox_Input,bmsTextbox_Input,deleteBtn_Input);
        
        // set the DB btn windows size
        HBox dbBtnUi_Input = new HBox();
        dbBtnUi_Input.setPadding(new Insets(10,10,10,10));
        dbBtnUi_Input.setAlignment(Pos.CENTER);
        //DB btn elements at the very btm
        dbBtnUi_Input.getChildren().addAll(locationCounterLbl_Input);
        
     // set the DB btn windows size
        HBox GBBtnUi_Input = new HBox();
        GBBtnUi_Input.setPadding(new Insets(10,10,10,10));
        GBBtnUi_Input.setAlignment(Pos.CENTER);
        GBBtnUi_Input.setSpacing(40);
        //DB btn elements at the very btm
        GBBtnUi_Input.getChildren().addAll(mainFindBtn_Input, mainMoveBtn_Input, mainPageBtn_Input);
        
    	// apply elements in input page
        vBoxForInput.getChildren().addAll(userNameLbl_Input,inputTable, inputUi_Input, dbBtnUi_Input, GBBtnUi_Input);
    	inputp = new Scene(vBoxForInput);
    	
    	
    	
    	//-----------------find page-------------------------------------
    	//----------------------------input label box----------------------------
        
        Label locationCounterLbl_Find = new Label();
        locationCounterLbl_Find.setStyle("-fx-font-size:15");
        locationCounterLbl_Find.setText("Total number of assets in the location ");
        
        //----------------------------find text box----------------------------
        
        //bms text box
    	bmsTextbox_Find = new TextField();
        bmsTextbox_Find.setPromptText("BMS");
        bmsTextbox_Find.setMinWidth(100);
        bmsTextbox_Find.setStyle("-fx-font-size:15");
        locationTextbox_Find = new TextField();
        locationTextbox_Find.setPromptText("Location");
        locationTextbox_Find.setMinWidth(100);
        locationTextbox_Find.setStyle("-fx-font-size:15");
        
    	//----------------------------find btn--------------------------------
        
        //deletebtn from view btn
        Button deleteBtn_Find = new Button("delete from list");
        deleteBtn_Find.setStyle("-fx-font-size:15");
        deleteBtn_Find.setMinWidth(150);
        deleteBtn_Find.setOnAction(e -> gui.deleteButtonClicked(findTable,locationCounterLbl_Find));
        //Delete data from db btn
        Button deleteDbBtn_Find = new Button("Delete From DB");
        deleteDbBtn_Find.setStyle("-fx-font-size:15");
        deleteDbBtn_Find.setMinWidth(150);
        deleteDbBtn_Find.setOnAction(e -> gui.DelDBBtn_Find_Clicked(findTable,locationTextbox_Find,userName,locationCounterLbl_Find));
        //go back to mainp btn
        Button mainPageBtn_Find = new Button("Main Page");
        mainPageBtn_Find.setStyle("-fx-font-size:15");
        mainPageBtn_Find.setMinWidth(150);
        mainPageBtn_Find.setOnAction(new EventHandler() {
        	
			@Override
			public void handle(Event event) {
				findTable.getItems().clear();
				bmsTextbox_Find.clear();
				locationTextbox_Find.clear();
				locationCounterLbl_Find.setText(findTable.getItems().size()+" Assets ");
				window.setScene(mainp);
				
			}
        });
        //go back to stock in page btn
        Button mainInputBtn_Find = new Button("Stock In Page");
        mainInputBtn_Find.setStyle("-fx-font-size:15");
        mainInputBtn_Find.setMinWidth(150);
        mainInputBtn_Find.setOnAction(new EventHandler() {
        	
			@Override
			public void handle(Event event) {
				findTable.getItems().clear();
				window.setScene(inputp);
				locationCounterLbl_Find.setText(findTable.getItems().size()+" Assets ");
				bmsTextbox_Find.clear();
				locationTextbox_Find.clear();
				locationTextbox_Input.requestFocus();
			}
        });
        //go back to move page btn
        Button mainMoveBtn_Find = new Button("Move Page");
        mainMoveBtn_Find.setStyle("-fx-font-size:15");
        mainMoveBtn_Find.setMinWidth(150);
        mainMoveBtn_Find.setOnAction(new EventHandler() {
        	
			@Override
			public void handle(Event event) {
				findTable.getItems().clear();
				window.setScene(moveP);
				locationCounterLbl_Find.setText(findTable.getItems().size()+" Assets ");
				bmsTextbox_Find.clear();
				locationTextbox_Find.clear();
				fromTextbox_Move.requestFocus();
			}
        });
        
        //-----------------------find column--------------------------------------
        
        //BMS column
    	TableColumn<Product, String> bmsColumn_Find = new TableColumn<>("BMS");
    	bmsColumn_Find.setMinWidth(200);
    	bmsColumn_Find.setCellValueFactory(new PropertyValueFactory<>("bms"));
    	
    	//Location column
    	TableColumn<Product, String> locationColumn_Find = new TableColumn<>("Location");
    	locationColumn_Find.setMinWidth(100);
    	locationColumn_Find.setCellValueFactory(new PropertyValueFactory<>("location"));
    	
    	//Location column
    	TableColumn<Product, String> userColumn_Find = new TableColumn<>("User");
    	userColumn_Find.setMinWidth(100);
    	userColumn_Find.setCellValueFactory(new PropertyValueFactory<>("User"));
    	
    	//Date column
    	TableColumn<Product, String> dateColumn_Find = new TableColumn<>("Date");
    	dateColumn_Find.setMinWidth(100);
    	dateColumn_Find.setCellValueFactory(new PropertyValueFactory<>("date"));
    	
    	//set tableview for find page
    	findTable = new TableView<>();
    	findTable.setItems(getProduct());
    	findTable.getColumns().addAll(bmsColumn_Find, locationColumn_Find,userColumn_Find, dateColumn_Find);
    	
        //--------------------------apply for find page------------------------------
    	
    	bmsTextbox_Find.setOnKeyPressed(new EventHandler<KeyEvent> () {
            @Override
              public void handle(KeyEvent event) {
            	if(event.getCode().equals(KeyCode.ENTER)){
            		gui.Showing_Input_Data(bmsTextbox_Find, findTable,true);
            		locationCounterLbl_Find.setText(findTable.getItems().size()+" Assets ");
            		bmsTextbox_Find.clear();
            		}
                }
              
        });
    	// preventing space data in location textbox
    	locationTextbox_Find.setOnKeyPressed(new EventHandler<KeyEvent> () {
            @Override
              public void handle(KeyEvent event) {
            	if(event.getCode().equals(KeyCode.SPACE)){
        			BeepSound();
        			locationTextbox_Find.clear();
               
            		}
            	else if(event.getCode().equals(KeyCode.ENTER)){
            		gui.Showing_Input_Data(locationTextbox_Find, findTable,false);
            		locationCounterLbl_Find.setText(findTable.getItems().size()+" Assets ");
            		runnable.run();
            		locationTextbox_Find.clear();
            		}
                }
              
        });
    	
        //set view input windows size 
        HBox inputUi_Find = new HBox();
        inputUi_Find.setPadding(new Insets(10,10,10,10));
        inputUi_Find.setAlignment(Pos.CENTER);
        inputUi_Find.setSpacing(10);
        //view elements at the btm
        inputUi_Find.getChildren().addAll(bmsTextbox_Find,locationTextbox_Find,deleteBtn_Find);
        
        // set the DB btn windows size
        HBox dbBtnUi_Find = new HBox();
        dbBtnUi_Find.setPadding(new Insets(10,10,10,10));
        dbBtnUi_Find.setAlignment(Pos.CENTER);
        //DB btn elements at the very btm
        dbBtnUi_Find.getChildren().addAll(locationCounterLbl_Find,deleteDbBtn_Find);
        
        // set the DB btn windows size
        HBox GBBtnUi_Find = new HBox();
        GBBtnUi_Find.setPadding(new Insets(10,10,10,10));
        GBBtnUi_Find.setAlignment(Pos.CENTER);
        GBBtnUi_Find.setSpacing(40);
        //DB btn elements at the very btm
        GBBtnUi_Find.getChildren().addAll(mainInputBtn_Find, mainMoveBtn_Find, mainPageBtn_Find);
        
    	// apply elements in find page
        vBoxForFind.getChildren().addAll(userNameLbl_Find,findTable, inputUi_Find, dbBtnUi_Find, GBBtnUi_Find);
    	findp = new Scene(vBoxForFind);
    	
    	
     
    	// set mainp as a main page
    	window.setScene(loginP);
    	window.setTitle("BMS Warehouse Stock Management System");
    	window.show();
    	
    	
    	
    	
    	//-----------------move page-------------------------------------
        
        //----------------------------input label box----------------------------
          
          Label locationCounterLbl_Move = new Label();
          locationCounterLbl_Move.setStyle("-fx-font-size:15");
          locationCounterLbl_Move.setText("Total number of assets in the location ");
          
        //----------------------------move text box----------------------------
          
          //bms text box
          fromTextbox_Move = new TextField();
          fromTextbox_Move.setPromptText("From");
          fromTextbox_Move.setMinWidth(100);
          fromTextbox_Move.setStyle("-fx-font-size:15");
          //lication text box
          toTextbox_Move = new TextField();
          toTextbox_Move.setPromptText("To");
          toTextbox_Move.setMinWidth(100);
          toTextbox_Move.setStyle("-fx-font-size:15");
          
      	//----------------------------Move btn--------------------------------
        
          //deletebtn from view
          Button deleteBtn_Move = new Button("Clear");
          deleteBtn_Move.setStyle("-fx-font-size:15");
          deleteBtn_Move.setMinWidth(150);
          //deleteBtn_Input.setOnAction(e -> inputTable.getItems().clear());
          deleteBtn_Move.setOnAction(new EventHandler() {
          	
  			@Override
  			public void handle(Event event) {
  				moveTable.getItems().clear();
  				fromTextbox_Move.clear();
  				toTextbox_Move.clear();
  				locationCounterLbl_Input.setText("Total number of assets in the location ");
  				
  			}
          });
        //upload btn
          Button MoveBtn_Move = new Button("Move");
          MoveBtn_Move.setStyle("-fx-font-size:15");
          MoveBtn_Move.setMinWidth(150);
          MoveBtn_Move.setOnAction(e -> gui.MoveBtn_Move_Clicked(moveTable, toTextbox_Move, fromTextbox_Move,userName, locationCounterLbl_Move));//Fix it
          
          //go back to mainp btn
          Button mainPageBtn_Move = new Button("Main Page");
          mainPageBtn_Move.setStyle("-fx-font-size:15");
          mainPageBtn_Move.setMinWidth(150);
          //clear all list on view and go to main page when user clicked main page btn
          mainPageBtn_Move.setOnAction(new EventHandler() {
          	
  			@Override
  			public void handle(Event event) {
  				moveTable.getItems().clear();
  				fromTextbox_Move.clear();
  				toTextbox_Move.clear();
  				locationCounterLbl_Move.setText("Total number of assets in the location ");
  				window.setScene(mainp);
  				
  			}
          });
         
          //go back to find page btn
          Button mainFindBtn_Move = new Button("Stock out Page");
          mainFindBtn_Move.setStyle("-fx-font-size:15");
          mainFindBtn_Move.setMinWidth(150);
          //clear all list on view and go to find data page when user clicked find data page btn
          mainFindBtn_Move.setOnAction(new EventHandler() {
          	
  			@Override
  			public void handle(Event event) {
  				moveTable.getItems().clear();
  				fromTextbox_Move.clear();
  				toTextbox_Move.clear();
  				locationCounterLbl_Move.setText("Total number of assets in the location ");
  				window.setScene(findp);
  				bmsTextbox_Find.requestFocus();
  				
  				
  			}
          });
          
          //go back to Input page btn
          Button mainInputBtn_Move = new Button("Stock In Page");
          mainInputBtn_Move.setStyle("-fx-font-size:15");
          mainInputBtn_Move.setMinWidth(150);
          mainInputBtn_Move.setOnAction(new EventHandler() {
            	
    			@Override
    			public void handle(Event event) {
    				moveTable.getItems().clear();
    				fromTextbox_Move.clear();
    				toTextbox_Move.clear();
    				locationCounterLbl_Move.setText("Total number of assets in the location ");
    				window.setScene(inputp);
    				locationTextbox_Input.requestFocus();
    				
    			}
            });
          
          
          //-----------------------Move page column--------------------------------------
          
          //BMS column
      	TableColumn<Product, String> bmsColumn_Move = new TableColumn<>("BMS");
      	bmsColumn_Move.setMinWidth(200);
      	bmsColumn_Move.setCellValueFactory(new PropertyValueFactory<>("bms"));
      	
      	//Location column
      	TableColumn<Product, String> locationColumn_Move = new TableColumn<>("Location");
      	locationColumn_Move.setMinWidth(100);
      	locationColumn_Move.setCellValueFactory(new PropertyValueFactory<>("location"));
      	
      	//User column
      	TableColumn<Product, String> userColumn_Move = new TableColumn<>("User");
      	userColumn_Move.setMinWidth(100);
      	userColumn_Move.setCellValueFactory(new PropertyValueFactory<>("User"));
      	
      	//Date column
    	TableColumn<Product, String> dateColumn_Move = new TableColumn<>("Date");
    	dateColumn_Move.setMinWidth(100);
    	dateColumn_Move.setCellValueFactory(new PropertyValueFactory<>("date"));
      	
      	//set tableview for input page
      	moveTable = new TableView<>();
      	moveTable.setItems(getProduct());
      	moveTable.getColumns().addAll(bmsColumn_Move, locationColumn_Move,userColumn_Move,dateColumn_Move);
          
         
          //--------------------------apply for Move page------------------------------
      	
      	// preventing space data in From textbox
      	fromTextbox_Move.setOnKeyPressed(new EventHandler<KeyEvent> () {
            @Override
              public void handle(KeyEvent event) {
            	if(event.getCode().equals(KeyCode.SPACE)){
            		BeepSound();
            		fromTextbox_Move.clear();
               
            		}
            	else if(event.getCode().equals(KeyCode.ENTER)){
            		runnable.run();
            		gui.Showing_Input_Data(fromTextbox_Move, moveTable,false);
              		locationCounterLbl_Move.setText(fromTextbox_Move.getText()+" -> "+db.locationSearchDB(fromTextbox_Move.getText(),moveTable));
              		toTextbox_Move.requestFocus();
            		}
                }
              
        });
     // preventing space data in To textbox
      	toTextbox_Move.setOnKeyPressed(new EventHandler<KeyEvent> () {
              @Override
                public void handle(KeyEvent event) {
              	
              	if(event.getCode().equals(KeyCode.SPACE)){
              		BeepSound();
              		toTextbox_Move.clear();
              		
              		}
              	else if(event.getCode().equals(KeyCode.ENTER)) {
              		runnable.run();
              		locationCounterLbl_Move.setText(toTextbox_Move.getText()+" -> "+db.locationSearchDB(toTextbox_Move.getText(),moveTable));
                    fromTextbox_Move.requestFocus();  
              	}
                  
              }
                
          });
      	
          //set view Move windows size 
          HBox inputUi_Move = new HBox();
          inputUi_Move.setPadding(new Insets(10,10,10,10));
          inputUi_Move.setAlignment(Pos.CENTER);
          inputUi_Move.setSpacing(10);
          //view elements at the btm
          inputUi_Move.getChildren().addAll(fromTextbox_Move,toTextbox_Move, deleteBtn_Move);
          
          // set the Counting Lbl windows size
          HBox counterLblUi_Move = new HBox();
          counterLblUi_Move.setPadding(new Insets(10,10,10,10));
          counterLblUi_Move.setAlignment(Pos.CENTER);
          //DB btn elements at the very btm
          counterLblUi_Move.getChildren().addAll(locationCounterLbl_Move, MoveBtn_Move);
          
       // set the DB btn windows size
          HBox GBBtnUi_Move = new HBox();
          GBBtnUi_Move.setPadding(new Insets(10,10,10,10));
          GBBtnUi_Move.setAlignment(Pos.CENTER);
          GBBtnUi_Move.setSpacing(40);
          //DB btn elements at the very btm
          GBBtnUi_Move.getChildren().addAll(mainInputBtn_Move, mainFindBtn_Move, mainPageBtn_Move);
          
      	// apply elements in input page
          vBoxForMove.getChildren().addAll(userNameLbl_Move,moveTable, inputUi_Move, counterLblUi_Move, GBBtnUi_Move);
      	moveP = new Scene(vBoxForMove);
      	
      	
      	
      	
      	
      	//-----------------History page-------------------------------------
        
      
        //----------------------------History text box----------------------------
          
          //bms text box
          historyTextbox_History = new TextField();
          historyTextbox_History.setStyle("-fx-font-size:15");
          historyTextbox_History.setPromptText("BMS");
          historyTextbox_History.setMinWidth(100);
          
      	
      	
      //----------------------------History btn--------------------------------
        
        
          //go back to mainp btn
          Button mainPageBtn_History = new Button("Main Page");
          mainPageBtn_History.setStyle("-fx-font-size:15");
          mainPageBtn_History.setMinWidth(150);
          //clear all list on view and go to main page when user clicked main page btn
          mainPageBtn_History.setOnAction(new EventHandler() {
          	
  			@Override
  			public void handle(Event event) {
  				historyTable.getItems().clear();
  				historyTextbox_History.clear();
  				window.setScene(mainp);
  				
  			}
          });
         
          
          //-----------------------History page column--------------------------------------
          
          //BMS column
      	TableColumn<Product, String> bmsColumn_History = new TableColumn<>("BMS");
      	bmsColumn_History.setMinWidth(200);
      	bmsColumn_History.setCellValueFactory(new PropertyValueFactory<>("bms"));
      	
      	//Location column
      	TableColumn<Product, String> locationColumn_History = new TableColumn<>("Location");
      	locationColumn_History.setMinWidth(100);
      	locationColumn_History.setCellValueFactory(new PropertyValueFactory<>("location"));
      	
      //User column
      	TableColumn<Product, String> userColumn_History = new TableColumn<>("User");
      	userColumn_History.setMinWidth(100);
      	userColumn_History.setCellValueFactory(new PropertyValueFactory<>("User"));
      	
      	//Date column
    	TableColumn<Product, String> dateColumn_History = new TableColumn<>("Date");
    	dateColumn_History.setMinWidth(100);
    	dateColumn_History.setCellValueFactory(new PropertyValueFactory<>("date"));
      	
      	//set tableview for input page
      	historyTable = new TableView<>();
      	historyTable.setItems(getProduct());
      	historyTable.getColumns().addAll(bmsColumn_History, locationColumn_History,userColumn_History,dateColumn_History);
          
      	
          //--------------------------apply for History page------------------------------
      	
      	// preventing space data in From textbox
      	historyTextbox_History.setOnKeyPressed(new EventHandler<KeyEvent> () {
            @Override
              public void handle(KeyEvent event) {
            	if(event.getCode().equals(KeyCode.SPACE)){

            		historyTextbox_History.clear();
               
            		}
            	else if(event.getCode().equals(KeyCode.ENTER)){
            		historyTable.getItems().clear();
            		db.SelectHistoryDb(historyTextbox_History.getText(), historyTable);


            		}
                }
              
        });
      	
      	
          //set view history windows size 
          HBox inputUi_History = new HBox();
          inputUi_History.setPadding(new Insets(10,10,10,10));
          inputUi_History.setAlignment(Pos.CENTER);
          inputUi_History.setSpacing(10);
          //view elements at the btm
          inputUi_History.getChildren().addAll(historyTextbox_History);
          
          
          // set the DB btn windows size
          HBox GBBtnUi_History = new HBox();
          GBBtnUi_History.setPadding(new Insets(10,10,10,10));
          GBBtnUi_History.setAlignment(Pos.CENTER);
          GBBtnUi_History.setSpacing(40);
          //DB btn elements at the very btm
          GBBtnUi_History.getChildren().addAll(mainPageBtn_History);
          
      	// apply elements in history page
          vBoxForHistory.getChildren().addAll(userNameLbl_His,historyTable, inputUi_History, GBBtnUi_History);
      	historyP = new Scene(vBoxForHistory);
      	
      
      	
      //-----------------Search page-------------------------------------
        
        
        //----------------------------History text box----------------------------
          
      	//bms text box
        searchTextbox_Search = new TextField();
        searchTextbox_Search.setStyle("-fx-font-size:15");
        searchTextbox_Search.setPromptText("BMS");
        searchTextbox_Search.setMinWidth(100);
    	
      	
      	
      //----------------------------History btn--------------------------------
        
        
          //go back to mainp btn
          Button mainPageBtn_Search = new Button("Main Page");
          mainPageBtn_Search.setStyle("-fx-font-size:15");
          mainPageBtn_Search.setMinWidth(150);
          //clear all list on view and go to main page when user clicked main page btn
          mainPageBtn_Search.setOnAction(new EventHandler() {
          	
  			@Override
  			public void handle(Event event) {
  				searchTable.getItems().clear();
  				searchTextbox_Search.clear();
  				window.setScene(mainp);
  				
  			}
          });
         
          
          //-----------------------History page column--------------------------------------
          
          //BMS column
      	TableColumn<Product, String> bmsColumn_Search = new TableColumn<>("BMS");
      	bmsColumn_Search.setMinWidth(200);
      	bmsColumn_Search.setCellValueFactory(new PropertyValueFactory<>("bms"));
      	
      	//Location column
      	TableColumn<Product, String> locationColumn_Search = new TableColumn<>("Location");
      	locationColumn_Search.setMinWidth(100);
      	locationColumn_Search.setCellValueFactory(new PropertyValueFactory<>("location"));
      	
      //User column
      	TableColumn<Product, String> userColumn_Search = new TableColumn<>("User");
      	userColumn_Search.setMinWidth(100);
      	userColumn_Search.setCellValueFactory(new PropertyValueFactory<>("User"));
      	
      	//Date column
    	TableColumn<Product, String> dateColumn_Search = new TableColumn<>("Date");
    	dateColumn_Search.setMinWidth(100);
    	dateColumn_Search.setCellValueFactory(new PropertyValueFactory<>("date"));
      	
      	//set tableview for input page
      	searchTable = new TableView<>();
      	searchTable.setItems(getProduct());
      	searchTable.getColumns().addAll(bmsColumn_Search, locationColumn_Search,userColumn_Search,dateColumn_Search);
          
      	
          //--------------------------apply for History page------------------------------
      	
     // preventing space data in From textbox
      	searchTextbox_Search.setOnKeyPressed(new EventHandler<KeyEvent> () {
            @Override
              public void handle(KeyEvent event) {
            	if(event.getCode().equals(KeyCode.SPACE)){

            		searchTextbox_Search.clear();
               
            		}
            	else if(event.getCode().equals(KeyCode.ENTER)){
            		searchTable.getItems().clear();
            		db.SelectDb(searchTextbox_Search.getText(), searchTable, true);
            		searchTextbox_Search.clear();

            		}
                }
              
        });
    	
    	//set search windows size
    	HBox inputUi_Search = new HBox();
    	inputUi_Search.setPadding(new Insets(10,10,10,10));
    	inputUi_Search.setSpacing(50);
    	inputUi_Search.setAlignment(Pos.CENTER);
    	
    	inputUi_Search.getChildren().addAll(searchTextbox_Search);
          
          
          // set the DB btn windows size
          HBox GBBtnUi_Search = new HBox();
          GBBtnUi_Search.setPadding(new Insets(10,10,10,10));
          GBBtnUi_Search.setAlignment(Pos.CENTER);
          GBBtnUi_Search.setSpacing(40);
          //DB btn elements at the very btm
          GBBtnUi_Search.getChildren().addAll(mainPageBtn_Search);
          
      	// apply elements in history page
          vBoxForSearch.getChildren().addAll(userNameLbl_Search,searchTable, inputUi_Search, GBBtnUi_Search);
          searchP = new Scene(vBoxForSearch);
    }

  //----------------------------not main--------------------------------
    public void BeepSound() {
    	Media sound = new Media(new File("/c:/beep/beepS.wav").toURI().toString());
    	MediaPlayer mediaPlayer = new MediaPlayer(sound);
    	mediaPlayer.play();
    }
    
    
    //get all of the products
    public ObservableList<Product> getProduct(){
    	ObservableList<Product> products = FXCollections.observableArrayList();
    	
    	return products;
    	
    }
    
    
    
}




//----------------------------not main--------------------------------



////when bms text box of StockIn page got data
//public void bmsTextboxGD_Input(TextField bmsInput, TextField locationInput, TableView<Product> table){
//	
//	int i = 0;
//	boolean nothing = true;
//	for(i=0 ; i<table.getItems().size(); i++) {
//		bms_Col=table.getColumns().get(0).getCellObservableValue(i).getValue().toString();
//		if(bmsInput.getText().equals(bms_Col)) {
//				
//			System.out.println("so far so good");
//			System.out.println(bmsInput.getText());
//			System.out.println(bms_Col);
//    		System.out.println("what is else if inside i"+i);
//			nothing = false;
//				
//    	    	        break;
//        			
//			}
//			
//	}
//	if(nothing){
//		
//		
//    		db.InsertDb(table,bmsInput.getText(),locationInput.getText());
//        
//    		db.SelectDb(bmsInput.getText(),table,true);
//    	
//        bmsInput.clear();
//        
//        System.out.println("what is else inside of bms_col "+bms_Col);
//        
//        System.out.println("what is else inside i "+i);
//        
//	}
//		System.out.println("what is outside i"+i);
//		System.out.println("what is size"+table.getItems().size());
//		System.out.println("the end");
//	
//}


////when bms text box of StockOut page got data
//public void bmsTextboxGD_Find(TextField bmsInput, TableView<Product> table,boolean bmsOrLocation){
//	int i = 0;
//	boolean nothing = true;
//	for(i=0 ; i<table.getItems().size(); i++) {
//		bms_Col=table.getColumns().get(0).getCellObservableValue(i).getValue().toString();
//		if(bmsInput.getText().equals(bms_Col)) {
//				
//			System.out.println("so far so good");
//			System.out.println(bmsInput.getText());
//			System.out.println(bms_Col);
//    		System.out.println("what is else if inside i"+i);
//			nothing = false;
//				
//    	    	        break;
//        			
//			}
//			else {
//				
//				System.out.println("what is else inside of bmsinput "+bmsInput.getText());
//				
//			}
//		
//	}
//	if(nothing){
//		
//		db.SelectDb(bmsInput.getText(),table,bmsOrLocation);
//		
////		Product product = new Product();
////        product.setBms(bmsInput.getText());
////        table.getItems().add(product);
//        //bmsInput.clear();
//        //bms_Col=inputTable.getColumns().get(0).getCellObservableValue(i).getValue().toString();
//        System.out.println("what is else inside of bms_col "+bms_Col);
//        
//        System.out.println("what is else inside i "+i);
//        
//	}
//		System.out.println("what is outside i"+i);
//		System.out.println("what is size"+table.getItems().size());
//		System.out.println("the end");
//	
//}


////upload btn of input clicked
//public void uploadBtn_Input_Clicked(){
//	
//	for(int i=0 ; i<inputTable.getItems().size(); i++){
//		bms_Col=inputTable.getColumns().get(0).getCellObservableValue(i).getValue().toString(); 
//		location_Col=inputTable.getColumns().get(1).getCellObservableValue(i).getValue().toString();
//		db.InsertDb(inputTable,bms_Col,location_Col);
//    }
//	Stage window = new Stage();
//
//    //Block events to other windows
//    window.initModality(Modality.APPLICATION_MODAL);
//    window.setTitle("Input data success");
//    window.setMinWidth(250);
//
//    Label label = new Label();
//    label.setText("total "+inputTable.getItems().size()+" assets are stored");
//    Button closeButton = new Button("Close this window");
//    closeButton.setOnAction(e -> window.close());
//
//    VBox layout = new VBox(10);
//    layout.getChildren().addAll(label, closeButton);
//    layout.setAlignment(Pos.CENTER);
//
//    //Display window and wait for it to be closed before returning
//    Scene scene = new Scene(layout);
//    window.setScene(scene);
//window.showAndWait();
//
//	
//}


//// delete data from db in StockOut is clicked
//
//public void DelDBBtn_Find_Clicked(TableView<Product> table,TextField bmsInput){
//	
//	for(int i=0 ; i<table.getItems().size(); i++){
//		bms_Col=table.getColumns().get(0).getCellObservableValue(i).getValue().toString(); 
//		location_Col=table.getColumns().get(1).getCellObservableValue(i).getValue().toString();
//		db.DeleteDb(table,bms_Col);
//    }
//	Stage window = new Stage();
//
//    //Block events to other windows
//    window.initModality(Modality.APPLICATION_MODAL);
//    window.setTitle("Delete data success");
//    window.setMinWidth(250);
//
//    Label label = new Label();
//    label.setText("total "+table.getItems().size()+" assets are deleted");
//    Button closeButton = new Button("Close this window");
//    closeButton.setOnAction(e -> window.close());
//
//    VBox layout = new VBox(10);
//    layout.getChildren().addAll(label, closeButton);
//    layout.setAlignment(Pos.CENTER);
//
//    //Display window and wait for it to be closed before returning
//    Scene scene = new Scene(layout);
//    window.setScene(scene);
//window.showAndWait();
//bmsInput.clear();
//table.getItems().clear();
//}

//// when move btn in Move Page clicked
//
//public void MoveBtn_Move_Clicked(TableView<Product> table, TextField totxtbox, TextField fromtxtbox){
//	
//	for(int i=0 ; i<table.getItems().size(); i++){
//		bms_Col=table.getColumns().get(0).getCellObservableValue(i).getValue().toString(); 
//		
//		db.UpdateDb(table,bms_Col,totxtbox.getText());
//    }
//	Stage window = new Stage();
//
//    //Block events to other windows
//    window.initModality(Modality.APPLICATION_MODAL);
//    window.setTitle("Move Assets success");
//    window.setMinWidth(250);
//
//    Label label = new Label();
//    label.setText("total "+table.getItems().size()+" assets are Moved");
//    Button closeButton = new Button("Close this window");
//    closeButton.setOnAction(e -> window.close());
//
//    VBox layout = new VBox(10);
//    layout.getChildren().addAll(label, closeButton);
//    layout.setAlignment(Pos.CENTER);
//
//    //Display window and wait for it to be closed before returning
//    Scene scene = new Scene(layout);
//    window.setScene(scene);
//window.showAndWait();
//totxtbox.clear();
//fromtxtbox.clear();
//table.getItems().clear();
//}

////Delete button clicked
//public void deleteButtonClicked(TableView<Product> table){
//    ObservableList<Product> productSelected, allProducts;
//    allProducts = table.getItems();
//    productSelected = table.getSelectionModel().getSelectedItems();
//
//    productSelected.forEach(allProducts::remove);
//}

////mainpage btn for go back to mainpage clicked
//public void mainPageBtn_Input_Clicked(){
//  mainTable.getItems().clear();
//  Main.window.setScene(mainp);
//  db.SelectAllDb(mainTable,false);
//}
