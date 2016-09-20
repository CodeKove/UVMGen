package uvmgen;

import java.io.*;
import java.util.*;

public class UVMGen {
	
	// to record created FileName 
	List<String> FileNameList = new ArrayList<String>();
	// to record created ObjectsName 
	List<String> NameList = new ArrayList<String>();
	
	
	public static void main(String[] args) throws IOException{
		
		Scanner scan = new Scanner(System.in);
		
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
		*/
		
		SqGen sq = new SqGen("case0_sequence", "case0");
		sq.writeSq();
		System.out.println("Your sequence is generated");
		
		
		
	}
}
