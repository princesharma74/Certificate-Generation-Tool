package WorkingCompleteProgram;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import org.apache.commons.csv.*;

import WorkingCompleteProgram.ImageViewer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
//import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
//import javafx.scene.Node;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
//import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Controller implements Initializable{
	@FXML private ImageView imageview, imageview1; 
	@FXML private ComboBox<String> comboBox;
	@FXML private ComboBox<String> Fonts;
	@FXML private ComboBox<String> Weight;
	@FXML private ComboBox<Integer> size;
	@FXML private Label x; 
	@FXML private Label y;
	private List<Integer> tempXarray = new ArrayList<>();
	private List<Integer> tempYarray = new ArrayList<>();
	private List<Integer> fontsize = new ArrayList<>();
	private List<Integer> style = new ArrayList<>();
	private List<String> Font = new ArrayList<>();
	private BufferedReader br;
	private int j = 0;
	private List<String> str = new ArrayList<>();
	private String strImage="", strCSV="";
	Integer size1[] = {8,9,10,11,12,14,16,18,20,22,24,26,28,36,48,72};
	ImageViewer img = new ImageViewer();
	String filename = "";
	
	public void DrawCenterAlignedString(String key, int x, int y, Graphics graphics) {
		int stringLen = (int)graphics.getFontMetrics().getStringBounds(key, graphics).getWidth();
		int reduce = stringLen/2; //alignment
		x = x-reduce;
		graphics.drawString(key, x, y);
	}
	
	//PreviewController pc = new PreviewController();
	
	/*
	public void PreviewButtonPressed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Preview.fxml"));
        Parent tableViewParent = loader.load();
        
        Scene tableViewScene = new Scene(tableViewParent);
        
        //access the controller and call a method
        PreviewController pcontroller = loader.getController();
        
        //creating an edited image
        BufferedImage bufferedImage = ImageIO.read(new File(strImage));
        Graphics graphics = bufferedImage.getGraphics();
		graphics.setColor(Color.BLACK);
		String str = Weight.getValue();		        			
		int w = set.Font(str); 
		Font font = new Font(Fonts.getValue(), w, (Integer) size.getValue());
		graphics.setFont(font);
		String key = "Sample Text";
		Point2D point = img.GetThePoint();
		int X = (int)point.getX();
		int Y = (int)point.getY();
		
		DrawCenterAlignedString(key, X, Y, graphics);
		
        Image image1 = SwingFXUtils.toFXImage(bufferedImage, null);
        
        pcontroller.ImageViewSETTER(image1);
        
        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(tableViewScene);
        window.show();
	}
	*/
	
	setComboBoxFontFamilies set = new setComboBoxFontFamilies();
	
	public BufferedImage ImageEdit(String header, BufferedImage bufferedImage, int x, int y, CSVRecord CSVRecord, String font, int style, int fontSize){
		Graphics graphics = bufferedImage.getGraphics();						
		        			graphics.setColor(Color.BLACK);
		        			Font font1 = new Font(font, style, fontSize);
		        			graphics.setFont(font1);
		        			String key = CSVRecord.get(header);
		        			DrawCenterAlignedString(key, x, y, graphics);
		return bufferedImage;
	}
	public void GenerateFiles() throws IOException {
        try(
                Reader reader = Files.newBufferedReader(Paths.get(strCSV));
                CSVParser CSVParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
            ){
        	String str1 = savebuttonpressed(null);
        	int i = 0;
        	BufferedImage bufferedImage = ImageIO.read(new File(strImage));
                for(CSVRecord CSVRecord : CSVParser){	
        			for(int j = 0; j<str.size(); j++) {
        				bufferedImage = ImageEdit(str.get(j), bufferedImage, tempXarray.get(j), tempYarray.get(j), CSVRecord, Font.get(j), style.get(j), fontsize.get(j));        			
        			}
        			
        			String s = str1+(++i)+".jpg";
        			File file = new File(s);
        			ImageIO.write(bufferedImage, "jpg", file);
        			System.out.println("Image Created");
        			
        			//to avoid overwriting the same edited image ---- renewing the bufferedImage for the next iteration.
        			bufferedImage = ImageIO.read(new File(strImage));
                }
            }
	}
	
	
	
	public String FileChooser1(Stage stage) {
		String path = "";
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extfilter1 = new FileChooser.ExtensionFilter("CSV Files","*.csv");
		FileChooser.ExtensionFilter extfilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg","*.png");
		fileChooser.getExtensionFilters().add(extfilter);
		fileChooser.getExtensionFilters().add(extfilter1);
		File selectedFile = fileChooser.showOpenDialog(stage);
		path = selectedFile.getAbsolutePath();
		return path;
	}
	
	
    public String savebuttonpressed(Stage stage) {
    	FileChooser fileChooser = new FileChooser();
        //Set extension filter
        //FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
        //FileChooser.ExtensionFilter extFilter1 = new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG");
        //fileChooser.getExtensionFilters().add(extFilter1);
        //fileChooser.getExtensionFilters().add(extFilter);
        //Show save file dialog
        ///fileChooser.setInitialFileName("Record");
        File file = fileChooser.showSaveDialog(stage);
        filename=file.getName();
        String savepatth=file.getPath();
        System.out.println(savepatth);
     
        return savepatth;
    }
	
	public void ImportCSV() {
		strCSV = FileChooser1(null);
		ComboBoxSETTER();
	}
	
	public void SubmitButtonPressed(ActionEvent event) {		
		String SelectedHeader = comboBox.getValue();
		str.add(SelectedHeader);
		//this.x.setText(Integer.toString(tempX));
		//this.y.setText(Integer.toString(tempY));
		j++;
		Point2D point = img.GetThePoint();
		int X = (int)point.getX();
		int Y = (int)point.getY();
		tempXarray.add(X);
		tempYarray.add(Y);
		
		String Fontsvalue = "";
		
		if(!Fonts.getSelectionModel().isEmpty()) {
			Fontsvalue = Fonts.getValue();
		}
		else {
			Fontsvalue = "Calibri";
		}
		Font.add(Fontsvalue);
		
		int SIZE = 28;
		if(!size.getSelectionModel().isEmpty()) {
			SIZE = size.getValue();
		}
		else {
			SIZE = 28;
		}
		fontsize.add(SIZE);
		
		int weight = 0; 
		if(!Weight.getSelectionModel().isEmpty()) {
			weight = set.Font(Fonts.getValue());
		}
		else {
			weight = 0;
		}
		style.add(weight);
		
		System.out.println("Submit Button Pressed!-"+j+"\ntempX: "+X+"\ntempY: "+Y+"\n");		
	}
	/*
	public void ShowButton(ActionEvent event) {
		for(int i =0; i<str.size(); i++) {
			System.out.println("Selected Header: "+str.get(i)+"  X-Coordinate: "+tempXarray.get(i)+"  Y-Coordinate: "+tempYarray.get(i) +" Font: "+Font.get(i)+" Style: "+style.get(i)+" FontSize: "+fontsize.get(i));
		}
	}
	*/
	public void ImportButtonPressed(ActionEvent event){
		strImage = FileChooser1(null);
		//System.out.println(strImage);
		img.ImageViewSETTER(imageview);
		img.GetImageCoordinates(strImage);
		img.LabelSETTER(x, y);
	}
	
	public void ComboBoxSETTER() {
		// TODO Auto-generated method stub
		String columns[] = null;
		String header = null;
		try {
			br = new BufferedReader(new FileReader(strCSV));
			
			try {
				header = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			columns=header.split(",");
			columns[0]=columns[0].replace("﻿","");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		for(int i = 0; i<columns.length; i++) {
			comboBox.getItems().add(columns[i]);
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		x.setText("");
		y.setText("");		
		comboBox.setPromptText("Choose Header");		
	    String weight[] = {"BOLD", "ITALIC", "PLAIN"}; 


	    ObservableList<String> list =  FXCollections.observableArrayList(weight);
	    setComboBoxFontFamilies set = new setComboBoxFontFamilies();	    
	    ObservableList<String> list1 = set.setComboBox();
	    ObservableList<Integer> list2 =  FXCollections.observableArrayList(size1);
	    
	    Fonts.setItems(list1);
	    Weight.setItems(list);
	    size.setItems(list2);
	    
	}
	
	
}
