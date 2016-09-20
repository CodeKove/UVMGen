package uvmgen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 
 * @author Pengcheng Yan
 * Interface is very important in a design
 * It should contain elements below:
 * 
 *1. signals needed
 *2. functions and tasks
 *3. clocking blocks
 *4. modports
 *5. some variables
 *
 */
public class InterfaceGen {
	private String name, fileName;
	
	public InterfaceGen() {
		this.name = name;
		this.fileName = fileName;
	}

	public void writeInterface(){
		
		try {
			File f= new File(fileName + ".sv");
			FileWriter fw = new FileWriter(f);
		} catch (IOException e) {
			
			e.printStackTrace();
			System.out.println("Failed to create ");
		}
	}
	
}
