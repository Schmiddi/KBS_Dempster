package dempster;
import java.util.List;
import java.util.ArrayList;
import java.util.TreeSet;

/**
 * @author patrick
 * 
 *         Enthaelt alle notenwendigen Funktionalitaeten und Informationen der
 *         Evidenztheorie fuer einen Frame. Diese Klasse ermittelt anhand der
 *         Daten fuer einen Frame die notwendigen Basismaße sowie die
 *         Akkumulation und stellt zudem notwendige Funktionen wie belief,
 *         zweifel und plausibilitaet zur Verfuegung
 * 
 */
public class  Statistic {
	private static final double meanStirn = 527.5; // Mean aus Testdaten
	private static final double stdDeviationStirn = 339.2;
	private static final double minStirn = 104;
	private static final double maxStirn = 1103;
	private static final double meanAugen = 18.02; // Mean aus Testdaten
	private static final double stdDeviationAugen = 6.498;
	private static final double minAugen = 9;
	private static final double maxAugen = 30;
	private static final double tolerance = 0.1;

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

	public static double berechneEvidenz(double min, double max, double value) {
		return 1 / (1 + Math.exp(-((Math.abs((value - min) / (max - min)) * 2 - 1.5))));
	}
}
