package uvmgen;

import java.io.FileWriter;
import java.io.IOException;

public interface AddSpace {
	//adding lines of space in the code
	public static void addSpace(FileWriter fw, int space) {
		try{
			for(int i = 0; i < space; i ++) {
				fw.write("\n");
			}
		} catch (IOException e) {
			System.out.println("Add space failed");
		}
		
	}
	//adding one line of space
	public static void addSpace(FileWriter fw) {
		try{
			for(int i = 0; i < 2; i ++) {
				fw.write("\n");
			}
		} catch (IOException e) {
			System.out.println("Add space failed");
		}
		
	}
}
