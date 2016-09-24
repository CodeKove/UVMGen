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
			this.addSpace(fw, 2);
			fw.write("class " + name + " extends uvm_sequence_item;\n");
			
			this.addNewFun(name, fw);
			
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
			} else {
				System.out.println("Finish non rand variable create");
			}
			
			this.addNonRandVars(nonRandVarsName, nonRandVarsType, fw);
			
			
			
			//add constrains
			System.out.println("How Many constrains do you want to create?");
			System.out.println("Please enter the number:");
			n = scan.nextInt();
			
			if (n != 0) {
				for (int i = 0; i < n; i ++){
					System.out.print("Please enter the name for No." + (i+1) +  "constraint");
					this.consName.add(scan.next());	
				}
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
					System.out.print("Please enter the name for No." + (i+1) +  "function");
					this.funcsName.add(scan.next());	
					System.out.println();
					System.out.print("Please enter the return type for it: ");
					this.funcsType.add(scan.next());
				}
			} else {
				System.out.println("Finish functions create");
			}
			
			this.addFuncs(funcsName, funcsType, fw);
			
			//add field automation
			fw.write("`uvm_object_utils_begin(" + name + ")\n");
			fw.write("\t//`uvm_field_" + "int(" + "name, " + "UVM_ALL_ON)\n");
			fw.write("`uvm_object_utils_end\n");
			

		
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
		
		String[] varNames =  new String[nonRandVarsName.size()];
		varNames = nonRandVarsName.toArray(varNames);
		String[] varTypes =  new String[nonRandVarsType.size()];
		varTypes = nonRandVarsType.toArray(varTypes);
		
		for (int i = 0; i <varNames.length; i++) {
			try {
				fw.write("\t" + varTypes[i] + "\t" +varNames[i] + ";\n" );

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
		//first create a string with size() of the list
		//second use toArray() to copy the List
		String[] varNames =  new String[randVarsName.size()];
		varNames = randVarsName.toArray(varNames);
		
		String[] varTypes =  new String[randVarsType.size()];
		varTypes = randVarsType.toArray(varTypes);
		
		
		for (int i = 0; i < varNames.length; i++) {
			try {
				fw.write("\trand " + varTypes[i] + "\t" +varNames[i] + ";\n" );

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
		
		String[]cons =  new String[consName.size()];
		cons = consName.toArray(cons);
		
		for (int i = 0; i < cons.length; i++) {
			try {
				fw.write("constraint" + cons[i] + "{\n" );
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
		String[] functionNames =  new String[funcsName.size()]; 
		functionNames = funcsName.toArray(functionNames);
		
		String[] functionTypes =  new String[funcsType.size()]; 
		functionTypes = funcsType.toArray(functionTypes);
		
		
		for (int i = 0; i < functionNames.length; i++) {
			try {
				fw.write("function " + functionTypes[i] + " " + functionNames[i] + " ()\n");
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
		String[] nonRandNameArray =  new String[nonRandVarsName.size()];
		nonRandNameArray = nonRandVarsName.toArray(nonRandNameArray);
		String[] nonRandTypeArray =  new String[nonRandVarsType.size()];
		nonRandTypeArray = nonRandVarsType.toArray(nonRandTypeArray);
		//record Rand variables
		String[] randNameArray =  new String[nonRandVarsName.size()];
		randNameArray = nonRandVarsName.toArray(randNameArray);
		String[] randTypeArray =  new String[nonRandVarsType.size()];
		randTypeArray = nonRandVarsType.toArray(randTypeArray);
		
		//create field macros accoring to its type
		for (int i = 0; i < nonRandNameArray.length; i++) {
			switch (nonRandNameArray[i]) {
				
			}
		}
		try {
			fw.write("\t//`uvm_field_" + "int(" + "name, " + "UVM_ALL_ON)\n");
		} catch (IOException e) {
			System.out.println("Field Automation failed");
		}
		
	}
	*/	
	
	//adding new function in the code
	private void addNewFun(String name, FileWriter fw){
		try {
			fw.write("function new (string name = \" " + name + "\""+ ");\n");
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
