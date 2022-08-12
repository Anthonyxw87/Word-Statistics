import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

public class WordStat {
        private HashTable wordTable; // a hashtable that stores the HashEntries of all the words
        private HashTable wordPairTable; // a hashTable that stores the HashEntries of all word pairs
        private HashEntry[] wordListLeastCommon; // a HashEntry array that stores the HashEntries of all words from least common to most common
        private HashEntry[] wordListMostCommon; // a HashEntry array that stores the HashEntries of all words from most common to least common
        private HashEntry[] wordPairsMostCommon; // a HashEntry array that stores the HashEntires of all word pairs from most common to least common
        private ArrayList<String> wordList; // an arraylist of all the words in strings (may have duplicates)
        private ArrayList<HashEntry> exportedArray; // an arraylist of all the words in HashEntry type (no duplicates)
        private ArrayList<HashEntry> exportedPairsArray; // an arraylist of all the word pairs in HashEntry type (no duplicates)

        // computes the words statistics from the file name
        public WordStat(String filename) throws FileNotFoundException, IOException {
                try {
                        Tokenizer a = new Tokenizer(filename);
                        setWordList(a.wordList());

                } catch (Exception e) {
                        throw e;
                }
                buildWordTable();
                exportedArray = getWordTable().exportArray();
                wordListLeastCommon = new HashEntry[exportedArray.size()];
                wordListMostCommon = new HashEntry[exportedArray.size()];
                buildWordPairsTable();
                exportedPairsArray = getWordPairTable().exportArray();
                wordPairsMostCommon = new HashEntry[exportedPairsArray.size()];
                buildWordListMostCommon();
                buildWordListLeastCommon();
                buildWordPairsListMostCommon();
        }

        // computes the word statistics from a string array
        public WordStat(String[] array) {
                Tokenizer a = new Tokenizer(array);
                setWordList(a.wordList());
                buildWordTable();
                exportedArray = getWordTable().exportArray();
                wordListLeastCommon = new HashEntry[exportedArray.size()];
                wordListMostCommon = new HashEntry[exportedArray.size()];
                buildWordPairsTable();
                exportedPairsArray = getWordPairTable().exportArray();
                wordPairsMostCommon = new HashEntry[exportedPairsArray.size()];
                buildWordListMostCommon();
                buildWordListLeastCommon();
                buildWordPairsListMostCommon();
        }

        // getter method for table field
        private HashTable getWordTable() {
                return wordTable;
        }

        // setter method for table field
        private void setWordTable(HashTable wordTable) {
                this.wordTable = wordTable;
        }

        // getter method for wordPairTable
        private HashTable getWordPairTable() {
                return wordPairTable;
        }

        // setter method for wordPairTable
        private void setWordPairTable(HashTable wordPairTable) {
                this.wordPairTable = wordPairTable;
        }

        // getter method for wordList field
        private ArrayList<String> getWordList() {
                return wordList;
        }

        // setter method for wordList Field
        private void setWordList(ArrayList<String> wordList) {
                this.wordList = wordList;
        }

        private HashEntry[] getWordListMostCommon() {
                return wordListMostCommon;
        }

        // getter method for wordListLeastCommon
        private HashEntry[] getWordListLeastCommon() {
                return wordListLeastCommon;
        }

        // getter method for wordPairsMostCommon
        private HashEntry[] getWordPairsMostCommon() {
                return wordPairsMostCommon;
        }

        // helper method that builds the wordtable from a word list
        private void buildWordTable() {
                setWordTable(new HashTable(getWordList().size() * 3));
                String word;
                for (int i = 0; i < getWordList().size(); i++) {
                        word = getWordList().get(i);
                        if (getWordTable().get(word) == -1) { // if the word does not exist in the table
                                getWordTable().put(word, 1);
                        }
                        // if the word exists in the table already, increment the value by one
                        else {
                                getWordTable().update(word, getWordTable().get(word) + 1);
                        }
                        if (i != 0){
                                getWordTable().getEntry(word).putInLeft(getWordList().get(i - 1));
                        }
                        if (i != getWordList().size() - 1)
                                getWordTable().getEntry(word).putInRight(getWordList().get(i + 1));
                }
        }

        // helper method that builds the wordPairTable
        private void buildWordPairsTable() {
                setWordPairTable(new HashTable(getWordList().size() * 3));
                String wordPair;
                int index1 = 0;
                int index2 = 1;
                while (index2 < getWordList().size()) {
                        wordPair = getWordList().get(index1) + " " + getWordList().get(index2);
                        // if the word pair is not in the table, put it in
                        if (getWordPairTable().get(wordPair) == -1) {
                                getWordPairTable().put(wordPair, 1);
                        }
                        // if the word pair exists in the table, increment the value of it by one
                        else {
                                if (getWordPairTable().get(wordPair) != -1)
                                        getWordPairTable().update(wordPair, getWordPairTable().get(wordPair) + 1);
                        }
                        index1 = index1 + 1;
                        index2 = index2 + 1;
                }
        }

