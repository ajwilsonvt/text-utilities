package extract;

import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.Map;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Extract {
	
	private Boolean empty;
	private Scanner sc;
	private Scanner scFile;
	private Map<String, String> map;

	public static void main(String[] args) {
		Extract e = new Extract();
		e.sc = new Scanner(System.in);
		e.empty = true;
		
		e.map = new LinkedHashMap<>();
		e.loadFile();
		e.extractWords();
		e.saveNewEntry();
		
		e.scFile.close();
		e.sc.close();
	}
	
	public void loadFile() {
		System.out.print("Enter raw text file: ");
		String s = sc.next();
		
		// prepare for next input
		sc.nextLine();
		
		File file = new File(s);

		try {
			scFile = new Scanner(file);
			scFile.useDelimiter("lang-|<|>");
			if(scFile.hasNext()) {
				empty = false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void extractWords() {
		if (empty) {
			System.out.println("File is empty. Cancelling...");
			return;
		}
		
		while (scFile.hasNext()) {
			String current = scFile.next();
			String word = "";
			String def = "";
			
			if (current.equals("en\"")) {
				word += scFile.next();
				while (scFile.hasNext()) {
					String current2 = scFile.next();
					if (current2.equals("en\"")) {
						def += scFile.next();
						break;
					}
				}
			}
			
			if (!word.equals("") && !def.equals("")) {
				map.put(word, def);
			}
		}
	}
	
	public void saveNewEntry() {
		try {
			File file = new File("output.txt");
			FileWriter fw = new FileWriter(file, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
	    
			map.forEach((k,v) -> pw.println(k+","+v));
	
		    pw.close();
		    System.out.println("Done!");
		    
		} catch(IOException e) {
		  e.printStackTrace();
		}
	}

}
