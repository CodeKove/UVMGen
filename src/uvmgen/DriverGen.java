/**
 * @author Pengcheng Yan
 * ID: 009755798
 * UNIVERSITY: SJSU EE
 * EE 297B Project
 * This is the file for generating UVM driver. It includes most features that are needed to generate
 * an UVM Driver
 */
package uvmgen;
import java.io.*;
import java.util.*;

/**
 * 
 * 
 * @category  Driver
 * This is the document to describe the main functions of a driver 
 * and main elements that one driver should has.
 * 
 * Main elements:
 * A driver should get transactions from the sequencer and then 
 * drive the transaction on the low level interface.
 * In order to achieve this goal. A well built driver should have these elements:
 *  
 * 1. 	proper interface to connect with the DUT
 * 2.	new function
 * 3.	build phase
 * 4.	run phase
 * 5.	task to drive the transaction
 * 6.	factory register
 *
 */


/**
 * 
 * @ description
 * This class is used to generate a UVM driver to drive the signals
 *  
 */
public class DriverGen {
	//Name, Filename and Transaction type for the Driver
	/**
	 * name is the instance name of the component
	 * fileName is file name to store the code
	 * transactionType is the transaction type 
	 * interfaceType and interfaceName are type and name for the interface 
	 * 
	 */
	private String name;
	private String fileName;
	private String transactionType;
	private String interfaceType, interfaceName;
	
	
	
	//Scanner for input from the user
	
	Scanner drvScan = new Scanner(System.in);
	
	//constructor
	
	public DriverGen(String name, String fileName){
		this.name = name;
		this.fileName = fileName;
	}
	
	
	  ////////////////////////////////////////////
	 //   Remember add \n after each write     //
	////////////////////////////////////////////
	
	//function to write the driver
	public void writeDriver() throws IOException{
		try {
			File f= new File(fileName + ".sv");
			FileWriter fw = new FileWriter(f);
			
			
			System.out.println("Please enter the transaction type for driver: ");
			this.setTransactionType(drvScan.next());
			
			System.out.println("Please enter the interface type for driver: ");
			this.interfaceType = drvScan.next();
			
			System.out.println("Please enter the interface name for driver: ");
			this.interfaceName = drvScan.next();
			//drvScan.close();
			
			
			//fw.write();
			fw.write("`ifndef " + name.toUpperCase() + "__SV\n" );
			fw.write("`define " + name.toUpperCase() + "__SV\n" );
			
			fw.write("class " + name + " extends uvm_driver#(" + transactionType + ");\n");
			//here to add driver body
			fw.write("`uvm_component_utils(" + name + ")\n");
			addSpace(fw);
			this.addInterface(interfaceType, interfaceName, fw);
			addSpace(fw, 2);
			fw.write("`uvm_component_utils_begin(" + name + ")\n");
			addSpace(fw);
			fw.write("`uvm_component_utils_end\n");
			
			addSpace(fw);
			fw.write("//Change parent if needed !!!\n");
			this.addNewFunc(name, "null", fw);
			addSpace(fw, 3);
			this.addBuildPhase(fw);
			addSpace(fw, 3);
			this.addRunPhase(fw);
			addSpace(fw, 3);
			this.addDriveTask(fw);
			addSpace(fw, 3);
			
			fw.write("\nendclass");
			fw.write("\n`endif\n");
			fw.close();
		} catch(IOException e) {
			System.out.println("Failed to create a driver !!!");
		}
		
	}
	
	//set Transaction Type for the driver
	private void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
		
		
	//adding new function in the code
	private void addNewFunc(String name, String parent, FileWriter fw){
		try {
			fw.write("function new (string name = \"" + name + "\" , uvm_component parent = " + parent + ");\n");
			fw.write("\tsuper.new(name, parent);\n");
			fw.write("endfunction\n");
			
		} catch (IOException e) {
			System.out.println("Failed to create new() function");
		}
		
	}
	
	
	private void addInterface(String interfaceType, String interfaceName, FileWriter fw) {
		try {
			fw.write("virtual " + interfaceType  + " " + interfaceName + " ;\n" );
		} catch (IOException e) {
			System.out.println("Failed to create interface");
		}
	}

	//adding build phase
	private void addBuildPhase(FileWriter fw){

		try {
			fw.write("virtual function void build_phase (uvm_phase phase);\n");
			fw.write("\tsuper.build_phase(phase)\n");
			
			//several cases for loop needed
			fw.write("\tif(!uvm_config_db#(virtual " + interfaceType + ")::get(this, \"\"," + "\"" + interfaceName + "\"," + interfaceName + "))\n");
			fw.write("\t\t`uvm_fatal(\"" + name + "\", " + "\"virtual interface must be set for " + name + "!!!)\"\n" );
			///
			fw.write("//Write your config code here\n");
			fw.write("\t if(!uvm_config_db#()::get(this, \"\", \"\", ))\n");
			fw.write("\t\t`uvm_fatal(\"\", " + "\"\")\n" );
			
			
			fw.write("endfunction\n");
			
		} catch (IOException e) {
			System.out.println("Failed to create build_phase");
		}
		
	}
	//adding run phase
	private void addRunPhase(FileWriter fw){
		try {
			fw.write("virtual task run_phase (uvm_phase phase);\n");
			addSpace(fw, 1);
			fw.write("//ADD RESET LOGIC HERE\n");
			addSpace(fw, 1);
			fw.write("fork\n");
			fw.write("\twhile(1) begin\n");
			fw.write("\t\tseq_item_port.get_next_item(req);\n");
			fw.write("\t\tdrive_my_pkt(req);\n");
			fw.write("\t\t//ADD Your OWN CODE Here\n");
			fw.write("\t\tseq_item_port.item_done();\n");
			fw.write("\tend\n");
			fw.write("join\n");
			fw.write("endtask");
			
		} catch (IOException e) {
			System.out.println("Failed to create build phase");
		}
		
	}
	
	
	//adding your drive task
	
	private void addDriveTask(FileWriter fw) {
		try {
			fw.write("virtual task drive_my_pkt(" + transactionType + "tr" + ")\n");
			fw.write("`uvm_info(\"" + name + "\", " + "\"Begin to drive\", " + "UVM_LOW);\n");
			fw.write("//ADD YOUR OWN MAGIC DRIVE POWER HERE\n");
			addSpace(fw, 1);
			fw.write("`uvm_info(\"" + name + "\", " + "\"End to drive\", " + "UVM_LOW)\n");
			fw.write("endtask");
		} catch (IOException e) {
			System.out.println("Failed to create drive task");
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
	//adding one line of space
	private void addSpace(FileWriter fw) {
		try{
			for(int i = 0; i < 1; i ++) {
				fw.write("\n");
			}
		} catch (IOException e) {
			System.out.println("Add space failed");
		}
		
	}
	
}
