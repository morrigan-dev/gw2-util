package cz.zweistein.gw2.api.transformer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import cz.zweistein.gw2.api.dto.AreaMap;
import cz.zweistein.gw2.api.dto.Color;
import cz.zweistein.gw2.api.dto.ColorHue;
import cz.zweistein.gw2.api.dto.Continent;
import cz.zweistein.gw2.api.dto.EventDetail;
import cz.zweistein.gw2.api.dto.EventLocation;
import cz.zweistein.gw2.api.dto.Guild;
import cz.zweistein.gw2.api.dto.GuildEmblem;
import cz.zweistein.gw2.api.dto.Heart;
import cz.zweistein.gw2.api.dto.Ingredient;
import cz.zweistein.gw2.api.dto.MapAreaFloor;
import cz.zweistein.gw2.api.dto.MapAreaSector;
import cz.zweistein.gw2.api.dto.MapFloor;
import cz.zweistein.gw2.api.dto.MapRectangle;
import cz.zweistein.gw2.api.dto.MapRegion;
import cz.zweistein.gw2.api.dto.Point2D;
import cz.zweistein.gw2.api.dto.PointOfInterest;
import cz.zweistein.gw2.api.dto.RGB;
import cz.zweistein.gw2.api.dto.Recipe;
import cz.zweistein.gw2.api.dto.Scores;
import cz.zweistein.gw2.api.dto.SkillChallenge;
import cz.zweistein.gw2.api.dto.WvWMap;
import cz.zweistein.gw2.api.dto.WvWMatchDetail;
import cz.zweistein.gw2.api.dto.WvWObjective;
import cz.zweistein.gw2.api.dto.ZRange;
import cz.zweistein.gw2.api.dto.enums.CraftingDiscipline;
import cz.zweistein.gw2.api.dto.enums.CraftingFlag;
import cz.zweistein.gw2.api.dto.enums.EventFlag;
import cz.zweistein.gw2.api.dto.enums.GameType;
import cz.zweistein.gw2.api.dto.enums.GuildEmblemFlag;
import cz.zweistein.gw2.api.dto.enums.LocationType;
import cz.zweistein.gw2.api.dto.enums.PoIType;
import cz.zweistein.gw2.api.dto.enums.Restriction;
import cz.zweistein.gw2.api.dto.enums.WvWMapType;
import cz.zweistein.gw2.api.dto.enums.WvWSide;
import cz.zweistein.gw2.api.dto.enums.items.AttributeType;
import cz.zweistein.gw2.api.dto.enums.items.BagModifier;
import cz.zweistein.gw2.api.dto.enums.items.ConsumableType;
import cz.zweistein.gw2.api.dto.enums.items.ContainerType;
import cz.zweistein.gw2.api.dto.enums.items.DamageType;
import cz.zweistein.gw2.api.dto.enums.items.GatheringType;
import cz.zweistein.gw2.api.dto.enums.items.GizmoType;
import cz.zweistein.gw2.api.dto.enums.items.InfusionSlotFlag;
import cz.zweistein.gw2.api.dto.enums.items.ItemClass;
import cz.zweistein.gw2.api.dto.enums.items.ItemFlag;
import cz.zweistein.gw2.api.dto.enums.items.ItemType;
import cz.zweistein.gw2.api.dto.enums.items.Rarity;
import cz.zweistein.gw2.api.dto.enums.items.ToolType;
import cz.zweistein.gw2.api.dto.enums.items.UnlockType;
import cz.zweistein.gw2.api.dto.enums.items.UpgradeComponentFlag;
import cz.zweistein.gw2.api.dto.enums.items.UpgradeComponentType;
import cz.zweistein.gw2.api.dto.enums.items.WeightClass;
import cz.zweistein.gw2.api.dto.items.Armor;
import cz.zweistein.gw2.api.dto.items.Attribute;
import cz.zweistein.gw2.api.dto.items.Back;
import cz.zweistein.gw2.api.dto.items.Bag;
import cz.zweistein.gw2.api.dto.items.Buff;
import cz.zweistein.gw2.api.dto.items.Consumable;
import cz.zweistein.gw2.api.dto.items.Container;
import cz.zweistein.gw2.api.dto.items.Gathering;
import cz.zweistein.gw2.api.dto.items.Gizmo;
import cz.zweistein.gw2.api.dto.items.InfixUpgrade;
import cz.zweistein.gw2.api.dto.items.InfusionSlot;
import cz.zweistein.gw2.api.dto.items.Item;
import cz.zweistein.gw2.api.dto.items.Tool;
import cz.zweistein.gw2.api.dto.items.Trinket;
import cz.zweistein.gw2.api.dto.items.UpgradeComponent;
import cz.zweistein.gw2.api.dto.items.Weapon;
import cz.zweistein.gw2.api.util.SupportedLanguage;

