package cz.zweistein.gw2.api.dto.items;

import cz.zweistein.gw2.api.dto.enums.items.GizmoType;

public class Gizmo {
	
	private GizmoType type;

	public Gizmo(GizmoType type) {
		super();
		this.type = type;
	}

	public GizmoType getType() {
		return type;
	}

	@Override
	public String toString() {
		return "Gizmo [type=" + type + "]";
	}

}
