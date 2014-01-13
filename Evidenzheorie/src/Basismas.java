import java.util.ArrayList;
import java.util.TreeSet;
/**
 * 
 * @author Dennis Schmidt, Patrick Kalmbach
 *
 * Haelt eine Alternativmenge an Emotionen sowie die dafuer errechnete Evidenz
 */
public class Basismas {
	/**
	 * Liste mit allen Teilmengen einschlie�lich Omega,
	 * kein Set das es identische Teilmengen geben kann.
	 */
	private ArrayList<TeilmengeBM> teilmengen;
	
	/**
	 * Name fuer das Basismas
	 */
	private String name;
	
	public Basismas(String name){
		this.name = name;
		this.teilmengen = new ArrayList<TeilmengeBM>();
	}
	
	public Basismas(String name, ArrayList<TeilmengeBM> teilmengen){
		this.name = name;
		this.teilmengen = teilmengen;
	}
	
	/**
	 * Erzeugt ein neues Basismas durch akkumulation von zwei Basismasen
	 * 
	 * @param name	 Name des Basismases
	 * @param m1	Das erste Basismas
	 * @param m2	Das zweite Basismas
	 */
	public Basismas(String name, Basismas m1, Basismas m2){
		/**
		 * TODO: Ein neues Basismas durch Akkumulation erstellen
		 */
		// Erzeugen der Akkumulation, der Umweg ueber die tmp Varable ist notwendig, da Java keine
		// Funktion bietet, die die Schnittmenge zweier Mengen zurueckgibt, mit retainAll werden nur die
		// Elemente aus einem Set entfernt, welche NICHT im zweiten (dem das der Funktion uebergeben wird)
		// enthalten sind
		this.name = name;
		this.teilmengen = new ArrayList<TeilmengeBM>();
		
		TreeSet<String> tmp;
		for(TeilmengeBM m1T: m1.getTeilmengen()){
			for(TeilmengeBM m2T: m2.getTeilmengen()){
				tmp = (TreeSet<String>) m1T.getEmotions().clone();
				tmp.retainAll(m2T.getEmotions());
				teilmengen.add(new TeilmengeBM(tmp, m1T.getEvidenz()*m2T.getEvidenz()));
			}
		}
		
		// Alle Teilmengen nach Leerenmengen durch suchen und korrigieren.
		ArrayList<TeilmengeBM> emptyTBM = new ArrayList<TeilmengeBM>();
		
		for(TeilmengeBM tbm: teilmengen){
			if(tbm.getEmotions().isEmpty()){
				emptyTBM.add(tbm);
			}
		}
		System.out.println(emptyTBM);
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
	 * Errechnet den Belief an eine uebergeben alternativenmenge
	 * @param emotions
	 * @return belief
	 */
	public double belief(TreeSet<String> emotions) {
		double belief = 0;
		for(TeilmengeBM tbm : this.teilmengen)
			if(tbm.getEmotions().containsAll(emotions))
				belief += tbm.getEvidenz();
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
		
		for(TeilmengeBM tbm : this.teilmengen) {
			tmp = (TreeSet<String>)emotions.clone();
			tmp.retainAll(tbm.getEmotions());
			if(!tmp.isEmpty())
				plausibility += tbm.getEvidenz();
		}
		return plausibility;
	}
	
	/**
	 * Gibt die Emotion zurueck, fuer die die Belieffunktion den hoechsten Wert ermittelt
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
	
	public void printBasismas(){
		System.out.println("-----"+this.name+"-----");
		for(TeilmengeBM tbm: this.teilmengen){
			System.out.println("# "+tbm.getEmotions().toString()+": "+tbm.getEvidenz());
		}
	}
}