public class JSONToJavaTransformer {

	private JSONToJavaTransformerUtils utils = new JSONToJavaTransformerUtils();

	public String translateLang(SupportedLanguage lang) {
		if (lang == null) {
			return null;
		} else {
			return lang.getCode();
		}
	}

	private InfixUpgrade parseInfixUpgrade(JSONObject obj) {
		InfixUpgrade infixUpgrade = null;
		if (obj.get("infix_upgrade") != null) {
			JSONObject infixObj = (JSONObject) obj.get("infix_upgrade");
			JSONArray attributesObj = (JSONArray) infixObj.get("attributes");
			List<Attribute> attributes = new ArrayList<Attribute>(attributesObj.size());
			for (Object attributeO : attributesObj) {
				JSONObject attributeObj = (JSONObject) attributeO;

				attributes.add(new Attribute(AttributeType.resolve((String) attributeObj.get("attribute")), Long.parseLong((String) attributeObj
						.get("modifier"))));
			}

			Buff buff = null;
			if (infixObj.get("buff") != null) {
				JSONObject buffObj = (JSONObject) infixObj.get("buff");
				buff = new Buff((String) buffObj.get("skill_id"), (String) buffObj.get("description"));
			}
			infixUpgrade = new InfixUpgrade(buff, attributes);
		}

		return infixUpgrade;
	}

	private List<InfusionSlot> parseInfusionSlots(JSONObject obj) {
		JSONArray infusionSlotsObj = (JSONArray) obj.get("infusion_slots");
		List<InfusionSlot> infusionSlots = new ArrayList<InfusionSlot>(infusionSlotsObj.size());

		for (Object infusionSlotO : infusionSlotsObj) {
			JSONObject infusionSlotObj = (JSONObject) infusionSlotO;

			JSONArray infusionFlagsObj = (JSONArray) infusionSlotObj.get("flags");
			List<InfusionSlotFlag> infusionFlags = new ArrayList<InfusionSlotFlag>(infusionFlagsObj.size());
			for (Object infusionFlagO : infusionFlagsObj) {
				String infusionFlag = (String) infusionFlagO;

				infusionFlags.add(InfusionSlotFlag.resolve(infusionFlag));
			}

			infusionSlots.add(new InfusionSlot(infusionFlags, utils.parseGracefullyLong(infusionSlotObj, "item_id")));

		}
		return infusionSlots;
	}

