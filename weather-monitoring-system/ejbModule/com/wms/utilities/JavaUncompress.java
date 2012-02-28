package com.wms.utilities;
import java.util.Date;
import java.util.zip.GZIPInputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class JavaUncompress {
	public static void main(String args[]) {
		
		System.out.println(""+new Date());
		
		try {
			// To Uncompress GZip File Contents we need to open the gzip
			// file.....
			
			 // if (args.length <= 0) {
			 // System.out.println("Please enter the valid file name"); } else {
			 
			// String inFilename = args[0];
			String inFilename = "E:\\test\\mesowest.out.gz";
			System.out
					.println("Opening the gzip file.......................... :  opened");

			GZIPInputStream gzipInputStream = null;
			FileInputStream fileInputStream = null;
			gzipInputStream = new GZIPInputStream(new FileInputStream(
					inFilename));
			System.out.println("Opening the output file............. : opened");

			// String outFilename = inFilename + ".out";
			
			String dateTime=""+new Date();
			dateTime=dateTime.replaceAll(":", "");
			
			String outFilename = inFilename.substring(0,
					inFilename.length() - 7)+"_"+dateTime+".out";

			OutputStream out = new FileOutputStream(outFilename);
			System.out
					.println("Transferring bytes from the compressed file to the output file........:Transfer successful");
			byte[] buf = new byte[1024]; // size can be changed according to
											// programmer's need.
			int len;
			while ((len = gzipInputStream.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			System.out
					.println("The file and stream is ......closing.......... : closed");
			gzipInputStream.close();
			out.close();
			// }
		} catch (IOException e) {
			System.out.println("Exception has been thrown" + e);
		}
	}
}
