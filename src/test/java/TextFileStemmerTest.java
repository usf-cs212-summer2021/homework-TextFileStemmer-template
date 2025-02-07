import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.MethodName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.TagFilter;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

/**
 * Tests of the {@link TextFileStemmer} class.
 *
 * @author CS 212 Software Development
 * @author University of San Francisco
 * @version Summer 2021
 *
 * @see TextFileStemmer
 */
@TestMethodOrder(MethodName.class)
public class TextFileStemmerTest {
	/** Path to the test resources. */
	public static final Path BASE_PATH = Path.of("src", "test", "resources");

	/**
	 * Collection of tests.
	 *
	 * @see TextFileStemmer#listStems(String)
	 * @see TextFileStemmer#listStems(String, opennlp.tools.stemmer.Stemmer)
	 */
	@Nested
	@TestMethodOrder(OrderAnnotation.class)
	public class A_ListStemsTests {
		/**
		 * Tests expected output for given test case.
		 *
		 * @param line the line to stem
		 * @param output the expected output
		 */
		public void test(String line, String[] output) {
			List<String> expected = Arrays.stream(output)
					.collect(Collectors.toList());
			List<String> actual = TextFileStemmer.listStems(line);

			assertEquals(expected, actual);
		}

		// Test cases from: http://snowballstem.org/algorithms/english/stemmer.html
		// Right-click individual test methods to run only that test.

		/**
		 * Runs a single test case.
		 */
		@Test
		@Order(1)
		public void testOneWord() {
			String line = "conspicuously";
			String[] output = { "conspicu" };
			test(line, output);
		}

		/**
		 * Runs a single test case.
		 */
		@Test
		@Order(2)
		public void testEmpty() {
			test("", new String[] {});
		}

		/**
		 * Runs a single test case.
		 */
		@Test
		@Order(3)
		public void testGroupOne() {
			String[] input = { "consign", "consigned", "consigning", "consignment",
					"consist", "consisted", "consistency", "consistent", "consistently",
					"consisting", "consists", "consolation", "consolations",
					"consolatory", "console", "consoled", "consoles", "consolidate",
					"consolidated", "consolidating", "consoling", "consolingly",
					"consols", "consonant", "consort", "consorted", "consorting",
					"conspicuous", "conspicuously", "conspiracy", "conspirator",
					"conspirators", "conspire", "conspired", "conspiring", "constable",
					"constables", "constance", "constancy", "constant" };

			String[] output = { "consign", "consign", "consign", "consign", "consist",
					"consist", "consist", "consist", "consist", "consist", "consist",
					"consol", "consol", "consolatori", "consol", "consol", "consol",
					"consolid", "consolid", "consolid", "consol", "consol", "consol",
					"conson", "consort", "consort", "consort", "conspicu", "conspicu",
					"conspiraci", "conspir", "conspir", "conspir", "conspir", "conspir",
					"constabl", "constabl", "constanc", "constanc", "constant" };

			String line = String.join(", ", input);
			test(line, output);
		}

		/**
		 * Runs a single test case.
		 */
		@Test
		@Order(4)
		public void testGroupTwo() {
			String[] input = { "KNACK", "KNACKERIES", "KNACKS", "KNAG", "KNAVE",
					"KNAVES", "KNAVISH", "KNEADED", "KNEADING", "KNEE", "KNEEL",
					"KNEELED", "KNEELING", "KNEELS", "KNEES", "KNELL", "KNELT", "KNEW",
					"KNICK", "KNIF", "KNIFE", "KNIGHT", "KNIGHTLY", "KNIGHTS", "KNIT",
					"KNITS", "KNITTED", "KNITTING", "KNIVES", "KNOB", "KNOBS", "KNOCK",
					"KNOCKED", "KNOCKER", "KNOCKERS", "KNOCKING", "KNOCKS", "KNOPP",
					"KNOT", "KNOTS" };

			String[] output = { "knack", "knackeri", "knack", "knag", "knave",
					"knave", "knavish", "knead", "knead", "knee", "kneel", "kneel",
					"kneel", "kneel", "knee", "knell", "knelt", "knew", "knick", "knif",
					"knife", "knight", "knight", "knight", "knit", "knit", "knit", "knit",
					"knive", "knob", "knob", "knock", "knock", "knocker", "knocker",
					"knock", "knock", "knopp", "knot", "knot" };

			String line = String.join(" **** ", input);
			test(line, output);
		}
	}

	/**
	 * Collection of tests.
	 *
	 * @see TextFileStemmer#uniqueStems(String)
	 * @see TextFileStemmer#uniqueStems(String, opennlp.tools.stemmer.Stemmer)
	 */
	@Nested
	@TestMethodOrder(OrderAnnotation.class)
	public class B_UniqueStemsTests extends A_ListStemsTests {
		@Override
		public void test(String line, String[] output) {
			Set<String> expected = Arrays.stream(output).collect(Collectors.toSet());
			Set<String> actual = TextFileStemmer.uniqueStems(line);

			assertEquals(expected, actual);
		}
	}

	/**
	 * Collection of tests.
	 *
	 * @see TextFileStemmer#listStems(Path)
	 */
	@Nested
	@TestMethodOrder(OrderAnnotation.class)
	public class C_ListStemFileTests {
		/**
		 * Tests expected output for given test case.
		 *
		 * @param path the file path to stem
		 * @param output the expected output
		 * @throws IOException if I/O error occurs
		 */
		public void test(Path path, String[] output) throws IOException {
			List<String> expected = Arrays.stream(output)
					.collect(Collectors.toList());
			List<String> actual = TextFileStemmer.listStems(path);

			assertEquals(expected, actual);
		}