	public Item transformItem(JSONObject obj) {

		JSONArray gameTypesObj = (JSONArray) obj.get("game_types");
		List<GameType> gameTypes = new ArrayList<GameType>(gameTypesObj.size());
		for (Object gameTypeO : gameTypesObj) {
			gameTypes.add(GameType.resolve((String) gameTypeO));
		}

		JSONArray flagsObj = (JSONArray) obj.get("flags");
		List<ItemFlag> flags = new ArrayList<ItemFlag>(flagsObj.size());
		for (Object flagO : flagsObj) {
			flags.add(ItemFlag.resolve(((String) flagO)));
		}

		JSONArray restrictionsObj = (JSONArray) obj.get("restrictions");
		List<Restriction> restrictions = new ArrayList<Restriction>(restrictionsObj.size());
		for (Object restrictionO : restrictionsObj) {
			restrictions.add(Restriction.resolve((String) restrictionO));
		}

		ItemClass itemClass = ItemClass.resolve((String) obj.get("type"));

		Armor armor = null;
		if (ItemClass.ARMOR.equals(itemClass)) {
			JSONObject armorObj = (JSONObject) obj.get("armor");

			InfixUpgrade infixUpgrade = parseInfixUpgrade(armorObj);
			List<InfusionSlot> infusionSlots = parseInfusionSlots(armorObj);

			armor = new Armor(ItemType.resolve((String) armorObj.get("type")), WeightClass.resolve((String) armorObj.get("weight_class")),
					Long.parseLong((String) armorObj.get("defense")), infusionSlots, infixUpgrade);
		}

		Weapon weapon = null;
		if (ItemClass.WEAPON.equals(itemClass)) {
			JSONObject weaponObj = (JSONObject) obj.get("weapon");

			InfixUpgrade infixUpgrade = parseInfixUpgrade(weaponObj);
			List<InfusionSlot> infusionSlots = parseInfusionSlots(weaponObj);

			weapon = new Weapon(ItemType.resolve((String) weaponObj.get("type")), DamageType.resolve((String) weaponObj.get("damage_type")),
					Long.parseLong((String) weaponObj.get("min_power")), Long.parseLong((String) weaponObj.get("max_power")), Long.parseLong((String) weaponObj
							.get("defense")), infusionSlots, infixUpgrade);
		}

		Trinket trinket = null;
		if (ItemClass.TRINKET.equals(itemClass)) {
			JSONObject trinketObj = (JSONObject) obj.get("trinket");

			InfixUpgrade infixUpgrade = parseInfixUpgrade(trinketObj);
			List<InfusionSlot> infusionSlots = parseInfusionSlots(trinketObj);

			trinket = new Trinket(ItemType.resolve((String) trinketObj.get("type")), infusionSlots, infixUpgrade, utils.parseGracefullyLong(trinketObj,
					"suffix_item_id"));
		}

		Bag bag = null;
		if (ItemClass.BAG.equals(itemClass)) {
			JSONObject bagObj = (JSONObject) obj.get("bag");

			List<BagModifier> bagModifiers = new ArrayList<BagModifier>();

			for (BagModifier bagModifier : BagModifier.values()) {
				if (bagObj.get(bagModifier.getTechName()) != null) {
					bagModifiers.add(bagModifier);
				}
			}

			bag = new Bag(Long.parseLong((String) bagObj.get("size")), bagModifiers);
		}

		Container container = null;
		if (ItemClass.CONTAINER.equals(itemClass)) {
			JSONObject containerObj = (JSONObject) obj.get("container");

			container = new Container(ContainerType.resolve((String) containerObj.get("type")));
		}

		Consumable consumable = null;
		if (ItemClass.CONSUMABLE.equals(itemClass)) {
			JSONObject consumableObj = (JSONObject) obj.get("consumable");

			ConsumableType consumableType = ConsumableType.resolve((String) consumableObj.get("type"));
			UnlockType unlockType = null;
			Long colorId = null;
			Long recipeId = null;
			if (ConsumableType.UNLOCK.equals(consumableType)) {
				unlockType = UnlockType.resolve((String) consumableObj.get("unlock_type"));
				switch (unlockType) {
				case CRAFTING_RECIPE:
					recipeId = Long.parseLong((String) consumableObj.get("recipe_id"));
					break;
				case DYE:
					colorId = Long.parseLong((String) consumableObj.get("color_id"));
					break;
				default:
					break;
				}
			}

			consumable = new Consumable(consumableType, (String) consumableObj.get("description"), utils.parseGracefullyLong(consumableObj, "duration_ms"),
					unlockType, colorId, recipeId);
		}

		UpgradeComponent upgradeComponent = null;
		if (ItemClass.UPGRADE_COMPONENT.equals(itemClass)) {
			JSONObject upgradeComponentObj = (JSONObject) obj.get("upgrade_component");

			InfixUpgrade infixUpgrade = parseInfixUpgrade(upgradeComponentObj);

			List<UpgradeComponentFlag> upgradeComponentFlags = new ArrayList<UpgradeComponentFlag>();
			JSONArray upgradeComponentFlagsObj = (JSONArray) upgradeComponentObj.get("flags");

			for (Object flagO : upgradeComponentFlagsObj) {
				upgradeComponentFlags.add(UpgradeComponentFlag.resolve(((String) flagO)));
			}

			JSONArray infusionSlotFlagsObj = (JSONArray) upgradeComponentObj.get("infusion_upgrade_flags");
			List<InfusionSlotFlag> infusionSlotFlags = new ArrayList<InfusionSlotFlag>(infusionSlotFlagsObj.size());
			for (Object infusionSlotFlagO : infusionSlotFlagsObj) {
				infusionSlotFlags.add(InfusionSlotFlag.resolve(((String) infusionSlotFlagO)));
			}

			List<String> bonuses = null;
			if (upgradeComponentObj.get("bonuses") != null) {
				JSONArray bonusesObj = (JSONArray) upgradeComponentObj.get("bonuses");
				bonuses = new ArrayList<String>(bonusesObj.size());
				for (Object bonusO : bonusesObj) {
					bonuses.add((String) bonusO);
				}
			}

			upgradeComponent = new UpgradeComponent(UpgradeComponentType.resolve((String) upgradeComponentObj.get("type")), upgradeComponentFlags,
					infixUpgrade, (String) upgradeComponentObj.get("suffix"), infusionSlotFlags, bonuses);
		}

		Back back = null;
		if (ItemClass.BACK.equals(itemClass)) {
			JSONObject backObj = (JSONObject) obj.get("back");

			InfixUpgrade infixUpgrade = parseInfixUpgrade(backObj);
			List<InfusionSlot> infusionSlots = parseInfusionSlots(backObj);

			back = new Back(infusionSlots, infixUpgrade, (String) backObj.get("suffix_item_id"));
		}

		Gathering gathering = null;
		if (ItemClass.GATHERING.equals(itemClass)) {
			JSONObject gatheringObj = (JSONObject) obj.get("gathering");

			gathering = new Gathering(GatheringType.resolve((String) gatheringObj.get("type")));
		}

		Gizmo gizmo = null;
		if (ItemClass.GIZMO.equals(itemClass)) {
			JSONObject gizmoObj = (JSONObject) obj.get("gizmo");

			gizmo = new Gizmo(GizmoType.resolve((String) gizmoObj.get("type")));
		}

		Tool tool = null;
		if (ItemClass.TOOL.equals(itemClass)) {
			JSONObject toolObj = (JSONObject) obj.get("tool");

			tool = new Tool(ToolType.resolve((String) toolObj.get("type")), Long.parseLong((String) toolObj.get("charges")));
		}

		return new Item(Long.parseLong((String) obj.get("item_id")), (String) obj.get("name"), (String) obj.get("description"), Long.parseLong((String) obj
				.get("level")), Rarity.resolve((String) obj.get("rarity")), Long.parseLong((String) obj.get("vendor_value")), gameTypes, flags, restrictions,
				utils.parseGracefullyLong(obj, "suffix_item_id"), itemClass, armor, weapon, bag, container, consumable, trinket, upgradeComponent, back,
				gathering, gizmo, tool);

	}

