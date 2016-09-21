package uvmgen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * 
 * @author Pengcheng Yan
 * 
 * 1. new
 * 2. build phase :: default sequence
 * 3. build phase
 */
public class TestGen {
	private String name, fileName;
	
	Scanner scan = new Scanner(System.in);
	
	public TestGen(String name, String fileName) {
		this.name = name;
		this.fileName = fileName;
	}
	/**
	 * name
	 * build
	 */
	public void writeTest(){
		
		try {
			File f= new File(fileName + ".sv");
			FileWriter fw = new FileWriter(f);
			fw.write("`ifndef " + name.toUpperCase() + "__SV\n" );
			fw.write("`define " + name.toUpperCase() + "__SV\n" );
			fw.write("`uvm_component_utils(" + name + ")\n");
			addNewFunc(name, "null" ,fw);
			addBuildPhase(fw);
			fw.write("\n`endif\n");
			fw.close();
		} catch (IOException e) {
			
			e.printStackTrace();
			System.out.println("Failed to create ");
		}
	}
	
	private void addBuildPhase(FileWriter fw){
		try{
			fw.write("virtual function void build_phase (uvm_phase phase);\n");
			fw.write("\tsuper.build_phase(phase)\n");
			//sequenceName, sequencerPath
			String sqName, sqrPath;
			System.out.println("Please enter the sequncer UVM Path to run the Test:");
			sqrPath = scan.next();
			System.out.println("Please enter the sequnce name to run on the sequencer:");
			sqName = scan.next();
			fw.write(" uvm_config_db#(uvm_object_wrapper)::set(this, " + "\n\"" + sqrPath + "\", " + "\n\"default_sequence\", " + "\n" + sqName + "::type_id::get());");
			fw.write("endfunction\n");
		} catch (IOException e){
			e.printStackTrace();
			System.out.println("Failed to create Build Phase");
		}
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
	
	
}
