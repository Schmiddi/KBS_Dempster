import java.util.List;
import java.util.ArrayList;
import java.util.TreeSet;

/**
 * @author patrick
 * 
 * Enthaelt alle notenwendigen Funktionalitaeten und Informationen der Evidenztheorie fuer einen Frame.
 * Diese Klasse ermittelt anhand der Daten fuer einen Frame die notwendigen Basisma√üe sowie die
 * Akkumulation und stellt zudem notwendige Funktionen wie belief, zweifel und plausibilitaet zur Verfuegung
 *
 */
public class Evidenz {
	private double meanStirn = 527.5; // Mean aus Testdaten
	private double meanAugen = 18.02; // Mean aus Testdaten
	private Basismas m1, m2, m3;
	private List<Basismas> akkumulation;
	private Frame frame;
	
	public Evidenz(Frame frame) {
		this.frame = frame;
		// erster Draft fuer die Festlegung der Basisma√üe, Werte sind voellig willkuerlich gewaehlt
		/** TODO: Ich wuerde nicht groesser kleiner mean machen, sondern in der Mitte einen Bereich der ebenfalls
		 * undefiniert ist, z.b. 10-20% der means. 
		 */
		if(frame.getPixelStirnfalten() <= meanStirn)
			m1 = new Basismas(new String[]{"wut"}, 0.4);
		else
			m1 = new Basismas(new String[]{"angst", "ueberraschung"}, 0.4);
		
		if(frame.getPixelAugen() <= meanAugen)
			m2 = new Basismas(new String[]{"verachtung", "ekel"}, 0.35);
		else
			m2 = new Basismas(new String[]{"angst", "ueberraschung"}, 0.35);
		
		/**
		 * TODO: Angst und Freude genau gleich gewichten oder unterschied machen?
		 * Beim nicht bewegen bzw nach innen bewegen der Mundwinkel ist keine Emotion definiert
		 * sollte man dann wirklich die Menge ohne Freude und Angst verwenden, oder sollte
		 * z.B. Angst ebenfalls einflieﬂen, denn erst ist nur "in einigen F‰llen"?
		 */
		if(frame.getMundwinkel() > 0)
			m3 = new Basismas(new String[]{"freude", "angst"}, 0.4);
		else
			m3 = new Basismas(new String[]{"ueberraschung", "wut", "verachtung", "ekel"}, 0.2);
		
		// Erzeugen der Akkumulation, der Umweg ueber die tmp Varable ist notwendig, da Java keine
		// Funktion bietet, die die Schnittmenge zweier Mengen zurueckgibt, mit retainAll werden nur die
		// Elemente aus einem Set entfernt, welche NICHT im zweiten (dem das der Funktion uebergeben wird)
		// enthalten sind
		TreeSet<String> tmp = (TreeSet<String>)m1.getEmotions().clone();
		tmp.retainAll(m2.getEmotions());		
		akkumulation = new ArrayList<>();
		akkumulation.add(new Basismas(tmp, m1.getEvidenz() * m2.getEvidenz()));
		akkumulation.add(new Basismas(m1.getEmotions(), m1.getEvidenz() * m2.getOmega()));
		akkumulation.add(new Basismas(m2.getEmotions(), m2.getEvidenz() * m1.getOmega()));
		
		tmp = (TreeSet<String>)m1.getEmotions().clone();
		tmp.retainAll(m3.getEmotions());
		akkumulation.add(new Basismas(tmp, m1.getEvidenz() * m3.getEvidenz()));
		akkumulation.add(new Basismas(m1.getEmotions(), m1.getEvidenz() * m3.getOmega()));
		akkumulation.add(new Basismas(m3.getEmotions(), m3.getEvidenz() * m1.getOmega()));
		
		tmp = (TreeSet<String>)m2.getEmotions().clone();
		tmp.retainAll(m3.getEmotions());
		akkumulation.add(new Basismas(tmp, m2.getEvidenz() * m3.getEvidenz()));
		akkumulation.add(new Basismas(m2.getEmotions(), m2.getEvidenz() * m3.getOmega()));
		akkumulation.add(new Basismas(m3.getEmotions(), m3.getEvidenz() * m2.getOmega()));
	}
	
	public Frame getFrame() {
		return frame;
	}

	/**
	 * Errechnet den Belief an eine uebergeben alternativenmenge
	 * @param emotions
	 * @return belief
	 */
	public double belief(TreeSet<String> emotions) {
		double belief = 0;
		for(Basismas mas : this.akkumulation)
			if(mas.getEmotions().containsAll(emotions))
				belief += mas.getEvidenz();
		return belief;
	}
	
	/**
	 * Errechnet den Zweifel an eine uebergebene Menge. Der Zweifel ist definiert als 
	 * der Glaube (belief) an die Alternativmenge der Menge, hier also an alle Emotionen auser an
	 * die, die mit emotions uebergeben wurden.
	 * @param emotions
	 * @return Zweifel an in emotions uebergebene Emotionen
	 */
	public double zweifel(TreeSet<String> emotions) {
		TreeSet<String> alternatives = new TreeSet<String>();
		alternatives.add("angst");
		alternatives.add("ueberraschung");
		alternatives.add("wut");
		alternatives.add("freude");
		alternatives.add("verachtung");
		alternatives.add("ekel");
		
		alternatives.removeAll(emotions);
		return belief(alternatives);
	}
	
	/**
	 * Gibt die Plausibilitaet einer Menge von Emotionen zurueck
	 * @param emotions
	 * @return Plausibilitaet
	 */
	public double plausibility(TreeSet<String> emotions) {
		TreeSet<String> tmp;
		double plausibility = 0;
		
		for(Basismas m : this.akkumulation) {
			tmp = (TreeSet<String>)emotions.clone();
			tmp.retainAll(m.getEmotions());
			if(!tmp.isEmpty())
				plausibility += m.getEvidenz();
		}
		return plausibility;
	}
	
	/**
	 * Gibt die Emotion zurueck, fuer die die Belieffunktion den h√∂chsten Wert ermittelt
	 * @return
	 */
	public String getMostLiklyEmotion() {
		String emotion = null;
		double value = 0;
		double tmp;
		String[] emotions = new String[]{"angst","ueberraschung","wut","freude","verachtung","ekel"};
		TreeSet<String> set = new TreeSet<String>();
		
		for(String s : emotions) {
			set.add(s);
			tmp = this.belief(set);
			if(tmp > value) {
				value = tmp;
				emotion = s;
			}
			set.clear();
		}
		
		return emotion;
	}
}
