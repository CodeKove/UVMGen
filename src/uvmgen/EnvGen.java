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
	
	//private List<String> fifoTypeList = new ArrayList<>();
	//all set to  uvm_tlm_analysis_fifo
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
			
			
			System.out.println("First please input infomation of Agents(press Q to break S to start):");
			//System.out.println("Enter Agent Type first:");
			while (true){
				String a = scan.next();
				if (a.equals("Q") || a.equals("q")){
					break;
				} else {
					this.setAgent(scan, agentTypeList, agentNameList, agentStatusList);
					System.out.println("Lets go to next(press Q to break S to start)");
				}
			}
			
			
			System.out.println("Second Please input infomation of other Components(press Q to break S to start):");
			while (true){
				String a = scan.next();
				if (a.equals("Q") || a.equals("q")){
					break;
				} else {
					this.setComp(scan, compTypeList, compNameList);
					System.out.println("Lets go to next(press Q to break S to start)");
				}
			}
			
			
			System.out.println("Third Please input infomation of fifo(press Q to break S to start):");
			while (true){
				String a = scan.next();
				if (a.equals("Q") || a.equals("q")){
					break;
				} else {
					this.setFifo(scan, fifoTransTypeList, fifoNameList);
					System.out.println("Lets go to next(press Q to break S to start)");
				}
			}
			
			
			File f= new File(fileName + ".sv");
			FileWriter fw = new FileWriter(f);
			//fw.write();
			fw.write("`ifndef " + name.toUpperCase() + "__SV\n" );
			fw.write("`define " + name.toUpperCase() + "__SV\n" );
			fw.write("class " + name + " extends uvm_env;\n");
			fw.write("\t`uvm_component_utils(" + name + ")\n");
			
			//declare components
			for(int i = 0; i < agentNameList.size(); i++) {
				fw.write("\t" + agentTypeList.get(i) + " " + agentNameList.get(i) + "\n;");
			}
			
			for(int i = 0; i < compNameList.size(); i++) {
				fw.write("\t" + compTypeList.get(i) + " " + compNameList.get(i) + "\n;");
			}
			addSpace(fw, 1);
			for(int i = 0; i < fifoNameList.size(); i++) {
				fw.write("\t" + "uvm_tlm_analysis_fifo " + " #(" + fifoTransTypeList.get(i) + ") " + fifoNameList.get(i) + "\n;");
			}
			
			addSpace(fw, 1);
			addNewFunc(name, fileName, fw);
			addSpace(fw, 1);
			addBuildPhase(fw);
			addSpace(fw, 1);
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
			System.out.println("Build Phase begin:");
			fw.write("virtual function void build_phase (uvm_phase phase);\n");
			fw.write("\tsuper.build_phase(phase)\n");
			//create agents
			for (int i = 0; i < agentNameList.size(); i++){
				fw.write("\t\t" + agentNameList.get(i) + " = " + agentTypeList.get(i) + "::type_id::create(\"" + agentNameList.get(i) + "\", this);\n" );
				fw.write("\t\t" + agentNameList.get(i) + ".is_active = " + agentStatusList.get(i) + "\n");
			}
			//create others
			for (int i = 0; i < compNameList.size(); i++){
				fw.write("\t\t" + compNameList.get(i) + " = " + compTypeList.get(i)+ "::type_id::create(\"" + compNameList.get(i) + "\", this);\n" );	
			}
			
			//create fifo
			for (int i = 0; i < fifoNameList.size(); i++){
				fw.write("\t\t" + fifoNameList.get(i) + "new(\"" + fifoNameList.get(i) + "\", this);\n");	
			}
			fw.write("endfunction\n");
			System.out.println("BUild Phase end");
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
			System.out.println("Connect Phase begin:");
			fw.write("virtual function void connect_phase (uvm_phase phase);\n");
			fw.write("\tsuper.connect_phase(phase)\n");
			System.out.println("You created :");
			for(int i = 0; i < this.agentNameList.size(); i++ ){
				System.out.println(this.agentNameList.get(i));
			}
			for(int i = 0; i < this.compNameList.size(); i++ ){
				System.out.println(this.compNameList.get(i));
			}
			System.out.println("You have fifos below");
			for(int i = 0; i < this.fifoNameList.size(); i++ ){
				System.out.println(this.fifoNameList.get(i));
			}
			System.out.println("Please connect them together");
			while (true){
				System.out.println("Begin to connect(Press Q to quit S to Start)");
				String a = scan.next();
				if (a.equals("Q") || a.equals("q")){
					break;
				} else {
					String name1, name2, port1, port2;
					System.out.println("EXAMPLE: NAME1.PORT1.connect(NAME2.PORT2);");
					System.out.println("Please enter the name of component1");
					name1 = scan.next();
					System.out.println("Please enter the  port name of component1");
					port1 = scan.next();
					System.out.println("Please enter the name of component2");
					name2 = scan.next();
					System.out.println("Please enter the port name of component1");
					port2 = scan.next();
					fw.write("\t" + name1 + "." + port1 + "." + "connect(" + name2 + "." + port2 + ");\n");
				}
			}
			
			
			System.out.println("Connect Phase end");
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
	
	//get function group
	
	/**
	 * This one is used to get information about agents in one env
	 * @param scan
	 * @param agentTypeList
	 * @param agentNameList
	 * @param agentStatusList
	 */
	//set agent
	private void setAgent(Scanner scan, List<String> agentTypeList,
							List<String> agentNameList, List<String> agentStatusList ){
		
			String tmp;
			System.out.println("Please enter the Type of this agent:");
			tmp = scan.next();
			agentTypeList.add(tmp);
			
			System.out.println("Please enter the Name of this agent:");
			tmp = scan.next();
			agentNameList.add(tmp);
			
			System.out.println("Is this agent active or passive ?");
			System.out.println("Press 0 for passive or press 1 for active:");
			tmp = scan.next();
			if(tmp.equals("0")) {
				agentStatusList.add("UVM_PASSIVE");
			} else if (tmp.equals("1")) {
				agentStatusList.add("UVM_ACTIVE");
			} else {
				agentStatusList.add("agent status not GIVEN!!!");
				System.out.println("Please configure the agent status later!!!");
			}
	}
	
	//set component
	private void setComp(Scanner scan, List<String> compTypeList, List<String> compNameList) {
		String tmp;
		System.out.println("Please enter the Type of this Component:");
		tmp = scan.next();
		compTypeList.add(tmp);
		
		System.out.println("Please enter the Name of this Component:");
		tmp = scan.next();
		compNameList.add(tmp);
	}
	
	//set fifo
	private void setFifo(Scanner scan, List<String> fifoTransTypeList, List<String> fifoNameList) {
		String tmp;
		System.out.println("Please enter the Transaction Type of this FIFO:");
		tmp = scan.next();
		fifoTransTypeList.add(tmp);
		
		System.out.println("Please enter the Name of this FIFO:");
		tmp = scan.next();
		fifoNameList.add(tmp);
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
