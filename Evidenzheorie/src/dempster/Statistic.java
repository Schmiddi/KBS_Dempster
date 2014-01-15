package dempster;
/**
 * @author Dennis Schmidt, Patrick Kalmbach
 * 
 * Statistic enthaelt alle fuer die Berechnung der Evidenz notwenidgen Werte
 * sowie die Funktion die die Evidenz der Augenoeffnung und der Stirnfalten
 * berechnet. 
 * 
 */
public class  Statistic {
	/*
	 * Statistische Werte fuer die Anzahl an Stirnfalten, die aus den 
	 * Trainingsdaten ermittelt wurden.
	 */
	/**
	 * Durchschnitt von der Anzahl an Stirnfalten.
	 */
	private static final double meanStirn = 527.5; // Mean aus Testdaten
	/**
	 * Standardabweichung bei der Anzahl an Stirnfalten.
	 */
	private static final double stdDeviationStirn = 339.2;
	/**
	 * Minimum von der Anzahl an Stirnfalten.
	 */
	private static final double minStirn = 104;
	/**
	 * Maximum von der Anzahl an Stirnfalten.
	 */
	private static final double maxStirn = 1103;
	
	/*
	 * Statistische Werte fuer die Augenoeffnung, die aus den Trainingsdaten
	 * ermittelt wurden.
	 */
	/**
	 * Durchschnitt bei Augenoeffnung.
	 */
	private static final double meanAugen = 18.02;
	/**
	 * Standardabweichung bei Augenoeffnung.
	 */
	private static final double stdDeviationAugen = 6.498;
	/**
	 * Minimum bei Augenoeffnung.
	 */
	private static final double minAugen = 9;
	/**
	 * Maximum bei Augenoeffnung.
	 */
	private static final double maxAugen = 30;
	
	/**
	 * Toleranz fuer die Festlegung der Teilmenge, Werte innerhalb des Toleranzbereiches
	 * gehoeren nicht zu den extremen und den damit verbundenen Emotionen.
	 */
	private static final double tolerance = 0.1;

	/**
	 * Berechnet die Evidenz anhand des Wertes fuer Augenoeffnung.
	 * 
	 * @param value Der Wert fuer Augenoeffnung
	 * @return Die Evidenz fuer die Teilmenge der Augenoeffnung
	 */
	public static double berechneEvidenzAugen(double value) {
		return 1 / (1 + Math.exp(-((Math.abs((value - minAugen) / (maxAugen - minAugen)) * 2 - 1.5))));
	}
	
	/**
	 * Berechnet die Evidenz anhand der Anzahl an Stirnfalten.
	 * 
	 * @param value Anzahl an Stirnfalten
	 * @return Die Evidenz fuer die Teilmenge der Stirnfalten
	 */
	public static double berechneEvidenzStirn(double value) {
		return 1 / (1 + Math.exp(-((Math.abs((value - minStirn) / (maxStirn - minStirn)) * 2 - 1.5))));
	}
	
	/*
	 * Nur Getter, da alle Werte final sind.
	 */
	public static double getMeanStirn() {
		return meanStirn;
	}

	public static double getStdDeviationStirn() {
		return stdDeviationStirn;
	}

	public static double getMinStirn() {
		return minStirn;
	}

	public static double getMaxStirn() {
		return maxStirn;
	}

	public static double getMeanAugen() {
		return meanAugen;
	}

	public static double getStdDeviationAugen() {
		return stdDeviationAugen;
	}

	public static double getMinAugen() {
		return minAugen;
	}

	public static double getMaxAugen() {
		return maxAugen;
	}

	public static double getTolerance() {
		return tolerance;
	}
}
