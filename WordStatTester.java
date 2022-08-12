import org.junit.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class WordStatTester {
        @Test
        public void testWordCount() {
                WordStat test = null;
                String path = getClass().getResource("testWordCount.txt").getPath();
                try {
                        test = new WordStat(path);
                        assertEquals(5, test.wordCount("A"));

                }
                catch(Exception e){
                        Assert.fail(e+"");
                }

                try{
                        test = new WordStat(path);
                        assertEquals(2, test.wordCount("Is"));
                }
                catch(Exception e){
                        Assert.fail(e+"");
                }
                
                try{
                        test = new WordStat(path);
                        assertEquals(2, test.wordCount("Paragraph"));
                }
                
                catch(Exception e){
                        Assert.fail(e+"");
                }
                
                try{
                        test = new WordStat(path);
                        assertEquals(0, test.wordCount("mom"));
                }
                catch (Exception e) {
                        Assert.fail(e+"");
                }

                String[] wordArray = { "Th-is is a TeST", "more more more", "this another", "more test", "is this" };
                WordStat test2 = new WordStat(wordArray);
                assertEquals(2, test2.wordCount("is"));
                assertEquals(4, test2.wordCount("More"));
                assertEquals(0, test2.wordCount("mom"));
                assertEquals(2, test2.wordCount("test"));
        }

        @Test
        public void testWordPairCount() {
                String path = getClass().getResource("testWordPairCount.txt").getPath();
                try {
                        WordStat test = new WordStat(path);
                        assertEquals(1, test.wordPairCount("test", "test"));
                        assertEquals(1, test.wordPairCount("most", "is"));
                        assertEquals(2, test.wordPairCount("it", "is"));
                        assertEquals(4, test.wordPairCount("stuff", "stuff"));
                } catch (Exception e) {
                        Assert.fail(e+"");
                }
        }
        
        @Test
        public void testWordRank(){
                String path = getClass().getResource("testWordPairCount.txt").getPath();
                try{
                        WordStat test = new WordStat(path);
                        assertEquals(0, test.wordRank("dad"));
                        assertEquals(1, test.wordRank("stuff"));
                }
                catch(Exception e){
                        Assert.fail(e+"");
                }
                
                String[] wordArray = { "Th-is is a TeST", "more more more", "this another", "more test", "is this" };
                WordStat test2 = new WordStat(wordArray);
                assertEquals(1 , test2.wordRank("more"));
        }

        @Test
        public void testWordPairRank(){
                String path = getClass().getResource("testWordPairCount.txt").getPath();
                try{
                        WordStat test = new WordStat(path);
                        assertEquals(1, test.wordPairRank("stuff", "stuff"));
                }
                catch(Exception e){
                        Assert.fail(e+"");
                }
        }

        @Test
        public void testMostCommonWords(){
                String path = getClass().getResource("testWordPairCount.txt").getPath();
                try{
                        WordStat test = new WordStat(path);
                        assertEquals("stuff", test.mostCommonWords(6)[0]);
                        assertEquals("is", test.mostCommonWords(10)[1]);
                        assertEquals("it", test.mostCommonWords(10)[2]);
                        assertEquals("matter", test.mostCommonWords(22)[21]);
                }
                catch(Exception e){
                        Assert.fail(e+"");
                }

                String[] wordArray = { "This is a TesT", "more more more", "this another", "more test", "is this" };
                WordStat test2 = new WordStat(wordArray);
                assertEquals("more", test2.mostCommonWords(6)[0]);
                assertEquals("this", test2.mostCommonWords(30)[1]);
        }

        @Test 
        public void testLeastCommonWords(){
                String path = getClass().getResource("testWordPairCount.txt").getPath();
                WordStat test;
                try{
                        test = new WordStat(path);
                        assertEquals("for", test.leastCommonWords(6)[0]);
                        assertEquals("stuff", test.leastCommonWords(32)[21]);
                }
                catch(Exception e){
                        Assert.fail(e+"");
                }

                String[] wordArray = { "This is a TesT", "more more more", "this another", "more test", "is this" };
                WordStat test2 = new WordStat(wordArray);
                assertEquals("another", test2.leastCommonWords(6)[1]);
        }


        @Test 
        public void testMostCommonWordPairs(){
                String path = getClass().getResource("testWordPairCount.txt").getPath();
                WordStat test;
                try{
                        test = new WordStat(path);
                        assertEquals("stuff stuff", test.mostCommonWordPairs(30)[0]);
                        assertEquals("it is", test.mostCommonWordPairs(30)[1]);
                }
                catch(Exception e){
                        Assert.fail(e+"");
                }

                String[] wordArray = { "This is a TesT", "more more more", "this another", "more test", "is this" };
                WordStat test2 = new WordStat(wordArray);
                assertEquals("more more", test2.mostCommonWordPairs(6)[0]);
        }
}

