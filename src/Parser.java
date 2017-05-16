import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.*;
import javax.swing.JFileChooser;

/**
 * The parser and interpreter. The top level parse function, a main method for
 * testing, and several utility methods are provided. You need to implement
 * parseProgram and all the rest of the parser.
 */
public class Parser {

	/**
	 * Top level parse method, called by the World
	 */
	static RobotProgramNode parseFile(File code) {
		Scanner scan = null;
		try {
			scan = new Scanner(code);

			// the only time tokens can be next to each other is
			// when one of them is one of (){},;
			scan.useDelimiter("\\s+|(?=[{}(),;])|(?<=[{}(),;])");

			RobotProgramNode n = parseProgram(scan); // You need to implement
														// this!!!
			scan.close();
			return n;
		} catch (FileNotFoundException e) {
			System.out.println("Robot program source file not found");
		} catch (ParserFailureException e) {
			System.out.println("Parser error:");
			System.out.println(e.getMessage());
			scan.close();
		} catch (NoSuchElementException e) {
			System.out.println("Unexpected end of file");
		}
		return null;
	}

	/** For testing the parser without requiring the world */

	public static void main(String[] args) {
		if (args.length > 0) {
			for (String arg : args) {
				File f = new File(arg);
				if (f.exists()) {
					System.out.println("Parsing '" + f + "'");
					RobotProgramNode prog = parseFile(f);
					System.out.println("Parsing completed ");
					if (prog != null) {
						System.out.println("================\nProgram:");
						System.out.println(prog);
					}
					System.out.println("=================");
				} else {
					System.out.println("Can't find file '" + f + "'");
				}
			}
		} else {
			while (true) {
				JFileChooser chooser = new JFileChooser(".");// System.getProperty("user.dir"));
				int res = chooser.showOpenDialog(null);
				if (res != JFileChooser.APPROVE_OPTION) {
					break;
				}
				RobotProgramNode prog = parseFile(chooser.getSelectedFile());
				System.out.println("Parsing completed");
				if (prog != null) {
					System.out.println("Program: \n" + prog);
				}
				System.out.println("=================");
			}
		}
		System.out.println("Done");
	}

	// Useful Patterns

	static Pattern NUMPAT = Pattern.compile("-?\\d+"); // ("-?(0|[1-9][0-9]*)");
	static Pattern OPENPAREN = Pattern.compile("\\(");
	static Pattern CLOSEPAREN = Pattern.compile("\\)");
	static Pattern OPENBRACE = Pattern.compile("\\{");
	static Pattern CLOSEBRACE = Pattern.compile("\\}");

	/**
	 * PROG ::= STMT+
	 */
	static RobotProgramNode parseProgram(Scanner s) {
		if (!s.hasNext()) {
			return null;
		}
		List<RobotProgramNode> statement = new ArrayList<>();
		while (s.hasNext()) {
			statement.add(parseStatement(s));
		}
		return new BlockNode(statement);
	}

	private static RobotProgramNode parseStatement(Scanner s) {
		String token = s.next();
		switch (token) {
			case ("loop"):
				return parseLoop(s);
			case ("if"):
				return parseIf(s);
			case("while"):
				return parseWhile(s);
		}
		return parseAction(token, s);
	}

	private static RobotProgramNode parseWhile(Scanner s) {
		List<RobotProgramNode> block = new ArrayList<>();

		if (!checkFor(OPENPAREN, s)) {
			fail("Expected open parenthesis", s);
		}
		ConditionNode condition = parseCondition(s);
		if (!checkFor(CLOSEPAREN, s)) {
			fail("Expected closed parenthesis", s);
		}
		if (!checkFor(OPENBRACE, s)) {
			fail("Expected open brace", s);
		}
		while (!checkFor(CLOSEBRACE, s)) {
			block.add(parseStatement(s));
		}
		if (block.size() <= 0) {
			fail("Requires at least one statement in block", s);
		}
		return new WhileNode(new BlockNode(block), condition);
	}

	private static RobotProgramNode parseIf(Scanner s) {
		List<RobotProgramNode> block = new ArrayList<>();

		if (!checkFor(OPENPAREN, s)) {
			fail("Expected open parenthesis", s);
		}
		ConditionNode condition = parseCondition(s);
		if (!checkFor(CLOSEPAREN, s)) {
			fail("Expected closed parenthesis", s);
		}
		if (!checkFor(OPENBRACE, s)) {
			fail("Expected open brace", s);
		}
		while (!checkFor(CLOSEBRACE, s)) {
			block.add(parseStatement(s));
		}
		if (block.size() <= 0) {
			fail("Requires at least one statement in block", s);
		}
		return new IfNode(new BlockNode(block), condition);
	}

