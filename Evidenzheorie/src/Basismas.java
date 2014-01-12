import java.util.TreeSet;

/**
 * 
 * @author patrick
 *
 * Haelt eine Alternativmenge an Emotionen sowie die dafuer errechnete Evidenz
 */
public class Basismas {
	/**
	 * Enthaelt Emotionen fuer die dieses Basismaß erzeugt werden soll
	 */
	private TreeSet<String> emotions;
	/**
	 * Die Evidenz die diesem Basismaß zugeordnet ist
	 */
	private double evidenz;
	
	/**
	 * Konstruktor fuer die Klasse Basismas, die ein Basismass der Evidenztheorie
	 * repraesentiert
	 * @param emotions
	 * @param evidenz
	 */
	public Basismas(String[] emotions, double evidenz) {
		this.emotions = new TreeSet<String>();
		for(String s : emotions)
			this.emotions.add(s);
		this.evidenz = evidenz;
	}
	
	public Basismas(TreeSet<String> emotions, double evidenz) {
		this.emotions = emotions;
		this.evidenz = evidenz;
	}

	public TreeSet<String> getEmotions() {
		return emotions;
	}

	public void setEmotions(TreeSet<String> emotions) {
		this.emotions = emotions;
	}

	public double getEvidenz() {
		return evidenz;
	}

	public void setEvidenz(double evidenz) {
		this.evidenz = evidenz;
	}
	
	//TODO: Omega ist nicht immer 1-evidenz, Omage kann auch sein 1-evidenzen (plural)
	public double getOmega() {
		return 1 - this.evidenz;
	}
}
