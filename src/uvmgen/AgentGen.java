package uvmgen;

import java.io.*;
import java.util.*;

/**
 * 
 * @author Pengcheng Yan
 * 
 * 1. components declare
 * 2. new
 * 3. build phase
 * 4.connect phase
 * 
 * step1: add sqr, driver, mon
 * step2: add port
 * step3: build
 * step4: connect
 * 
 * Different agent stands for different protocols
 * 
 */
public class AgentGen {
	
	private String name, fileName;
	private String drvType, drvName;
	private String monType, monName;
	private String sqrType, sqrName;
	private String transactionType;
	private String portType, portName;
	
	Scanner scan = new Scanner(System.in);
	
	public AgentGen(String name, String fileName) {
		this.name = name;
		this.fileName = fileName;
	}
	
	
	public void writeAgent(){
		
		try {
			
			System.out.println("Please enter the driver Type:");
			drvType = scan.next();
			System.out.println("Please enter the driver Name:");
			drvName = scan.next();
			System.out.println("Please enter the monitor Type:");
			monType = scan.next();
			System.out.println("Please enter the monitor Name:");
			monName = scan.next();
			System.out.println("Please enter the Sequencer Type:");
			sqrType = scan.next();
			System.out.println("Please enter the Sequencer Name:");
			sqrName = scan.next();
			System.out.println("Please enter the Transaction Type:");
			transactionType = scan.next();
			
			System.out.println("Please enter the Port Type:");
			portType = scan.next();
			System.out.println("Please enter the Port Name:");
			portName = scan.next();
			
			
			File f= new File(fileName + ".sv");
			FileWriter fw = new FileWriter(f);
			
			//fw.write();
			fw.write("`ifndef " + name.toUpperCase() + "__SV\n" );
			fw.write("`define " + name.toUpperCase() + "__SV\n" );
			
			fw.write("class " + name + " extends uvm_agent#;\n");
			fw.write("`uvm_component_utils(" + name + ")\n");
			
			addSpace(fw, 1);
			
			//declare components
			fw.write(drvType + drvName + ";\n");
			fw.write(monType + monName + ";\n");
			fw.write(sqrType + sqrName + ";\n");
			
			//declare ports
			fw.write(portType + "#(" + transactionType + ") " + portName + ";\n");
			
			//new function
			addNewFunc(fw);
			//build
			this.addBuildPhase(fw);
			//connect
			this.addConnectPhase(fw);
	
			fw.write("\nendclass");
			fw.write("\n`endif\n");
			fw.close();
			
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to create Agent ");

		}
	}
		
	
	
	private void addBuildPhase(FileWriter fw){
		try{
			fw.write("virtual function void build_phase (uvm_phase phase);\n");
			fw.write("\tsuper.build_phase(phase)\n");
			fw.write("\tif (is_active == UVM_ACTIVE) begin\n");
			//add active code
			fw.write("\t" + this.drvName + " = " + this.drvType + "::type_id::create(\"" + this.drvName + "\", this);\n");
			fw.write("\t" + this.sqrName + " = " + this.sqrType + "::type_id::create(\"" + this.sqrName + "\", this);\n");
			fw.write("\tend\n");
			//add passive code
			fw.write(this.monName + " = " + this.monType + "::type_id::create(\"" + this.monName + "\", this);\n");
			
			
			fw.write("endfunction\n");
		} catch (IOException e){
			e.printStackTrace();
			System.out.println("Failed to create Build Phase");
		}
	}
	
	private void addConnectPhase(FileWriter fw){
		try{
			fw.write("virtual function void connect_phase (uvm_phase phase);\n");
			fw.write("\tsuper.connect_phase(phase)\n");
			
			fw.write("\tif (is_active == UVM_ACTIVE) begin\n");
			//add active code
			fw.write(drvName + ".seq_item_port.connect(" + sqrName + ".seq_item_export);\n");
			fw.write("\tend\n");
			//add passive code
			//need to build links
			fw.write(portName + " = " + monName + "." + portName);
			fw.write("endfunction\n");
		} catch (IOException e){
			e.printStackTrace();
			System.out.println("Failed to create Connect Phase");
		}
	}
	
	
	//adding new function in the code
	private void addNewFunc(FileWriter fw){
		try {
			fw.write("function new (string name  , uvm_component parent);\n");
			fw.write("\tsuper.new(name, parent);\n");
			fw.write("endfunction\n");
			
		} catch (IOException e) {
			System.out.println("Failed to create new() function");
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
