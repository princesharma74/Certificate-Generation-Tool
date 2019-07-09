package readCSVfile;

import java.io.*;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
import org.apache.commons.csv.*;
import java.nio.file.Files;
import java.nio.file.Paths;
public class readCSVfile
{
    private static String path = "";
    public static void main(String[] args) throws IOException{                      
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int returnValue = jfc.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION){
            File selectedFile = jfc.getSelectedFile();  
            path =  selectedFile.getAbsolutePath();
            
        }
        try(
            Reader reader = Files.newBufferedReader(Paths.get(path));
            CSVParser CSVParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader().withSkipHeaderRecord().withIgnoreHeaderCase().withTrim());
        ){
            for(CSVRecord CSVRecord : CSVParser){
                String Event = CSVRecord.get("Event");
                String Year = CSVRecord.get("Year");
                String Name = CSVRecord.get("Name");
                String Position = CSVRecord.get("Position");                
                String Course = CSVRecord.get("Course");
                System.out.println("This certificate is awarded to "+Name+" of "+Course+" for securing "+Position+" in the "+Event+" of ANDC FEST "+ Year+".\n");
            }
        }
    }    
    
}
