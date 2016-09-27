/**
 * @Author Pengcheng Yan
 * ID: 009755798
 * UNIVERSITY: SJSU EE
 * EE 297B Project
 * This is the file for generating UVM transaction. It generates the type of transaction which is
 *  required by the user
 * 
 * one transaction needs:
 * 1. data field
 * 2.constraints
 * 3.some functions
 * 4. auto field
 * 5.new function
 */

package uvmgen;

import java.util.*;
import java.io.*;

/**
 * 
 * uvm_sequence_item
 * 
 * 1. rand data
 * 2. not rand data
 * 3. constrains
 * 4. functions
 * 5. automation field
 * 6. new function
 * 
 *
 */
public class TransGen {
	private String name, fileName;
	
	private List<String> randVarsName = new ArrayList<String>();
	private List<String> randVarsType = new ArrayList<String>();
	
	private List<String> nonRandVarsName = new ArrayList<String>();
	private List<String> nonRandVarsType = new ArrayList<String>();
	
	private List<String> consName = new ArrayList<String>();
	
	private List<String> funcsName = new ArrayList<String>();
	private List<String> funcsType = new ArrayList<String>();
	
	//Scanner for input from the user
	
	Scanner scan = new Scanner(System.in);
	
	//constructor
	public TransGen(String name, String fileName) {
		this.name = name;
		this.fileName = fileName;
	}
	/**
	 * This is the function to write Transaction to a file
	 */
	public void writeTrans() {
		try {
			File f= new File(fileName + ".sv");
			FileWriter fw = new FileWriter(f);
			
			
			//fw.write();
			fw.write("`ifndef " + name.toUpperCase() + "__SV\n" );
			fw.write("`define " + name.toUpperCase() + "__SV\n" );
			this.addSpace(fw, 1);
			fw.write("class " + name + " extends uvm_sequence_item;\n");
			this.addSpace(fw, 1);
			this.addNewFun(name, fw);
			this.addSpace(fw, 1);
			//add random variables
			System.out.println("How Many rand variables do you want to create?");
			System.out.println("Please enter the number:");
			int n = scan.nextInt();
			
			if (n != 0) {
				for (int i = 0; i < n; i ++){
					System.out.print("Please enter the data type of the No." + (i+1) + " rand variable: ");
					this.randVarsType.add(scan.next());
					System.out.println();
					System.out.print("Please enter the data name for it: ");
					this.randVarsName.add(scan.next());	
				}
				System.out.println("Finish rand variable create");
			} else {
				System.out.println("Finish rand variable create");
			}
			
			this.addRandVars(randVarsName, randVarsType, fw);
			
			
			
			//add none random variables
			
			System.out.println("How Many  Nonrand variables do you want to create?");
			System.out.println("Please enter the number:");
			n = scan.nextInt();
			
			if (n != 0) {
				for (int i = 0; i < n; i ++){
					System.out.print("Please enter the data type of the No." + (i+1) + " Nonrand variable: ");
					this.nonRandVarsType.add(scan.next());
					System.out.println();
					System.out.print("Please enter the data name for it: ");
					this.nonRandVarsName.add(scan.next());	
				}
				System.out.println("Finish non rand variable create");
			} else {
				System.out.println("Finish non rand variable create");
			}
			addSpace(fw, 1);
			this.addNonRandVars(nonRandVarsName, nonRandVarsType, fw);
			
			//add field automation
			fw.write("`uvm_object_utils_begin(" + name + ")\n");
			fw.write("\t//`uvm_field_" + "int(" + "name, " + "UVM_ALL_ON)\n");
			fw.write("`uvm_object_utils_end\n");
			
			
			//add constrains
			System.out.println("How Many constrains do you want to create?");
			System.out.println("Please enter the number:");
			n = scan.nextInt();
			
			if (n != 0) {
				for (int i = 0; i < n; i ++){
					System.out.println("Please enter the name for No." + (i+1) +  "constraint:");
					this.consName.add(scan.next());	
				}
				System.out.println("Finish constraints create");
			} else {
				System.out.println("Finish constraints create");
			}
			
			this.addCons(consName, fw);
			
			
			
			
			//add  functions
			
			System.out.println("How Many functions do you want to create?");
			System.out.println("Please enter the number:");
			n = scan.nextInt();
			
			if (n != 0) {
				for (int i = 0; i < n; i ++){
					System.out.println("Please enter the name for No." + (i+1) +  "function:");
					this.funcsName.add(scan.next());	
					System.out.println();
					System.out.print("Please enter the return type for it: ");
					this.funcsType.add(scan.next());
				}
				System.out.println("Finish functions create");
			} else {
				System.out.println("Finish functions create");
			}
			
			this.addFuncs(funcsName, funcsType, fw);
			
			
			

		
			fw.write("\nendclass");
			this.addSpace(fw);
			fw.write("\n`endif\n");
			fw.close();
			
		} catch (IOException e) {
			
		}
	}
	
	
	/**
	 * 
	 * @param nonRandVarsName
	 * @param randvarsType
	 * @param fw
	 * 
	 * This is function is used to create list of Non rand variables according to their type and name 
	 */
	
