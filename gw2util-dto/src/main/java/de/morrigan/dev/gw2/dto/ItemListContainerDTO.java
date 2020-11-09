package de.morrigan.dev.gw2.dto;

import java.util.ArrayList;
import java.util.List;

import de.morrigan.dev.gw2.dto.common.Protocol;

public class ItemListContainerDTO extends ResultDTO {

	private static final long serialVersionUID = 1L;

	private List<ItemDTO> items = new ArrayList<ItemDTO>();

	public ItemListContainerDTO() {
		super(new Protocol());
	}

	public ItemListContainerDTO(Protocol protocol) {
		super(protocol);
	}

	public ItemListContainerDTO(Protocol protocol, List<ItemDTO> items) {
		super(protocol);
		this.items = items;
	}

	public List<ItemDTO> getItems() {
		return this.items;
	}

	public void setItems(List<ItemDTO> items) {
		this.items = items;
	}

}
