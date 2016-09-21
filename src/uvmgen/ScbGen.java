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
	
	private List<String> portNameList = new ArrayList<>();
	private List<String> portTypeList = new ArrayList<>();
	private List<String> portTransTypeList = new ArrayList<>();
	
	
	//Scanner for input from the user
	
	Scanner scan = new Scanner(System.in);
	
	public ScbGen(String name, String fileName) {
		this.name = name;
		this.fileName = fileName;
	}
	
	public void writeScb(){
		setPort();
		try {
			File f= new File(fileName + ".sv");
			FileWriter fw = new FileWriter(f);
			
			fw.write("`ifndef " + name.toUpperCase() + "__SV\n" );
			fw.write("`define " + name.toUpperCase() + "__SV\n" );
			fw.write("class " + name + " extends uvm_scoreboard;\n");
			fw.write("`uvm_component_utils(" + name + ")\n");
			
			 for (int i = 0; i < portTypeList.size(); i++) {
				 fw.write(portTypeList.get(i) + " #(" + portTransTypeList.get(i) + ") " + portNameList.get(i) + ";\n");
			 }
			 
			 addNewFunc(name, "null", fw);
			 addSpace(fw, 1);
			 addBuildPhase(fw);
			 addSpace(fw, 1);
			 addRunPhase(fw);
			
			fw.write("\nendclass");
			fw.write("\n`endif\n");
			fw.close();
			
		} catch (IOException e) {
			
			e.printStackTrace();
			System.out.println("Failed to create ");
		}
	}
	
	
	private void setPort(){
		//first enter port type
		//second port transaction type
		//finally port name
		 while (true) {
	        	System.out.println("Please enter next port type (Q to quit):");
	        	String portType = scan.next();
	        	System.out.println("Please enter next port transaction type:");
	        	String portTransType = scan.next();
	        	System.out.println("Please enter next String:");
	        	String portName = scan.next();
	        	
	        	if (portType.equals("Q") || portType.equals("q")){
	        		break;
	        	} else {
	        		portTypeList.add(portType);
	        		portNameList.add(portName);
	        		portTransTypeList.add(portTransType);
	        		continue;
	        	}
	        }
		// for (int i = 0; i < portTypeList.size(); i++) {
			 //System.out.println(portTypeList.get(i) + " #(" + portTransTypeList.get(i) + ") " + portNameList.get(i) + ";\n");
		 //}
	}
	/**
	 * new function is OK
	 * @param name
	 * @param parent
	 * @param fw
	 */
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
			for (int i = 0; i < portNameList.size(); i++)
			fw.write(portNameList.get(i) + "= new(\"" + portNameList.get(i) + "\", this);\n");
			fw.write("endfunction");
		} catch(IOException e) {
			e.getStackTrace();
			System.out.print("Failed to create build phase");
		}
	}
	
	private void addRunPhase(FileWriter fw){
		try{
			fw.write("virtual task void run_phase(uvm_phase phase);\n");
			fw.write("\tsuper.run_phase(phase);\n");
			fw.write("\tfork");
			fw.write("//ADD Your code here");
			fw.write("\tjoin");
			fw.write("endtask");
		} catch(IOException e) {
			
		}
	}
	
	//adding lines of space in the code
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