	private void addNonRandVars(List<String> nonRandVarsName, List<String> nonRandVarsType, FileWriter fw){	
		for (int i = 0; i <nonRandVarsName.size(); i++) {
			try {
				fw.write("\t" + nonRandVarsType.get(i) + "\t" + nonRandVarsName.get(i) + ";\n" );
			} catch(IOException e) {
				System.out.println("Failed to create NonRand variable field");
			}
		}
	}
	
	/**
	 * 
	 * @param randvarsName
	 * @param randvarsType
	 * @param fw
	 * This is function is used to create list of rand variables according to their type and name
	 */
	
	private void addRandVars(List<String> randVarsName, List<String> randVarsType, FileWriter fw){
	
		for (int i = 0; i < randVarsName.size(); i++) {
			try {
				fw.write("\trand " + randVarsType.get(i) + "\t" + randVarsName.get(i) + ";\n" );

			} catch(IOException e) {
				System.out.println("Failed to create Rand variable field");
			}
		}
	}
	/**
	 * 
	 * @param consName
	 * @param fw
	 * 
	 * This is function is used to create constraints for the transaction
	 */
	private void addCons(List<String> consName , FileWriter fw){
		
		for (int i = 0; i < consName.size(); i++) {
			try {
				fw.write("constraint " + consName.get(i) + "{\n" );
				fw.write("//Add constraints here\n");
				fw.write("}\n");

			} catch(IOException e) {
				System.out.println("Failed to create Rand variable field");
			}
		}
	}
	
	/**
	 * 
	 * @param funcsName
	 * @param fw
	 *  This is function is used to create functions for the transaction
	 */
	private void addFuncs(List<String> funcsName, List<String> funcsType, FileWriter fw){
	
		for (int i = 0; i < funcsName.size(); i++) {
			try {
				fw.write("function " + funcsType.get(i) + " " + funcsName.get(i) + " ()\n");
				fw.write("//ADD CODE HERE FOR FUNCTION\n");
				fw.write("endfunction\n");

			} catch(IOException e) {
				System.out.println("Failed to create Rand variable field");
			}
		}
	}
	
	/**
	 * 
	 * @param funcsName
	 * @param fw
	 * This is function is used to create field automation for the transaction in the factory
	 */
	/*
	private void addFieldAuto(List<String> nonRandVarsName, List<String> nonRandVarsType, List<String> randVarsName, List<String> randVarsType, FileWriter fw){
		//record none Rand variables
	}
	*/	
	
	//adding new function in the code
	private void addNewFun(String name, FileWriter fw){
		try {
			fw.write("function new (string name = \"" + name + "\""+ ");\n");
			fw.write("\tsuper.new();\n");
			fw.write("endfunction\n");
				
		} catch (IOException e) {
			System.out.println("Failed to create new() function");
		}
			
	}
	
	//add spaces between lines
	private void addSpace(FileWriter fw, int N) {
		try{
			for(int i = 0; i < N; i ++) {
				fw.write("\n");
			}
		} catch (IOException e) {
			System.out.println("Add spaces failed");
		}
			
	}
		
		
	//add 3 lines of space
	private void addSpace(FileWriter fw) {
		try{
				fw.write("\n");
		} catch (IOException e) {
			System.out.println("Add space failed");
		}
			
	}
	
}
