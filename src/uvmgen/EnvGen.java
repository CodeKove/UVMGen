

package uvmgen;

import java.io.*;
import java.util.*;;


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

//It is ok to create a class to contain these infos
public class EnvGen {
	private String name, fileName;
	
	private List<String> compTypeList = new ArrayList<>();
	private List<String> compNameList = new ArrayList<>();
	//private List<String> compPortList = new ArrayList<>();
	
	private List<String> agentTypeList = new ArrayList<>();
	private List<String> agentNameList = new ArrayList<>();
	//private List<String> agentPortList = new ArrayList<>();
	private List<String> agentStatusList = new ArrayList<>();
	//active or passive
	
	private List<String> fifoTypeList = new ArrayList<>();
	private List<String> fifoTransTypeList = new ArrayList<>();
	private List<String> fifoNameList = new ArrayList<>();
	
	//Scanner for input from the user
	Scanner scan = new Scanner(System.in);
	
	public EnvGen(String name, String fileName) {
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
			
			for(int i = 0; i < agentNameList.size(); i++) {
				fw.write(agentTypeList.get(i) + " " + agentNameList.get(i));
			}
			
			for(int i = 0; i < compNameList.size(); i++) {
				fw.write(compTypeList.get(i) + " " + compNameList.get(i));
			}
			
			for(int i = 0; i < fifoNameList.size(); i++) {
				fw.write(fifoTypeList.get(i) + " #(" + fifoTransTypeList.get(i) + ") " + fifoNameList.get(i));
			}
			
			addNewFunc(name, fileName, fw);
			addBuildPhase(fw);
			addConnectPhase(fw);
			
			fw.write("\nendclass");
			fw.write("\n`endif\n");
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to create ");
		}
	}
	
	/**
	 * create agent  model scb fifos
	 * active or not
	 * @param fw
	 */
	
	
	private void addBuildPhase(FileWriter fw){
		try{
			fw.write("virtual function void build_phase (uvm_phase phase);\n");
			fw.write("\tsuper.build_phase(phase)\n");
			//create agents
			for (int i = 0; i < agentNameList.size(); i++){
				fw.write("\t" + agentNameList.get(i) + " = " + agentTypeList + "::type_id::create(\"" + agentNameList.get(i) + "\", this);\n" );
				fw.write(agentNameList.get(i) + ".is_active = " + agentStatusList.get(i));
			}
			//create others
			for (int i = 0; i < compNameList.size(); i++){
				fw.write("\t" + compNameList.get(i) + " = " + compTypeList + "::type_id::create(\"" + compNameList.get(i) + "\", this);\n" );	
			}
			
			//create fifo
			for (int i = 0; i < fifoNameList.size(); i++){
				fw.write("\t" + fifoNameList.get(i) + "new(\"" + fifoNameList.get(i) + "\", this);\n");	
			}
			fw.write("endfunction\n");
		} catch (IOException e){
			e.printStackTrace();
			System.out.println("Failed to create Build Phase");
		}
	}
	
	/**
	 * connect port to port
	 * @param fw
	 */
	
	private void addConnectPhase(FileWriter fw){
		try{
			fw.write("virtual function void connect_phase (uvm_phase phase);\n");
			fw.write("\tsuper.connect_phase(phase)\n");
			fw.write("endfunction\n");
		} catch (IOException e){
			e.printStackTrace();
			System.out.println("Failed to create Connect Phase");
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
