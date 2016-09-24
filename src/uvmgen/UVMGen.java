package uvmgen;

import java.io.*;
import java.util.*;

public class UVMGen {
	//here are the field to record the program
	// to record created FileName 
	private static List<String> FileNameList = new ArrayList<String>();
	private static List<String> drvFileNameList = new ArrayList<String>();
	private static List<String> sqrFileNameList = new ArrayList<String>();
	private static List<String> monFileNameList = new ArrayList<String>();
	private static List<String> transFileNameList = new ArrayList<String>();
	private static List<String> sqFileNameList = new ArrayList<String>();
	private static List<String> scbFileNameList = new ArrayList<String>();
	private static List<String> modelFileNameList = new ArrayList<String>();
	private static List<String> agentFileNameList = new ArrayList<String>();
	private static List<String> envFileNameList = new ArrayList<String>();
	
	 // to record created ObjectsName 
	private static List<String> NameList = new ArrayList<String>();
	private static List<String> drvNameList = new ArrayList<String>();
	private static List<String> sqrNameList = new ArrayList<String>();
	private static List<String> monNameList = new ArrayList<String>();
	private static List<String> transNameList = new ArrayList<String>();
	private static List<String> sqNameList = new ArrayList<String>();
	private static List<String> scbNameList = new ArrayList<String>();
	private static List<String> modelNameList = new ArrayList<String>();
	private static List<String> agentNameList = new ArrayList<String>();
	private static List<String> envNameList = new ArrayList<String>();

	
	
	public static void main(String[] args) throws IOException{

		
		Scanner scan = new Scanner(System.in);
		String name, fileName;
		
		/***************************************
		 * Here is the code to generate a driver
		 * It needs its file name and name
		 * ****************************************
		 */
		/*
		int n = 3;
		for (int i = 0; i < n; i++){
			//Scanner scan = new Scanner(System.in);
			System.out.println("Be ready to create next driver (Q to quit Y to contine):");
			System.out.println("Please enter the file name of the driver:");
			fileName = scan.next();
			FileNameList.add(fileName);
			drvFileNameList.add(fileName);
			
			System.out.println("Please enter the name of the driver:");
			name = scan.next();
			NameList.add(name);
			drvNameList.add(name);
			
			DriverGen Driver = new DriverGen(name, fileName);
			Driver.writeDriver();
			System.out.println("Your driver named " + name + " is generated in " + fileName +".sv");
			
		}*/
		//scan.close();
		
		while(true){
				System.out.println("Be ready to create next driver (Q to quit Y to contine):");
			
				System.out.println("Please enter the file name of the driver:");
				fileName = scan.next();
				
				if (fileName.equals("Q") || fileName.equals("q")){
					break;
				} else {
				FileNameList.add(fileName);
				drvFileNameList.add(fileName);
				
				System.out.println("Please enter the name of the driver:");
				name = scan.next();
				NameList.add(name);
				drvNameList.add(name);
				
				DriverGen Driver = new DriverGen(name, fileName);
				Driver.writeDriver();
				System.out.println("Your driver named " + name + " is generated in " + fileName +".sv");
			}
		}
				
		System.out.println("done");

		
		
		
		/*
		System.out.print("Please enter the FileName:");
		String fileName = new String();
		fileName = scan.next();
		
		System.out.print("Please enter the Name of the Component:");
		String name = new String();
		name = scan.next();
		
		DriverGen Driver = new DriverGen(name, fileName);
		Driver.writeDriver();
		System.out.println("Your driver is generated");
		
		
		//need more communication with user
		SqrGen Sequencer = new SqrGen("my_sequencer", "sequencer");
		Sequencer.writeSequencer();
		System.out.println("Your sequencer is generated");
		
		MonitorGen Monitor = new MonitorGen("my_monitor", "monitor");
		Monitor.writeMonitor();
		System.out.println("Your Monitor is generated");
		
		
		//TransGen Trans = new TransGen("trans", "Trans");
		//Trans.writeTrans();
		//System.out.println("Your transaction is generated");
		
		
		SqGen sq = new SqGen("case0_sequence", "case0");
		sq.writeSq();
		System.out.println("Your sequence is generated");
		*/
		
	}
}
