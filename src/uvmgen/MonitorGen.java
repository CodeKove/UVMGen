package uvmgen;

import java.io.*;
import java.util.*;
/**
 * 
 * @author Pengcheng Yan
 * 
 * A Monitor is used to sample the data on the interface and send it to analysis
 * component
 * The main elements for a monitor are:
 * 1. virtual interface
 * 2. analysis_port
 * 3.factory register
 * 4. new function
 * 5.build phase and task to pick the data
 * 
 * you can also have some fields and field automate them
 *
 */

public class MonitorGen {
	private String name, fileName;
	
	private String transactionType;
	//assume that monitor only has analysis ports
	private List<String> anaPortNameList = new ArrayList<String>();
	//private List<String> portTypeList = new ArrayList<String>();
	
	
	private String interfaceType, interfaceName;
	
	//Scanner for input from the user
	
	Scanner scan = new Scanner(System.in);
	
	public MonitorGen(String name, String fileName) {
		this.name = name;
		this.fileName = fileName;
	}
	
	
	
	public void writeMonitor() {
		try {
			File f= new File(fileName + ".sv");
			FileWriter fw = new FileWriter(f);
			
			/**
			 * get information needed for the monitor
			 */
			
			System.out.println("Please enter the transaction type for monitor: ");
			this.setTransactionType(scan.next());
			
			System.out.println("Please enter the interface type for monitor: ");
			this.interfaceType = scan.next();
			System.out.println("Please enter the interface name for monitor: ");
			this.interfaceName = scan.next();
		
			
			fw.write("`ifndef " + name.toUpperCase() + "__SV\n" );
			fw.write("`define " + name.toUpperCase() + "__SV\n" );
			fw.write("class " + name + " extends uvm_monitor;\n");
			addSpace(fw, 1);
			fw.write("`uvm_component_utils(" + name + ")\n");
		
			this.addInterface(interfaceType, interfaceName, fw);
			
			setPort(anaPortNameList);
			this.addPort(anaPortNameList, fw);
			addSpace(fw, 1);
			
			this.addCoverage(fw);
			addSpace(fw, 2);
			
			this.addNewFunc(name, "null", fw);
			addSpace(fw, 2);
			
			this.addBuildPhase(fw);
			addSpace(fw, 2);
			
			this.addRunPhase(fw);
			addSpace(fw, 3);
			
			this.addCollectTask(fw);
			addSpace(fw, 1);
			
			fw.write("\nendclass");
			fw.write("\n`endif\n");
			fw.close();	
		} catch (IOException e) {
			System.out.println("There is an problem on creating monitor");
		}
	}
	
	//set Transaction Type for the monitor
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
			System.out.println("Failed to add new function for monitor");
		}
		
	}

	//adding interface for the monitor
	private void addInterface(String interfaceType, String interfaceName, FileWriter fw) {
		try {
			fw.write("\tvirtual " + interfaceType  + " " + interfaceName + " ;\n" );
		} catch (IOException e) {
			System.out.println("Failed to create interface");
		}
	}
	//write ports
	
	private void setPort( List<String> anaPortNameList) {
		System.out.println("Please enter the TOTAL port number for this monitor (NUMBER ONLY!!!):");
		int n = scan.nextInt();
		for (int i = 0; i < n; i++) {
			System.out.println("Please enter the next port name for monitor: ");
			anaPortNameList.add(scan.next());	
		}
	}
	//adding analysis port
	private void addPort(List<String> anaPortNameList, FileWriter fw) {
		try {
			for(int i = 0; i < anaPortNameList.size(); i++) {
				fw.write("\tuvm_analysis_port #(" + this.transactionType + ") " + anaPortNameList.get(i) + ";\n" );
			}
		} catch (IOException e) {
			System.out.println("Failed to create interface");
		}
	}
	
	
	//adding build phase
	private void addBuildPhase(FileWriter fw){

		try {
			fw.write("virtual function void build_phase (uvm_phase phase);\n");
			fw.write("\tsuper.build_phase(phase)\n");
			//several cases
			fw.write("\tif(!uvm_config_db#(virtual " + interfaceType + ")::get(this, \"\"," + "\"" + interfaceName + "\"," + interfaceName + "))\n");
			fw.write("\t\t`uvm_fatal(\"" + name + "\", " + "\"virtual interface must be set for " + name + "!!!)\"\n" );
			
			for (int i = 0; i < this.anaPortNameList.size(); i++) {
				fw.write("\t" + this.anaPortNameList.get(i) + " = new(\"" + this.anaPortNameList.get(i) + "\" , this)\n" );
			}
			fw.write("endfunction\n");
		} catch (IOException e) {
			System.out.println("Failed to create build phase");
		}	
	}
	
	//adding main phase
	private void addRunPhase(FileWriter fw){
		try {
			fw.write("virtual task run_phase (uvm_phase phase)\n");
			addSpace(fw, 1);
			fw.write("//ADD Your OWN CODE Here\n");
			fw.write(this.transactionType + " tr;\n");
			addSpace(fw, 1);
			fw.write("fork\n");
			fw.write("while(1) begin\n");
			fw.write("\ttr = new(\"tr\");\n");
			fw.write("\tcollect_my_pkt(tr);\n");
			//need to implement collect_my_pkt and drive_my_pkt
			//fw.write("\t"+ portName+ ".write(tr);\n");
			fw.write("end\n");
			fw.write("join\n");
			this.addSpace(fw, 1);
			fw.write("endtask\n");
			
		} catch (IOException e) {
			System.out.println("Failed to create build phase");
		}
		
	}
	
	//adding your drive task
	
	private void addCollectTask(FileWriter fw) {
		try {
			fw.write("virtual task collect_my_pkt(" + transactionType + " tr" + ")\n");
			fw.write("`uvm_info(\"" + name + "\", " + "\"Begin to collect\", " + "UVM_LOW);\n");
			fw.write("//ADD YOUR OWN COLLECT CODE HERE\n");
			addSpace(fw, 1);
			fw.write("`uvm_info(\"" + name + "\", " + "\"End to collect\", " + "UVM_LOW)\n");
			fw.write("endtask\n");
		} catch (IOException e) {
			System.out.println("Failed to create drive task");
		}
	}
	
	
	private void addCoverage(FileWriter fw) {
		try {
			fw.write("covergroup;\n");
			fw.write("\tcoverpoint;\n");
			fw.write("endgroup;\n");
		} catch (IOException e){
			System.out.println("Failed to create coverage");
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
