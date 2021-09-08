package cz.zweistein.gw2.api.dao;

//The MIT License (MIT)
//
// Copyright (c) <2013> <Keven "Varonth" Schulz>
// Copyright (c) <2013> <Petr Prokop>
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in
// all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
// THE SOFTWARE.

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.rmi.RemoteException;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import cz.zweistein.gw2.api.util.ParametersMap;
import cz.zweistein.gw2.api.util.Utilities;

public class OnlineJsonDao implements JsonDao {
	private static final Logger LOGGER = Logger.getLogger(OnlineJsonDao.class.getName());

	public static final String STANDARD_URL = "https://api.guildwars2.com";
	public static final String API_VERSION = "/v1";

	/**
	 * SSL context for calling https url
	 */
	private SSLContext sslCon;

	private String oAuth2Token;

	public OnlineJsonDao() throws RemoteException {
		try {
			sslCon = SSLContext.getInstance("SSL");
			sslCon.init(null, null, new SecureRandom());
		} catch (GeneralSecurityException e) {
			throw new RemoteException(e.getMessage(), e);
		}
	}

	private Reader getContentFromURL(URL url) throws IOException {
		HttpsURLConnection httpConnection = (HttpsURLConnection) url.openConnection();
		if (oAuth2Token != null) {
			httpConnection.addRequestProperty("Authorization", "Bearer " + oAuth2Token);
		}
		httpConnection.setSSLSocketFactory(sslCon.getSocketFactory());
		return new BufferedReader(new InputStreamReader(httpConnection.getInputStream(), Charset.forName("UTF-8")));
	}

	/**
	 * Retrieves JSON Object from URL
	 * 
	 * @param url
	 * @return
	 * @throws RemoteException
	 */
	public JSONObject getJSONObject(URL url) throws RemoteException {
		try {
			JSONObject obj = (JSONObject) new JSONParser().parse(getContentFromURL(url));

			LOGGER.log(Level.FINE, "Returned object " + obj);

			return obj;
		} catch (IOException e) {
			throw new RemoteException(e.toString() + " " + url, e);
		} catch (ParseException e) {
			throw new RemoteException(e.toString(), e);
		}
	}

