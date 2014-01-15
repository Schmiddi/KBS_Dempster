package dempster;

/**
 * 
 * @author Dennis Schmidt, Patrick Kalmbach
 *
 * Haelt die Informationen fuer einen Frame aus den Daten bereit
 */
public class Frame {
	private int id;
	private int pixelStirnfalten;
	private int mundwinkel;
	private int pixelAugen;
	
	public Frame(int id, int pixelStirnfalten, int mundwinkel, int pixelAugen) {
		this.id = id;
		this.pixelAugen = pixelAugen;
		this.mundwinkel = mundwinkel;
		this.pixelStirnfalten = pixelStirnfalten;
	}

	/*
	 * Setter und Getter Funktionen
	 */
	public int getPixelStirnfalten() {
		return pixelStirnfalten;
	}

	public void setPixelStirnfalten(int pixelStirnfalten) {
		this.pixelStirnfalten = pixelStirnfalten;
	}

	public int getMundwinkel() {
		return mundwinkel;
	}

	public void setMundwinkel(int mundwinkel) {
		this.mundwinkel = mundwinkel;
	}

	public int getPixelAugen() {
		return pixelAugen;
	}

	public void setPixelAugen(int pixelAugen) {
		this.pixelAugen = pixelAugen;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
