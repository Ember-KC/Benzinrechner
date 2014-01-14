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

    // Privater Default-Konstruktor wird für das Importieren von FuelEntries aus einer XML-Datei benötigt.
    // Das Framework Simple XML verwendet den Default-Konstruktor per Reflection.
    private FuelEntry(){

    };

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

//    @Override
//    public boolean equals(Object o) {
//        return super.equals(o);
//    }
//
//    @Override
//    public int hashCode() {
//        int result = 31 + age;
//        result = 31 * result + ((name == null) ? 0 : name.hashCode());
//        long temp = Double.doubleToLongBits( kilometers );
//        result = 31 * result + (int) (temp ^ (temp >>> 32));
//
//        return result;
//    }
}
