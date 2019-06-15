package basic_image_Edit;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class Testing {
	public static void main(String[] args) throws IOException {
	        try(
	                Reader reader = Files.newBufferedReader(Paths.get("C:/Users/Prince Sharma/Desktop/Extras/Elite Project/Book1.csv"));
	                CSVParser CSVParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader("Event", "Year", "Name", "Position", "Course").withSkipHeaderRecord().withIgnoreHeaderCase().withTrim());
	            ){
	        	
	        	int i = 0;
	                for(CSVRecord CSVRecord : CSVParser){
	        			BufferedImage bufferedImage = ImageIO.read(new File("C:/Users/Prince Sharma/Desktop/Extras/Elite Project/Sample_certi.jpg"));			
	        			Graphics graphics = bufferedImage.getGraphics();
	        			graphics.setColor(Color.BLACK);
	        			graphics.setFont(new Font("Microsoft JhengHei UI Light", Font.BOLD, 100));
	        			String key = CSVRecord.get("Name");
	        			graphics.drawString(key, 960, 600);
	        			String s = "C:/Users/Prince Sharma/Desktop/Output/out_"+(++i)+".jpg";
	        			File file = new File(s);
	        			ImageIO.write(bufferedImage, "jpg", file);
	        			System.out.println("Image Created");
	                }
	            }
	}
}
