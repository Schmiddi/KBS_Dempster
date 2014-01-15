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
	private TreeSet<Emotion> emotions;
	/**
	 * Die Evidenz die diesem Basismas zugeordnet ist.
	 */
	private double evidenz;
	
	/**
	 * Konstruktor fuer die Klasse TeilmengeBM, die eine Teilmenge eines Basismass
	 * repraesentiert.
	 * 
	 * @param emotions Eine Liste von Emotionen, die die Teilmenge definieren.
	 * @param evidenz Der Evidenzwert der zu der Teilmenge gehoert.
	 */
	public TeilmengeBM(Emotion[] emotions, double evidenz){
		this.emotions = new TreeSet<Emotion>();
		for(Emotion s : emotions)
			this.emotions.add(s);
		this.evidenz = evidenz;
	}
	
	/**
	 * Konstruktor fuer die Klasse TeilmengeBM, die eine Teilmenge eines Basismass
	 * repraesentiert.
	 * 
	 * @param emotions Ein Set von Emotionen, das die Teilmenge definieren.
	 * @param evidenz Der Evidenzwert der zu der Teilmenge gehoert.
	 */
	public TeilmengeBM(TreeSet<Emotion> emotions, double evidenz) {
		this.emotions = emotions;
		this.evidenz = evidenz;
	}

	/*
	 * Getter und Setter.
	 */
	public TreeSet<Emotion> getEmotions() {
		return emotions;
	}

	public void setEmotions(TreeSet<Emotion> emotions) {
		this.emotions = emotions;
	}

	public double getEvidenz() {
		return evidenz;
	}

	public void setEvidenz(double evidenz) {
		this.evidenz = evidenz;
	}
}
