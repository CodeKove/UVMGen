

package uvmgen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 
 * @author Pengcheng Yan
 * 
 * 1. agent
 * 2. model
 * 3. scb
 * 4. fifo uvm_tlm_analysis_fifo #(my_transaction) agt_scb_fifo;
 * 5. new
 * 6. build phase
 * 7. connect phase
 * 
 */
public class EnvGen {
	private String name, fileName;
	
	public EnvGen() {
		this.name = name;
		this.fileName = fileName;
	}
	
	public void writeEnv(){
		
		try {
			File f= new File(fileName + ".sv");
			FileWriter fw = new FileWriter(f);
			//fw.write();
			fw.write("`ifndef " + name.toUpperCase() + "__SV\n" );
			fw.write("`define " + name.toUpperCase() + "__SV\n" );
			
			fw.write("class " + name + " extends uvm_env;\n");
			
			fw.write("`uvm_component_utils(" + name + ")\n");
			
			
			fw.write("\nendclass");
			fw.write("\n`endif\n");
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to create ");
		}
	}
	
	private void addBuildPhase(FileWriter fw){
		
	}
	
	private void addConnectPhase(FileWriter fw){
		
	}
	
	//adding new function in the code
	private void addNewFun(String name, String parent, FileWriter fw){
		try {
			fw.write("function new (string name = \"" + name + "\" , uvm_component parent = " + parent + ");\n");
			fw.write("\tsuper.new(name, parent);\n");
			fw.write("endfunction\n");
				
		} catch (IOException e) {
			System.out.println("Failed to create new() function");
		}
			
	}
}
