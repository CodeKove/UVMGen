package uvmgen;

import java.io.*;
import java.util.*;

/**
 * 
 * @author Pengcheng Yan
 * 
 * 1. transaction needed
 * 2. body task
 * 3. factory
 */
public class SqGen {
	private String name, fileName;
	private String transName, transType;
	
	Scanner scan = new Scanner(System.in);
	public SqGen(String name, String fileName) {
		this.name = name;
		this.fileName = fileName;
	}
	
	//String transType, String transName
	private void setTrans() {
		System.out.print("Please enter the transaction type for the sequence:");
		this.transType = scan.next();
		System.out.print("Please enter the transaction name for the sequence:");
		this.transName = scan.next();
		//scan.close();
	}
	
	public void writeSq() {
		
		try {
			File f= new File(fileName + ".sv");
			FileWriter fw = new FileWriter(f);
			
			setTrans();
			fw.write("`ifndef " + name.toUpperCase() + "__SV\n" );
			fw.write("`define " + name.toUpperCase() + "__SV\n" );
			
			fw.write("class " + name + " extends uvm_sequence#(" + transType + ");\n");
			fw.write("`uvm_object_utils(" + name + ")\n");
			addSpace(fw, 1);
			fw.write(transType + " " + transName + "\n");
			addSpace(fw, 1);
			this.addNewFunc(name, fw);
			addSpace(fw, 1);
			this.addBody(transName, fw);
			addSpace(fw, 1);
			fw.write("endclass");
			fw.write("\n`endif\n");
			fw.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void addBody(String transName, FileWriter fw){
		try {
			fw.write( "virtual task body();\n");
			fw.write("\tif(starting_phase != null)\n");
			fw.write("\t\tstarting_phase.raise_objection(this);\n");
			fw.write("\t\t//ADD YOUR CODE HERE\n");
			fw.write("\t\t`uvm_do(" + transName + ")\n");
			fw.write("\tif(starting_phase != null)\n");
			fw.write("\t\tstarting_phase.drop_objection(this);\n");
			fw.write("endtask\n");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to add body task");
		}
	}
	
	private void addBody2(String transName, FileWriter fw){
		try {
			fw.write("start_item(req);");
			fw.write("req.randomize() with { };");
			fw.write("finish_item(req);");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to add body task");
		}
	}
	//adding new function in the code
	private void addNewFunc(String name, FileWriter fw){
		try {
			fw.write("function new (string name = \"" + name + "\""+ ");\n");
			fw.write("\tsuper.new(name);\n");
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
