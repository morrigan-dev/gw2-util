package cz.zweistein.gw2.api.dto.enums.items;

public enum GizmoType {
	
	DEFAULT("Default"),
	RENTABLE_CONTRACT_NPC("RentableContractNpc"),
	UNLIMITED_CONSUMABLE("UnlimitedConsumable");
	
	private String techName;

	private GizmoType(String name) {
		this.techName = name;
	}

	public static GizmoType resolve(String name) {

		for (GizmoType state : GizmoType.values()) {
			if (state.techName.equals(name)) {
				return state;
			}
		}
		
		throw new IllegalArgumentException(name);
	}

}
