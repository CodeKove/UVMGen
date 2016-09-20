package uvmgen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 
 * @author Pengcheng Yan
 * 
 * 1. new
 * 2. build phase :: default sequence
 * 3. build phase
 */
public class TestGen {
	private String name, fileName;
	
	public TestGen() {
		this.name = name;
		this.fileName = fileName;
	}
	
	public void writeTest(){
		
		try {
			File f= new File(fileName + ".sv");
			FileWriter fw = new FileWriter(f);
			fw.write("`ifndef " + name.toUpperCase() + "__SV\n" );
			fw.write("`define " + name.toUpperCase() + "__SV\n" );
			
			fw.write("`endif");
		} catch (IOException e) {
			
			e.printStackTrace();
			System.out.println("Failed to create ");
		}
	}
}
