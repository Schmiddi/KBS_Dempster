package dempster;

/**
 * 
 * @author Dennis Schmidt, Patrick Kalmbach
 * 
 * Enumeration die die einzelnen Emotionen speichert.
 *
 */
public enum Emotions {
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
	public static Emotions[] all(){
		return new Emotions[]{VERACHTUNG,EKEL,WUT,UEBERRASCHUNG,ANGST,FREUDE};
	}
}
