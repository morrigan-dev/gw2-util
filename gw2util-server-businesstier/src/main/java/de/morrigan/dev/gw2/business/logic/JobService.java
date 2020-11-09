package de.morrigan.dev.gw2.business.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opencsv.CSVWriter;

import de.morrigan.dev.gw2.business.constants.HTTPConstants;
import de.morrigan.dev.gw2.business.local.interfaces.IJobService;
import de.morrigan.dev.gw2.dao.interfaces.IGemsStatisticDAO;
import de.morrigan.dev.gw2.dao.interfaces.IItemDAO;
import de.morrigan.dev.gw2.dao.interfaces.IUserDAO;
import de.morrigan.dev.gw2.dao.interfaces.IWeaponDAO;
import de.morrigan.dev.gw2.dto.api.gson.gw2.GsonCoinsPerGem;
import de.morrigan.dev.gw2.dto.api.gson.gw2.GsonInfusionSlots;
import de.morrigan.dev.gw2.dto.api.gson.gw2.GsonIngredients;
import de.morrigan.dev.gw2.dto.api.gson.gw2.GsonItemDetails;
import de.morrigan.dev.gw2.dto.api.gson.gw2.GsonItemIds;
import de.morrigan.dev.gw2.dto.api.gson.gw2.GsonRecipesDetails;
import de.morrigan.dev.gw2.dto.api.gson.gw2.GsonRecipesIds;
import de.morrigan.dev.gw2.dto.api.gson.gw2.GsonWeapon;
import de.morrigan.dev.gw2.dto.common.enums.DamageType;
import de.morrigan.dev.gw2.dto.common.enums.ItemRarity;
import de.morrigan.dev.gw2.dto.common.enums.ItemType;
import de.morrigan.dev.gw2.dto.common.enums.Language;
import de.morrigan.dev.gw2.dto.common.enums.WeaponType;
import de.morrigan.dev.gw2.dto.exceptions.ServiceException;
import de.morrigan.dev.gw2.entity.GemsStatistic;
import de.morrigan.dev.gw2.entity.Item;
import de.morrigan.dev.gw2.entity.User;
import de.morrigan.dev.gw2.entity.Weapon;
import de.morrigan.dev.gw2.utils.exceptions.PersistenceException;

@Stateless
@Local(IJobService.class)
public class JobService implements IJobService {

	/** Logger für Debugausgaben */
	private static final Logger LOG = LoggerFactory.getLogger(JobService.class);

	private static final int GEMS_QUANTITY = 100;
	private static final int COINS_QUANTITY = 10000;
	private static final int ITEMS_PER_CALL = 100;

	@EJB
	private IUserDAO userDAO;

	@EJB
	private IGemsStatisticDAO gemsStatisticDAO;

	@EJB
	private IItemDAO itemDAO;

	@EJB
	private IWeaponDAO weaponDAO;