	public Recipe transformRecipe(JSONObject obj) {
		JSONArray ingredientsObj = (JSONArray) obj.get("ingredients");

		List<Ingredient> ingredients = new ArrayList<Ingredient>(ingredientsObj.size());

		for (Object ingredientO : ingredientsObj) {
			JSONObject ingredientObj = (JSONObject) ingredientO;

			ingredients.add(new Ingredient(Long.parseLong((String) ingredientObj.get("count")), (Long.parseLong((String) ingredientObj.get("item_id")))));
		}

		JSONArray flagsObj = (JSONArray) obj.get("flags");
		List<CraftingFlag> flags = new ArrayList<CraftingFlag>(flagsObj.size());
		for (Object flagO : flagsObj) {
			flags.add(CraftingFlag.resolve((String) flagO));
		}

		JSONArray disciplinesObj = (JSONArray) obj.get("disciplines");
		List<CraftingDiscipline> craftingDisciplines = new ArrayList<CraftingDiscipline>(disciplinesObj.size());
		for (Object disciplineO : disciplinesObj) {
			craftingDisciplines.add(CraftingDiscipline.resolve((String) disciplineO));
		}

		return new Recipe(Long.parseLong((String) obj.get("recipe_id")), ingredients, Long.parseLong((String) obj.get("min_rating")),
				Long.parseLong((String) obj.get("time_to_craft_ms")), Long.parseLong((String) obj.get("output_item_id")), Long.parseLong((String) obj
						.get("output_item_count")), ItemType.resolve((String) obj.get("type")), craftingDisciplines, flags);
	}

