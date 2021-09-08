package cz.zweistein.gw2.app.eventwatch;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cz.zweistein.gw2.api.GW2API;
import cz.zweistein.gw2.api.dto.Event;
import cz.zweistein.gw2.api.dto.enums.EventState;
import cz.zweistein.gw2.api.util.SupportedLanguage;
import cz.zweistein.gw2.app.eventwatch.gui.EventDialog;

public class EventWatcherModel {
	
	private boolean initialized;

	private Map<String, String> eventNames;
	private Map<Long, String> worldNames;
	private GW2API api;
	private EventDialog gui;

	private String selectedEvent;
	private Long selectedWorld;

	private EventState eventState;

	public EventWatcherModel() throws RemoteException {
		super();
		
		initialized = false;

		this.eventState = EventState.INVALID;

		this.api = new GW2API();

		SupportedLanguage language = SupportedLanguage.resolve(Locale.getDefault().getLanguage());

		this.eventNames = api.getEventNames(language);
		this.worldNames = api.getWorldNames(language);
		
		initialized = true;

	}

	public boolean getInitialized() {
		return this.initialized;
	}

	public Map<String, String> getEventNames() {
		return this.eventNames;
	}

	public Map<Long, String> getWorldNames() {
		return this.worldNames;
	}

	public String getSelectedEvent() {
		return this.selectedEvent;
	}

	public Long getSelectedWorld() {
		return this.selectedWorld;
	}

	public EventState getEventState() {
		return this.eventState;
	}

	public void setSelectedEvent(String selectedEvent) {
		this.selectedEvent = selectedEvent;
		this.eventState = EventState.INVALID;
	}

	public void setSelectedWorld(Long selectedWorld) {
		this.selectedWorld = selectedWorld;
		this.eventState = EventState.INVALID;
	}

	public void setGui(EventDialog gui) {
		this.gui = gui;
	}

	public void update() {

		try {
			List<Event> eventState = api.getEvents(selectedEvent, null, selectedWorld);
			
			if (eventState.size() > 0) {
				
				this.eventState = eventState.get(0).getEventState();
				
				gui.updateEventState();
			}
		} catch (RemoteException e) {
			System.out.println(e);
		}

	}

}
