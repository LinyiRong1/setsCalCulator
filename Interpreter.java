package assignment2;

import java.io.PrintStream;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.HashMap;
import java.math.BigInteger;
import java.math.*;

public class Interpreter<T extends SetInterface<BigInteger>> implements InterpreterInterface<T> {
	HashMap<String, Set<BigInteger>> hashMap = new HashMap<>();
	PrintStream out;
	public Interpreter() {
		out = new PrintStream(System.out);
	}
	char nextChar(Scanner in) {
		return in.next().charAt(0);
	}

	boolean nextCharIs(Scanner in, char c) {
		return in.hasNext(Pattern.quote(c + ""));
	}

	boolean nextCharIsDigit(Scanner in) {
		return in.hasNext("[0-9]");
	}

	boolean nextCharIsLetter(Scanner in) {
		return in.hasNext("[a-zA-Z]");
	}

	void ignoreSpaces(Scanner in) {
		in.useDelimiter("");
		while (nextCharIs(in, ' ')) {
			nextChar(in);
		}
	}
	
	BigInteger positiveNumbers(Scanner in) throws APException {
		in.useDelimiter("");
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(checkNotZero(in));
		while (in.hasNext()) {
			if (nextCharIs(in, ' ')) {
				ignoreSpaces(in);
				if (in.hasNext("[0-9]")) {
					throw new APException("No spaces allowed between natural numbers.");
				}
			} else if (nextCharIs(in, ',') || nextCharIs(in, '}')) {
				BigInteger result = new BigInteger(stringBuffer.toString());
				return result;
			} else {
				stringBuffer.append(number(in));
			}
		}
		BigInteger result = new BigInteger(stringBuffer.toString());
		return result;
	}

	BigInteger naturalNumbers(Scanner in) throws APException {
		in.useDelimiter("");
		BigInteger result = new BigInteger("0");
		if (in.hasNext("[0-9]")) {
			if (in.hasNext("[1-9]")) {
				result = positiveNumbers(in);
			} else if (isZero(in)) {
				ignoreSpaces(in);
				if (!nextCharIs(in, ',') && !nextCharIs(in, '}')) {
					throw new APException("Positive numbers cannot begin with zero.");
				}
				return result;
			}
		} else {
			throw new APException("Missing number.");
		}
		return result;
	}

	char checkLetter(Scanner in) throws APException {
		if (!nextCharIsLetter(in)) {
			throw new APException("All identifiers must begin with a letter.");
		}
		return nextChar(in);
	}

	char checkNotZero(Scanner in) throws APException {
		if (!in.hasNext("[1-9]")) {
			throw new APException("Not Zero.");
		}
		return nextChar(in);
	}

	boolean isZero(Scanner in) {
		if (!in.hasNext("[0]")) {
			return false;
		}
		nextChar(in);
		return true;
	}

	char number(Scanner in) throws APException {
		if (isZero(in)) {
			return '0';
		} else if (in.hasNext("[1-9]")) {
			return checkNotZero(in);
		} else {
			String s = in.nextLine();
			out.printf("%s ", s);
			out.println();
			throw new APException("A number must be either zero or positive.");
		}
	}
	void multiplicativeOperator(Scanner input) throws APException {
		checkValidCharacter(input, '*');
	}

	char additiveOperator(Scanner in) throws APException {
		if (nextCharIs(in, '+')) {
			return '+';
		} else if (nextCharIs(in, '|')) {
			return '|';
		} else if (nextCharIs(in, '-')) {
			return '-';
		} else {
			throw new APException("Invalid additive operator.");
		}
	}

	void checkValidCharacter(Scanner in, char c) throws APException {
		in.useDelimiter("");
		if (!nextCharIs(in, c)) {
			throw new APException("Invalid character," + " " + "'" + c + "'" + " expected.");
		}
		nextChar(in);
	}

	void endOfLine(Scanner in) throws APException {
		in.useDelimiter(" +");
		if (in.hasNext()) {
			throw new APException("No end of line detected.");
		}
	}

	void comment(Scanner in) throws APException {
		checkValidCharacter(in, '/');
		if (in.hasNext()) {
			in.nextLine();
		}
		endOfLine(in);
	}
	
