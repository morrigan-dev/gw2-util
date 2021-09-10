package cz.zweistein.gw2.app.wvwexplorer;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingUtilities;
import javax.swing.tree.TreePath;

import cz.zweistein.gw2.api.GW2API;
import cz.zweistein.gw2.api.dto.WvWMap;
import cz.zweistein.gw2.api.dto.WvWMatch;
import cz.zweistein.gw2.api.dto.WvWMatchDetail;
import cz.zweistein.gw2.api.dto.WvWObjective;
import cz.zweistein.gw2.api.dto.enums.WvWSide;
import cz.zweistein.gw2.api.util.SupportedLanguage;
import cz.zweistein.gw2.app.wvwexplorer.componentmodel.IUpdateable;
import cz.zweistein.gw2.app.wvwexplorer.componentmodel.WorldNamesComboBoxModel;
import cz.zweistein.gw2.app.wvwexplorer.componentmodel.WorldNamesComboBoxModel.WorldNameEntry;
import cz.zweistein.gw2.app.wvwexplorer.constants.WvWNames;
import cz.zweistein.gw2.app.wvwexplorer.gui.WvWExplorerGui;

public class WvWExplorerModel {

	private GW2API api;
	private WvWExplorerGui gui;
	private MidiSynth sound;

	private String matchId;
	private WvWMatchDetail matchDetails;
	private WvWSide homeSide;
	private Set<Long> watchedObjectives;
	private Map<Long, String> objectiveNames;
	private Map<WvWSide, String> sides;

	private Map<Long, String> worldNames;

	private IUpdateable worldNamesComboBoxModel;
	private IUpdateable wvWTreeModel;

	private boolean initialized;

	public WvWExplorerModel() {
		super();

		matchDetails = new WvWMatchDetail(null, null, new ArrayList<WvWMap>(0));

		worldNames = new HashMap<Long, String>();
	}

	public void init() throws RemoteException {

		gui.setStatusInitializing();

		this.api = new GW2API();
		SupportedLanguage lang = SupportedLanguage.resolve(Locale.getDefault().getLanguage());

		gui.tickStatus();
		this.worldNames = api.getWorldNames(lang);
		worldNamesComboBoxModel.fireChange();
		if (worldNamesComboBoxModel instanceof WorldNamesComboBoxModel) {
			((WorldNamesComboBoxModel) worldNamesComboBoxModel).setSelectedItem(new WorldNameEntry(worldNames.entrySet().iterator().next()));
		}

		gui.tickStatus();
		Long homeWorldId = worldNames.keySet().iterator().next();

		gui.tickStatus();
		this.objectiveNames = api.getWvWObjectiveNames(lang);

		this.watchedObjectives = new HashSet<Long>();
		watchedObjectives.addAll(objectiveNames.keySet());

		changeWorld(homeWorldId);

		this.initialized = true;

	}

	public void changeWorld(Long homeWorldId) {
		gui.setStatusInitializing();

		WvWMatch match = null;
		try {
			match = findMatchForWorld(api.getWvWMatches(), homeWorldId);
			gui.tickStatus();
			this.matchDetails = api.getWvWMatchDetails(match.getMatchId());
			this.matchId = match.getMatchId();
		} catch (RemoteException e) {
			gui.setStatusError(e);
			return;
		}

		this.sides = getSideWorldNames(match, worldNames);
		this.homeSide = getMatchSide(match, homeWorldId);
		wvWTreeModel.fireChange();

		gui.setStatusIdle();

		gui.updateTitle();
	}

	public WvWMatchDetail getMatchDetails() {
		return matchDetails;
	}

	public Map<Long, String> getWorldNames() {
		return worldNames;
	}

	public WvWSide getHomeSide() {
		return homeSide;
	}

	public Map<WvWSide, String> getSides() {
		return sides;
	}

	public Map<Long, String> getObjectiveNames() {
		return this.objectiveNames;
	}

	public Set<Long> getWatchedObjectives() {
		return watchedObjectives;
	}