	public WvWMatchDetail transformWvWMatchDetails(JSONObject obj) {

		JSONArray mapsObj = (JSONArray) obj.get("maps");
		List<WvWMap> wvwMaps = new ArrayList<WvWMap>(mapsObj.size());

		for (Object mapO : mapsObj) {
			JSONObject mapObj = (JSONObject) mapO;

			JSONArray objectivesObj = (JSONArray) mapObj.get("objectives");
			List<WvWObjective> objectives = new ArrayList<WvWObjective>(objectivesObj.size());

			for (Object objectiveO : objectivesObj) {
				JSONObject objectiveObj = (JSONObject) objectiveO;

				objectives.add(new WvWObjective((Long) objectiveObj.get("id"), (String) objectiveObj.get("owner_guild"), WvWSide.resolve((String) objectiveObj
						.get("owner"))));
			}

			JSONArray scores = (JSONArray) mapObj.get("scores");
			wvwMaps.add(new WvWMap(new Scores((Long) scores.get(0), (Long) scores.get(1), (Long) scores.get(2)),
					WvWMapType.resolve((String) mapObj.get("type")), objectives));
		}

		JSONArray scores = (JSONArray) obj.get("scores");
		return new WvWMatchDetail(new Scores((Long) scores.get(0), (Long) scores.get(1), (Long) scores.get(2)), (String) obj.get("match_id"), wvwMaps);
	}

	public Color transformColor(JSONObject colorsObj) {
		ColorHue defaultColor = transformColorHue((JSONObject) colorsObj.get("default"));
		ColorHue metalColor = transformColorHue((JSONObject) colorsObj.get("metal"));
		ColorHue clothColor = transformColorHue((JSONObject) colorsObj.get("cloth"));
		ColorHue leatherColor = transformColorHue((JSONObject) colorsObj.get("leather"));
		RGB rgb = transformRGB((JSONArray) colorsObj.get("base_rgb"));
		return new Color((String) colorsObj.get("name"), defaultColor, metalColor, clothColor, leatherColor, rgb);
	}

	private ColorHue transformColorHue(JSONObject colorHueObj) {
		if (colorHueObj != null) {

			RGB rgb = transformRGB((JSONArray) colorHueObj.get("rgb"));

			return new ColorHue(utils.parseDouble(colorHueObj.get("brightness")), utils.parseDouble(colorHueObj.get("contrast")), utils.parseDouble(colorHueObj
					.get("hue")), utils.parseDouble(colorHueObj.get("saturation")), utils.parseDouble(colorHueObj.get("lightness")), rgb);
		} else {
			return null;
		}
	}

