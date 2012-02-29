package com.wms.utilities;

import java.io.File;

public class RenameFile {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// File (or directory) with old name
		File oldfileName = new File("E://Test//mesowest_priyank.out");

		// File (or directory) with new name
		String dir = "E://test//test23";
		if (!(new File(dir)).exists())
			new File(dir).mkdir();
		
		//New File Name
		String newFileName = "renish_shah.out";

		// Rename file (or directory)
		boolean success = oldfileName.renameTo(new File(dir, newFileName));
		if (!success) {
			// File was not successfully renamed
			System.out.println("ERROR");
		}
		else
			System.out.println("SUCCESS");

	}
}
