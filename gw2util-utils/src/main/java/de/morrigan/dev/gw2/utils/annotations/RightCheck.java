package de.morrigan.dev.gw2.utils.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Dient zur Angabe von Berechtigungen.
 * 
 * @author morrigan
 */
// TODO: Prüfen, ob das komplette Berechtigungskonzept als eigenes Modul/Framework aufgebaut werden kann mit dem Ziel
// der Kapselung, Wiederverwendbarkeit und Wartbarkeit.
// Erstellt von morrigan am 08.11.2020
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface RightCheck {

	public enum ChangeType {
		VISIBILITY, CHANGEABILITY;
	}

	public enum Type {
		METHOD, FIELD
	}

	public ChangeType changeType() default ChangeType.VISIBILITY;

	public boolean quietOnFail() default true;

	public String[] rightKeys();

	public Type type() default Type.FIELD;
}