	private RGB transformRGB(JSONArray rgbObj) {
		return new RGB((Long) rgbObj.get(0), (Long) rgbObj.get(1), (Long) rgbObj.get(2));
	}

	public Guild transformGuildDetail(JSONObject obj) {
		JSONObject emblemObj = (JSONObject) obj.get("emblem");

		GuildEmblem emblem = null;
		if (emblemObj != null) {
			JSONArray flagsObj = (JSONArray) emblemObj.get("flags");
			List<GuildEmblemFlag> flags = new ArrayList<GuildEmblemFlag>(flagsObj.size());
			for (Object flagO : flagsObj) {
				flags.add(GuildEmblemFlag.resolve((String) flagO));
			}

			emblem = new GuildEmblem((Long) emblemObj.get("background_id"), (Long) emblemObj.get("foreground_id"), flags,
					(Long) emblemObj.get("foreground_primary_color_id"), (Long) emblemObj.get("foreground_secondary_color_id"),
					(Long) emblemObj.get("background_color_id"));
		}

		return new Guild((String) obj.get("guild_id"), (String) obj.get("tag"), (String) obj.get("guild_name"), emblem);
	}

	public EventDetail transformEventDetail(JSONObject eventObj) {
		JSONArray flagsObj = (JSONArray) eventObj.get("flags");
		List<EventFlag> flags = new ArrayList<EventFlag>(flagsObj.size());
		for (Object flagO : flagsObj) {
			flags.add(EventFlag.resolve((String) flagO));
		}

		JSONObject locationObj = (JSONObject) eventObj.get("location");
		LocationType type = LocationType.resolve((String) locationObj.get("type"));

		ZRange zRange = null;
		List<Point2D> points = new ArrayList<Point2D>();
		if (LocationType.POLY.equals(type)) {

			JSONArray pointsObj = (JSONArray) locationObj.get("points");

			for (Object pointO : pointsObj) {
				points.add(utils.parsePoint2D((JSONArray) pointO));
			}

			JSONArray zRangeObj = (JSONArray) locationObj.get("z_range");

			zRange = new ZRange(utils.parseDouble(zRangeObj.get(0)), utils.parseDouble(zRangeObj.get(1)));

		}

		EventLocation location = new EventLocation(utils.parsePoint3D((JSONArray) locationObj.get("center")), utils.parseDouble(locationObj.get("rotation")),
				utils.parseDouble(locationObj.get("radius")), utils.parseDouble(locationObj.get("height")), zRange, points, type);

		EventDetail eventDetail = new EventDetail(flags, (Long) eventObj.get("map_id"), (Long) eventObj.get("level"), location, (String) eventObj.get("name"));

		return eventDetail;
	}

	public Continent transformContinent(JSONObject continentObj) {
		List<Long> floors = utils.parseLongList((JSONArray) continentObj.get("floors"));

		return new Continent((String) continentObj.get("name"), utils.parsePoint2D((JSONArray) continentObj.get("continent_dims")),
				(Long) continentObj.get("min_zoom"), (Long) continentObj.get("max_zoom"), floors);
	}

	public AreaMap transformAreaMap(JSONObject mapObj) {
		System.out.println(mapObj);

		List<Long> floors = utils.parseLongList((JSONArray) mapObj.get("floors"));
		MapRectangle continentRectangle = utils.parseRectangle((JSONArray) mapObj.get("continent_rect"));
		MapRectangle mapRectangle = utils.parseRectangle((JSONArray) mapObj.get("map_rect"));
		return new AreaMap((String) mapObj.get("continent_name"), (Long) mapObj.get("continent_id"), (String) mapObj.get("region_name"),
				(Long) mapObj.get("region_id"), (String) mapObj.get("map_name"), (Long) mapObj.get("min_level"), (Long) mapObj.get("max_level"), mapRectangle,
				continentRectangle, floors, (Long) mapObj.get("default_floor"));
	}

