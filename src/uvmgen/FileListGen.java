package uvmgen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 
 * @author Pengcheng Yan
 * 1. record filenames
 * 2. record connect info
 * 3. record objects info 
 *
 */

public class FileListGen {
	private String name, fileName;
	
	public FileListGen() {
		this.name = name;
		this.fileName = fileName;
	}
	
	public void writeFileList(){
		
		try {
			File f= new File(fileName + ".sv");
			FileWriter fw = new FileWriter(f);
		} catch (IOException e) {
			
			e.printStackTrace();
			System.out.println("Failed to create ");
		}
	}
	
}