		/**
		 * Runs a single test case.
		 *
		 * @throws IOException if I/O error occurs
		 */
		@Test
		@Order(1)
		public void testWords() throws IOException {
			Path path = BASE_PATH.resolve("words.tExT");
			String[] output = { "observa", "observ", "observacion", "observ", "observ",
					"observ", "observ", "observ", "observ", "observ", "observ", "observ", 
					"observ", "perfor", "perfor", "perforc", "perform", "perform", "perform", 
					"perform", "perform", "perform", "perform", "perform", "respect", 
					"respect", "respect", "respect", "respect", "respect", "respect", 
					"respect", "respect", "respect", "respect", "respect" };
			test(path, output);
		}

		/**
		 * Runs a single test case.
		 *
		 * @throws IOException if I/O error occurs
		 */
		@Test
		@Order(2)
		public void testSymbols() throws IOException {
			Path path = BASE_PATH.resolve("symbols.txt");
			String[] output = { "antelop", "antelop", "antelop", "antelop", "antelop",
					"antelop", "antelop", "antelop", "antelop", "antelop" };
			test(path, output);
		}

		/**
		 * Runs a single test case.
		 *
		 * @throws IOException if I/O error occurs
		 */
		@Test
		@Order(3)
		public void testAnimals() throws IOException {
			Path path = BASE_PATH.resolve("animals.text");
			String[] output = { "okapi", "okapi", "mongoos", "lori", "lori", "lori",
					"axolotl", "narwhal", "platypus", "echidna", "tarsier" };
			test(path, output);
		}

		/**
		 * Runs a single test case.
		 *
		 * @throws IOException if I/O error occurs
		 */
		@Test
		@Order(4)
		public void testStemmer() throws IOException {
			Path input = BASE_PATH.resolve("stem-in.txt");
			Path output = BASE_PATH.resolve("stem-out.txt");

			String[] expected = TextParser
					.parse(Files.readString(output, StandardCharsets.UTF_8));
			test(input, expected);
		}
	}

	/**
	 * Collection of tests.
	 *
	 * @see TextFileStemmer#uniqueStems(Path)
	 */
	@Nested
	@TestMethodOrder(OrderAnnotation.class)
	public class D_UniqueStemFileTests extends C_ListStemFileTests {
		@Override
		public void test(Path path, String[] output) throws IOException {
			Set<String> expected = Arrays.stream(output).collect(Collectors.toSet());
			Set<String> actual = TextFileStemmer.uniqueStems(path);

			assertEquals(expected, actual);
		}
	}

	/**
	 * Attempts to check for issues with the approach.
	 */
	@Nested
	@Tag("approach")
	public class E_ApproachTests {
		/**
		 * Checks to see if the File class was imported.
		 */
		@Test
		public void testFileImport() {
			String regex = "(is)\\bimport\\s+java.io.File\\s*;";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(source);
			Assertions.assertFalse(matcher.find(),
					"Do not use the java.io.File class in your code!");
		}

		/**
		 * Checks to see if try-with-resources was used
		 */
		@Test
		public void testTryWithResources() {
			String regex = "(?is)\\btry\\s*\\([^}]+\\)";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(source);
			Assertions.assertTrue(matcher.find(),
					"Make sure you use try-with-resources in your code!");
		}
		
		/**
		 * Checks that methods throw exceptions as expected
		 */
		@Test
		public void testThrowsNullExceptions() {
			Assertions.assertThrows(NullPointerException.class, () -> {
				Path nullPath = null;
				TextFileStemmer.listStems(nullPath);
			});
		}
		
		/**
		 * Checks that methods throw exceptions as expected
		 */
		@Test
		public void testThrowsDirectoryExceptions() {
			Assertions.assertThrows(IOException.class, () -> {
				Path nullPath = Path.of("src");
				TextFileStemmer.listStems(nullPath);
			});
		}
		
		/**
		 * Checks that methods throw exceptions as expected
		 */
		@Test
		public void testThrowsNoFileExceptions() {
			Assertions.assertThrows(IOException.class, () -> {
				Path nullPath = Path.of("nowhere");
				TextFileStemmer.uniqueStems(nullPath);
			});
		}
		
		/**
		 * Causes this group of tests to fail if the other non-approach tests are
		 * not yet passing.
		 */
		@Test
		public void testOthersPassing() {
			var request = LauncherDiscoveryRequestBuilder.request()
					.selectors(DiscoverySelectors.selectClass(TextFileStemmerTest.class))
					.filters(TagFilter.excludeTags("approach"))
					.build();

			var launcher = LauncherFactory.create();
			var listener = new SummaryGeneratingListener();

			Logger logger = Logger.getLogger("org.junit.platform.launcher");
			logger.setLevel(Level.SEVERE);

			launcher.registerTestExecutionListeners(listener);
			launcher.execute(request);

			Assertions.assertEquals(0, listener.getSummary().getTotalFailureCount(),
					"Must pass other tests to earn credit for approach group!");
		}
		
		/** The source code for TextFileStemmer. */
		private String source;

		/**
		 * Loads the entire source code as a String object.
		 *
		 * @throws IOException if an IO error occurs
		 */
		@BeforeEach
		public void setup() throws IOException {
			Path path = Path.of("src", "main", "java",
					TextFileStemmer.class.getSimpleName() + ".java");
			source = Files.readString(path, StandardCharsets.UTF_8);
		}
	}
}
