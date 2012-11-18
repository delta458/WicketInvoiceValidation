package model;

import java.io.Serializable;

public class Adresse implements Serializable{
    
	private String name;
	private Ort ort;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Ort getOrt() {
		return ort;
	}
	public void setOrt(Ort ort) {
		this.ort = ort;
	}
}
