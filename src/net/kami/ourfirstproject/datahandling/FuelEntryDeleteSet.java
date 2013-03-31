package net.kami.ourfirstproject.datahandling;

import java.util.HashSet;

public class FuelEntryDeleteSet extends HashSet<FuelEntry> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static FuelEntryDeleteSet instance = null;

	/**
	 * Default-Konstruktor, der nicht au�erhalb dieser Klasse aufgerufen werden
	 * kann
	 */
	private FuelEntryDeleteSet() {
	}

	/**
	 * Statische Methode, liefert die einzige Instanz dieser Klasse zur�ck
	 */
	public static FuelEntryDeleteSet getInstance() {
		if (instance == null) {
			instance = new FuelEntryDeleteSet();
		}
		return instance;
	}

}
