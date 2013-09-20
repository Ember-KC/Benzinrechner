package net.kami.ourfirstproject.datahandling;

import org.simpleframework.xml.Element;

@Element
public class FuelEntry implements Comparable<FuelEntry> {
	@Element
	private double liters;
	@Element
	private double kilometers;
	@Element
	private String date;
	@Element
	private double usage;

	public FuelEntry(final double pLiters, final double pKilometers,
			final String pDate, final double pUsage) {
		this.liters = pLiters;
		this.kilometers = pKilometers;
		this.date = pDate;
		this.usage = pUsage;

	}

	public final double getLiters() {
		return liters;
	}

	public final void setLiters(final double pLiters) {
		this.liters = pLiters;
	}

	public final double getKilometers() {
		return kilometers;
	}

	public final void setKilometers(final double pKilometers) {
		this.kilometers = pKilometers;
	}

	public final String getDate() {
		return date;
	}

	public final void setDate(final String pDate) {
		this.date = pDate;
	}

	public final double getUsage() {
		return usage;
	}

	public final void setUsage(final double pUsage) {
		this.usage = pUsage;
	}

	@Override
	public int compareTo(FuelEntry another) {
		return this.getDate().compareTo(another.getDate());
	}

}
