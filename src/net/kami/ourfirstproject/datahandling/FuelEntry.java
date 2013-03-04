package net.kami.ourfirstproject.datahandling;

public class FuelEntry {
	private double liters;
	private double kilometers;
	private String date;
	private double usage;

	public FuelEntry(double liters, double kilometers, String date, double usage) {
		this.liters = liters;
		this.kilometers = kilometers;
		this.date = date;
		this.usage = usage;

	}

	public double getLiters() {
		return liters;
	}

	public void setLiters(double liters) {
		this.liters = liters;
	}

	public double getKilometers() {
		return kilometers;
	}

	public void setKilometers(double kilometers) {
		this.kilometers = kilometers;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public double getUsage() {
		return usage;
	}

	public void setUsage(double usage) {
		this.usage = usage;
	}

}
