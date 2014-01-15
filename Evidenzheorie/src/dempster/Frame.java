package dempster;

/**
 * 
 * @author Dennis Schmidt, Patrick Kalmbach
 *
 * Haelt die Informationen fuer einen Frame aus den Daten bereit
 */
public class Frame {
	/**
	 * Die Nummer des Frames.
	 */
	private int id;
	/**
	 * Die Anzahl an Stirnfalten.
	 */
	private int pixelStirnfalten;
	/**
	 * Der Wert fuer den Mundwinkel.
	 */
	private int mundwinkel;
	/**
	 * Der Wert fuer die Augenoeffnung.
	 */
	private int pixelAugen;
	
	/**
	 * Konstruktor der einen Frame aus einer Textzeile erzeugt.
	 * 
	 * @param line Textzeile mit den Daten fuer das Frame, es gilt folgende
	 * 			   Reihenfolge "Frame ID","Stirnfalten","Mundwinkel"
	 * 			   und "Augenoeffnung". 
	 */
	public Frame(String line) {
		String[] tokens;
		if(line.matches("\\d*;\\d*;\\d*;\\d*")) {
			tokens = line.split(";");
			
			try{
				id = Integer.parseInt(tokens[0]);
				pixelStirnfalten = Integer.parseInt(tokens[1]);
				mundwinkel = Integer.parseInt(tokens[2]);
				pixelAugen = Integer.parseInt(tokens[3]);
			}
			catch(NumberFormatException e) {
				System.err.println("Could not create frame for data line " + line);
				e.printStackTrace();
			}
		}
	}

	/*
	 * Setter und Getter Funktionen.
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
