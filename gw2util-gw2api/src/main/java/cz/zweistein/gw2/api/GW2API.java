package cz.zweistein.gw2.api;

//The MIT License (MIT)
//
//Copyright (c) <2013> <Petr Prokop>
//
//Permission is hereby granted, free of charge, to any person obtaining a copy
//of this software and associated documentation files (the "Software"), to deal
//in the Software without restriction, including without limitation the rights
//to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//copies of the Software, and to permit persons to whom the Software is
//furnished to do so, subject to the following conditions:
//
//The above copyright notice and this permission notice shall be included in
//all copies or substantial portions of the Software.
//
//THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
//THE SOFTWARE.

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.bind.DatatypeConverter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import cz.zweistein.gw2.api.dao.JsonDao;
import cz.zweistein.gw2.api.dao.OnlineJsonDao;
import cz.zweistein.gw2.api.dto.AreaMap;
import cz.zweistein.gw2.api.dto.Color;
import cz.zweistein.gw2.api.dto.Continent;
import cz.zweistein.gw2.api.dto.Event;
import cz.zweistein.gw2.api.dto.EventDetail;
import cz.zweistein.gw2.api.dto.Guild;
import cz.zweistein.gw2.api.dto.MapFloor;
import cz.zweistein.gw2.api.dto.Recipe;
import cz.zweistein.gw2.api.dto.WvWMatch;
import cz.zweistein.gw2.api.dto.WvWMatchDetail;
import cz.zweistein.gw2.api.dto.enums.EventState;
import cz.zweistein.gw2.api.dto.items.Item;
import cz.zweistein.gw2.api.transformer.JSONToJavaTransformer;
import cz.zweistein.gw2.api.util.SupportedLanguage;

public class GW2API {

	private JsonDao dao;
	private JSONToJavaTransformer transformer;

	public GW2API() throws RemoteException {
		this.dao = new OnlineJsonDao();
		this.transformer = new JSONToJavaTransformer();
	}

	public void setDao(JsonDao dao) {
		this.dao = dao;
	}

	public void setTransformer(JSONToJavaTransformer transformer) {
		this.transformer = transformer;
	}

	/**
	 * Return list of discovered item_ids
	 * 
	 * @return
	 * @throws RemoteException
	 */
	@SuppressWarnings("unchecked")
	public List<Long> getItems() throws RemoteException {
		JSONObject obj = dao.getItems();
		return (List<Long>) obj.get("items");
	}

	@SuppressWarnings("unchecked")
	public List<Long> getRecipes() throws RemoteException {
		JSONObject obj = dao.getRecipes();
		return (List<Long>) obj.get("recipes");
	}

	public Map<String, String> getEventNames(SupportedLanguage lang) throws RemoteException {
		JSONArray obj = dao.getEventNames(transformer.translateLang(lang));

		Map<String, String> eventNames = new HashMap<String, String>();

		for (Object object : obj) {
			JSONObject eventName = (JSONObject) object;
			eventNames.put((String) eventName.get("id"), (String) eventName.get("name"));

		}

		return eventNames;
	}

	public Map<Long, String> getWorldNames(SupportedLanguage lang) throws RemoteException {
		JSONArray obj = dao.getWorldNames(transformer.translateLang(lang));

		Map<Long, String> worldNames = new HashMap<Long, String>();

		for (Object object : obj) {
			JSONObject worldName = (JSONObject) object;
			worldNames.put(Long.parseLong((String) worldName.get("id")), (String) worldName.get("name"));

		}

		return worldNames;
	}

	public Map<Long, String> getMapNames(SupportedLanguage lang) throws RemoteException {
		JSONArray obj = dao.getMapNames(transformer.translateLang(lang));

		Map<Long, String> mapNames = new HashMap<Long, String>();

		for (Object object : obj) {
			JSONObject mapName = (JSONObject) object;
			mapNames.put(Long.parseLong((String) mapName.get("id")), (String) mapName.get("name"));

		}

		return mapNames;
	}

	public Map<Long, String> getWvWObjectiveNames(SupportedLanguage lang) throws RemoteException {
		JSONArray obj = dao.getWvWObjectiveNames(transformer.translateLang(lang));

		Map<Long, String> mapNames = new HashMap<Long, String>();

		for (Object object : obj) {
			JSONObject mapName = (JSONObject) object;
			mapNames.put(Long.parseLong((String) mapName.get("id")), (String) mapName.get("name"));

		}

		return mapNames;
	}

	public List<Event> getEvents(String eventId, Long mapId, Long worldId) throws RemoteException {
		JSONObject obj = dao.getEvents(eventId, mapId, worldId);
		JSONArray eventObjects = (JSONArray) obj.get("events");

		List<Event> events = new ArrayList<Event>(eventObjects.size());

		for (Object object : eventObjects) {
			JSONObject eventObject = (JSONObject) object;

			events.add(new Event((String) eventObject.get("event_id"), (Long) eventObject.get("map_id"), (Long) eventObject.get("world_id"), EventState
					.resolve((String) eventObject.get("state"))));
		}

		return events;
	}

