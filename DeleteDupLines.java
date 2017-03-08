package deleteDupLines;

import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Deletes duplicate lines from .txt files. Place
 * the raw .txt file in the same directory as the
 * runnable JAR. Final product is an output.txt file
 * in the same directory as the runnable JAR. Maintains
 * order of entries. Deletes all empty lines except for
 * the first.
 */
public class DeleteDupLines {
	
	private Boolean empty;
	private Scanner sc;
	private Scanner scFile;
	private Set<String> set;

	public static void main(String[] args) {
		DeleteDupLines d = new DeleteDupLines();
		d.sc = new Scanner(System.in);
		d.empty = true;
		
		d.set = new LinkedHashSet<>();
		d.loadFile();
		d.getUnique();
		
		d.sc.close();
	}
	
	public void loadFile() {
		System.out.print("Enter raw text file: ");
		String s = sc.next();
		
		// prepare for next input
		sc.nextLine();
		
		File file = new File(s);

		try {
			scFile = new Scanner(file);
			if(scFile.hasNext()) {
				empty = false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void getUnique() {
		if (empty) {
			System.out.println("File is empty. Cancelling...");
			return;
		}

		int count = 0;
		
		while (scFile.hasNext()) {
			count++;
			String line = scFile.nextLine();
			set.add(line);
		}
		
		count = count - set.size();
		System.out.println(count + " duplicates found!");
		
		if (count == 0) {
			System.out.println("Goodbye!");
		} else {
			System.out.print("Delete? (y/n): ");
			String s = sc.next();
			
			if (s.equals("y")) {
				saveNewEntry();
			} else {
				System.out.println("Cancelling...");
			}
		}
	}
	
	public void saveNewEntry() {
		try {
		  File file = new File("output.txt");
			FileWriter fw = new FileWriter(file, true);
			BufferedWriter bw = new BufferedWriter(fw);
	    PrintWriter pw = new PrintWriter(bw);
	    
	    for (String s : set) {
	    	pw.println(s);
	    }
	
	    pw.close();
	    
	    System.out.println("Done!");
		} catch(IOException e) {
		  e.printStackTrace();
		}
	}

}
