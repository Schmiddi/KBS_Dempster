public enum Emotions {
	VERACHTUNG, EKEL, WUT, UEBERRASCHUNG, ANGST, FREUDE;
	
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
}
