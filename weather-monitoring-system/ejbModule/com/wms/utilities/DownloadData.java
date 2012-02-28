package com.wms.utilities;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
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

		new DownloadData().downloadData();
		/*
		 * try { URL u = new
		 * URL("http://mesowest.utah.edu/data/mesowest.out.gz"); InputStream is
		 * = u.openStream(); InputStreamReader isr = new InputStreamReader(is);
		 * BufferedReader br = new BufferedReader(isr); String theLine; while
		 * ((theLine = br.readLine()) != null) { System.out.println(theLine); }
		 * } catch (MalformedURLException ex) { System.err.println(ex); } catch
		 * (IOException ex) { System.err.println(ex); }
		 */

		/*
		 * URL google;
		 * 
		 * google = new URL("http://mesowest.utah.edu/data/mesowest.out.gz");
		 * 
		 * ReadableByteChannel rbc = Channels.newChannel(google.openStream());
		 * FileOutputStream fos = new FileOutputStream(rbc); } catch
		 * (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

	}

	public void downloadData() {
		try {

			URL url = new URL("http://mesowest.utah.edu/data/mesowest.out.gz");
			URLConnection con = url.openConnection();
			BufferedInputStream in = new BufferedInputStream(
					con.getInputStream());
			FileOutputStream out = new FileOutputStream("E://Test//mesowest.out.gz");

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

			String inFilename = "mesowest.out.gz";
			System.out.println("Opening the gzip file....:  opened");

			GZIPInputStream gzipInputStream = null;
			FileInputStream fileInputStream = null;
			gzipInputStream = new GZIPInputStream(new FileInputStream(
					inFilename));
			System.out.println("Opening the output file...: opened");

			// String outFilename = inFilename + ".out";

			String outFilename = inFilename.substring(0,
					inFilename.length() - 2);

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