	@Override
	public void receiveGemsStatisticValue() throws ServiceException {
		final Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();

		StringBuilder gemsUrl = new StringBuilder();
		gemsUrl.append(HTTPConstants.GW2_API_URL_GEMS);
		gemsUrl.append("?");
		gemsUrl.append(HTTPConstants.GW2_API_PARAM_QUANTITY);
		gemsUrl.append("=");
		gemsUrl.append(GEMS_QUANTITY);

		StringBuilder coinsUrl = new StringBuilder();
		coinsUrl.append(HTTPConstants.GW2_API_URL_COINT);
		coinsUrl.append("?");
		coinsUrl.append(HTTPConstants.GW2_API_PARAM_QUANTITY);
		coinsUrl.append("=");
		coinsUrl.append(COINS_QUANTITY);

		GsonCoinsPerGem gemsPerGold = null;
		GsonCoinsPerGem coinsPerGem = null;

		InputStream gemsInputStream = null;
		InputStream coinsInputStream = null;
		BufferedReader gemsReader = null;
		BufferedReader coinsReader = null;
		try {
			// Lese Anzahl an Edelsteine, die man für ein Gold erhält
			gemsInputStream = new URL(gemsUrl.toString()).openStream();
			gemsReader = new BufferedReader(new InputStreamReader(gemsInputStream, "UTF-8"));
			gemsPerGold = gson.fromJson(gemsReader, GsonCoinsPerGem.class);

			// Lese Anzahl an Kupfer, die man für ein Edelstein erhält
			coinsInputStream = new URL(coinsUrl.toString()).openStream();
			coinsReader = new BufferedReader(new InputStreamReader(coinsInputStream, "UTF-8"));
			coinsPerGem = gson.fromJson(coinsReader, GsonCoinsPerGem.class);

		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		} finally {
			if (gemsReader != null) {
				try {
					gemsReader.close();
				} catch (IOException e) {
					LOG.error(e.getMessage(), e);
				}
			}
			if (coinsReader != null) {
				try {
					coinsReader.close();
				} catch (IOException e) {
					LOG.error(e.getMessage(), e);
				}
			}
		}

		try {
			User systemUser = this.userDAO.findById(User.SYS_ADMIN_ID);
			Date curDate = new Date();
			long coinsToGem = (gemsPerGold == null) ? 0 : gemsPerGold.getCoinsPerGem();
			long gemToCoins = coinsPerGem == null ? 0 : coinsPerGem.getCoinsPerGem();
			GemsStatistic gemsStatisctic = new GemsStatistic(0, curDate, systemUser, curDate, systemUser, coinsToGem,
					gemToCoins);
			this.gemsStatisticDAO.save(gemsStatisctic);
		} catch (PersistenceException e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e);
		}
	}

	@Override
	public long[] receiveItemIds() throws ServiceException {
		final Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();

		GsonItemIds itemIds = null;
		InputStream itemIdsInputStream = null;
		BufferedReader itemIdsReader = null;
		long[] ids = new long[0];
		try {

			// Lese sämtliche Item IDs
			itemIdsInputStream = new URL(HTTPConstants.GW2_API_URL_ITEMS).openStream();
			itemIdsReader = new BufferedReader(new InputStreamReader(itemIdsInputStream, "UTF-8"));
			itemIds = gson.fromJson(itemIdsReader, GsonItemIds.class);
			ids = itemIds.getIds();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		} finally {
			if (itemIdsReader != null) {
				try {
					itemIdsReader.close();
				} catch (IOException e) {
					LOG.error(e.getMessage(), e);
				}
			}
		}
		return ids;
	}

	@Override
	public void receiveItems(long[] ids) throws ServiceException {
		LOG.debug("ids: {}", Arrays.asList(ids));

		final Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();

		GsonItemDetails itemDetails = null;
		InputStream itemDetailsInputStream = null;
		BufferedReader itemDetailsReader = null;
		long id = 0;
		try {

			for (Language lang : Language.values()) {
				for (int i = 0; i < ids.length; i++) {
					id = ids[i];
					// Lese zu jeder Item ID die Item Details
					StringBuilder url = new StringBuilder();
					url.append(HTTPConstants.GW2_API_URL_ITEM_DETAILS);
					url.append("?");
					url.append(HTTPConstants.GW2_API_PARAM_ITEM_ID);
					url.append("=");
					url.append(id);
					url.append("&");
					url.append(HTTPConstants.GW2_API_PARAM_LANG);
					url.append("=");
					url.append(lang.getApiValue());
					LOG.info("url = " + url);
					itemDetailsInputStream = new URL(url.toString()).openStream();
					itemDetailsReader = new BufferedReader(new InputStreamReader(itemDetailsInputStream, "UTF-8"));
					itemDetails = gson.fromJson(itemDetailsReader, GsonItemDetails.class);

					long externalId = itemDetails.getItemId();
					LOG.info("Prüfe " + externalId + " - " + itemDetails.getName());
					Item itemToSave = this.itemDAO.findByExternalId(externalId, lang);

					User systemUser = this.userDAO.findById(User.SYS_ADMIN_ID);
					Date curDate = new Date();

					// Item Daten
					Date createDate = curDate;
					User createUser = systemUser;
					Date updateDate = curDate;
					User updateUser = systemUser;
					String name = itemDetails.getName();
					String description = itemDetails.getDescription();
					ItemType type = ItemType.getValueOf(itemDetails.getType());
					int level = itemDetails.getLevel();
					ItemRarity rarity = ItemRarity.getValueOf(itemDetails.getRarity());
					long vendorValue = itemDetails.getVendorValue();
					long iconFileId = itemDetails.getIconFileId();
					String iconFileSignature = itemDetails.getIconFileSignature();
					long defaultSkin = itemDetails.getDefaultSkin();

					if (itemToSave == null) {
						itemToSave = new Item(createDate, createUser, updateDate, updateUser, externalId, name,
								description, type, level, rarity, vendorValue, iconFileId, iconFileSignature,
								defaultSkin, lang, null);
					} else {
						itemToSave.setUpdateDate(updateDate);
						itemToSave.setUpdateUser(updateUser);
						itemToSave.setName(name);
						itemToSave.setDescription(description);
						itemToSave.setType(type);
						itemToSave.setLevel(level);
						itemToSave.setRarity(rarity);
						itemToSave.setVendorValue(vendorValue);
						itemToSave.setIconFileId(iconFileId);
						itemToSave.setIconFileSignature(iconFileSignature);
						itemToSave.setDefaultSkin(defaultSkin);
						itemToSave.setLanguage(lang);
					}

					Item savedItem = this.itemDAO.save(itemToSave);

					// Weapon Daten
					GsonWeapon weapon = itemDetails.getWeapon();
					if (weapon != null) {
						WeaponType weaponType = WeaponType.getValueOf(weapon.getType());
						DamageType damageType = DamageType.getValueOf(weapon.getDamageType());
						int minPower = weapon.getMinPower();
						int maxPower = weapon.getMaxPower();
						int defense = weapon.getDefense();
						StringBuilder infusionSlots = new StringBuilder();
						for (GsonInfusionSlots slot : weapon.getInfusionSlots()) {
							StringBuilder flags = new StringBuilder();
							for (String flag : slot.getFlags()) {
								flags.append(flag);
								flags.append(",");
							}
						}

						Item suffixItem = getItemFromGsonIdField(weapon.getSuffixItemId(), lang);
						Item secondarySuffixItem = getItemFromGsonIdField(weapon.getSecondarySuffixItemId(), lang);

						Weapon weaponToSave = savedItem.getWeapon();
						if (weaponToSave == null) {
							weaponToSave = new Weapon(weaponType, damageType, minPower, maxPower, defense,
									infusionSlots.toString(), suffixItem, secondarySuffixItem);
						} else {
							weaponToSave.setWeaponType(weaponType);
							weaponToSave.setDamageType(damageType);
							weaponToSave.setMinPower(minPower);
							weaponToSave.setMaxPower(maxPower);
							weaponToSave.setDefense(defense);
							weaponToSave.setInfusionSlots(infusionSlots.toString());
							weaponToSave.setSuffixItem(suffixItem);
							weaponToSave.setSecondarySuffixItem(secondarySuffixItem);
						}
						this.weaponDAO.save(weaponToSave);
					}

				}
			}

		} catch (Exception e) {
			LOG.error(e.getMessage() + " Failed ID: " + id, e);
		} finally {
			if (itemDetailsReader != null) {
				try {
					itemDetailsReader.close();
				} catch (IOException e) {
					LOG.error(e.getMessage(), e);
				}
			}
		}
	}

	@Override
	public void receiveRecipes(long[] ids) throws ServiceException {
		LOG.debug("ids: {}", Arrays.asList(ids));

		final Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();

		GsonRecipesDetails recipesDetails = null;
		InputStream recipesDetailsInputStream = null;
		BufferedReader recipesDetailsReader = null;
		long id = 0;
		try {

			CSVWriter csvRecipesDetails = new CSVWriter(new FileWriter(new File("recipesdetails.csv"), true));

			for (int i = 0; i < ids.length; i++) {
				id = ids[i];
				LOG.info("*** ID = " + id + " ***");
				// Lese zu jeder Rezept ID die Rezept Details
				StringBuilder url = new StringBuilder();
				url.append(HTTPConstants.GW2_API_URL_RECIPES_DETAILS);
				url.append("?");
				url.append(HTTPConstants.GW2_API_PARAM_RECIPE_ID);
				url.append("=");
				url.append(id);
				recipesDetailsInputStream = new URL(url.toString()).openStream();
				recipesDetailsReader = new BufferedReader(new InputStreamReader(recipesDetailsInputStream, "UTF-8"));
				recipesDetails = gson.fromJson(recipesDetailsReader, GsonRecipesDetails.class);
				GsonIngredients[] incredients = recipesDetails.getIngredients();
				StringBuilder incr = new StringBuilder();
				for (GsonIngredients gsonIngredients : incredients) {
					incr.append("\"");
					incr.append(gsonIngredients.getItemId());
					incr.append("\",\"");
					incr.append(gsonIngredients.getCount());
					incr.append("\",");
				}

				csvRecipesDetails.writeNext(new String[] {
						//
						Long.toString(recipesDetails.getRecipeId()), //
						recipesDetails.getType(), //
						Long.toString(recipesDetails.getOutputItemId()), //
						Long.toString(recipesDetails.getOutputItemCount()), //
						Long.toString(recipesDetails.getMinRating()), //
						Long.toString(recipesDetails.getTimeToCraftMS()), //
						Arrays.toString(recipesDetails.getDisciplines()), //
						Arrays.toString(recipesDetails.getFlags()), //
						incr.toString(), //
				});
			}

			csvRecipesDetails.flush();
			csvRecipesDetails.close();

		} catch (Exception e) {
			LOG.error(e.getMessage() + " Failed ID: " + id, e);
		} finally {
			if (recipesDetailsReader != null) {
				try {
					recipesDetailsReader.close();
				} catch (IOException e) {
					LOG.error(e.getMessage(), e);
				}
			}
		}
	}

	@Override
	public long[] receiveRecipesIds() throws ServiceException {
		final Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();

		GsonRecipesIds recipesIds = null;
		InputStream recipesIdsInputStream = null;
		BufferedReader recipesIdsReader = null;
		long[] ids = new long[0];
		try {

			// Lese sämtliche Rezepte IDs
			recipesIdsInputStream = new URL(HTTPConstants.GW2_API_URL_RECIPES).openStream();
			recipesIdsReader = new BufferedReader(new InputStreamReader(recipesIdsInputStream, "UTF-8"));
			recipesIds = gson.fromJson(recipesIdsReader, GsonRecipesIds.class);
			ids = recipesIds.getIds();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		} finally {
			if (recipesIdsReader != null) {
				try {
					recipesIdsReader.close();
				} catch (IOException e) {
					LOG.error(e.getMessage(), e);
				}
			}
		}
		return ids;
	}

	@Override
	public void updateItems() throws ServiceException {
		long[] receiveItemIds = receiveItemIds();
		for (int index = 0; index < receiveItemIds.length; index += ITEMS_PER_CALL) {
			int endIndex = index + ITEMS_PER_CALL;
			if (endIndex > receiveItemIds.length) {
				endIndex = receiveItemIds.length;
			}
			long[] idsToLoad = Arrays.copyOfRange(receiveItemIds, index, endIndex);
			LOG.info("Lade die nächsten " + ITEMS_PER_CALL + " Items...");
			receiveItems(idsToLoad);
		}
	}

	private Item getItemFromGsonIdField(String itemId, Language language) throws ServiceException {
		LOG.debug("itemId: {}, language: {}", itemId, language);

		Item result = null;
		try {
			if ((itemId != null) && !itemId.isEmpty()) {
				long idToLoad = Long.valueOf(itemId);
				result = this.itemDAO.findByExternalId(idToLoad, language);
			}
		} catch (NumberFormatException e) {
			LOG.error(e.getMessage(), e);
		} catch (PersistenceException e) {
			LOG.error(e.getMessage(), e);
			throw new ServiceException(e);
		}
		return result;
	}
}