	public List<WvWMatch> getWvWMatches() throws RemoteException {
		JSONObject obj = dao.getWvWMatches();

		JSONArray matchObjects = (JSONArray) obj.get("wvw_matches");

		List<WvWMatch> matches = new ArrayList<WvWMatch>(matchObjects.size());

		for (Object object : matchObjects) {
			JSONObject matchObject = (JSONObject) object;
			Date startDate = DatatypeConverter.parseDateTime((String) matchObject.get("start_time")).getTime();
			Date endDate = DatatypeConverter.parseDateTime((String) matchObject.get("end_time")).getTime();
			matches.add(new WvWMatch((String) matchObject.get("wvw_match_id"), (Long) matchObject.get("blue_world_id"), (Long) matchObject.get("red_world_id"),
					(Long) matchObject.get("green_world_id"), startDate, endDate));

		}

		return matches;
	}

	public WvWMatchDetail getWvWMatchDetails(String id) throws RemoteException {
		JSONObject obj = dao.getWvWMatchDetails(id);

		return transformer.transformWvWMatchDetails(obj);
	}

	public Recipe getRecipeDetails(Long id, SupportedLanguage lang) throws RemoteException {
		JSONObject obj = dao.getRecipeDetails(id, transformer.translateLang(lang));

		return transformer.transformRecipe(obj);
	}

	public Item getItemDetails(Long id, SupportedLanguage lang) throws RemoteException {
		JSONObject obj = dao.getItemDetails(id, transformer.translateLang(lang));

		return transformer.transformItem(obj);
	}

	public Guild getGuildDetails(String guildID, String guildName) throws RemoteException {
		JSONObject obj = dao.getGuildDetails(guildID, guildName);

		return transformer.transformGuildDetail(obj);
	}

	public Long getBuild() throws RemoteException {
		JSONObject obj = dao.getBuild();

		return (Long) obj.get("build_id");
	}

	@SuppressWarnings("unchecked")
	public Map<Long, Color> getColors() throws RemoteException {
		JSONObject obj = dao.getColors();

		JSONObject colorsObj = (JSONObject) obj.get("colors");

		Map<Long, Color> colors = new HashMap<Long, Color>();
		for (Entry<Object, Object> entry : (Set<Entry<Object, Object>>) colorsObj.entrySet()) {
			colors.put(Long.valueOf((String) entry.getKey()), transformer.transformColor((JSONObject) entry.getValue()));
		}

		return colors;
	}

	@SuppressWarnings("unchecked")
	public Map<String, EventDetail> getEventDetail(String eventId, SupportedLanguage lang) throws RemoteException {
		JSONObject obj = dao.getEventDetail(eventId, transformer.translateLang(lang));

		Map<String, EventDetail> map = new HashMap<String, EventDetail>();

		JSONObject eventsObj = (JSONObject) obj.get("events");

		for (String key : (Set<String>) eventsObj.keySet()) {
			JSONObject eventObj = (JSONObject) eventsObj.get(key);
			map.put(key, transformer.transformEventDetail(eventObj));
		}

		return map;
	}

	@SuppressWarnings("unchecked")
	public Map<Long, Continent> getContinents(SupportedLanguage lang) throws RemoteException {
		JSONObject continentsObj = (JSONObject) dao.getContinents(transformer.translateLang(lang)).get("continents");

		Map<Long, Continent> continents = new HashMap<Long, Continent>();

		for (String key : (Set<String>) continentsObj.keySet()) {
			JSONObject eventObj = (JSONObject) continentsObj.get(key);
			continents.put(Long.parseLong(key), transformer.transformContinent(eventObj));
		}

		return continents;
	}

	@SuppressWarnings("unchecked")
	public Map<Long, AreaMap> getMapDetail(Long mapId, SupportedLanguage lang) throws RemoteException {
		JSONObject mapsObj = (JSONObject) dao.getMapDetail(mapId, transformer.translateLang(lang)).get("maps");

		Map<Long, AreaMap> maps = new HashMap<Long, AreaMap>();
		
		for (String key : (Set<String>) mapsObj.keySet()) {
			JSONObject mapObj = (JSONObject) mapsObj.get(key);
			maps.put(Long.parseLong(key), transformer.transformAreaMap(mapObj));
		}

		return maps;
	}

	public MapFloor getMapFloor(Long continentId, Long floor, SupportedLanguage lang) throws RemoteException {
		JSONObject mapFloorObj = dao.getMapFloor(continentId, floor, transformer.translateLang(lang));
		return transformer.transformMapFloor(mapFloorObj);
	}

}