        // build an array list with the words in sorted order from least common to most
        // common
        private void buildWordListLeastCommon() {
                for (int i = 0; i < exportedArray.size(); i++) {
                        getWordListLeastCommon()[i] = exportedArray.get(i);
                        getWordListLeastCommon()[i].setMostCommon(false); // a boolean value that will determine how an array is sorted(most to least or least to most)
                }
                // sorts the array using Java's arraylist sort method
                Arrays.sort(getWordListLeastCommon());
        }

        // builds an array list with the words in sorted order from most common to least
        // common
        private void buildWordListMostCommon() {
                for (int i = 0; i < exportedArray.size(); i++) {
                        if (exportedArray.get(i) != null) {
                                getWordListMostCommon()[i] = exportedArray.get(i);
                                getWordListMostCommon()[i].setMostCommon(true); // a boolean value that will determine how an array is sorted(most to least or least to most)
                        }
                }

                // sorts the array using Java's
                Arrays.sort(getWordListMostCommon());

                // sets the rank of the HashEntry using the index since the array is sorted from
                // most common to least common
                for (int j = 0; j < getWordListMostCommon().length; j++) {
                        getWordListMostCommon()[j].setRankMostCommon(j + 1);
                }
        }

        // builds a list with the word pairs from most common to least
        private void buildWordPairsListMostCommon() {
                for (int i = 0; i < exportedPairsArray.size(); i++) {
                        if (exportedPairsArray.get(i) != null) {
                                getWordPairsMostCommon()[i] = exportedPairsArray.get(i);
                                getWordPairsMostCommon()[i].setMostCommon(true); // a boolean value that will determinehow an array is sorted(most to least or least to most)
                        }
                }
                Arrays.sort(getWordPairsMostCommon());

                // sets the rank of the HashEntry using the index since the array is sorted from
                // most common to least common
                for (int j = 0; j < getWordPairsMostCommon().length; j++) {
                        getWordPairsMostCommon()[j].setRankMostCommon(j + 1);
                }
        }

        // helper method that normalizes the string input
        private String normalize(String s) {
                StringBuilder b = new StringBuilder();
                for (int i = 0; i < s.length(); i++) {
                        if (s.charAt(i) == 39) { // if there is a apostrophe, include it
                                b.append(s.charAt(i));
                        }
                        if (Character.isLetter(s.charAt(i)))
                                b.append(Character.toLowerCase(s.charAt(i)));
                }
                return b.toString();
        }

        // returns the count of the word
        public int wordCount(String word) {
                word = normalize(word);
                int count = getWordTable().get(word);
                if (count == -1)
                        return 0;
                else
                        return count;
        }

        // returns the count of the word pair
        public int wordPairCount(String w1, String w2) {
                String s = normalize(w1) + " " + normalize(w2);
                if (getWordPairTable().get(s) != -1) // if the word pair exists in the table
                        return getWordPairTable().get(s);
                else
                        return 0;
        }

        // returns the rank of the word (rank 1 being most common)
        public int wordRank(String word) {
                word = normalize(word);
                if (getWordTable().getRankMostCommon(word) != -1) // if the word exists in the table
                        return getWordTable().getRankMostCommon(word);

                else
                        return 0;
        }

        // returns the rank of the word pair
        public int wordPairRank(String w1, String w2) {
                String s = normalize(w1) + " " + normalize(w2);
                if (getWordPairTable().getRankMostCommon(s) != -1) // if the word pair exists in the table
                        return getWordPairTable().getRankMostCommon(s);
                else
                        return 0;
        }

        // returns an array with the words ordered from most common to least common
        public String[] mostCommonWords(int k) {
                if (k > getWordListMostCommon().length)
                        k = exportedArray.size();

                if (k <= 0)
                        return null;

                String[] arrayToReturn = new String[k];
                // this loop adds k elements from the sorted array (most common to least common)
                for (int i = 0; i < k; i++) {
                        arrayToReturn[i] = getWordListMostCommon()[i].getKey();
                }
                return arrayToReturn;
        }

        // returns an String array with the words ordered from least common to most
        // common
        public String[] leastCommonWords(int k) {
                if (k >= getWordListLeastCommon().length)
                        k = exportedArray.size();

                if (k <= 0)
                        return null;

                String[] arrayToReturn = new String[k];
                // this loops adds k elements from the sorted array (least common to most
                // common)
                for (int i = 0; i < k; i++) {
                        arrayToReturn[i] = getWordListLeastCommon()[i].getKey();
                }

                return arrayToReturn;
        }

        // returns an String array with the word pairs ordered from most common to least common
        public String[] mostCommonWordPairs(int k) {
                if (k > getWordPairsMostCommon().length)
                        k = exportedPairsArray.size();

                if (k <= 0)
                        return null;

                String[] arrayToReturn = new String[k];

                // this loop adds k elements from the sorted array (most common to least common)
                for (int i = 0; i < k; i++) {
                        arrayToReturn[i] = getWordPairsMostCommon()[i].getKey();
                }
                return arrayToReturn;
        }

