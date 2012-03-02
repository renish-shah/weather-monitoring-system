package com.wms.utilities;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

public class FileRead {

	public static void main(String[] args) {

		try {
			// Open the file that is the first
			// command line parameter
			FileInputStream fstream = new FileInputStream(
					"F:\\SJSU_aCAdEMICS\\CMPE_275_JohnGash\\Data\\mesowest\\mesowestTest.out");
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line
			String pattern = "\\s{1,}";

			while ((strLine = br.readLine()) != null) {
				// Print the content on the console
				// System.out.println(strLine);
				strLine = strLine.replaceAll(pattern, "|");
				System.out.println("" + strLine);
				String a[] = strLine.split("\\|");
				
//				for (int i = 0; i < a.length; i++) {
//					System.out.println("" + a[i]);
//				}
			}
			// Close the input stream
			in.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}

}
