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
	private Basismas m1, m2, m3, m12, m123;
	private Frame frame;
	/**
	 * TODO: Emotionen in Enumertion ‰ndern!
	 */
	private String[] allEmotions = {"verachtung","ekel","wut","ueberraschung","angst","freude"};
	public Evidenz(Frame frame) {
		this.frame = frame;
		// erster Draft fuer die Festlegung der Basisma√üe, Werte sind voellig willkuerlich gewaehlt
		/** TODO: Ich wuerde nicht groesser kleiner mean machen, sondern in der Mitte einen Bereich der ebenfalls
		 * undefiniert ist, z.b. 10-20% der means. 
		 */
		ArrayList<TeilmengeBM> teilmengen = new ArrayList<TeilmengeBM>();
		if(frame.getPixelStirnfalten() <= meanStirn)
			teilmengen.add(new TeilmengeBM(new String[]{"wut"}, 0.4));
		else
			teilmengen.add(new TeilmengeBM(new String[]{"angst", "ueberraschung"}, 0.4));
		teilmengen.add(new TeilmengeBM(allEmotions, 0.6));
		m1 = new Basismas("m1", teilmengen);
		
		teilmengen = new ArrayList<TeilmengeBM>();
		if(frame.getPixelAugen() <= meanAugen)
			teilmengen.add(new TeilmengeBM(new String[]{"verachtung", "ekel"}, 0.35));
		else
			teilmengen.add(new TeilmengeBM(new String[]{"angst", "ueberraschung"}, 0.35));
		teilmengen.add(new TeilmengeBM(allEmotions, 0.65));
		m2 = new Basismas("m2", teilmengen);
		
		/**
		 * TODO: Angst und Freude genau gleich gewichten oder unterschied machen?
		 * Beim nicht bewegen bzw nach innen bewegen der Mundwinkel ist keine Emotion definiert
		 * sollte man dann wirklich die Menge ohne Freude und Angst verwenden, oder sollte
		 * z.B. Angst ebenfalls einflieﬂen, denn erst ist nur "in einigen F‰llen"?
		 */
		teilmengen = new ArrayList<TeilmengeBM>();
		if(frame.getMundwinkel() > 0) {
			teilmengen.add(new TeilmengeBM(new String[]{"freude", "angst"}, 0.4));
			teilmengen.add(new TeilmengeBM(allEmotions, 0.6));
		}
		else {
			teilmengen.add(new TeilmengeBM(new String[]{"ueberraschung", "wut", "verachtung", "ekel"}, 0.2));
			teilmengen.add(new TeilmengeBM(allEmotions, 0.8));
		}
		m3 = new Basismas("m3", teilmengen);
		
		m12 = new Basismas("m12", m1, m2);
		m123 = new Basismas("m123", m12, m3);
	}
	
	public Frame getFrame() {
		return frame;
	}
	
	public String getMostLiklyEmotion() {
		return m123.getMostLiklyEmotion();
	}
	public void printM123(){
		m123.printBasismas();
	}
}
