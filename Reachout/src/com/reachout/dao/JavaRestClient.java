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

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) {

		//List<Charity> list1 = new JavaRestClient().callService(uri);
		List<Charity> list2 = new JavaRestClient().readLocalCharity();

		List<Charity> listAllCharities = new ArrayList<Charity>();
		//listAllCharities.addAll(list1);
		listAllCharities.addAll(list2);

		for (Charity ch : listAllCharities) {

			System.out.println("" + ch.getCharityID());

		}

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
