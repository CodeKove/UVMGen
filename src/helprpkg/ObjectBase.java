package helprpkg;

import java.util.*;
import java.io.*;

public class ObjectBase {
	private String name;
	private String fileName;
	private String transactionType;
	private String interfaceType, interfaceName;
	
	Scanner scan = new Scanner(System.in);
	
	public ObjectBase(String name, String fileName){
		this.name = name;
		this.fileName = fileName;
	}
	
	
	
	
	//adding new function in the code
	private void addNewFunc(String name, String parent, FileWriter fw){
		try {
			fw.write("function new (string name = \"" + name + ");\n");
			fw.write("\tsuper.new(name, parent);\n");
			fw.write("endfunction\n");
			
		} catch (IOException e) {
			System.out.println("Failed to create new() function");
		}
	}
	
	
	
	//adding build phase
		private void addBuildPhase(FileWriter fw){

			try {
				fw.write("virtual function void build_phase (uvm_phase phase);\n");
				fw.write("\tsuper.build_phase(phase)\n");
				fw.write("endfunction\n");
				
			} catch (IOException e) {
				System.out.println("Failed to create build_phase");
			}
			
		}
		
		//adding run phase
		private void addRunPhase(FileWriter fw){
			try {
				fw.write("virtual task run_phase (uvm_phase phase);\n");
				fw.write("endtask");
				
			} catch (IOException e) {
				System.out.println("Failed to create build phase");
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
