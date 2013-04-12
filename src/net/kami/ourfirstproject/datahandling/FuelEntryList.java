package net.kami.ourfirstproject.datahandling;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class FuelEntryList {

	@ElementList
	private List<FuelEntry> fuelEntries = new ArrayList<FuelEntry>();

	public final List<FuelEntry> getFuelEntries() {
		return fuelEntries;
	}

	public final void setFuelEntries(final List<FuelEntry> pFuelEntries) {
		this.fuelEntries = pFuelEntries;
	}

}
