package uvmgen;

import java.io.*;
import java.util.*;

/**
 * 
 * @author Pengcheng Yan
 * Interface is very important in a design
 * It should contain elements below:
 * 
 *1. signals needed
 *2. functions and tasks
 *3. clocking blocks
 *4. modports
 *5. some variables
 *
 */

/**
 * 
 * @author CodeMan
 * This code is used to generate a file of interface
 */
public class InterfaceGen {
	private String name, fileName;
	
	private List<String> funcsName = new ArrayList<>();
	private List<String> funcsType = new ArrayList<>();
	private List<String> taskName = new ArrayList<>();
	
	
	private List<String> varsName = new ArrayList<>();
	private List<String> varsType = new ArrayList<>();
	
	private List<String> mpName = new ArrayList<>();
	private List<String> clkBlkName = new ArrayList<>();
	
	Scanner scan = new Scanner(System.in);
	
	
	public InterfaceGen(String name, String fileName) {
		this.name = name;
		this.fileName = fileName;
		
	}

	public void writeInterface(){
		
		try {
			File f= new File(fileName + ".sv");
			FileWriter fw = new FileWriter(f);
			fw.write("`ifndef " + name.toUpperCase() + "__SV\n" );
			fw.write("`define " + name.toUpperCase() + "__SV\n" );
			addSpace(fw, 1);
			fw.write("interface " + name + "();\n");
			
			this.setVars(varsName, varsType);
			addSpace(fw, 1);
			this.addVars(fw, varsName, varsType);
			addSpace(fw, 1);
			this.setFuncs(funcsName, funcsType);
			this.addFuncs(fw, funcsName, funcsType);
			addSpace(fw, 1);
			this.setTasks(taskName);
			this.addTasks(fw, taskName);
			addSpace(fw, 1);
			this.setModPorts(mpName);
			this.addModPorts(fw, mpName);
			addSpace(fw, 1);
			this.setClkBLKs(clkBlkName);
			this.addClkBLKs(fw, clkBlkName);
			addSpace(fw, 1);
			
			fw.write("endinterface");
			fw.write("\n`endif\n");
			fw.close();
		} catch (IOException e) {
			
			e.printStackTrace();
			System.out.println("Failed to create ");
		}
	}
	
	private void addVars(FileWriter fw, List<String> varsName, List<String> varsType) {
		try {
			for (int i = 0; i < varsName.size(); i++) {
				fw.write(varsType.get(i) + varsName.get(i));
			}
		} catch (IOException e) {
			System.out.println("Failed to create variables in interface");
		}
	}
	
	private void setVars(List<String> varsName, List<String> varsType) {
		System.out.println("Begin to set variables in an Interface (Press Q to quit or S to Start )");
		while(true) {
			String a = scan.next();
			if (a.equals("Q") || a.equals("q")){
				break;
			} else {
				System.out.println("Please enter the type of next variable:");
				varsType.add(scan.next());
				System.out.println("Please enter the name of next variable:");
				varsName.add(scan.next());
			}
		}
	}
	
	private void addFuncs(FileWriter fw, List<String> funcsName, List<String> funcsType) {
		try {
			for (int i = 0; i < funcsName.size(); i++) {
				fw.write("function " + funcsType.get(i) + " "+ funcsName.get(i) + "();\n" );
				
				fw.write("endfunction");
			}
		} catch (IOException e) {
			System.out.println("Failed to create Functions in interface");
		}
	}
	
	private void setFuncs(List<String> funcsName, List<String> funcsType) {
		System.out.println("Begin to set functions in an Interface (Press Q to quit or S to Start )");
		while(true) {
			String a = scan.next();
			if (a.equals("Q") || a.equals("q")){
				break;
			} else {
				System.out.println("Please enter the return type of next function:");
				funcsType.add(scan.next());
				System.out.println("Please enter the name of next function:");
				funcsName.add(scan.next());
			}
		}
	}
	
	private void addTasks(FileWriter fw, List<String> taskName) {
		try {
			for (int i = 0; i < taskName.size(); i++) {
				fw.write("task " + taskName.get(i) + "();\n" );
				fw.write("endtask");
			}
		} catch (IOException e) {
			System.out.println("Failed to create Tasks in interface");
		}
	}
	
	private void setTasks(List<String> taskName) {
		System.out.println("Begin to set tasks in an Interface (Press Q to quit or S to Start )");
		while(true) {
			String a = scan.next();
			if (a.equals("Q") || a.equals("q")){
				break;
			} else {
				System.out.println("Please enter the name of next task:");
				funcsName.add(scan.next());
			}
		}
	}
	
	private void addModPorts(FileWriter fw, List<String> mpName) {
		try {
			for (int i = 0; i < taskName.size(); i++) {
				fw.write("modport " + mpName.get(i) + "();\n" );
			}
		} catch (IOException e) {
			System.out.println("Failed to creat ModPorts");
		}
	}
	
	private void setModPorts(List<String> mpName) {
		System.out.println("Begin to set Modports in an Interface (Press Q to quit or S to Start )");
		while(true) {
			String a = scan.next();
			if (a.equals("Q") || a.equals("q")){
				break;
			} else {
				System.out.println("Please enter the name of next modport:");
				mpName.add(scan.next());
			}
		}
	}
	
	private void addClkBLKs(FileWriter fw, List<String> clkBlkName) {
		try {
			for (int i = 0; i < clkBlkName.size(); i++) {
				fw.write("clocking " + clkBlkName.get(i) + "@();\n");
				fw.write("endclocking");
			}
		} catch (IOException e) {
			
		}
	}
	
	private void setClkBLKs(List<String> clkBlkName) {
		System.out.println("Begin to set clocking block in an Interface (Press Q to quit or S to Start )");
		while(true) {
			String a = scan.next();
			if (a.equals("Q") || a.equals("q")){
				break;
			} else {
				System.out.println("Please enter the name of next clocking block:");
				funcsName.add(scan.next());
			}
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
