package com.sopovs.moradanen.zkoss.popups;

public class Accessories {
	boolean abs;
	boolean airbag;
	boolean gps;
	boolean keyless;

	public Accessories() {
	}

	public Accessories(boolean abs, boolean airbag, boolean gps, boolean keyless) {
		this.abs = abs;
		this.airbag = airbag;
		this.gps = gps;
		this.keyless = keyless;
	}

	public boolean isAbs() {
		return abs;
	}

	public void setAbs(boolean abs) {
		this.abs = abs;
	}

	public boolean isAirbag() {
		return airbag;
	}

	public void setAirbag(boolean airbag) {
		this.airbag = airbag;
	}

	public boolean isGps() {
		return gps;
	}

	public void setGps(boolean gps) {
		this.gps = gps;
	}

	public boolean isKeyless() {
		return keyless;
	}

	public void setKeyless(boolean keyless) {
		this.keyless = keyless;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		if(abs)
			sb.append(", ABS");
		if(airbag)
			sb.append(", Airbag");
		if(gps)
			sb.append(", GPS");
		if(keyless)
			sb.append(", Keyless");
		if(sb.length() <= 0)
			sb.append("None");
		else
			sb.delete(0, 2);
		return sb.toString();
	}
}
