import java.util.List;
import java.util.ArrayList;
import java.util.TreeSet;

/**
 * @author patrick
 * 
 * Enthaelt alle notenwendigen Funktionalitaeten und Informationen der Evidenztheorie fuer einen Frame.
 * Diese Klasse ermittelt anhand der Daten fuer einen Frame die notwendigen Basismaße sowie die
 * Akkumulation und stellt zudem notwendige Funktionen wie belief, zweifel und plausibilitaet zur Verfuegung
 *
 */
public class Evidenz {
	private double meanStirn = 527.5; // Mean aus Testdaten
	private double stdDeviationStirn = 339.2;
	private double minStirn = 104;
	private double maxStirn = 1103;
	private double meanAugen = 18.02; // Mean aus Testdaten
	private double stdDeviationAugen = 6.498;
	private double minAugen = 9;
	private double maxAugen = 30;
	private double tolerance = 0.1;
	private Basismas m1, m2, m3, m12, m123;
	private Frame frame;
	private String[] allEmotions = {"verachtung","ekel","wut","ueberraschung","angst","freude"};
	public Evidenz(Frame frame) {
		this.frame = frame;
		// erster Draft fuer die Festlegung der Basismaße, Werte sind voellig willkuerlich gewaehlt
		ArrayList<TeilmengeBM> teilmengen = new ArrayList<TeilmengeBM>();
		double evidenz = 1/(1+Math.exp(-((Math.abs((frame.getPixelStirnfalten()-minStirn)/(maxStirn-minStirn))*2-1.5))));
		if(frame.getPixelStirnfalten() <= meanStirn*(1-tolerance))
			teilmengen.add(new TeilmengeBM(new Emotions[]{Emotions.WUT}, evidenz));
		else if(frame.getPixelStirnfalten() >= meanStirn*(1+tolerance))
			teilmengen.add(new TeilmengeBM(new Emotions[]{Emotions.ANGST,Emotions.UEBERRASCHUNG}, evidenz));
		else
			teilmengen.add(new TeilmengeBM(new Emotions[]{Emotions.EKEL,Emotions.FREUDE,Emotions.VERACHTUNG}, evidenz));
		teilmengen.add(new TeilmengeBM(Emotions.all(), 1-evidenz));
		m1 = new Basismas("m1", teilmengen);
		
		teilmengen = new ArrayList<TeilmengeBM>();
		evidenz = 1/(1+Math.exp(-((Math.abs((frame.getPixelAugen()-minAugen)/(maxAugen-minAugen))*2-1.5))));
		if(frame.getPixelAugen() <= meanAugen*(1-tolerance))
			teilmengen.add(new TeilmengeBM(new Emotions[]{Emotions.VERACHTUNG,Emotions.EKEL}, evidenz));
		else if(frame.getPixelAugen() >= meanAugen*(1+tolerance))
			teilmengen.add(new TeilmengeBM(new Emotions[]{Emotions.ANGST,Emotions.UEBERRASCHUNG}, evidenz));
		else
			teilmengen.add(new TeilmengeBM(new Emotions[]{Emotions.WUT,Emotions.FREUDE}, evidenz));
		
		teilmengen.add(new TeilmengeBM(Emotions.all(), 1-evidenz));
		m2 = new Basismas("m2", teilmengen);
		
		/**
		 * TODO: Angst und Freude genau gleich gewichten oder unterschied machen?
		 * Beim nicht bewegen bzw nach innen bewegen der Mundwinkel ist keine Emotion definiert
		 * sollte man dann wirklich die Menge ohne Freude und Angst verwenden, oder sollte
		 * z.B. Angst ebenfalls einflie�en, denn erst ist nur "in einigen F�llen"?
		 */
		teilmengen = new ArrayList<TeilmengeBM>();
		if(frame.getMundwinkel() > 0) {
			teilmengen.add(new TeilmengeBM(new Emotions[]{Emotions.FREUDE,Emotions.ANGST}, 0.4));
			teilmengen.add(new TeilmengeBM(Emotions.all(), 0.6));
		}
		else {
			teilmengen.add(new TeilmengeBM(new Emotions[]{Emotions.UEBERRASCHUNG,Emotions.WUT,Emotions.VERACHTUNG,Emotions.EKEL}, 0.2));
			teilmengen.add(new TeilmengeBM(Emotions.all(), 0.8));
		}
		m3 = new Basismas("m3", teilmengen);
		
		m12 = new Basismas("m12", m1, m2);
		m123 = new Basismas("m123", m12, m3);
	}
	
	public Frame getFrame() {
		return frame;
	}
	
	public Emotions getMostLiklyEmotion() {
		return m123.getMostLiklyEmotion();
	}
	public void printM123(){
		m123.printBasismas();
	}
}
