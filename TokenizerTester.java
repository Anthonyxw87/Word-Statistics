import org.junit.*;
import static org.junit.Assert.assertEquals;

public class TokenizerTester {

    @Test
    public void testFileConstructor() {
        Tokenizer fileTest = null;
        String path = getClass().getResource("testTokenizer.txt").getPath();
        try {
            fileTest = new Tokenizer(path);
        } catch (Exception e) {
        }
        assertEquals("[in, reality, though, the, unity, and, coherence, of, ideas, among, sentences, is, what, constitutes, a, paragraph, a, paragraph, is, defined, as, a, group, of, sentences, or, a, single, sentence, that, forms, a, unit, lunsford, and, connors]", fileTest.wordList().toString());
    }

    @Test
    public void testStringArrayConstructor(){
        String[] wordArray = {"This is a TesT", "more more more", "this anot--her", "more test", "is this"};
        Tokenizer arrayTest = new Tokenizer(wordArray);
        assertEquals("[this, is, a, test, more, more, more, this, another, more, test, is, this]", arrayTest.wordList().toString());

    }
}
