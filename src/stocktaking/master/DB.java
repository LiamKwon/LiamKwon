package stocktaking.master;

import java.awt.Toolkit;

import java.sql.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.application.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.*;
import javafx.util.Callback;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;

import org.apache.poi.hssf.usermodel.HSSFRow;

import org.apache.poi.hssf.usermodel.HSSFSheet;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
public class DB {
	private static Connection conn = null;
	private static String sql;
	private static PreparedStatement pstmt = null;
	private static Object[] obj = new Object[] { };
	private static ResultSet rs =null;
	private static Statement stmt;
	private ObservableList<ObservableList> data;
	static GUI gui = new GUI();
	static String id = "WarehouseDB";
	static HSSFRow row;
	static HSSFCell cell;
	final Runnable runnable = (Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.default");

	
	public static void init(){
	      
	      try {
	         //Class.forName("com.mysql.jdbc.Driver");
	    	  //Class.forName("org.gjt.mm.mysql.Driver");//my sql
	         //conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bmswh","LiamKwonnz","123456789");//my sql
	    	  Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");//ms sql
		      conn = DriverManager.getConnection("jdbc:sqlserver://BMSDC:1433;databaseName=STOCKLOCATOR;user=stocklocator;password=BMS1234");//ms sql
	    	  stmt = conn.createStatement();
	         	        
	      } catch(ClassNotFoundException ex) {
	         System.out.println("Couldn't load database driver: " + ex.getMessage());           
	      } catch(SQLException ex) {
	         System.out.println("SQLException caught: " + ex.getMessage());
	      } 
	   }
	
	
	
	public void SelectAllDb(TableView tableview, boolean counter){
		
		data = FXCollections.observableArrayList(); 
		sql= "select * from warehouse where BMSNO != ?";
        pstmt = null;
       
        //obj = new Object[] {BMS_NO,LOCATION,DATE};
        
                   
		 try {
		
			 pstmt = conn.prepareStatement(sql);
             pstmt.setString(1,id);
             rs = pstmt.executeQuery();
        
            
//        if(counter) {
//        for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
//            //We are using non property style for making dynamic table
//            final int j = i;                
//            TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
//            col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
//                public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {                                                                                              
//                    return new SimpleStringProperty(param.getValue().get(j).toString());                        
//                }                    
//            });
//
//            tableview.getColumns().addAll(col); 
//            System.out.println("Column ["+i+"] ");
//        }
//        }
//        /********************************
//         * Data added to ObservableList *
//         ********************************/
//        while(rs.next()){
//            //Iterate Row
//            ObservableList<String> row = FXCollections.observableArrayList();
//            for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
//                //Iterate Column
//                row.add(rs.getString(i));
//            }
//            System.out.println("Row [1] added "+row );
//            data.add(row);
//
//        }
//
//        //FINALLY ADDED TO TableView
//        tableview.setItems(data);
//        
        while(rs.next()){
           
            Product product = new Product();
        	
            	product.setBms(rs.getString("BMSNO"));
                product.setLocation(rs.getString("LOCATION"));
                product.setUser(rs.getString("USERS"));//ms sql
                //product.setUser(rs.getString("USER"));//my sql
                product.setDate(rs.getDate("DATE"));
               
                tableview.getItems().add(product);
            
            
        	
        }
        pstmt.close();
        rs.close();
     } catch(SQLException ex) {
        System.out.println("SQLException caught: " + ex.getMessage());
     }
	}

public void SelectDb(String textbox,TableView tableview,boolean bmsOrLocation){
	
	if(bmsOrLocation) {
		
	sql= "select * from warehouse where BMSNO = ? and BMSNO !=?";
	}
	else {
		sql= "select * from warehouse where LOCATION = ?";
	}
	
	pstmt = null;
            
		 try {
			 
			 pstmt = conn.prepareStatement(sql);
             pstmt.setString(1,textbox);
             if(bmsOrLocation)pstmt.setString(2,id);
             rs = pstmt.executeQuery();
        
        
        if(!rs.next()) {
        	BeepSound();
        }else {
        
        	do{
            
        		
        		Product product = new Product();
        		product.setBms(rs.getString("BMSNO"));
        		product.setLocation(rs.getString("LOCATION"));
        		product.setUser(rs.getString("USERS"));//ms sql
        		//product.setUser(rs.getString("USER"));//my sql
        		product.setDate(rs.getDate("DATE"));
        		
        		tableview.getItems().add(product);
            }while(rs.next());
        	runnable.run();
        }
        
        pstmt.close();
        rs.close();
     } catch(SQLException ex) {
        System.out.println("SQLException caught: " + ex.getMessage());
     }
	}
	
	
public void InsertDb(TableView tableview,String bms, String loca, String userName){
	
	
	//sql = "INSERT INTO warehouse VALUES(?, ?, ?,now()) ON DUPLICATE KEY UPDATE LOCATION = ?,USER = ?, DATE = now()";//my sql
	sql = "IF NOT EXISTS (SELECT * FROM warehouse WHERE BMSNO = ?) BEGIN INSERT INTO warehouse VALUES(?, ?, ?,getdate()) END ELSE BEGIN UPDATE warehouse set LOCATION = ?,USERS=?,DATE = getdate() WHERE BMSNO = ? END";//ms sql
    
	pstmt = null;
                                      
    //obj = new Object[] {bms,loca,userName,loca,userName};//my sql
    obj = new Object[] {bms,bms,loca,userName,loca,userName,bms};//ms sql
                   
		 try {
		
			 pstmt = conn.prepareStatement(sql);
            	 for(int i=0; i<obj.length; i++) {
            		 pstmt.setObject(i + 1, obj[i]);
            	 }
            	 

        
   	 if(pstmt.executeUpdate()==0) { BeepSound();} 
   	 
        pstmt.close();

     } catch(SQLException ex) {
        System.out.println("SQLException caught: " + ex.getMessage());
     }
	}

public void DeleteDb(TableView tableview,String txtbox, String userName){
	
	//String sqlHistory = "insert into history value(null,?,'DEL',?,now())";//my sql
	String sqlHistory = "insert into historyk values(?,'DEL',?,getdate())";//ms sql
	sql= "delete from warehouse where BMSNO = ?";
	 obj = new Object[] {txtbox,userName};
    pstmt = null;
             
		 try {
		
			 pstmt = conn.prepareStatement(sql);
             pstmt.setString(1,txtbox);
             pstmt.executeUpdate();
             
             pstmt = null;
             pstmt = conn.prepareStatement(sqlHistory);
        	 for(int i=0; i<obj.length; i++) {
        		 pstmt.setObject(i + 1, obj[i]);
        	 }
        	 
         
    if(pstmt.executeUpdate()==0) {BeepSound();}
    else runnable.run();
             
             pstmt.close();

     } catch(SQLException ex) {
        System.out.println("SQLException caught: " + ex.getMessage());
     }
	}

//public boolean DelCheck(String idtxt, String pwtxt) {
//	
//	sql= "select * from usertable where NAME = ?";
//	pstmt = null;
//    
//	 try {
//		 
//		 pstmt = conn.prepareStatement(sql);
//        pstmt.setString(1,idtxt);
//        rs = pstmt.executeQuery();
//   System.out.println("checking data after adding data"+idtxt);
//   while(rs.next()){
//      
//       System.out.println(rs.getString("NAME")+","+rs.getString("PWD"));
//	
//       if(rs.getString("PWD").equals(pwtxt)) {
//    	   DeleteAllDb();
//    	   return true;
//       }
//       
//  }
//   pstmt.close();
//   rs.close();
//} catch(SQLException ex) {
//   System.out.println("SQLException caught: " + ex.getMessage());
//}
//	 return false;
//}

public void DeleteAllDb(){
	
	sql= "delete from warehouse";
	//String sql2= "delete from history";//my sql
	String sql2= "delete from historyk";//ms sql
    pstmt = null;
             
		 try {
		
			 pstmt = conn.prepareStatement(sql);
             pstmt.executeUpdate();
             pstmt = conn.prepareStatement(sql2);
             pstmt.executeUpdate();
             pstmt.close();

     } catch(SQLException ex) {
        System.out.println("SQLException caught: " + ex.getMessage());
     }
		 
	}

public int locationSearchDB(String textbox,TableView tableview){
	
	int locationConter=0;
			
	
		sql= "select * from warehouse where LOCATION = ?";
	
	
	pstmt = null;
            
		 try {
			 
			 pstmt = conn.prepareStatement(sql);
             pstmt.setString(1,textbox);
             rs = pstmt.executeQuery();
        
        while(rs.next()){
           
        	locationConter++;
            
	   }
        pstmt.close();
        rs.close();
     } catch(SQLException ex) {
        System.out.println("SQLException caught: " + ex.getMessage());
     }
		 
	return locationConter;
	
}

public void UpdateDb(TableView tableview,String bms, String loca,String userName){
	
	sql = "UPDATE warehouse SET LOCATION = ?,USERS=?,DATE = getdate() WHERE BMSNO = ?";//ms sql
	//sql = "UPDATE warehouse SET LOCATION = ?,USER=?,DATE = now() WHERE BMSNO = ?";//my sql
	pstmt = null;
                                      
    obj = new Object[] {loca,userName,bms};
                   
		 try {
		
			 pstmt = conn.prepareStatement(sql);
             
			 for(int i=0; i<obj.length; i++) {
	                pstmt.setObject(i + 1, obj[i]);
	             }
			 if(pstmt.executeUpdate()==0) {BeepSound();}
			    else runnable.run();
             pstmt.close();

     } catch(SQLException ex) {
        System.out.println("SQLException caught: " + ex.getMessage());
     }
	}

public void SelectAllHistoryDb(TableView tableview){
	
	data = FXCollections.observableArrayList(); 
	//sql= "select * from history";//my sql
	sql= "select * from historyk";//ms sql
    pstmt = null;
   
             
	 try {
	
		 pstmt = conn.prepareStatement(sql);
         
         rs = pstmt.executeQuery();
    
         

    while(rs.next()){
       
        Product product = new Product();
    	
        	product.setBms(rs.getString("BMSNO"));
        	product.setLocation(rs.getString("LOCATION"));
        	//product.setUser(rs.getString("USER"));//my sql
        	product.setUser(rs.getString("USERS"));//ms sql
            product.setDate(rs.getDate("DATE"));
            tableview.getItems().add(product);
        
        
    	
    }
    pstmt.close();
    rs.close();
 } catch(SQLException ex) {
    System.out.println("SQLException caught: " + ex.getMessage());
 }
}

public void SelectHistoryDb(String textbox,TableView tableview){
	
	//sql= "select * from history where BMSNO = ?";//my sql
	sql= "select * from historyk where BMSNO = ?";//ms sql
	
	
	pstmt = null;
            
		 try {
			 
			 pstmt = conn.prepareStatement(sql);
             pstmt.setString(1,textbox);
             
             rs = pstmt.executeQuery();
        
        
        if(!rs.next()) {
        	BeepSound();
        }else {
        
        	do{
            
        		
        		Product product = new Product();
        		product.setBms(rs.getString("BMSNO"));
        		product.setLocation(rs.getString("LOCATION"));
        		//product.setUser(rs.getString("USER"));//my sql
        		product.setUser(rs.getString("USERS"));//ms sql
        		product.setDate(rs.getDate("DATE"));
        		tableview.getItems().add(product);
            }while(rs.next());
        	runnable.run();
        }
        
        pstmt.close();
        rs.close();
     } catch(SQLException ex) {
        System.out.println("SQLException caught: " + ex.getMessage());
     }
	}

public boolean LoginCheck(String idtxt, String pwtxt) {
	
	sql= "select * from usertable where NAME = ? and PWD = ?";
	pstmt = null;
    
	 try {
		 
		 pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,idtxt);
        pstmt.setString(2,pwtxt);
        rs = pstmt.executeQuery();
   
   while(rs.next()){
      
       if(rs.getString("NAME").equals(idtxt)&&rs.getString("PWD").equals(pwtxt)) {
    	   
    	   return true;
       }
       
  }
   pstmt.close();
   rs.close();
} catch(SQLException ex) {
   System.out.println("SQLException caught: " + ex.getMessage());
}
	 return false;
}


public void CreateExcel(){
	
	HSSFWorkbook workbook = new HSSFWorkbook();

    //Sheet

    HSSFSheet sheet = workbook.createSheet();
    sheet.setAutoFilter(CellRangeAddress.valueOf("A0:C0"));

    CellStyle style = workbook.createCellStyle();
    style.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	
	sql= "select * from warehouse where BMSNO != ?";
    pstmt = null;
   
    
               
	 try {
	
		 pstmt = conn.prepareStatement(sql);
         pstmt.setString(1,id);
         rs = pstmt.executeQuery();
    
         System.out.println("checking data after adding data");

         int i = 0;
         do {
        	 row = sheet.createRow(i);
        	 if(i==0) {
        		 row.createCell(0).setCellValue("BMSNO");
        		 row.createCell(1).setCellValue("Location");
        		 row.createCell(2).setCellValue("USER");
        		 row.createCell(3).setCellValue("date");

                 row.getCell(0).setCellStyle(style);
                 row.getCell(1).setCellStyle(style);
                 row.getCell(2).setCellStyle(style);
                 row.getCell(3).setCellStyle(style);
        	 }else {
        		 row.createCell(0).setCellValue(rs.getString("BMSNO"));
        		 row.createCell(1).setCellValue(rs.getString("LOCATION"));
        		 row.createCell(2).setCellValue(rs.getString("USERS"));//ms sql
        		 //row.createCell(2).setCellValue(rs.getString("USER"));//my sql
        		 row.createCell(3).setCellValue(rs.getString("DATE"));
        	 }
        		
        	 i++;
        	 
         }while(rs.next());
        
         FileOutputStream outFile;

        outFile = new FileOutputStream("StockLocationMaster.xls");

        workbook.write(outFile);

        outFile.close();
        runnable.run();
        pstmt.close();
        rs.close();
 } catch(SQLException ex) {
    System.out.println("SQLException caught: " + ex.getMessage());
 }catch (Exception e) {

     e.printStackTrace();

  }
}
	
public void BeepSound() {
  	Media sound = new Media(new File("/c:/beep/beepS.wav").toURI().toString());
  	MediaPlayer mediaPlayer = new MediaPlayer(sound);
  	mediaPlayer.play();
  }
}
