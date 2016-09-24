/**
 * @author Pengcheng Yan
 * ID: 009755798
 * UNIVERSITY: SJSU EE
 * EE 297B Project
 * This is the file for generating UVM sequencer. It includes most features that are needed to generate
 * an sequencer
 */

package uvmgen;
import java.io.*;
import java.util.Scanner;
/**
 * 
 * 1. new function
 * 2. factory
 *
 */
public class SqrGen {
	private String name;
	private String filename;;
	private String TransactionType;
	
	//constructor for the sequencer
	public SqrGen(String name, String filename){
		this.name = name;
		this.filename = filename;
	}
	
	
	
	
	//write the sequencer to the file
	public void writeSequencer(){
		try {
			File f = new File(filename + ".sv");
			FileWriter fw = new FileWriter(f);
			
			//Scanner for inputs from the user
			Scanner scan = new Scanner(System.in);
			System.out.println("Please enter the transaction type for sequencer: ");
			
			this.setTransactionType(scan.next());
			//scan.close();
			
			//fw.write();
			fw.write("`ifndef " + name.toUpperCase() + "__SV\n" );
			fw.write("`define " + name.toUpperCase() + "__SV\n" );
			this.addSpace(fw, 2);
			fw.write("class " + name + " extends uvm_sequencer#(" + TransactionType + ");\n");
			fw.write("\t`uvm_component_utils(" + name + ")\n");
			this.addSpace(fw, 1);
			this.addNewFun(fw);
			this.addSpace(fw, 1);
			fw.write("\nendclass");
			this.addSpace(fw);
			fw.write("\n`endif\n");
			fw.close();
			
		} catch (IOException e) {
			System.out.println("Failed to create sequencer");
		}
	}
	
	//set Transaction Type for the sequencer
		private void setTransactionType(String TransactionType){
			this.TransactionType = TransactionType;
		}
		
	
	// add new function for the sequencer
	private void addNewFun(FileWriter fw){
		try {
			fw.write("\tfunction new (string name, uvm_component parent);\n");
			fw.write("\t\tsuper.new(name, parent);\n");
			fw.write("\tendfunction\n");
			
		} catch (IOException e) {
			System.out.println("Failed to create new function for sequencer");
		}
		
	}
	
	
	//add spaces between lines
	private void addSpace(FileWriter fw, int N) {
		try{
			for(int i = 0; i < N; i ++) {
				fw.write("\n");
			}
		} catch (IOException e) {
			System.out.println("Add space failed");
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
