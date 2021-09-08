package cz.zweistein.gw2.api.dao;

import java.rmi.RemoteException;
import java.util.List;

import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class OfflineJsonDaoTest {

	private JsonDao dao;

	@Before
	public void init() throws RemoteException {
		dao = new OfflineJsonDao();
	}

	@Test
	public void getRecipes() throws RemoteException {

		Assert.assertNotNull(dao.getRecipes());

		Assert.assertTrue(dao.getRecipes().keySet().size() > 0);

		Assert.assertNotNull((((List<?>) (dao.getRecipes().get("recipes"))).get(0)));

	}

	@Test
	public void getRecipeDetails() throws RemoteException {

		Long recipeId = (Long) (((List<?>) (dao.getRecipes().get("recipes"))).get(0));

		JSONObject recipe = dao.getRecipeDetails(recipeId, null);

		Assert.assertEquals(recipeId, Long.valueOf((String) recipe.get("recipe_id")));

	}

	@Test
	public void getItems() throws RemoteException {

		Assert.assertNotNull(dao.getItems());

		Assert.assertTrue(dao.getItems().keySet().size() > 0);

		Assert.assertNotNull((((List<?>) (dao.getItems().get("items"))).get(0)));

	}

	@Test
	public void getItemDetails() throws RemoteException {

		Long itemId = (Long) (((List<?>) (dao.getItems().get("items"))).get(0));

		JSONObject item = dao.getItemDetails(itemId, null);

		Assert.assertEquals(itemId, Long.valueOf((String) item.get("item_id")));

	}

	@Test
	public void getWvWObjectiveNames() throws RemoteException {

		Assert.assertNotNull(dao.getWvWObjectiveNames(null));

		Assert.assertTrue(dao.getWvWObjectiveNames(null).size() > 0);

	}

	@Test
	public void getEventNames() throws RemoteException {

		Assert.assertNotNull(dao.getEventNames(null));

		Assert.assertTrue(dao.getEventNames(null).size() > 0);

	}

	@Test
	public void getWorldNames() throws RemoteException {

		Assert.assertNotNull(dao.getWorldNames(null));

		Assert.assertTrue(dao.getWorldNames(null).size() > 0);

	}

	@Test
	public void getMapNames() throws RemoteException {

		Assert.assertNotNull(dao.getMapNames(null));

		Assert.assertTrue(dao.getMapNames(null).size() > 0);

	}
	
	@Test
	public void getColors() throws RemoteException {
		
		Assert.assertNotNull(dao.getColors());
		
	}

}
