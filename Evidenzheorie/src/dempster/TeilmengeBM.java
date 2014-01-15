package dempster;
import java.util.TreeSet;

/**
 * 
 * @author Dennis Schmidt, Patrick Kalmbach
 * 
 * Haelt eine Alternativmenge an Emotionen sowie die defuer errechnete Evidenz.
 * Ist ein Teil eines Basismasses.
 */
public class TeilmengeBM {	
	/**
	 * Enthaelt Emotionen fuer die dieses Basismass erzeugt werden soll.
	 */
	private TreeSet<Emotion> emotionen;
	/**
	 * Die Evidenz die diesem Basismas zugeordnet ist.
	 */
	private double evidenz;
	
	/**
	 * Konstruktor fuer die Klasse TeilmengeBM, die eine Teilmenge eines Basismass
	 * repraesentiert.
	 * 
	 * @param emotionen Eine Liste von Emotionen, die die Teilmenge definieren.
	 * @param evidenz Der Evidenzwert der zu der Teilmenge gehoert.
	 */
	public TeilmengeBM(Emotion[] emotionen, double evidenz){
		this.emotionen = new TreeSet<Emotion>();
		for(Emotion s : emotionen)
			this.emotionen.add(s);
		this.evidenz = evidenz;
	}
	
	/**
	 * Konstruktor fuer die Klasse TeilmengeBM, die eine Teilmenge eines Basismass
	 * repraesentiert.
	 * 
	 * @param emotionen Ein Set von Emotionen, das die Teilmenge definieren.
	 * @param evidenz Der Evidenzwert der zu der Teilmenge gehoert.
	 */
	public TeilmengeBM(TreeSet<Emotion> emotionen, double evidenz) {
		this.emotionen = emotionen;
		this.evidenz = evidenz;
	}

	/*
	 * Getter und Setter.
	 */
	public TreeSet<Emotion> getEmotionen() {
		return emotionen;
	}

	public void setEmotionen(TreeSet<Emotion> emotionen) {
		this.emotionen = emotionen;
	}

	public double getEvidenz() {
		return evidenz;
	}

	public void setEvidenz(double evidenz) {
		this.evidenz = evidenz;
	}
}
