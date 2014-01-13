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
	List<Evidenz> evidenzen;
	String path;
	public Classification() {
		System.out.println(System.getProperty("user.dir"));
		path = "bin/data/E_015_train.csv";
		frames = new ArrayList<Frame>();
		evidenzen = new ArrayList<Evidenz>();
		
		try {
			readFrames(frames, path);
		}
		catch(Exception e) {
			System.err.println("Not able to read from file, abort");
			return;
		}
		
		for(Frame f : frames)
			evidenzen.add(new Evidenz(f));
		
		for(Evidenz e : evidenzen)
			System.out.println("Frame " + e.getFrame().getId() + " --> " + e.getMostLiklyEmotion());
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
		String[] tokens;
		int id;
		int stirn;
		int mund;
		int augen;
		FileInputStream instream = null;
		DataInputStream indata = null;
		BufferedReader reader = null;
		
		try {
			instream = new FileInputStream(path);
			indata = new DataInputStream(instream);
			reader = new BufferedReader(new InputStreamReader(indata));
			
			try {
				while((line = reader.readLine()) != null) {
					if(line.matches("\\d*;\\d*;\\d*;\\d*")) {
						tokens = line.split(";");
						
						try{
							id = Integer.parseInt(tokens[0]);
							stirn = Integer.parseInt(tokens[1]);
							mund = Integer.parseInt(tokens[2]);
							augen = Integer.parseInt(tokens[3]);
							frames.add(new Frame(id, stirn, mund, augen));
						}
						catch(NumberFormatException e) {
							System.err.println("Could not create frame for data line " + line);
							e.printStackTrace();
						}
					}
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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Classification();
	}

}
