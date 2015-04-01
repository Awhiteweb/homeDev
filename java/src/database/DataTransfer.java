package database;

import java.io.File;

//import com.mysql.jdbc.Connection;
//import com.mysql.jdbc.Statement;

public class DataTransfer {

	public void main(String[] args) {

		File path = new File("/Volumes/Public/Shared Videos/Movie Archive/");
		
		File[] files = path.listFiles();
		
		for( File file : files ) {
			System.out.println( file.getName() );
		}
		
	}
	
	
	
	
}