	public MapFloor transformMapFloor(JSONObject mapFloorObj) {

		JSONObject regionsObj = (JSONObject) mapFloorObj.get("regions");

		Map<Long, MapRegion> regions = new HashMap<Long, MapRegion>();

		@SuppressWarnings("unchecked")
		Set<String> regionKeys = (Set<String>) regionsObj.keySet();
		for (String regionKey : regionKeys) {
			JSONObject regionObj = (JSONObject) regionsObj.get(regionKey);
			Map<Long, MapAreaFloor> maps = new HashMap<Long, MapAreaFloor>();
			JSONObject mapsObj = (JSONObject) regionObj.get("maps");
			@SuppressWarnings("unchecked")
			Set<String> mapKeys = (Set<String>) mapsObj.keySet();
			for (String mapKey : mapKeys) {
				JSONObject mapObj = (JSONObject) mapsObj.get(mapKey);
				MapRectangle continentRectangle = utils.parseRectangle((JSONArray) mapObj.get("continent_rect"));
				MapRectangle mapRectangle = utils.parseRectangle((JSONArray) mapObj.get("map_rect"));

				JSONArray skillChallengesObj = (JSONArray) mapObj.get("skill_challenges");
				List<SkillChallenge> skillChallenges = new ArrayList<SkillChallenge>(skillChallengesObj.size());
				for (Object skillChallengesO : skillChallengesObj) {
					skillChallenges.add(new SkillChallenge(utils.parsePoint2D((JSONArray) ((JSONObject) skillChallengesO).get("coord"))));
				}

				JSONArray poisObj = (JSONArray) mapObj.get("points_of_interest");
				List<PointOfInterest> pois = new ArrayList<PointOfInterest>(poisObj.size());
				for (Object poiO : poisObj) {
					JSONObject poiObj = (JSONObject) poiO;
					pois.add(new PointOfInterest(utils.parsePoint2D((JSONArray) poiObj.get("coord")), (String) poiObj.get("name"), (Long) poiObj.get("floor"),
							(Long) poiObj.get("poi_id"), PoIType.resolve((String) poiObj.get("type"))));
				}

				JSONArray tasksObj = (JSONArray) mapObj.get("tasks");
				List<Heart> tasks = new ArrayList<Heart>(tasksObj.size());
				for (Object tasksO : tasksObj) {
					JSONObject taskObj = (JSONObject) tasksO;
					tasks.add(new Heart(utils.parsePoint2D((JSONArray) taskObj.get("coord")), (Long) taskObj.get("level"), (String) taskObj.get("objective"),
							(Long) taskObj.get("task_id")));
				}

				JSONArray sectorsObj = (JSONArray) mapObj.get("sectors");
				List<MapAreaSector> sectors = new ArrayList<MapAreaSector>(sectorsObj.size());
				for (Object sectorO : sectorsObj) {
					JSONObject sectorObj = (JSONObject) sectorO;
					sectors.add(new MapAreaSector(utils.parsePoint2D((JSONArray) sectorObj.get("coord")), (Long) sectorObj.get("level"), (String) sectorObj
							.get("name"), (Long) sectorObj.get("sector_id")));
				}

				maps.put(Long.parseLong(mapKey), new MapAreaFloor((String) mapObj.get("name"), (Long) mapObj.get("min_level"), (Long) mapObj.get("max_level"),
						mapRectangle, continentRectangle, (Long) mapObj.get("default_floor"), skillChallenges, pois, tasks, sectors));
			}

			regions.put(Long.parseLong(regionKey), new MapRegion((String) regionObj.get("name"), utils.parsePoint2D((JSONArray) regionObj.get("label_coord")),
					maps));
		}

		return new MapFloor(utils.parsePoint2D((JSONArray) mapFloorObj.get("texture_dims")), regions);
	}
}
