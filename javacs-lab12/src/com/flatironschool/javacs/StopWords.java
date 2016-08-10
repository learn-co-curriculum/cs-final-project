package com.flatironschool.javacs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class StopWords {
	public static Set<String> stopwords = new HashSet<String>();
	public StopWords() {
		stopwords = new HashSet<String>();
		String slash = File.separator;
		String filename = "resources" + slash + "stopwords.txt";
		URL fileURL = JedisMaker.class.getClassLoader().getResource(filename);
		System.out.println(filename);
		try {
			FileInputStream fis = new FileInputStream(fileURL.getFile());
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			String line = null;
			while ((line = br.readLine()) != null) {
				stopwords.add(line.trim().toLowerCase());
			}
			br.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Set<String> getStopWords() {
		return stopwords;
	}
}
