/**
 * @author Pengcheng Yan
 * ID: 009755798
 * UNIVERSITY: SJSU EE
 * EE 297B Project
 * This is the file for generating UVM BaseTest. It includes most features that are needed to generate a Base Test
 * 
 * Base test:
 *
 * Fields:
 * 
 * Functions:
 * 
 * Includes:
 */
package uvmgen;

import java.io.*;
import java.util.Scanner;


/**
 * 
 *1.declare env
 *2.new function
 *3.build phase
 *4.report phase
 *5.factory
 *
 */
public class BaseTestGen {
	private String name, fileName;
	private String envName, envType;
	
	//Scanner for input from the user
	
	Scanner scan = new Scanner(System.in);
	
	public BaseTestGen(String name, String fileName) {
		this.name = name;
		this.fileName = fileName;
	}
	
	public void writeBaseTest(){
		try {
			File f= new File(fileName + ".sv");
			FileWriter fw = new FileWriter(f);
				
			System.out.println("Please enter the Environment type for Test: ");
			this.envType = scan.next();
			
			System.out.println("Please enter the Environment name for Test: ");
			this.envName = scan.next();
			//fw.write();
			fw.write("`ifndef " + name.toUpperCase() + "__SV\n" );
			fw.write("`define " + name.toUpperCase() + "__SV\n" );
			
			fw.write("class " + name + " extends uvm_test;\n");
			
			fw.write("`uvm_component_utils(" + name + ")\n");
			addSpace(fw,1);
			this.addNewFunc(fileName, "null", fw);
			addSpace(fw, 1);
			this.addBuildPhase(fw);
			addSpace(fw, 1);
			this.addReportPhase(fw);
			fw.write("\nendclass");
			fw.write("\n`endif\n");
			fw.close();
		} catch(IOException e ) {
			System.out.println("Failed to create Base Test !!!");
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
	
	private void addBuildPhase(FileWriter fw){
		try{
			fw.write("virtual function void build_phase (uvm_phase phase);\n");
			fw.write("\tsuper.build_phase(phase)\n");
			fw.write("envName = " + envType + "::type_id::create(\"" + envName + "\", this);\n");
			fw.write("endfunction\n");
		} catch (IOException e){
			e.printStackTrace();
			System.out.println("Failed to create Build Phase");
		}
	}
	
	private void addReportPhase(FileWriter fw){
		try{
			fw.write("virtual function void report_phase (uvm_phase phase);\n");
			fw.write("uvm_report_server server;");
			fw.write("int err_num;");
			fw.write("\tsuper.report_phase(phase)\n");
			fw.write( "\tserver = get_report_server();\n");
			fw.write("\terr_num = server.get_severity_count(UVM_ERROR);\n");
			fw.write("\tif (err_num != 0) begin\n");
			fw.write("\t\t$display(\"TEST CASE FAILED\");\n");
			fw.write("\tend\n");
			fw.write("\telse begin\n");
			fw.write("\t\t $display(\"TEST CASE PASSED\");\n");
			fw.write("\tend\n");
			fw.write("//More code from you !!!");
			fw.write("endfunction\n");
		} catch (IOException e){
			e.printStackTrace();
			System.out.println("Failed to create Report Phase");
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
