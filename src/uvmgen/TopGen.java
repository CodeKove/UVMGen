/**
 * Author Pengcheng Yan
 * ID: 009755798
 * UNIVERSITY: SJSU EE
 * EE 297B Project
 * This is the file for generating UVM monitor. It includes most features that are needed to generate
 * an UVM Monitor
 */
package uvmgen;
import java.io.*;
import java.util.*;
/**
 * 
 * @author Pengcheng Yan
 * 
 * 1. runtest("testcase")
 * 2. configure field
 *
 */
public class TopGen {
	private String name, fileName; 
	
	public TopGen(String name, String fileName) {
		this.name = name;
		this.fileName = fileName;
	}
	
	Scanner scan = new Scanner(System.in);
	
	public void writeTop() {
		try {
			File f= new File(fileName + ".sv");
			FileWriter fw = new FileWriter(f);
			//fw.write();
			fw.write("`ifndef " + name.toUpperCase() + "__SV\n" );
			fw.write("`define " + name.toUpperCase() + "__SV\n" );
			
			
			
			this.addSpace(fw, 3);
			fw.write("\n`endif\n");
			fw.close();
		} catch (IOException e) {
			System.out.println("There is an problem on creating Top");
		}
	}
	
	private void addConfig(FileWriter fw){
		try{
			
			
			fw.write("initial begin\n");
			fw.write("\tuvm_config_db#(" + dataType + ")::set( null, \"" + path + "\"," + "\"" + tag + "\","  + value);
			
			//fw.write("uvm_config_db#(" + dataType + ")::get( this, \"" + path + "\"," + "\"" + tag + "\","  + tag);
			//uvm_config_db#(virtual my_if)::set(null, "uvm_test_top.env.i_agt.drv", "vif", input_if);
			
			fw.write("end\n");
		} catch (IOException e){
			e.getStackTrace();
			System.out.println("Failed to add configure field");
		}
	}
	
	private void addRunTest(FileWriter fw){
		try{
			fw.write("initial begin\n");
			fw.write("\trun_test();\n");
			fw.write("end\n");
		} catch (IOException e){
			e.getStackTrace();
			System.out.println("Failed to add configure field");
		}
	}
	
	private void addSpace(FileWriter fw, int space) {
		try{
			for(int i = 0; i < space; i ++) {
				fw.write("\n");
			}
		} catch (IOException e) {
			System.out.println("Add space failed");
		}	
	}
	
}