	/**
	 * Retrieves JSON Array from URL
	 * 
	 * @param url
	 * @return
	 * @throws RemoteException
	 */
	public JSONArray getJSONArray(URL url) throws RemoteException {
		try {
			JSONArray obj = (JSONArray) new JSONParser().parse(getContentFromURL(url));

			LOGGER.log(Level.FINE, "Returned object " + obj);

			return obj;
		} catch (IOException e) {
			throw new RemoteException(e.toString() + " " + url, e);
		} catch (ParseException e) {
			throw new RemoteException(e.toString(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cz.zweistein.gw2.api.dao.JSONDAO#getItems()
	 */
	@Override
	public JSONObject getItems() throws RemoteException {

		return getJSONObject(Utilities.buildQuerryURL("items.json", null));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cz.zweistein.gw2.api.dao.JSONDAO#getItemDetails(java.lang.Long,
	 * java.lang.String)
	 */
	@Override
	public JSONObject getItemDetails(Long id, String lang) throws RemoteException {
		assert (id != null);
		ParametersMap params = new ParametersMap();
		params.putIfNotNull("item_id", id);
		params.putIfNotNull("lang", lang);
		return getJSONObject(Utilities.buildQuerryURL("item_details.json", params));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cz.zweistein.gw2.api.dao.JSONDAO#getRecipes()
	 */
	@Override
	public JSONObject getRecipes() throws RemoteException {
		return getJSONObject(Utilities.buildQuerryURL("recipes.json", null));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cz.zweistein.gw2.api.dao.JSONDAO#getRecipeDetails(java.lang.Long,
	 * java.lang.String)
	 */
	@Override
	public JSONObject getRecipeDetails(Long id, String lang) throws RemoteException {
		assert (id != null);
		ParametersMap params = new ParametersMap();
		params.putIfNotNull("recipe_id", id);
		params.putIfNotNull("lang", lang);
		return getJSONObject(Utilities.buildQuerryURL("recipe_details.json", params));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cz.zweistein.gw2.api.dao.JSONDAO#getWvWMatches()
	 */
	@Override
	public JSONObject getWvWMatches() throws RemoteException {
		return getJSONObject(Utilities.buildQuerryURL("wvw/matches.json", null));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.zweistein.gw2.api.dao.JSONDAO#getWvWMatchDetails(java.lang.String)
	 */
	@Override
	public JSONObject getWvWMatchDetails(String id) throws RemoteException {
		assert (id != null);
		ParametersMap params = new ParametersMap();
		params.putIfNotNull("match_id", id);
		return getJSONObject(Utilities.buildQuerryURL("wvw/match_details.json", params));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cz.zweistein.gw2.api.dao.JSONDAO#getWvWObjectiveNames(java.lang.String)
	 */
	@Override
	public JSONArray getWvWObjectiveNames(String lang) throws RemoteException {
		ParametersMap params = new ParametersMap();
		params.putIfNotNull("lang", lang);
		return getJSONArray(Utilities.buildQuerryURL("wvw/objective_names.json", params));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cz.zweistein.gw2.api.dao.JSONDAO#getEvents(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public JSONObject getEvents(String eventId, Long mapId, Long worldId) throws RemoteException {
		ParametersMap params = new ParametersMap();
		params.putIfNotNull("world_id", worldId);
		params.putIfNotNull("event_id", eventId);
		params.putIfNotNull("map_id", mapId);
		return getJSONObject(Utilities.buildQuerryURL("events.json", params));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cz.zweistein.gw2.api.dao.JSONDAO#getEventNames(java.lang.String)
	 */
	@Override
	public JSONArray getEventNames(String lang) throws RemoteException {
		ParametersMap params = new ParametersMap();
		params.putIfNotNull("lang", lang);
		return getJSONArray(Utilities.buildQuerryURL("event_names.json", params));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cz.zweistein.gw2.api.dao.JSONDAO#getWorldName(java.lang.String)
	 */
	@Override
	public JSONArray getWorldNames(String lang) throws RemoteException {
		ParametersMap params = new ParametersMap();
		params.putIfNotNull("lang", lang);
		return getJSONArray(Utilities.buildQuerryURL("world_names.json", params));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cz.zweistein.gw2.api.dao.JSONDAO#getMapNames(java.lang.String)
	 */
	@Override
	public JSONArray getMapNames(String lang) throws RemoteException {
		ParametersMap params = new ParametersMap();
		params.putIfNotNull("lang", lang);
		return getJSONArray(Utilities.buildQuerryURL("map_names.json", params));
	}

	public void setoAuth2Token(String oAuth2Token) {
		this.oAuth2Token = oAuth2Token;
	}

	@Override
	public JSONObject getGuildDetails(String guildID, String guildName) throws RemoteException {
		ParametersMap params = new ParametersMap();
		params.putIfNotNull("guild_id", guildID);
		params.putIfNotNull("guild_name", guildName);
		return getJSONObject(Utilities.buildQuerryURL("guild_details.json", params));
	}

	@Override
	public JSONObject getBuild() throws RemoteException {
		return getJSONObject(Utilities.buildQuerryURL("build.json", null));
	}

	@Override
	public JSONObject getColors() throws RemoteException {
		return getJSONObject(Utilities.buildQuerryURL("colors.json", null));
	}

	@Override
	public JSONObject getEventDetail(String eventId, String lang) throws RemoteException {
		ParametersMap params = new ParametersMap();
		params.putIfNotNull("event_id", eventId);
		params.putIfNotNull("lang", lang);
		return getJSONObject(Utilities.buildQuerryURL("event_details.json", params));
	}

	@Override
	public JSONObject getContinents(String lang) throws RemoteException {
		ParametersMap params = new ParametersMap();
		params.putIfNotNull("lang", lang);
		return getJSONObject(Utilities.buildQuerryURL("continents.json", params));
	}

	@Override
	public JSONObject getMapDetail(Long mapId, String lang) throws RemoteException {
		ParametersMap params = new ParametersMap();
		params.putIfNotNull("map_id", mapId);
		params.putIfNotNull("lang", lang);
		return getJSONObject(Utilities.buildQuerryURL("maps.json", params));
	}

	@Override
	public JSONObject getMapFloor(Long continentId, Long floor, String lang) throws RemoteException {
		ParametersMap params = new ParametersMap();
		params.putIfNotNull("continent_id", continentId);
		params.putIfNotNull("floor", floor);
		params.putIfNotNull("lang", lang);
		return getJSONObject(Utilities.buildQuerryURL("map_floor.json", params));
	}

}