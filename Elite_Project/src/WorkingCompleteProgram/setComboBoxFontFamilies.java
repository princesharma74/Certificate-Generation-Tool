package WorkingCompleteProgram;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.text.Font;

public class setComboBoxFontFamilies {
	private ObservableList<String> list1 = FXCollections.observableArrayList(Font.getFontNames());
	
	public ObservableList<String> setComboBox(){
		list1 =  FXCollections.observableArrayList(Font.getFontNames());
		return list1;
	}
	public int Font(String str) {
		if(str.equals("PLAIN")) 
			return 0;		
		if(str.equals("ITALIC"))
			return 2;
		if(str.equals("BOLD"))
			return 1;
		return -1;
	}
}
