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
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

import org.apache.commons.csv.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class Controller implements Initializable{
	@FXML private ImageView imageview; 
	@FXML private ComboBox<String> comboBox;
	@FXML private Label x; 
	@FXML private Label y;
	private int tempX, tempY; 
	private List<Integer> tempXarray = new ArrayList<>();
	private List<Integer> tempYarray = new ArrayList<>();
	private BufferedReader br;
	private int j = 0;
	private List<String> str = new ArrayList<>();
	private String strImage="", strCSV="";
	
	public void DrawCenterAlignedString(String key, int x, int y, Graphics graphics) {
		int stringLen = (int)graphics.getFontMetrics().getStringBounds(key, graphics).getWidth();
		int reduce = stringLen/2;
		x = x-reduce;
		graphics.drawString(key, x, y);
	}
	
	public BufferedImage ImageEdit(String header, BufferedImage bufferedImage, int x, int y, CSVRecord CSVRecord){
		Graphics graphics = bufferedImage.getGraphics();						
		        			graphics.setColor(Color.BLACK);
		        			graphics.setFont(new Font("Microsoft JhengHei UI Light", Font.BOLD, 100));//For Check 1
		        			//graphics.setFont(new Font("Calibri",Font.BOLD,  14));//For Check 2
		        			String key = CSVRecord.get(header);
		        			DrawCenterAlignedString(key, x, y, graphics);
		return bufferedImage;
	}
	public void GenerateFiles() throws IOException {
        try(
                Reader reader = Files.newBufferedReader(Paths.get(strCSV));
                CSVParser CSVParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
            ){
        	
        	int i = 0;
        	BufferedImage bufferedImage = ImageIO.read(new File(strImage));
                for(CSVRecord CSVRecord : CSVParser){	
        			for(int j = 0; j<str.size(); j++) {
        				bufferedImage = ImageEdit(str.get(j), bufferedImage, tempXarray.get(j), tempYarray.get(j), CSVRecord);
        			}
        			String s = "C:/Users/Prince Sharma/Desktop/Output/out_"+(++i)+".jpg";
        			File file = new File(s);
        			ImageIO.write(bufferedImage, "jpg", file);
        			System.out.println("Image Created");
        			
        			//to avoid overwriting the same edited image ---- renewing the bufferedImage for the next iteration.
        			bufferedImage = ImageIO.read(new File(strImage));
                }
            }
	}
	
	public String FileChooser() {
		String path = "";
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int returnValue = jfc.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION){
            File selectedFile = jfc.getSelectedFile();  
            path =  selectedFile.getAbsolutePath();
            
        }
        return path;
	}
	public void ImportCSV() {
		strCSV = FileChooser();
		ComboBoxSETTER();
	}
	
	public void SubmitButtonPressed(ActionEvent event) {		
		String SelectedHeader = comboBox.getValue();
		tempXarray.add(tempX);
		tempYarray.add(tempY);
		str.add(SelectedHeader);
		//this.x.setText(Integer.toString(tempX));
		//this.y.setText(Integer.toString(tempY));
		j++;
		System.out.println("Submit Button Pressed!-"+j+"\ntempX: "+tempX+"\ntempY: "+tempY+"\n");		
	}
	public void ShowButton(ActionEvent event) {
		for(int i =0; i<str.size(); i++) {
			System.out.println("Selected Header: "+str.get(i)+"  X-Coordinate: "+tempXarray.get(i)+"  Y-Coordinate: "+tempYarray.get(i));
		}
	}
	
	public void ImportButtonPressed(ActionEvent event){
		strImage = FileChooser();
		Image image = new Image("file:"+strImage);
		imageview.setImage(image);
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				int getX= (int)(e.getX()*(image.getHeight()/330));
				int getY= (int)(e.getY()*(image.getHeight()/330));
				if (getX>image.getWidth()) {
					getX = (int)image.getWidth();
				}
				else if(getY>image.getHeight()){
					getY = (int)image.getHeight();
				}
				tempX = getX;
				tempY = getY;
				x.setText(Integer.toString(tempX));
				y.setText(Integer.toString(tempY));
				System.out.println("["+getX+" , "+getY+"]");
				
			}
			
		};
		
		imageview.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
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
			// TODO Auto-generated catch block
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
	}
	
	
}
