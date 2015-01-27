package com.sameas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class InputTriplesDatabase {

	public void inputFilesForFolder(final File folder) {
	
		
		String temp = "";
		String filePath = null;
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				// System.out.println("Reading files under the folder "+folder.getAbsolutePath());
				inputFilesForFolder(fileEntry);
			} else {
				if (fileEntry.isFile()) {
					temp = fileEntry.getName();
					temp = temp.substring(temp.lastIndexOf('.') + 1,
							temp.length()).toLowerCase();
					if (temp.equals("ttl") || temp.equals("nt"))
						filePath = folder.getAbsolutePath()
								+ "/" + fileEntry.getName();
						System.out.println("File= " + filePath);
						if(filePath != null)
							insertFileDataBase(filePath);
				}

			}
		}
	}

	private void insertFileDataBase(String pFile) {
		//Read the file
		try{
		BufferedReader reader = new BufferedReader(new FileReader(pFile));
		String line;
		while ((line = reader.readLine()) != null) {
			if(line.startsWith("<"))
			{
				String sLine[] = line.split("<");
				String URIDBPedia = sLine[1].substring(0, sLine[1].indexOf(">"));
				String sameas = sLine[2].substring(0, sLine[2].indexOf(">"));
				String URIFreeBase = sLine[3].substring(0, sLine[3].indexOf(">"));
				
				if (URIDBPedia.indexOf("dbpedia") < 1)
				{
					String sURI = URIFreeBase;
					URIFreeBase = URIDBPedia;
					URIDBPedia = sURI;
				}
					
				AccesPostGreeSQL ac = new AccesPostGreeSQL();
				if(sameas.indexOf("sameAs") > 0)
				{
					ac.insertTriple(URIFreeBase, sameas, URIDBPedia, "TBL_URI");
				}
				else
				{
					ac.insertTriple(URIFreeBase, sameas, URIDBPedia, "TBL_NOT_SAMEAS");
				}
				
			}
		}
		reader.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private void insertTriplesFile(String pFile) {
		//Read the file
		try{
		BufferedReader reader = new BufferedReader(new FileReader(pFile));
		String line;
		while ((line = reader.readLine()) != null) {
			if(line.startsWith("<"))
			{
				String sLine[] = line.split("<");
				String from = sLine[1].substring(0, sLine[1].indexOf(">"));
				String to = sLine[3].substring(0, sLine[3].indexOf(">"));
				
				AccesPostGreeSQL ac = new AccesPostGreeSQL();
				ac.insertTripleRedirect(from, to, "TBL_REDIRECT");
			}
		}
		reader.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) {
		try {
			System.out.println("Starting...");
			InputTriplesDatabase in = new InputTriplesDatabase();
			
			AccesPostGreeSQL.truncateTable("TBL_REDIRECT");
			in.insertTriplesFile("/home/valdestilhas/Downloads/SameAsPaperDimitris/Redirect/redirects_transitive_en.ttl");
			
			System.out.println("Starting second part...");
			
			AccesPostGreeSQL.truncateTable("TBL_URI");
			final File folder = new File("/home/valdestilhas/Downloads/SameAsPaperDimitris/DataBaseDimitris/");
			in.inputFilesForFolder(folder);
			
			System.out.println("end...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
