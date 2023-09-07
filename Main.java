package assignment2;

import java.util.Scanner;

public class Main {
	

	void start() {
		Interpreter newInterpreter = new Interpreter();
		Scanner in = new Scanner(System.in);
		while (in.hasNextLine()) {
			newInterpreter.start(in);
			in.nextLine();
		}
	}
	public static void main(String[] argv) {
		new Main().start();
	}

}


