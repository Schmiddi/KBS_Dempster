import java.util.TreeSet;

/**
 * 
 * @author Patrick Kalmbach
 * 
 * Haelt eine Alternativmenge an Emotionen sowie die defuer errechnete Evidenz.
 * Ist ein Teil eines Basismases
 */
public class TeilmengeBM {	
	/**
	 * Enthaelt Emotionen fuer die dieses Basismas erzeugt werden soll
	 */
	private TreeSet<String> emotions;
	/**
	 * Die Evidenz die diesem Basismas zugeordnet ist
	 */
	private double evidenz;
	
	/**
	 * Konstruktor fuer die Klasse Basismas, die ein Basismas der Evidenztheorie
	 * repraesentiert
	 * @param emotions
	 * @param evidenz
	 */
	public TeilmengeBM(String[] emotions, double evidenz){
		this.emotions = new TreeSet<String>();
		for(String s : emotions)
			this.emotions.add(s);
		this.evidenz = evidenz;
	}
	
	public TeilmengeBM(TreeSet<String> emotions, double evidenz) {
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
}