	public void update() {

		gui.setStatusUpdating();

		WvWMatchDetail newMatchDetails = null;
		try {
			newMatchDetails = api.getWvWMatchDetails(matchId);
		} catch (final RemoteException e) {
			gui.setStatusError(e);
			return;
		}

		gui.tickStatus();

		final List<TreePath> changedPaths = new ArrayList<TreePath>();

		for (WvWMap oldMap : matchDetails.getMaps()) {
			for (WvWMap newMap : newMatchDetails.getMaps()) {
				if (oldMap.getType() == newMap.getType()) {

					List<WvWObjective> oldObjectives = oldMap.getObjectives();
					List<WvWObjective> newObjectives = newMap.getObjectives();
					for (WvWObjective oldObjective : oldObjectives) {
						for (WvWObjective newObjective : newObjectives) {
							if (oldObjective.getObjectiveId().equals(newObjective.getObjectiveId())) {
								if (!oldObjective.getOwner().equals(newObjective.getOwner()) && newObjective.getOwner() == homeSide && watchedObjectives.contains(oldObjective.getObjectiveId())) {
									sound.captured();
									gui.updateLog(WvWNames.map.get(oldMap.getType()) + " " + objectiveNames.get(oldObjective.getObjectiveId()) + " (" + WvWNames.objective.get(oldObjective.getObjectiveId()) + ") Captured from " + sides.get(oldObjective.getOwner()));
								} else if (!oldObjective.getOwner().equals(newObjective.getOwner()) && oldObjective.getOwner() == homeSide && watchedObjectives.contains(oldObjective.getObjectiveId())) {
									sound.lost();
									gui.updateLog(WvWNames.map.get(oldMap.getType()) + " " + objectiveNames.get(oldObjective.getObjectiveId()) + " (" + WvWNames.objective.get(oldObjective.getObjectiveId()) + ") Lost to " + sides.get(newObjective.getOwner()));
								}

								if (!oldObjective.getOwner().equals(newObjective.getOwner())) {
									oldMap.getObjectives().set(oldMap.getObjectives().indexOf(oldObjective), newObjective);
									changedPaths.add(new TreePath(new Object[] { matchDetails, oldMap, oldObjective }));
								}
							}
						}
					}
				}
			}

		}

//		matchDetails = newMatchDetails;

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				wvWTreeModel.fireChange(changedPaths);
			}
		});

		gui.setStatusIdle();

	}

	public void setGui(WvWExplorerGui wvWExplorerGui) {
		this.gui = wvWExplorerGui;
	}

	private static Map<WvWSide, String> getSideWorldNames(WvWMatch match, Map<Long, String> worldNames) {
		Map<WvWSide, String> map = new HashMap<WvWSide, String>();

		map.put(WvWSide.BLUE, worldNames.get(match.getBlueWorldId()));
		map.put(WvWSide.RED, worldNames.get(match.getRedWorldId()));
		map.put(WvWSide.GREEN, worldNames.get(match.getGreenWorldId()));

		return map;
	}

	private static WvWSide getMatchSide(WvWMatch match, Long homeWorldId) {
		if (match.getBlueWorldId().equals(homeWorldId)) {
			return WvWSide.BLUE;
		} else if (match.getRedWorldId().equals(homeWorldId)) {
			return WvWSide.RED;
		} else if (match.getGreenWorldId().equals(homeWorldId)) {
			return WvWSide.GREEN;
		} else {
			return null;
		}
	}

	private static WvWMatch findMatchForWorld(List<WvWMatch> matches, Long worldId) {

		for (WvWMatch wvWMatch : matches) {
			if (wvWMatch.getBlueWorldId().equals(worldId) || wvWMatch.getRedWorldId().equals(worldId) || wvWMatch.getGreenWorldId().equals(worldId)) {
				return wvWMatch;
			}
		}

		return null;

	}

	public void setWorldNamesListener(IUpdateable worldNamesComboBoxModel) {
		this.worldNamesComboBoxModel = worldNamesComboBoxModel;
	}

	public void setWvWTreeListener(IUpdateable wvWTreeModel) {
		this.wvWTreeModel = wvWTreeModel;
	}

	public boolean isInitialized() {
		return initialized;
	}

	public void setSound(MidiSynth sound) {
		this.sound = sound;
	}

}
