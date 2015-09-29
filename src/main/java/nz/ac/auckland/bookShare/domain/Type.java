package nz.ac.auckland.bookShare.domain;

public enum Type {
	PAPERBACK, EBOOK, COMIC;
	
	public static Type fromString(String text) {
	    if (text != null) {
	      for (Type g : Type.values()) {
	        if (text.equalsIgnoreCase(g.toString())) {
	          return g;
	        }
	      }
	    }
	    return null;
	  }
}
