package dempster;

/**
 * 
 * @author Dennis Schmidt, Patrick Kalmbach
 * 
 * Enumeration die die einzelnen Emotionen speichert.
 *
 */
public enum Emotion {
	VERACHTUNG, EKEL, WUT, UEBERRASCHUNG, ANGST, FREUDE;
	
	/**
	 * Wandelt die Enumerationswerte in einen gleichnamigen
	 * String um, bei dem nur der erste Buchstabe gross
	 * geschrieben ist.
	 */
	public String toString()
	{
	    switch(this){
	    	case VERACHTUNG:	return "Verachtung";
	    	case EKEL:			return "Ekel";
	    	case WUT:   		return "Wut";
	    	case UEBERRASCHUNG:	return "Ueberraschung";
	    	case ANGST:   		return "Angst";
	    	case FREUDE:   		return "Freude";
	    	default:    		return "";
	    }
	}
	
	/**
	 * Die Methode dient nur der Rueckgabe einer Liste die
	 * alle Emotionen enthaelt.
	 * 
	 * @return Eine Liste die alle Emotionen enthaelt.
	 */
	public static Emotion[] all(){
		return new Emotion[]{VERACHTUNG,EKEL,WUT,UEBERRASCHUNG,ANGST,FREUDE};
	}
}
