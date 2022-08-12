import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class HashTableTester {
        @Test
        public void testPut() {
                HashTable test = new HashTable();
                // test for adding a word and getting the value
                test.put("word", 1);
                assertEquals(1, test.get("word"));
                test.put("this", 2);
                assertEquals(2, test.get("this"));
                // test for adding word with same hashCode as an obove word
                test.put("hello", 3, "word".hashCode());
                assertEquals(3, test.get("hello", "word".hashCode())); // have to use word hashcode for testing
                test.put("Anthony", 4);
                assertEquals(4, test.get("Anthony"));
                test.put("Peter", 5);
                assertEquals(5, test.get("Peter"));
                assertEquals(5, test.get("Peter"));
        }

        @Test
        public void testUpdate() {
                HashTable test = new HashTable(2);
                test.put("word", 1);
                test.put("this", 2);
                test.put("hello", 3);
                test.put("Anthony", 4);
                assertEquals(2, test.get("this"));
                assertEquals(4, test.get("Anthony"));
                test.update("Anthony", 7);
                assertEquals(7, test.get("Anthony"));
                test.update("word", 0);
                assertEquals(0, test.get("word"));
                // test below for adding new word because it doens't exist
                test.update("how", 3);
                assertEquals(3, test.get("how"));
        }
}
