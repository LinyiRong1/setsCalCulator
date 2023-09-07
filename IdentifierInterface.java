package assignment2;

public interface IdentifierInterface {
	
	public void init(char c);
	/* PRE - c is a letter.
	 * POST - The identifier now only contains c.
	 */
	
	public void addChar(char c);
	/* PRE - c is an alphanumeric character
	 * POST - c is now in the identifier.
	 */
	
	public boolean equals(IdentifierInterface i);
	/* PRE -
	 * POST - true: the string of this identifier is not equal to the string of identifierInterface i.
	 * 		  false: the string of this identifier is not equal to the string of identifierInterface i.
	 */
	
	public String toString();
	/* PRE -
	 * POST - return the identifier as a string.
	 */
	
	public int hashCode();
	/* PRE -
	 * POST - The hashCode of the identifier is returned.
	 */
}