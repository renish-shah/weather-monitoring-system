/**
 * 
 */
package com.reachout.dao;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.web.bind.annotation.RequestMethod;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import com.reachout.ws.domain.Charity;

/**
 * @author RENISH
 * 
 */
public class JavaRestClient {

	public List<Charity> callService(String uri) {
		try {

			URL url = new URL(uri);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			String output1 = "";
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
				output1 = output;
			}

			conn.disconnect();
			return readJson(output1);

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
		return null;

	}

	public void callPostHttp(String uri) {

		try {

			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost postRequest = new HttpPost(uri);

			String input1 = "{\"amount\":\"5000\",\"createdBy\":\"dipen\",\"fromCommunityId\":\"356 N\",\"fromUserName\":\"dipenmodi\",\"name\":\"donate amnt\",\"description\":\"sample description\"}";
			StringEntity input = new StringEntity(input1);
			input.setContentType("application/json");
			input.setContentEncoding("UTF-8");
			input.setChunked(true);

			postRequest.setEntity(input);

			HttpResponse response = httpClient.execute(postRequest);

			/*
			 * if (response.getStatusLine().getStatusCode() != 201) { throw new
			 * RuntimeException("Failed : HTTP error code : " +
			 * response.getStatusLine().getStatusCode()); }
			 */
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(response.getEntity().getContent())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			httpClient.getConnectionManager().shutdown();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	public List<Charity> callPostService(String uri) {
		try {

			URL url = new URL(uri);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setAllowUserInteraction(false);
			conn.setRequestMethod("" + RequestMethod.POST);
			conn.setRequestProperty("Content-Type", "application/json");

			String input = "{\"amount\":\"5000\",\"createdBy\":\"dipen\",\"fromCommunityId\":\"356 N\",\"fromUserName\":\"dipenmodi\",\"name\":\"donate amnt\",\"description\":\"sample description\"}";

			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			/*
			 * if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) { throw
			 * new RuntimeException("Failed : HTTP error code : " +
			 * conn.getResponseCode()); }
			 */

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			// return readJson(output1);

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
		return null;

	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) {

		String uri = "http://localhost:8080/ReachOut/charity";
		// String uri = "http://192.168.1.84:8080/ReachOut/charity";
		new JavaRestClient().callPostService(uri);
		// new JavaRestClient().callPostHttp(uri);

		// List<Charity> list1 = new JavaRestClient().callService(uri);
		/*
		 * List<Charity> list2 = new JavaRestClient().readLocalCharity();
		 * 
		 * List<Charity> listAllCharities = new ArrayList<Charity>(); //
		 * listAllCharities.addAll(list1); listAllCharities.addAll(list2);
		 * 
		 * for (Charity ch : listAllCharities) {
		 * 
		 * System.out.println("" + ch.getCharityID());
		 * 
		 * }
		 */
	}

	public List<Charity> readLocalCharity() {
		List<Charity> charities = new ArrayList<Charity>();

		Charity charity = new Charity();
		charity.setAmount("amount");
		charity.setCharityID("KEY");
		charity.setCreatedBy("created_by");
		charity.setDescription("charity_desc");
		charity.setFromCommunityId("community_id");
		charity.setFromUserName("username");
		charity.setName("charity_name");

		charities.add(charity);
		return charities;

	}

	public List<Charity> readJson(String output) {

		/*
		 * {"data":[{"fromCommunityId":"BrainStormers","amount":"22222","createdBy"
		 * :"createdBy"
		 * ,"charityID":"3","fromUserName":"renish","name":"FOOD","description"
		 * :"Description"},
		 * {"fromCommunityId":"BrainStormers","amount":"22222","createdBy"
		 * :"createdBy","charityID":"2",
		 * "fromUserName":"renish","name":"FOOD","description"
		 * :"Description"},{"fromCommunityId":"BrainStormers",
		 * "amount":"22222","createdBy"
		 * :"createdBy","charityID":"1","fromUserName":"renish","name":"FOOD"
		 * ,"description"
		 * :"Description"},{"fromCommunityId":"null","amount":"null"
		 * ,"createdBy":"null",
		 * "charityID":"4","fromUserName":"null","name":"FO"
		 * ,"description":"null"}]}
		 */
		JSONObject json = (JSONObject) JSONSerializer.toJSON(output);
		/*
		 * double coolness = json.getDouble("coolness"); int altitude =
		 * json.getInt("altitude");
		 */// JSONObject pilot = json.getJSONObject("data");
			// String data = json.getString("data");

		JSONArray data1 = json.getJSONArray("data");
		data1.size();
		List<Charity> remoteCharities = new ArrayList<Charity>();

		for (int i = 0; i < data1.size(); i++) {
			json = data1.getJSONObject(i);
			// System.out.println(""+json.toString());

			Charity charity = new Charity();
			charity.setFromCommunityId(json.getString("fromCommunityId"));
			charity.setAmount(json.getString("amount"));
			charity.setCharityID(json.getString("charityID"));
			charity.setCreatedBy(json.getString("createdBy"));
			charity.setDescription(json.getString("fromCommunityId"));
			charity.setFromUserName(json.getString("fromUserName"));
			charity.setName(json.getString("name"));
			remoteCharities.add(charity);

		}
		return remoteCharities;

	}

}
