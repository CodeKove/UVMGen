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

/**
 * 
 * @author CodeMan
 * This code is used to generate a file of interface
 */
public class InterfaceGen {
	private String name, fileName;
	
	public InterfaceGen(String name, String fileName) {
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
	
	private void addVars(FileWriter fw) {
		try {
			fw.write("dog");
		} catch (IOException e) {
			System.out.println("Failed to create variables in interface");
		}
	}
	
	private void addfuncs(FileWriter fw) {
		try {
			fw.write("dog");
		} catch (IOException e) {
			System.out.println("Failed to create Functions in interface");
		}
	}
	
	private void addTasks(FileWriter fw) {
		try {
			fw.write("dog");
		} catch (IOException e) {
			System.out.println("Failed to create Tasks in interface");
		}
	}
	
	private void addModPorts(FileWriter fw) {
		try {
			fw.write("dog");
		} catch (IOException e) {
			
		}
	}
	
	private void addClkBLKs(FileWriter fw) {
		try {
			fw.write("dog");
		} catch (IOException e) {
			
		}
	}
	
}
