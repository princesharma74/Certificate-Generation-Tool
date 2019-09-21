package readCSVfile;

import java.io.*;
//import javax.swing.JFileChooser;
//import javax.swing.filechooser.FileSystemView;
import org.apache.commons.csv.*;
import java.nio.file.Files;
import java.nio.file.Paths;
public class readCSVfile
{
    private static String path = "";
   public static void main(String[] args) throws IOException{           
	   /*
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int returnValue = jfc.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION){
            File selectedFile = jfc.getSelectedFile();  
            path =  selectedFile.getAbsolutePath();
            System.out.println(path);
            
        }*/
	   path = "C:/Users/Prince Sharma/Desktop/hey.csv";
	   
        try(
            Reader reader = Files.newBufferedReader(Paths.get(path));
            CSVParser CSVParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader().withSkipHeaderRecord().withIgnoreHeaderCase().withTrim());
        ){        	
        	//System.out.println("Lasers");
            for(CSVRecord CSVRecord : CSVParser){
            	String Email = CSVRecord.get("Email1");
            		System.out.println(Email);
            	}          	
              }
        }
    }


