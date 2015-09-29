package nz.ac.auckland.bookShare.domain;

public enum Genre {
    CHILDRENS, FICTION, SCIFI, FANTASY, HISTORY,
    CRIME, HORROR, COMEDY, CLASSIC, MYSTERY, SHORTS,
    MEMOIR, BIOGRAPHY;
    
	public static Genre fromString(String text) {
	    if (text != null) {
	      for (Genre g : Genre.values()) {
	        if (text.equalsIgnoreCase(g.toString())) {
	          return g;
	        }
	      }
	    }
	    return null;
	  }
}
