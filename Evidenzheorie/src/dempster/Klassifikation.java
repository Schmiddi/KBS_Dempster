package dempster;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Kontrolliert den Daten und Informationsfluss, stoesst berechnung der
 * Evidenzen an und liesst Informationen zu Frames aus Datei aus
 * 
 * @author Dennis Schmidt, Patrick Kalmbach
 *
 */
public class Klassifikation {
	
	/**
	 * Enthaelt alle Frames die in der Quelldatei spezifiziert werden
	 */
	private List<Frame> frames;
	
	/**
	 * Konstruktor, Pfad zu der Datei die die Features in der Form
	 * <id>;<Pixel Stirnfalten>;<Mundwinkel>;<Pixel Augenoeffnung> enthaelt.
	 * Frames werden ausgelesen, Evidenzen berechnet und ausgegeben
	 * @param pfad	Pfad zu der Datei die Feature enthaelt
	 */
	public Klassifikation(String pfad) {
		frames = new ArrayList<Frame>();
		
		try {
			readFrames(pfad);
		}
		catch(FileNotFoundException e) {
			System.err.println("Die Datei " + pfad + " wurde nicht gefunden! Abbruch");
			e.printStackTrace();
			return;			
		}
		catch(IOException e) {
			System.err.println("Die Datei " + pfad + " konnte nicht gelesen werden! Abbruch");
			e.printStackTrace();
			return;
		}
		
		for(Frame f : frames)
			System.out.println("Frame " + f.getId() + " --> " + berechneEvidenz(f));
	}
	
	/**
	 * Liest Eintraege einer Datei in der Features fuer Emotionen extrahiert aus einem Bild
	 * in nachfolgendem Format gespeichert sind aus und erzeugt daraus Frame objekte fuer
	 * die weitere Verarbeitung.
	 * Kann die Datei nicht gefunden oder von ihr gelesen werden wird eine Exception erzeugt
	 * Format: <id>;<Pixel Stirnfalten>;<Mundwinkel>;<Pixel Augenoeffnung>
	 * @param pfad						Pfad zu der Datei die Feature enthaelt
	 * @throws FileNotFoundException	Datei wurde nicht gefunden
	 * @throws IOException				Es konnte nicht von der Datei gelesen werden
	 */
	private void readFrames(String pfad) throws FileNotFoundException, IOException {
		String line;
		FileInputStream instream = null;
		DataInputStream indata = null;
		BufferedReader reader = null;
		
		try {
			instream = new FileInputStream(pfad);
			indata = new DataInputStream(instream);
			reader = new BufferedReader(new InputStreamReader(indata));
			
			try {
				while((line = reader.readLine()) != null) {
					frames.add(new Frame(line));
				}
			}
			catch(IOException e) {
				throw e;
			}
		} 
		catch (FileNotFoundException e) {
			throw e;
		}
		finally {
			try {
				reader.close();
				indata.close();
				instream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	/**
	 * Erstellt die fuer die Erkennung von der in dem Frame dargestellten
	 * Emotion benoetigten Basismasse, akkumuliert diese und wendet die Konfliktregel
	 * an.
	 * 
	 * @param frame	Frame fuer den die dargestellte Emotione ermittelt werden soll
	 * @return		Ein String mit der maximalen Plausibilitaet und den dazu gehoerigen Emotionen 
	 */
	public String berechneEvidenz(Frame frame){
		Basismass m1, m2, m3, m12, m123;
		
		ArrayList<TeilmengeBM> teilmengen = new ArrayList<TeilmengeBM>();
		double evidenz = Statistik.berechneEvidenzStirn(frame.getPixelStirnfalten());
		
		if (frame.getPixelStirnfalten() <= Statistik.getMeanStirn() * (1 - Statistik.getTolerance()))
			teilmengen.add(new TeilmengeBM(new Emotion[] { Emotion.WUT },
					evidenz));
		else if (frame.getPixelStirnfalten() >= Statistik.getMeanStirn() * (1 + Statistik.getTolerance()))
			teilmengen.add(new TeilmengeBM(new Emotion[] { Emotion.ANGST,
					Emotion.UEBERRASCHUNG }, evidenz));
		else
			teilmengen.add(new TeilmengeBM(new Emotion[] { Emotion.EKEL,
					Emotion.FREUDE, Emotion.VERACHTUNG }, evidenz));
		teilmengen.add(new TeilmengeBM(Emotion.all(), 1 - evidenz));
		m1 = new Basismass("m1", teilmengen);

		teilmengen = new ArrayList<TeilmengeBM>();
		evidenz = Statistik.berechneEvidenzAugen(frame.getPixelAugen()); 

		if (frame.getPixelAugen() <= Statistik.getMeanAugen() * (1 - Statistik.getTolerance()))
			teilmengen.add(new TeilmengeBM(new Emotion[] {
					Emotion.VERACHTUNG, Emotion.EKEL }, evidenz));
		else if (frame.getPixelAugen() >= Statistik.getMeanAugen() * (1 + Statistik.getTolerance()))
			teilmengen.add(new TeilmengeBM(new Emotion[] { Emotion.ANGST,
					Emotion.UEBERRASCHUNG }, evidenz));
		else
			teilmengen.add(new TeilmengeBM(new Emotion[] { Emotion.WUT,
					Emotion.FREUDE }, evidenz));

		teilmengen.add(new TeilmengeBM(Emotion.all(), 1 - evidenz));
		m2 = new Basismass("m2", teilmengen);

		/*
		 * Angst ist in der Aufgabenstellung definiert als in einigen Fällen
		 * zutreffend für den Fall der sich nach ausen bewegenden Mundwinkel
		 * Daher wurde Angst mit einer geringeren Evidenz zum Basismaß für diesen
		 * Fall hinzugefügt und ist also in dem für sich nich nach außen
		 * bewegenden Mundwinkel vorhanden.
		 */
		teilmengen = new ArrayList<TeilmengeBM>();
		if (frame.getMundwinkel() > 0) {
			teilmengen.add(new TeilmengeBM(new Emotion[] { Emotion.FREUDE }, 0.4));
			teilmengen.add(new TeilmengeBM(new Emotion[] {Emotion.ANGST}, 0.2));
			teilmengen.add(new TeilmengeBM(Emotion.all(), 0.4));
		}
		else {
			teilmengen.add(new TeilmengeBM(new Emotion[] {
					Emotion.UEBERRASCHUNG, Emotion.WUT, Emotion.VERACHTUNG,
					Emotion.EKEL, Emotion.ANGST }, 0.2));
			teilmengen.add(new TeilmengeBM(Emotion.all(), 0.8));
		}
		m3 = new Basismass("m3", teilmengen);

		m12 = new Basismass("m12", m1, m2);
		m123 = new Basismass("m123", m12, m3);
		
		return m123.getMostLikelyEmotion();
	}

	/**
	 * @param args arg[0] muss Pfad zu der Datei in der die Frames spezifiziert werden
	 * 				enthalten
	 */
	public static void main(String[] args) {
		if(args.length == 1)
			new Klassifikation(args[0]);
		else
			System.err.println("Pfad zu der Datei mit aufbereiteten Featuren der" +
					" Frames im Format <id>;<Pixel Stirnfalten>;<Mundwinkel>;<Pixel Augenoeffnung>" +
					" muss als erstes und einziges Argument angegeben werden! Abbruch");
	}

}