	Set<BigInteger> rowNaturalNumbers(Scanner in) throws APException {
		Set<BigInteger> set = new Set<>();
		in.useDelimiter("");
		ignoreSpaces(in);
		if (nextCharIs(in, '}')) {
			return (Set<BigInteger>) set.copy();
		}
		if (in.hasNext()) {
			set.add(naturalNumbers(in));
			ignoreSpaces(in);
			while (in.hasNext()) {
				if (nextCharIs(in, ',')) {
					checkValidCharacter(in, ',');
					ignoreSpaces(in);
					set.add(naturalNumbers(in));
					ignoreSpaces(in);
				} else if (nextCharIs(in, '}')) {
					return (Set<BigInteger>) set.copy();
				}
			}
		}
		return (Set<BigInteger>) set.copy();
	}

	Set<BigInteger> set(Scanner in) throws APException {
		in.useDelimiter("");
		Set<BigInteger> set = new Set<>();
		checkValidCharacter(in, '{');
		ignoreSpaces(in);
		set = rowNaturalNumbers(in);
		ignoreSpaces(in);
		checkValidCharacter(in, '}');
		return (Set<BigInteger>) set.copy();
	}

	Set<BigInteger> complexFactor(Scanner in) throws APException {
		Set<BigInteger> set = new Set<>();
		checkValidCharacter(in, '(');
		ignoreSpaces(in);
		set = expression(in);
		ignoreSpaces(in);
		checkValidCharacter(in, ')');
		return (Set<BigInteger>) set.copy();
	}

	Set<BigInteger> factor(Scanner in) throws APException {
		Set<BigInteger> set = new Set<>();
		Identifier id = new Identifier();
		ignoreSpaces(in);
		in.useDelimiter("");
		if (nextCharIsLetter(in)) {
			id = identifier(in);
			if (hashMap.containsKey(id.toString())) {
				set = hashMap.get(id.toString());
			} else {
				throw new APException("No such identifier found.");
			}
		} else if (nextCharIs(in, '(')) {
			set = complexFactor(in);
		} else if (nextCharIs(in, '{')) {
			set = set(in);
		}
		return (Set<BigInteger>) set.copy();
	}

	Set<BigInteger> term(Scanner in) throws APException {
		Set<BigInteger> set = new Set<>();
		in.useDelimiter("");
		set = factor(in);
		ignoreSpaces(in);
		while (nextCharIs(in, '*')) {
			multiplicativeOperator(in);
			ignoreSpaces(in);
			set = (Set<BigInteger>) set.calIntersection(factor(in));
			ignoreSpaces(in);
		}
		if (!nextCharIs(in, '+') && !nextCharIs(in, '-') && !nextCharIs(in, '|') && !nextCharIsLetter(in)
				&& !nextCharIs(in, '*') && !nextCharIs(in, ')')) {
			ignoreSpaces(in);
			if (nextCharIs(in, '}')) {
				checkValidCharacter(in, '}');
			}
			ignoreSpaces(in);
		} else {
			return (Set<BigInteger>) set.copy();
		}
		ignoreSpaces(in);
		return (Set<BigInteger>) set.copy();
	}

	Set<BigInteger> expression(Scanner in) throws APException {
		Set<BigInteger> set = new Set<>();
		ignoreSpaces(in);
		set = term(in);
		ignoreSpaces(in);
		while (in.hasNext()) {
			if (nextCharIs(in, '}') || nextCharIs(in, ')') || nextCharIs(in, '*')) {
				return (Set<BigInteger>) set.copy();
			}
			if (additiveOperator(in) == '+') {
				checkValidCharacter(in, '+');
				ignoreSpaces(in);
				set = (Set<BigInteger>) set.calUnion(term(in));
				ignoreSpaces(in);
			} else if (additiveOperator(in) == '-') {
				checkValidCharacter(in, '-');
				ignoreSpaces(in);
				set = (Set<BigInteger>) set.calDifference(term(in));
				ignoreSpaces(in);
			} else if (additiveOperator(in) == '|') {
				checkValidCharacter(in, '|');
				ignoreSpaces(in);
				set = (Set<BigInteger>) set.calSymmetricDifference(term(in));
				ignoreSpaces(in);
			}
		}
		return (Set<BigInteger>) set.copy();
	}

