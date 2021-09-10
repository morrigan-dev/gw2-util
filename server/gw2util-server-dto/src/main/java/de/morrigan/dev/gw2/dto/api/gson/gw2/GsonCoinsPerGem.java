package de.morrigan.dev.gw2.dto.api.gson.gw2;

import com.google.gson.annotations.SerializedName;

/**
 * Kapselt das Ergebnis des GW2 API Endpunktes, der den aktuellen Gold zu Edelstein Wechselkurs zurückgibt. Beibei gibt
 * es zwei mögliche Endpunkte, die diese Rückgabeform benutzen:<br>
 * - /v2/commerce/exchange/gems<br>
 * - /v2/commerce/exchange/coins
 * 
 * @author morrigan
 */
public class GsonCoinsPerGem {

	/** Die Anzahl an Kupfer, die man in Edelsteine tauschen möchte. */
	@SerializedName("coins_per_gem")
	private long coinsPerGem;

	/** Die Anzahl an Edelsteinen, die man für die Menge an Gold bekommt. */
	@SerializedName("quantity")
	private long quantity;

	public GsonCoinsPerGem(long coinsPerGem, long quantity) {
		super();
		this.coinsPerGem = coinsPerGem;
		this.quantity = quantity;
	}

	public long getCoinsPerGem() {
		return this.coinsPerGem;
	}

	public long getQuantity() {
		return this.quantity;
	}

	public void setCoinsPerGem(long coinsPerGem) {
		this.coinsPerGem = coinsPerGem;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("[: coinsPerGem").append(this.coinsPerGem);
		sb.append(", quantity: ").append(this.quantity);
		sb.append("]");
		return sb.toString();
	}
}
