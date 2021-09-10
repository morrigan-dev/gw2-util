package cz.zweistein.gw2.app.discovery;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;

import cz.zweistein.gw2.api.GW2API;
import cz.zweistein.gw2.api.dao.OfflineJsonDao;
import cz.zweistein.gw2.api.dto.Ingredient;
import cz.zweistein.gw2.api.dto.Recipe;
import cz.zweistein.gw2.api.dto.enums.CraftingDiscipline;
import cz.zweistein.gw2.api.dto.enums.CraftingFlag;

public class Discovery {

	public static void main(String[] args) throws RemoteException {

		CraftingDiscipline discipline = CraftingDiscipline.CHEF;
		Long maxCraftingLevel = 400L;

		craftingdiscoveryReport(discipline, maxCraftingLevel);

	}

	private static void craftingdiscoveryReport(CraftingDiscipline discipline, Long maxCraftingLevel) throws RemoteException {

		GW2API api = new GW2API();
		api.setDao(new OfflineJsonDao());

		List<Long> recipes = api.getRecipes();

		Map<Long, Long> baseItems = new HashMap<Long, Long>();
		Map<Long, Long> toCraft = new HashMap<Long, Long>();

		List<Long> haveItems = new ArrayList<Long>();
		Queue<Long> needItems = new LinkedList<Long>();
		
		List<Long> discoveryCreatedItems = new LinkedList<Long>();

		Map<Long, Recipe> recipesForItem = new HashMap<Long, Recipe>();

		
		// find discovery recipes and convert recipes to easily searchable map. 
		for (Long recipeId : recipes) {
			Recipe recipe = api.getRecipeDetails(recipeId, null);

			recipesForItem.put(recipe.getOutputItem(), recipe);

			if ((recipe.getCraftingDisciplines().contains(discipline) || discipline == null) && recipe.getFlags().isEmpty()) {
				if (recipe.getRating() <= maxCraftingLevel) {
					needItems.add(recipe.getOutputItem());
					for (int i = 0; i < recipe.getOutputCount(); i++) {
						discoveryCreatedItems.add(recipe.getOutputItem());
					}
				}
			}

		}

		// we have dsicoveries in to-acquire items list, 
		while (needItems.size() > 0) {
			Long itemId = needItems.remove();

			if (haveItems.contains(itemId)) {
				// if we have already crafted needed item and have extras left, use that one
				haveItems.remove(itemId);
			} else {
				// else, get recipe and craft it.
				Recipe recipe = recipesForItem.get(itemId);

				if (recipe != null) {

					if (discoveryCreatedItems.contains(itemId)) {
						// if item is schedulled to be discovered and crafted anyway, we will push it to end of list to craft later.
						discoveryCreatedItems.remove(itemId);
						needItems.add(itemId);
					} else {
						// we just craft it - which means putting ingredients into to-acquire list, output to have-list and recipe to to-do list.
						for (Ingredient ingredient : recipe.getIngredients()) {
							for (int i = 0; i < ingredient.getCount(); i++) {
								needItems.add(ingredient.getItemId());
							}
						}
						for (int i = 0; i < recipe.getOutputCount(); i++) {
							haveItems.add(recipe.getOutputItem());
						}
						Long count = toCraft.get(itemId);
						if (count == null) {
							count = 0L;
						}
						count++;
						toCraft.put(itemId, count);
					}
				} else {
					// we have no recipe recipe, item is base material.
					Long count = baseItems.get(itemId);
					if (count == null) {
						count = 0L;
					}
					count++;
					baseItems.put(itemId, count);
				}
			}

		}

		System.out.println("-- TODO: --");

		for (Entry<Long, Long> item : toCraft.entrySet()) {

			String mode = "";

			if (recipesForItem.get(item.getKey()).getFlags().contains(CraftingFlag.AUTO_LEARNED)) {
				mode = "Automatic";
			} else if (recipesForItem.get(item.getKey()).getFlags().contains(CraftingFlag.LEARNED_FROM_ITEM)) {
				mode = "Recipe Sheet";
			} else {
				mode = "Discovery";
			}

			System.out.println(item.getValue() + ";" + api.getItemDetails(item.getKey(), null).getName() + ";" + api.getItemDetails(item.getKey(), null).getRarity() + ";" + recipesForItem.get(item.getKey()).getRating() + ";" + mode);
		}

		System.out.println("-- TOBUY: --");

		for (Entry<Long, Long> item : baseItems.entrySet()) {
			System.out.println(item.getValue() + ";" + api.getItemDetails(item.getKey(), null).getName());
		}

	}

}