	private static ConditionNode parseCondition(Scanner s) {
		String op = s.next();
		if (!checkFor(OPENPAREN, s)) {
			fail("Expected open parenthesis", s);
		}
		VariableNode v1 = parseVariable(s.next(), s);
		if (!checkFor(",", s)) {
			fail("Expected comma", s);
		}
		VariableNode v2 = parseVariable(s.next(), s);
		if (!checkFor(CLOSEPAREN, s)) {
			fail("Expected closed parenthesis", s);
		}
		return new ConditionNode(op, v1, v2);
	}
	
	private static VariableNode parseVariable(String str, Scanner s) {
		switch (str) {
			case ("fuelLeft"):
				return new VariableNode("fuelLeft", 0);
			case ("oppLR"):
				return new VariableNode("oppLR", 0);
			case ("oppFB"):
				return new VariableNode("oppFB", 0);
			case ("barrelLR"):
				return new VariableNode("barrelLR", 0);
			case ("barrelFB"):
				return new VariableNode("barrelFB", 0);
			case ("numBarrels"):
				return new VariableNode("numBarrels", 0);
			case ("wallDist"):
				return new VariableNode("wallDist", 0);
			default:
				if (isInteger(str)) {
					return new VariableNode(str, Integer.parseInt(str));
				}
				fail("Invalid variable", s);
				return null;
		}
	}

	private static RobotProgramNode parseLoop(Scanner s) {
		List<RobotProgramNode> block = new ArrayList<>();
		s.next();
		while (!checkFor(CLOSEBRACE, s)) {
			block.add(parseStatement(s));
		}
		if (block.size() <= 0) {
			fail("Requires at least one statement in block", s);
		}
		return new LoopNode(new BlockNode(block));
	}
	
	static RobotProgramNode parseAction(String token, Scanner s) {
		RobotProgramNode node = null;
		switch (token) {
			case ("turnL"):
				node = new TurnNode(-1);
				break;
			case ("turnR"):
				node = new TurnNode(1);
				break;
			case("turnAround"):
				node = new TurnNode(2);
				break;
			case ("move"):
				node = new MoveNode();
				break;
			case ("takeFuel"):
				node = new TakeFuelNode();
				break;
			case ("wait"):
				node = new WaitNode();
				break;
			case ("shieldOn"):
				node = new ShieldNode(true);
				break;
			case("shieldOff"):
				node = new ShieldNode(false);
				break;
			default:
				fail("Unknown command: " + token, s);
				break;
		}
		require(";", "No semicolon found where expected", s);
		return node;
	}
	
	// utility methods for the parser

	/**
	 * Report a failure in the parser.
	 */
	static void fail(String message, Scanner s) {
		String msg = message + "\n   @ ...";
		for (int i = 0; i < 5 && s.hasNext(); i++) {
			msg += " " + s.next();
		}
		throw new ParserFailureException(msg + "...");
	}

	// helper function taken from http://stackoverflow.com/questions/237159/whats-the-best-way-to-check-to-see-if-a-string-represents-an-integer-in-java
	public static boolean isInteger(String str) {
		if (str == null) {
			return false;
		}
		int length = str.length();
		if (length == 0) {
			return false;
		}
		int i = 0;
		if (str.charAt(0) == '-') {
			if (length == 1) {
				return false;
			}
			i = 1;
		}
		for (; i < length; i++) {
			char c = str.charAt(i);
			if (c < '0' || c > '9') {
				return false;
			}
		}
		return true;
	}

	/**
	 * Requires that the next token matches a pattern if it matches, it consumes
	 * and returns the token, if not, it throws an exception with an error
	 * message
	 */
	static String require(String p, String message, Scanner s) {
		if (s.hasNext(p)) {
			return s.next();
		}
		fail(message, s);
		return null;
	}

	static String require(Pattern p, String message, Scanner s) {
		if (s.hasNext(p)) {
			return s.next();
		}
		fail(message, s);
		return null;
	}

	/**
	 * Requires that the next token matches a pattern (which should only match a
	 * number) if it matches, it consumes and returns the token as an integer if
	 * not, it throws an exception with an error message
	 */
	static int requireInt(String p, String message, Scanner s) {
		if (s.hasNext(p) && s.hasNextInt()) {
			return s.nextInt();
		}
		fail(message, s);
		return -1;
	}

	static int requireInt(Pattern p, String message, Scanner s) {
		if (s.hasNext(p) && s.hasNextInt()) {
			return s.nextInt();
		}
		fail(message, s);
		return -1;
	}

	/**
	 * Checks whether the next token in the scanner matches the specified
	 * pattern, if so, consumes the token and return true. Otherwise returns
	 * false without consuming anything.
	 */
	static boolean checkFor(String p, Scanner s) {
		if (s.hasNext(p)) {
			s.next();
			return true;
		} else {
			return false;
		}
	}

	static boolean checkFor(Pattern p, Scanner s) {
		if (s.hasNext(p)) {
			s.next();
			return true;
		} else {
			return false;
		}
	}

}

// You could add the node classes here, as long as they are not declared public
// (or private)