        // returns an String array with the k most common words to the right or left of the baseWord
        public String[] mostCommonCollocs(int k, String baseWord, int i) {
                if (i < 0) {
                        ArrayList<HashEntry> arrayToSort = getWordTable().getEntry(baseWord).getLeftTable().exportArray();
                        String[] arrayToReturn = new String[k];
                        HashEntry[] wordListMostCommonCollocs = new HashEntry[arrayToSort.size()];
                        for (int j = 0; j < arrayToSort.size() && j < k; j++) {
                                if (arrayToSort.get(j) != null) {
                                        wordListMostCommonCollocs[j] = arrayToSort.get(j);
                                        wordListMostCommonCollocs[j].setMostCommon(true); 
                                }
                        }
                        Arrays.sort(wordListMostCommonCollocs);

                        int count = 0;
                        for(HashEntry e : wordListMostCommonCollocs){
                                if (e != null) {
                                        arrayToReturn[count] = e.getKey();
                                        count = count + 1;
                                }
                        }
                        return arrayToReturn;
                }

                if (i > 0) {
                        ArrayList<HashEntry> arrayToSort = getWordTable().getEntry(baseWord).getRightTable().exportArray();
                        HashEntry[] wordListMostCommonCollocs = new HashEntry[arrayToSort.size()];
                        String[] arrayToReturn = new String[k];
                        for (int x = 0; x < arrayToSort.size() && x < k; x++) {
                                if (arrayToSort.get(x) != null) {
                                        wordListMostCommonCollocs[x] = arrayToSort.get(x);
                                        wordListMostCommonCollocs[x].setMostCommon(true); 
                                }
                        }
                        Arrays.sort(wordListMostCommonCollocs);

                        int count = 0;
                        for(HashEntry e : wordListMostCommonCollocs){
                                if (e != null) {
                                        arrayToReturn[count] = e.getKey();
                                        count = count + 1;
                                }
                        }
                        return arrayToReturn;
                }
                else 
                        return null;
        }

        public static void main(String[] args) {
                System.out.println("\n" + "Demonstration for WordStat class:" + "\n");

                System.out.println(
                                "****Demonstration for WordStat(String filename), WordStat(String[] array), wordCount(), wordPairCount(), wordRank(), wordPairRank(),"
                                                + "\n" +
                                                "mostCommonWords(), leastCommonWords(), mostCommonWordPairs(), mostCommonCollocs()****"
                                                + "\n");

                System.out.println("Code: ");
                System.out.println(
                                "try {" + "\n" +
                                                "WordStat test = new WordStat('/Users/anthonywang64/Desktop/P4/testWordCount.txt');"
                                                + "\n" +
                                                "WordStat test2 = new WordStat('/Users/anthonywang64/Desktop/P4/testWordPairCount.txt');"
                                                + "\n" +
                                                "assertEquals(5, test.wordCount('A'));" + "\n" +
                                                "assertEquals(2, test2.wordPairCount('it', 'is'));" + "\n" +
                                                "assertEquals(1, test2.wordRank('stuff'));" + "\n" +
                                                "assertEquals(1, test2.wordPairRank('stuff', 'stuff'));" + "\n" +
                                                "assertEquals('stuff', test2.mostCommonWords(6)[0]);" + "\n" +
                                                "assertEquals('for', test2.leastCommonWords(6)[0]);" + "\n" +
                                                "assertEquals('it is', test.mostCommonWordPairs(30)[1]);" + "\n" +
                                                "}" + "\n" +
                                                "catch(Exception e){" + "\n" +
                                                "Assert.fail(e+'');" + "\n" +
                                                "}");

                System.out.println("\n" + "Output:");
                try {
                        WordStat test = new WordStat("/Users/anthonywang64/Desktop/P4/testWordCount.txt");
                        WordStat test2 = new WordStat("/Users/anthonywang64/Desktop/P4/testWordPairCount.txt");
                        System.out.println("test.wordCount('A'): " + test.wordCount("A"));
                        System.out.println("test2.wordPairCount('it', 'is'): " + test2.wordPairCount("it", "is"));
                        System.out.println("test2.wordRank('stuff'): " + test2.wordRank("stuff"));
                        System.out.println("test2.wordPairRank('stuff', 'stuff'): "
                                        + test2.wordPairRank("stuff", "stuff"));
                        System.out.println("test2.mostCommonWords(6)[0]: " + test2.mostCommonWords(6)[0]);
                        System.out.println("test2.leastCommonWords(6)[0]: " + test2.leastCommonWords(6)[0]);
                        System.out.println("test2.mostCommonWordPairs(30)[1]: " + test2.mostCommonWordPairs(30)[1]);
                } catch (Exception e) {
                }
        }
}
