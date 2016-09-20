package uvmgen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
	
	public AgentGen() {
		this.name = name;
		this.fileName = fileName;
	}
	
	
	public void writeAgent(){
		
		try {
			File f= new File(fileName + ".sv");
			FileWriter fw = new FileWriter(f);
			
			//fw.write();
			fw.write("`ifndef " + name.toUpperCase() + "__SV\n" );
			fw.write("`define " + name.toUpperCase() + "__SV\n" );
			
			fw.write("class " + name + " extends uvm_agent#;\n");
			//here to add driver body\
			addSpace(fw, 1);
			
			//declare components
			
			//declare ports
			
			//new function
			
			//build
			
			//connect
			
			
			fw.write("`uvm_component_utils(" + name + ")\n");
			
			
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
			fw.write("\tend\n");
			//add passive code
			
			
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
			fw.write("\tend\n");
			//add passive code
			
			fw.write("endfunction\n");
		} catch (IOException e){
			e.printStackTrace();
			System.out.println("Failed to create Connect Phase");
		}
	}
	
	
	//adding new function in the code
	private void addNewFun(FileWriter fw){
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
