package de.morrigan.dev.gw2.dto.remote.interfaces;

import java.math.BigDecimal;
import java.util.Vector;

import de.morrigan.dev.gw2.dto.AuthenticateDTO;
import de.morrigan.dev.gw2.dto.exceptions.ServiceException;

public interface IRemoteAuthenticationService {

	String echo(String msg);

	/**
	 * Diese Methode führt eine Authorisierung durch und prüft, ob die Logindaten zulässig sind, die der Client gesendet
	 * hat. Sind diese zulässig, so wird eine Session angelegt bzw. eine vorhandene Session aktualisiert und dem Client
	 * der Sessionschlüssel mitgeteilt.
	 * 
	 * @param auth beinhaltet Anmeldeinformationen. (not null)
	 * @return ein DTO mit der Session für den Client. (not null)
	 * @throws ServiceException falls bei der Authentifikation ein Fehler auftritt.
	 * @throws IllegalArgumentException falls einer der Parameter ungültig ist.
	 */
	AuthenticateDTO authenticate(AuthenticateDTO auth) throws ServiceException;

	/**
	 * Diese Methode ermittelt die Rechte des Benutzers, der diese Methode aufruft. Es werden nur dann Rechte ermittelt,
	 * wenn der Benutzer eine gültige Session besitzt.
	 * 
	 * @param authDTO
	 * @return
	 * @throws ServiceException
	 */
	Vector<String> getRightsOfUser(final AuthenticateDTO authDTO) throws ServiceException;

	/**
	 * @return die aktuelle Version des Clients, die mit dieser Serverversion kompatibel ist.
	 */
	BigDecimal getVersion();

	/**
	 * Diese Methode löscht physisch die Session eines Benutzers, sodass dieser sich beim nächsten Mal wieder einloggen
	 * muss. Die Session wird über den Session Schlüssel ermittelt.
	 * 
	 * @param auth Ein DTO mit der Session vom Client. (not null)
	 * @throws ServiceException falls während des Abmeldens ein Fehler auftritt.
	 */
	void logout(final AuthenticateDTO auth) throws ServiceException;

	/**
	 * Erstellt einen neuen Benutzer, sofern der Benutzername nicht bereits vergeben ist.
	 * 
	 * @param auth beinhaltet Anmeldeinformationen. (not null)
	 * @param username ein Benutzername
	 * @param password ein Passwort
	 * @throws ServiceException falls während der Verarbeitung ein Fehler auftritt.
	 */
	void registerNewUser(AuthenticateDTO authDTO, String username, String password) throws ServiceException;
}
