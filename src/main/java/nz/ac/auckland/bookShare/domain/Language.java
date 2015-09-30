package nz.ac.auckland.bookShare.domain;

public enum Language {
	ENGLISH, CHINESE, ARABIC, KOREAN, JAPANESE, TAMIL, HEBREW, MALAY, GERMAN, FRENCH;
	
	public static Language fromString(String text) {
	    if (text != null) {
	      for (Language l : Language.values()) {
	        if (text.equalsIgnoreCase(l.toString())) {
	          return l;
	        }
	      }
	    }
	    return null;
	  }
}
