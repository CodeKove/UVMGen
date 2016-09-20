package uvmgen;

import java.io.*;
import java.util.*;



/**
 * 
 * @author Pengcheng Yan
 * 
 * 1. ports uvm_blocking_get_port #(my_transaction)  act_port;
 * 2. trans
 * 3. factory
 * 4. new
 * 5. build phase
 * 6. main_phase or run phase
 *
 */
public class ScbGen {
	private String name, fileName;

	public ScbGen() {
		
		this.name = name;
		this.fileName = fileName;
	}
	
	public void writeScb(){
		
		try {
			File f= new File(fileName + ".sv");
			FileWriter fw = new FileWriter(f);
		} catch (IOException e) {
			
			e.printStackTrace();
			System.out.println("Failed to create ");
		}
	}
	
	private void addNewFunc(String name, String parent, FileWriter fw){
		try {
			fw.write("function new (string name = \"" + name + "\" , uvm_component parent = " + parent + ");\n");
			fw.write("\tsuper.new(name, parent);\n");
			fw.write("endfunction\n");
			
		} catch (IOException e) {
			System.out.println("Failed to add new function for monitor");
		}
	}
	
	private void addBuildPhase(FileWriter fw){
		try{
			fw.write("virtual function void build_phase(uvm_phase phase);\n");
			fw.write("\tsuper.build_phase(phase);\n");
			for (int i = 0; i < portNameList.length; i++)
			fw.write(portNameList[i] + "= new(\"" + portNameList[i] = "\", this);\n");
			fw.write("endfunction");
		} catch(IOException e) {
			e.getStackTrace();
			System.out.print("Failed to create build phase");
		}
	}
	
	BigInteger big = new BigInteger();
	private void addRunPhase(FileWriter fw){
		try{
			fw.write("virtual task void run_phase(uvm_phase phase);\n");
			fw.write("\tsuper.run_phase(phase);\n");
			fw.write("endtask");
		} catch(IOException e) {
			
		}
	}
	
	
}
