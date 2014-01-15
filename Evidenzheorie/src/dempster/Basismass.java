package dempster;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
/**
 * 
 * @author Dennis Schmidt, Patrick Kalmbach
 *
 * Haelt eine Alternativmenge an Emotionen sowie die dafuer errechnete Evidenz
 */
public class Basismass {
	/**
	 * Liste mit allen Teilmengen einschlieﬂlich Omega.
	 */
	private ArrayList<TeilmengeBM> teilmengen;
	
	/**
	 * Name fuer das Basismas
	 */
	private String name;
	
	/**
	 * Konstruktor, nimmt einen Bezeichner fuer das Basismass entgegen und
	 * initialisiert eine leere Liste fuer die einzelnen Alternativmengen
	 * @param name	Bezeichner des Basismass
	 */
	public Basismass(String name){
		this.name = name;
		this.teilmengen = new ArrayList<TeilmengeBM>();
	}
	
	/**
	 * Konstruktor, nimmt einen Bezeichner fuer das Basismass und eine Liste von
	 * Alternativmengen entgegen
	 * @param name			Bezeichner des Basismass
	 * @param teilmengen	Alternativmengen des Basimass
	 */
	public Basismass(String name, ArrayList<TeilmengeBM> teilmengen){
		this.name = name;
		this.teilmengen = teilmengen;
	}
	
	/**
	 * Erzeugt ein neues Basismas durch akkumulation von zweier existierender
	 * Basismasse
	 * 
	 * @param name	Bezeichner des Basismasses
	 * @param m1	Das erste Basismass
	 * @param m2	Das zweite Basismass
	 */
	@SuppressWarnings("unchecked")
	public Basismass(String name, Basismass m1, Basismass m2){
		// Erzeugen der Akkumulation, der Umweg ueber die tmp Varable ist notwendig, da Java keine
		// Funktion bietet, die die Schnittmenge zweier Mengen zurueckgibt, mit retainAll werden nur die
		// Elemente aus einem Set entfernt, welche NICHT im zweiten (dem das der Funktion uebergeben wird)
		// enthalten sind
		this.name = name;
		this.teilmengen = new ArrayList<TeilmengeBM>();
		
		TreeSet<Emotion> tmp;
		for(TeilmengeBM m1T: m1.getTeilmengen()){
			for(TeilmengeBM m2T: m2.getTeilmengen()){
				tmp = (TreeSet<Emotion>) m1T.getEmotions().clone();
				tmp.retainAll(m2T.getEmotions());
				teilmengen.add(new TeilmengeBM(tmp, m1T.getEvidenz()*m2T.getEvidenz()));
			}
		}
		
		// Alle Teilmengen nach Leerenmengen durch suchen und korrigieren.
		ArrayList<TeilmengeBM> emptyTBM = new ArrayList<TeilmengeBM>();
		
		// Suche nach leeren Teilmengen und fuege sie der Liste emptyTBM hinzu
		for(TeilmengeBM tbm: teilmengen){
			if(tbm.getEmotions().isEmpty()){
				emptyTBM.add(tbm);
			}
		}
		
		// Fuer jede leere Teilmenge wende die Konfliktregel auf alle
		// Teilmengen an
		for(TeilmengeBM tbm: emptyTBM){
			double korrektur = 1/(1-tbm.getEvidenz());
			teilmengen.remove(tbm);
			for(TeilmengeBM tbm1 : teilmengen){
				tbm1.setEvidenz(tbm1.getEvidenz()*korrektur);
			}
		}
	}

	public ArrayList<TeilmengeBM> getTeilmengen() {
		return teilmengen;
	}

	public void setTeilmengen(ArrayList<TeilmengeBM> teilmengen) {
		this.teilmengen = teilmengen;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void addTeilmenge(TeilmengeBM teilmenge){
		teilmengen.add(teilmenge);
	}
	
	/**
	 * Errechnet den Glaube an eine uebergeben alternativenmenge
	 * @param emotions
	 * @return belief
	 */
	public double glaube(TreeSet<Emotion> emotions) {
		double belief = 0;
		for(TeilmengeBM tbm : this.teilmengen)
			if(tbm.getEmotions().containsAll(emotions))
				belief += tbm.getEvidenz();
		return belief;
	}
	
	/**
	 * Errechnet den Zweifel an eine uebergebene Menge. Der Zweifel ist definiert als 
	 * der Glaube an die Alternativmenge der Menge, hier also an alle Emotionen ausser an
	 * die, die mit emotions uebergeben wurden.
	 * @param emotions	Menge von Emotionen fuer die der Zweifel berechnet werden soll
	 * @return 			Zweifel an in emotions uebergebene Emotionen
	 */
	public double zweifel(TreeSet<Emotion> emotions) {
		TreeSet<Emotion> alternatives = new TreeSet<Emotion>();
		alternatives.add(Emotion.ANGST);
		alternatives.add(Emotion.UEBERRASCHUNG);
		alternatives.add(Emotion.WUT);
		alternatives.add(Emotion.FREUDE);
		alternatives.add(Emotion.VERACHTUNG);
		alternatives.add(Emotion.EKEL);
		
		alternatives.removeAll(emotions);
		return glaube(alternatives);
	}
	
	/**
	 * Gibt die Plausibilitaet einer Menge von Emotionen zurueck
	 * @param emotions	Menge von Emotionen fuer die die Plausibilitaet berechnet werden soll
	 * @return 			Plausibilitaet
	 */
	@SuppressWarnings("unchecked")
	public double plausibilitaet(TreeSet<Emotion> emotions) {
		TreeSet<Emotion> tmp;
		double plausibility = 0;
		
		for(TeilmengeBM tbm : this.teilmengen) {
			tmp = (TreeSet<Emotion>)emotions.clone();
			tmp.retainAll(tbm.getEmotions());
			if(!tmp.isEmpty())
				plausibility += tbm.getEvidenz();
		}
		return plausibility;
	}
	
	/**
	 * Gibt die Emotion zurueck, fuer die der Glaube maximal ist
	 * @return	Liste von Emotionen mit fuer die der Glaube maximal ist
	 */
	public List<Emotion> getMostLikelyEmotion() {
		List<Emotion> emotion = new ArrayList<Emotion>();
		double value = 0;
		double tmp;
		Emotion[] emotions = Emotion.all();
		TreeSet<Emotion> set = new TreeSet<Emotion>();
		
		for(Emotion s : emotions) {
			set.add(s);
			tmp = this.glaube(set);
			if(tmp >= value) {
				value = tmp;
				emotion.add(s);
			}
			set.clear();
		}
		
		return emotion;
	}
	
	/**
	 * Gibt den Namen des Basismasses sowie enthaltene Teilmengen mit zugehoeriger
	 * Evidenz aus
	 */
	public void printBasismass(){
		System.out.println("-----"+this.name+"-----");
		for(TeilmengeBM tbm: this.teilmengen){
			System.out.println("\t# "+tbm.getEmotions().toString()+": "+tbm.getEvidenz());
		}
	}
}
