package dempster;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class Classification {

	List<Frame> frames;
	List<List<Emotions>> evidenzen;
	String path;
	public Classification() {
		System.out.println(System.getProperty("user.dir"));
		path = "bin/data/E_015_train.csv";
		frames = new ArrayList<Frame>();
		evidenzen = new ArrayList<List<Emotions>>();
		
		try {
			readFrames(frames, path);
		}
		catch(Exception e) {
			System.err.println("Not able to read from file, abort");
			return;
		}
		
		for(Frame f : frames)
			System.out.println("Frame " + f.getId() + " --> " + calculateEvidenz(f));
	
		/*
		Evidenz e = new Evidenz(frames.get(30));
		System.out.println("Frame " + e.getFrame().getId() + " --> " + e.getMostLiklyEmotion());
		e.printM123();*/
	}
	
	/**
	 * Liest Eintraege einer Datei in der Features fuer Emotionen extrahiert aus einem Bild
	 * in nachfolgendem Format gespeichert sind aus und erzeugt daraus Frame objekte fuer
	 * die weitere Verarbeitung.
	 * Kann die Datei nicht gefunden oder von ihr gelesen werden wird eine Exception erzeugt
	 * Format: <id>;<Pixel stirnfalten>;<mundwinkel>;<Pixel Augen offen>
	 * @param frames
	 * @param path
	 * @throws FileNotFoundException
	 */
	private void readFrames(List<Frame> frames, String path) throws FileNotFoundException {
		String line;
		FileInputStream instream = null;
		DataInputStream indata = null;
		BufferedReader reader = null;
		
		try {
			instream = new FileInputStream(path);
			indata = new DataInputStream(instream);
			reader = new BufferedReader(new InputStreamReader(indata));
			
			try {
				while((line = reader.readLine()) != null) {
					frames.add(new Frame(line));
				}
			}
			catch(IOException e) {
				System.err.println("Could not read from file" + path);
				e.printStackTrace();
				throw new FileNotFoundException();
			}
		} 
		catch (FileNotFoundException e) {
			System.err.println("Could not open file " + path);
			e.printStackTrace();
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
	
	public List<Emotions> calculateEvidenz(Frame frame){
		Basismas m1, m2, m3, m12, m123;
		
		ArrayList<TeilmengeBM> teilmengen = new ArrayList<TeilmengeBM>();
		double evidenz = Statistic.berechneEvidenzStirn(frame.getPixelStirnfalten());
		
		if (frame.getPixelStirnfalten() <= Statistic.getMeanStirn() * (1 - Statistic.getTolerance()))
			teilmengen.add(new TeilmengeBM(new Emotions[] { Emotions.WUT },
					evidenz));
		else if (frame.getPixelStirnfalten() >= Statistic.getMeanStirn() * (1 + Statistic.getTolerance()))
			teilmengen.add(new TeilmengeBM(new Emotions[] { Emotions.ANGST,
					Emotions.UEBERRASCHUNG }, evidenz));
		else
			teilmengen.add(new TeilmengeBM(new Emotions[] { Emotions.EKEL,
					Emotions.FREUDE, Emotions.VERACHTUNG }, evidenz));
		teilmengen.add(new TeilmengeBM(Emotions.all(), 1 - evidenz));
		m1 = new Basismas("m1", teilmengen);

		teilmengen = new ArrayList<TeilmengeBM>();
		evidenz = Statistic.berechneEvidenzAugen(frame.getPixelAugen()); 

		if (frame.getPixelAugen() <= Statistic.getMeanAugen() * (1 - Statistic.getTolerance()))
			teilmengen.add(new TeilmengeBM(new Emotions[] {
					Emotions.VERACHTUNG, Emotions.EKEL }, evidenz));
		else if (frame.getPixelAugen() >= Statistic.getMeanAugen() * (1 + Statistic.getTolerance()))
			teilmengen.add(new TeilmengeBM(new Emotions[] { Emotions.ANGST,
					Emotions.UEBERRASCHUNG }, evidenz));
		else
			teilmengen.add(new TeilmengeBM(new Emotions[] { Emotions.WUT,
					Emotions.FREUDE }, evidenz));

		teilmengen.add(new TeilmengeBM(Emotions.all(), 1 - evidenz));
		m2 = new Basismas("m2", teilmengen);

		/**
		 * Angst ist in der Aufgabenstellung definiert als in einigen Fällen
		 * zutreffend für den Fall der sich nach ausen bewegenden Mundwinkel
		 * Daher wurde Angst mit einer geringeren Evidenz zum Basismaß für diesen
		 * Fall hinzugefügt und ist also in dem für sich nich nach außen
		 * bewegenden Mundwinkel vorhanden.
		 */
		teilmengen = new ArrayList<TeilmengeBM>();
		if (frame.getMundwinkel() > 0) {
			teilmengen.add(new TeilmengeBM(new Emotions[] { Emotions.FREUDE }, 0.4));
			teilmengen.add(new TeilmengeBM(new Emotions[] {Emotions.ANGST}, 0.2));
			teilmengen.add(new TeilmengeBM(Emotions.all(), 0.4));
		}
		else {
			teilmengen.add(new TeilmengeBM(new Emotions[] {
					Emotions.UEBERRASCHUNG, Emotions.WUT, Emotions.VERACHTUNG,
					Emotions.EKEL, Emotions.ANGST }, 0.2));
			teilmengen.add(new TeilmengeBM(Emotions.all(), 0.8));
		}
		m3 = new Basismas("m3", teilmengen);

		m12 = new Basismas("m12", m1, m2);
		m123 = new Basismas("m123", m12, m3);
		
		return m123.getMostLiklyEmotion();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Classification();
	}

}
