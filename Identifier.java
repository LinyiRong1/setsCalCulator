package assignment2;

public class Identifier implements IdentifierInterface {
	

	private StringBuffer identifierA;

	public Identifier() {
		identifierA = new StringBuffer("x");
	}

	public Identifier(Identifier copySource) {
		identifierA = new StringBuffer(copySource.identifierA);
	}

	public void init(char c) {
		identifierA.delete(0, identifierA.length());
		addChar(c);
	}
	
	public void addChar(char c) {
		identifierA.append(c);
	}

	public boolean equals(IdentifierInterface i) {
		Identifier identifier = (Identifier)i;
		return this.identifierA.toString().equals(identifier.identifierA.toString());
	}
	
	public String toString() {
		return identifierA.toString();
	}
	
	public int hashCode() {
		return identifierA.toString().hashCode();
	}

}


