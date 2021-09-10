package de.morrigan.dev.gw2.business.factories;

/**
 * Diese Klasse bietet verschiedene Factory Methoden an, um Serviceklassen zu erzeugen ohne über den EJB Kontext zu
 * gehen. Bitte in der {@link ServiceFactory} einen abstrakten Signatureintrag für jede Methode eintragen.
 * 
 * @author morrigan
 */
public abstract class NoEJBServiceFactory extends ServiceFactory {

	private static final long serialVersionUID = 1L;

}