	Identifier identifier(Scanner in) throws APException {
		Identifier id = new Identifier();
		in.useDelimiter("");
		ignoreSpaces(in);
		char c = checkLetter(in);
		id.init(c);
		while (in.hasNext()) {
			if (nextCharIsLetter(in)) {
				c = checkLetter(in);
				id.addChar(c);
			} else if (nextCharIsDigit(in)) {
				c = number(in);
				id.addChar(c);
			} else if (nextCharIs(in, ' ')) {
				ignoreSpaces(in);
				if (nextCharIsDigit(in) || nextCharIsLetter(in)) {
					throw new APException("No spaces allowed within an identifier.");
				}
				return id;
			} else {
				ignoreSpaces(in);
				if (!nextCharIs(in, '=') && !nextCharIs(in, '(') && !nextCharIs(in, ')')
						&& !nextCharIs(in, '{') && !nextCharIs(in, '+') && !nextCharIs(in, '-')
						&& !nextCharIs(in, '|') && !nextCharIs(in, '*')) {
					throw new APException("Identifiers can only consist of alphanumeric characters.");
				}
				return id;
			}
		}
		return id;
	}
	
	void print(Set<BigInteger> set) {
		Set<BigInteger> c = (Set<BigInteger>) set.copy();
		while (c.size() > 0) {
			BigInteger number = c.get();
			System.out.print(number + " ");
			c.remove(number);
		}
		System.out.println();
	}

	Set<BigInteger> printStatement(Scanner in) throws APException {
		checkValidCharacter(in, '?');
		ignoreSpaces(in);
		if (!in.hasNext()) {
			throw new APException("Expression not found.");
		} else {
			Set<BigInteger> set = expression(in);
			endOfLine(in);
			print(set);
			return (Set<BigInteger>) set.copy();
		}
	}

	void assignment(Scanner in) throws APException {
		Identifier identifier = new Identifier();
		Set<BigInteger> set = new Set<>();
		String inputStr = in.nextLine();
		Scanner inputStrScanner = new Scanner(inputStr);
		inputStrScanner.useDelimiter("");
		ignoreSpaces(inputStrScanner);
		identifier = identifier(inputStrScanner);
		ignoreSpaces(inputStrScanner);
		if (nextCharIs(inputStrScanner, '=')) {
			checkValidCharacter(inputStrScanner, '=');
			ignoreSpaces(inputStrScanner);
			if (!inputStrScanner.hasNext()) {
				throw new APException("No expression found");
			} else {
				ignoreSpaces(inputStrScanner);
				set = expression(inputStrScanner);
				endOfLine(inputStrScanner);
				hashMap.put(identifier.toString(), set);
			}
		}
		else {
			throw new APException("Incomplete statement.");
		}
	}

	Set<BigInteger> statement(Scanner in) throws APException {
		in.useDelimiter("");
		ignoreSpaces(in);
		if (nextCharIs(in, '/')) {
			comment(in);
			return null;
		} else if (nextCharIs(in, '?')) {
			return printStatement(in);
		} else if (nextCharIsLetter(in)) {
			assignment(in);
			return null;
		} else {
			throw new APException("Statements can only be assignment, comment or print statements.");
		}
	}

	void start(Scanner in) {
		while (in.hasNextLine()) {
			try {
				String inputString = in.nextLine();
				if (inputString.length() == 0) {
					throw new APException("Empty line not allowed.");
				} else {
					Scanner inputStringScanner = new Scanner(inputString);
					statement(inputStringScanner);
				}
			} catch (APException e) {
				out.println(e.getMessage());
			}
		}
	}

	@SuppressWarnings("unchecked")
	public T eval(String s) {
		Scanner sScanner = new Scanner(s);
		try {
			return (T) statement(sScanner);
		} catch (APException e) {
			out.println(e.getMessage());
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public T getMemory(String var) {
		if (hashMap.containsKey(var)) {
			return (T) hashMap.get(var);
		} else {
			return null;
		}
	}
}
