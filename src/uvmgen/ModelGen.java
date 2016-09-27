package uvmgen;

import java.io.*;
import java.util.*;

public class ModelGen {
	private String name, fileName;
	private List<String> portNameList = new ArrayList<>();
	private List<String> portTypeList = new ArrayList<>();
	private List<String> portTransTypeList = new ArrayList<>();
	
	//Scanner for input from the user
	
	Scanner scan = new Scanner(System.in);
	
	public ModelGen(String name, String fileName) {
		this.name = name;
		this.fileName = fileName;
	}
	
	public void writeModel(){
		setPort();
		try {
			File f= new File(fileName + ".sv");
			FileWriter fw = new FileWriter(f);
			
			fw.write("`ifndef " + name.toUpperCase() + "__SV\n" );
			fw.write("`define " + name.toUpperCase() + "__SV\n" );
			fw.write("class " + name + " extends uvm_scoreboard;\n");
			fw.write("\t`uvm_component_utils(" + name + ")\n");
			
			 for (int i = 0; i < portTypeList.size(); i++) {
				 fw.write("\t" + portTypeList.get(i) + " #(" + portTransTypeList.get(i) + ") " + portNameList.get(i) + ";\n");
			 }
			 
			 addSpace(fw, 1);
			 addNewFunc(fw);
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
			 
			 	System.out.println("Please set the next port for Reference Models " + this.name + "(Press Q to quit S to Start):");
			 	String a = scan.next();
	        	if (a.equals("Q") || a.equals("q")){
	        		break;
	        	} else {
	        		System.out.println("Please enter next port type (Q to quit):");
	        		portTypeList.add(scan.next());
		        	System.out.println("Please enter next port transaction type:");
		        	portTransTypeList.add(scan.next());		        	
		        	System.out.println("Please enter next port name:");
		        	portNameList.add(scan.next());
	        		continue;
	        	}
	        }
	}
	
	private void addNewFunc(FileWriter fw){
		try {
			fw.write("function new (string name , uvm_component parent);\n");
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
			for (int i = 0; i < portNameList.size(); i++){
				fw.write("\t" + portNameList.get(i) + "= new(\"" + portNameList.get(i) + "\", this);\n");
			}
			fw.write("endfunction\n");
		} catch(IOException e) {
			e.getStackTrace();
			System.out.print("Failed to create build phase");
		}
	}
	
	private void addRunPhase(FileWriter fw){
		try{
			fw.write("virtual task run_phase(uvm_phase phase);\n");
			fw.write("\tsuper.run_phase(phase);\n");
			fw.write("\tfork\n");
			fw.write("//ADD Your code here\n");
			fw.write("\tjoin\n");
			fw.write("endtask\n");
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
