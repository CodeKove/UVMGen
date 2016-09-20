/**
 * Author Pengcheng Yan
 * ID: 009755798
 * UNIVERSITY: SJSU EE
 * EE 297B Project
 * This is the file for generating UVM driver. It includes most features that are needed to generate
 * an UVM Driver
 */
package uvmgen;
import java.io.*;
import java.util.*;

public class DriverGen {
	//assumption the name and transaction type will be given in the top
	private String name, fileName;
	private String transactionType;
	
	public DriverGen(String name, String fileName){
		this.name = name;
		this.fileName = fileName;
		//this.transactionType = transactionType;
	}
	
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	
	  ////////////////////////////////////////////
	 //   Remember add \n after each write     //
	////////////////////////////////////////////
	public void writeDriver() throws IOException{
		try {
			File fdr= new File(fileName + ".sv");
			FileWriter fw = new FileWriter(fdr);
			//fw.write();
			fw.write("`ifndef " + name.toUpperCase() + "__SV\n" );
			fw.write("`define " + name.toUpperCase() + "__SV\n" );
			fw.write("class " + name + " extends uvm_driver#(" + TransactionType + ");\n");
			//here to add driver body\
			addSpace(fw, 1);
			fw.write("virtual " + "interfaceType " + "instance name of interface\n" );
			addSpace(fw, 2);
			fw.write("`uvm_component_utils(" + name + ")\n");
			addSpace(fw, 3);
			addNewFun(name, "null", fw);
			addSpace(fw, 3);
			addBuildPhase(fw);
			addSpace(fw, 3);
			addMainPhase(fw);
			addSpace(fw, 3);
			
			
			fw.write("\nendclass");
			fw.write("\n`endif\n");
			fw.close();
		} catch(IOException e) {
			System.out.println("There is one File problem");
		}
		
	}
	//adding new function in the code
	private void addNewFun(String name, String parent, FileWriter fw){
		try {
			fw.write("function new (string name = \"" + name + "\" , uvm_component parent = " + parent + ");\n");
			fw.write("\tsuper.new(name, parent);\n");
			fw.write("endfunction\n");
			
		} catch (IOException e) {
			System.out.println("Can not add new function to File");
		}
		
	}

	//adding build phase
	private void addBuildPhase(FileWriter fw){

		try {
			fw.write("virtual function void build_phase (uvm_phase phase);\n");
			fw.write("super.build_phase(phase)\n");
			//several cases
			fw.write("\t!uvm_config_db#(virtual " + "intefaceType" + ")::get(this, \"\"," + "\"" + "vifname" + "\"" + "\"vifname\"\n" );
			fw.write("\t\t`uvm_fatal(\"name\"," + "\"message from the console\")\n");
			
			fw.write("endfunction\n");
		} catch (IOException e) {
			System.out.println("Failed to create build phase");
		}
		
	}
	//adding main phase
	private void addMainPhase(FileWriter fw){
		try {
			fw.write("virtual task main_phase (uvm_phase phase)\n");
			addSpace(fw, 1);
			fw.write("//ADD Your OWN CODE Here\n");
			addSpace(fw, 1);
			fw.write("while(1) begin\n");
			fw.write("\tseq_item_port.get_next_item(req);\n");
			fw.write("\tdrive_my_pkt(req);\n");
			fw.write("\tseq_item_port.item_donw();\n");
			fw.write("end\n");
			fw.write("endtask");
			
		} catch (IOException e) {
			System.out.println("Failed to create build phase");
		}
		
	}
	
	
	//adding 3 lines of space in the code
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

/*try {

} catch (IOException e) {
	System.out.println("Failed to create build phase");
}*/
