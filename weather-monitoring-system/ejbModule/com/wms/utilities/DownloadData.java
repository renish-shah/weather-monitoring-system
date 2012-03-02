package com.wms.utilities;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Date;
import java.util.zip.GZIPInputStream;

/**
 * 
 */

/**
 * @author RENISH
 * 
 */
public class DownloadData {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		DownloadData data = new DownloadData();

		String link = "http://mesowest.utah.edu/data/mesowest.out.gz";

		String[] fileNameWithExtension = link.split("/");
		String newName = fileNameWithExtension[fileNameWithExtension.length - 1];
		System.out.println("" + newName);

		String[] fileName = newName.split("\\.");
		System.out.println(fileName[0]);

		data.downloadData(fileName[0]);
		data.extractData();

	}

	public void downloadData(String outputFileName) {
		try {

			String envVar = System.getenv("JAVA_HOME");
			// String environmentVariable="C:/Program Files/Java/jdk1.7.0";
			System.out.print("Environment Variable is =>" + envVar);

			String dir = envVar + "/WMS";
			if (!(new File(dir)).exists())
				new File(dir).mkdirs();

			URL url = new URL("http://mesowest.utah.edu/data/mesowest.out.gz");

			URLConnection con = url.openConnection();
			BufferedInputStream in = new BufferedInputStream(
					con.getInputStream());
			FileOutputStream out = new FileOutputStream(dir+"/"+outputFileName);

			int i = 0;
			byte[] bytesIn = new byte[3000000];
			while ((i = in.read(bytesIn)) >= 0) {
				out.write(bytesIn, 0, i);
			}
			out.close();
			in.close();

		} catch (Exception e) {
			System.out.println("Exception :" + e);
		}

	}

	public void extractData() {

		try {

			String inFilename = "E://Test//mnet_no.cgi";
			System.out.println("Opening the gzip file....:  opened");

			GZIPInputStream gzipInputStream = null;
			// FileInputStream fileInputStream = null;
			gzipInputStream = new GZIPInputStream(new FileInputStream(
					inFilename));
			System.out.println("Opening the output file...: opened");

			String dateTime = "" + new Date();
			dateTime = dateTime.replaceAll(":", "");

			String outFilename = inFilename.substring(0,
					inFilename.length() - 7) + "_" + dateTime + ".out";

			// String outFilename="E://Test//mesowest_renish.out";

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
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}
