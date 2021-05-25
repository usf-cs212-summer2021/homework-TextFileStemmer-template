import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * Tests of the {@link TextParser} class.
 *
 * @author CS 212 Software Development
 * @author University of San Francisco
 * @version Summer 2021
 *
 * @see TextParser
 */
public class TextParserTest {
	/**
	 * Tests the {@link TextParser#clean(String)} method.
	 */
	@Nested
	@TestMethodOrder(OrderAnnotation.class)
	public class CleanTestCases {
		/**
		 * Calls {@link TextParser#clean(String)} on the supplied text, and makes
		 * sure it matches the expected text.
		 *
		 * @param text the text to clean
		 * @param expected the cleaned output
		 */
		public void test(String text, String expected) {
			String actual = TextParser.clean(text);
			assertEquals(expected, actual);
		}

		/**
		 * Runs {@link #test(String, String)} with the supplied test case.
		 */
		@Test
		@Order(1)
		public void helloWorld() {
			test("hello world", "hello world");
		}

		/**
		 * Runs {@link #test(String, String)} with the supplied test case.
		 */
		@Test
		@Order(2)
		public void withTabs() {
			test("\t hello  world ", "\t hello  world ");
		};

		/**
		 * Runs {@link #test(String, String)} with the supplied test case.
		 */
		@Test
		@Order(3)
		public void withPunctuation() {
			test("hello, world!", "hello world");
		}

		/**
		 * Runs {@link #test(String, String)} with the supplied test case.
		 */
		@Test
		@Order(4)
		public void withDigit() {
			test("hello 1 world", "hello  world");
		}

		/**
		 * Runs {@link #test(String, String)} with the supplied test case.
		 */
		@Test
		@Order(5)
		public void withSymbol() {
			test("hello @world", "hello world");
		}

		/**
		 * Runs {@link #test(String, String)} with the supplied test case.
		 */
		@Test
		@Order(6)
		public void withUppercase() {
			test("HELLO WORLD", "hello world");
		}

		/**
		 * Runs {@link #test(String, String)} with the supplied test case.
		 */
		@Test
		@Order(7)
		public void withExclamation() {
			test("¡Hello world!", "hello world");
		}

		/**
		 * Runs {@link #test(String, String)} with the supplied test case.
		 */
		@Test
		@Order(8)
		public void withMarks() {
			test("héḶlõ ẁörld", "hello world");
		}

		/**
		 * Runs {@link #test(String, String)} with the supplied test case.
		 */
		@Test
		@Order(9)
		public void onlySpaces() {
			test("   ", "   ");
		}

		/**
		 * Runs {@link #test(String, String)} with the supplied test case.
		 */
		@Test
		@Order(10)
		public void onlyDigits() {
			test("1234567890", "");
		}
	}

	/**
	 * Tests the {@link TextParser#parse(String)} method.
	 */
	@Nested
	@TestMethodOrder(OrderAnnotation.class)
	public class ParseTextTests {
		/**
		 * Calls {@link TextParser#parse(String)} on the supplied text, and makes
		 * sure it matches the expected array.
		 *
		 * @param text the text to parse
		 */
		public void test(String text) {
			String[] expected = new String[] { "hello", "world" };
			assertArrayEquals(expected, TextParser.parse(text));
		}

		/**
		 * Runs {@link #test(String)} with the supplied test case.
		 */
		@Test
		@Order(1)
		public void helloWorld() {
			test("hello world");
		}

		/**
		 * Runs {@link #test(String)} with the supplied test case.
		 */
		@Test
		@Order(2)
		public void withTabs() {
			test("\t hello  world ");
		}

		/**
		 * Runs {@link #test(String)} with the supplied test case.
		 */
		@Test
		@Order(3)
		public void withPunctuation() {
			test("hello, world!");
		}

		/**
		 * Runs {@link #test(String)} with the supplied test case.
		 */
		@Test
		@Order(4)
		public void withDigit() {
			test("hello 1 world");
		}

		/**
		 * Runs {@link #test(String)} with the supplied test case.
		 */
		@Test
		@Order(5)
		public void withSymbol() {
			test("hello @world");
		}

		/**
		 * Runs {@link #test(String)} with the supplied test case.
		 */
		@Test
		@Order(6)
		public void withUppercase() {
			test("HELLO WORLD");
		}

		/**
		 * Runs {@link #test(String)} with the supplied test case.
		 */
		@Test
		@Order(7)
		public void withExclamation() {
			test("¡Hello world!");
		}

		/**
		 * Runs {@link #test(String)} with the supplied test case.
		 */
		@Test
		@Order(8)
		public void withMarks() {
			test("héḶlõ ẁörld");
		}
	}

	/**
	 * Tests the {@link TextParser#parse(String)} method.
	 */
	@Nested
	@TestMethodOrder(OrderAnnotation.class)
	public class ParseTextEmptyTests {
		/**
		 * Calls {@link TextParser#parse(String)} on the supplied text, and makes
		 * sure it matches an empty array.
		 *
		 * @param text the text to parse
		 */
		public void test(String text) {
			String[] expected = new String[0];
			assertArrayEquals(expected, TextParser.parse(text));
		}

		/**
		 * Runs {@link #test(String)} with the supplied test case.
		 */
		@Test
		@Order(1)
		public void singleSpace() {
			test(" ");
		}

		/**
		 * Runs {@link #test(String)} with the supplied test case.
		 */
		@Test
		@Order(2)
		public void emptyString() {
			test("");
		}

		/**
		 * Runs {@link #test(String)} with the supplied test case.
		 */
		@Test
		@Order(3)
		public void onlyDigits() {
			test("1234567890");
		}

		/**
		 * Runs {@link #test(String)} with the supplied test case.
		 */
		@Test
		@Order(4)
		public void mixedSymbols() {
			test("\t 11@ ");
		}
	}
}
