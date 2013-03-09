package net.kami.ourfirstproject.datahandling;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class FuelEntryList {

	@ElementList
	public List<FuelEntry> fuelEntries = new ArrayList<FuelEntry>();

}
