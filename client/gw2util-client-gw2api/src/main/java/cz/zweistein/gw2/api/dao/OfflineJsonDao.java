package cz.zweistein.gw2.api.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class OfflineJsonDao implements JsonDao {

  private JSONObject recipes;
  private JSONObject items;
  private JSONObject colors;
  private JSONArray events;
  private JSONArray maps;
  private JSONArray worlds;
  private JSONArray wvwObjectives;

  public OfflineJsonDao() throws RemoteException {
    try {
      recipes = loadJSONObjectFile("/recipes.json");
      items = loadJSONObjectFile("/items.json");
      colors = loadJSONObjectFile("/colors.json");
      events = loadJsonArrayFile("/events.json");
      maps = loadJsonArrayFile("/maps.json");
      worlds = loadJsonArrayFile("/worlds.json");
      wvwObjectives = loadJsonArrayFile("/wvwobjectives.json");
    } catch (FileNotFoundException e) {
      throw new RemoteException(e.toString(), e);
    } catch (IOException e) {
      throw new RemoteException(e.toString(), e);
    } catch (ParseException e) {
      throw new RemoteException(e.toString(), e);
    }
  }

  private JSONArray loadJsonArrayFile(String fileName) throws FileNotFoundException, IOException, ParseException {
    return (JSONArray) new JSONParser()
        .parse(new InputStreamReader(getClass().getResourceAsStream(fileName), Charset.forName("UTF16")));
  }

  private JSONObject loadJSONObjectFile(String fileName) throws FileNotFoundException, IOException, ParseException {
    //		return (JSONObject) new JSONParser().parse(new InputStreamReader(new FileInputStream(new File(fileName)), Charset.forName("UTF16")));
    System.out.println(getClass().getResourceAsStream(fileName));
    return (JSONObject) new JSONParser().parse(new InputStreamReader(getClass().getResourceAsStream(fileName),
        Charset.forName("UTF16")));
  }

  @SuppressWarnings("unchecked")
  @Override
  public JSONObject getItems() throws RemoteException {
    JSONObject obj = new JSONObject();
    Set<String> ids = items.keySet();
    List<Long> result = new ArrayList<Long>(ids.size());
    for (String object : ids) {
      result.add(Long.valueOf(object));
    }
    obj.put("items", result);
    return obj;
  }

  @Override
  public JSONObject getItemDetails(Long id, String lang) throws RemoteException {
    return (JSONObject) items.get(id.toString());
  }

  @SuppressWarnings("unchecked")
  @Override
  public JSONObject getRecipes() throws RemoteException {
    JSONObject obj = new JSONObject();
    Set<String> ids = recipes.keySet();
    List<Long> result = new ArrayList<Long>(ids.size());
    for (String object : ids) {
      result.add(Long.valueOf(object));
    }
    obj.put("recipes", result);
    return obj;
  }

  @Override
  public JSONObject getRecipeDetails(Long id, String lang) throws RemoteException {
    return (JSONObject) recipes.get(id.toString());
  }

  @Override
  public JSONObject getWvWMatches() throws RemoteException {
    throw new UnsupportedOperationException();
  }

  @Override
  public JSONObject getWvWMatchDetails(String id) throws RemoteException {
    throw new UnsupportedOperationException();
  }

  @Override
  public JSONArray getWvWObjectiveNames(String lang) throws RemoteException {
    return wvwObjectives;
  }

  @Override
  public JSONObject getEvents(String eventId, Long mapId, Long worldId) throws RemoteException {
    throw new UnsupportedOperationException();
  }

  @Override
  public JSONArray getEventNames(String lang) throws RemoteException {
    return events;
  }

  @Override
  public JSONArray getWorldNames(String lang) throws RemoteException {
    return worlds;
  }

  @Override
  public JSONArray getMapNames(String lang) throws RemoteException {
    return maps;
  }

  @Override
  public JSONObject getGuildDetails(String guildID, String guildName) throws RemoteException {
    throw new UnsupportedOperationException();
  }

  @Override
  public JSONObject getBuild() throws RemoteException {
    throw new UnsupportedOperationException();
  }

  @Override
  public JSONObject getColors() throws RemoteException {
    return colors;
  }

  @Override
  public JSONObject getEventDetail(String eventId, String translateLang) throws RemoteException {
    throw new UnsupportedOperationException();
  }

  @Override
  public JSONObject getContinents(String translateLang) throws RemoteException {
    throw new UnsupportedOperationException();
  }

  @Override
  public JSONObject getMapDetail(Long mapId, String translateLang) throws RemoteException {
    throw new UnsupportedOperationException();
  }

  @Override
  public JSONObject getMapFloor(Long continentId, Long floor, String translateLang) throws RemoteException {
    throw new UnsupportedOperationException();
  }

}
