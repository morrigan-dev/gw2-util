package cz.zweistein.gw2.api.dto.items;

public class Buff {
	
	private String skillId;
	
	private String description;

	public Buff(String skill_id, String description) {
		super();
		this.skillId = skill_id;
		this.description = description;
	}

	public String getSkillId() {
		return skillId;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return "Buff [skill_id=" + skillId + ", description=" + description + "]";
	}

}
