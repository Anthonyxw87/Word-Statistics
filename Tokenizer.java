import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.FileNotFoundException;

public class Tokenizer {
    // a list that stores the values of the characters in ascii integers
    ArrayList<String> list = new ArrayList<String>();

    // converts the file into a list with all the words
    public Tokenizer(String filename) throws FileNotFoundException, IOException {
        list = new ArrayList<String>();
        this.convertFileToList(filename);
    }

    // puts the strings in the array into a list with all the words, while normalizing the words
    public Tokenizer(String[] array) {
        for (int i = 0; i < array.length; i++) {
            String[] stringAfterSplit = array[i].replaceAll("[^a-zA-Z_\\s+]", "").toLowerCase().split("\\s+"); // replace
            List<String> newList = Arrays.asList(stringAfterSplit);
            list.addAll(newList);
        }
    }

    private void convertFileToList(String filename) throws FileNotFoundException, IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            char c;
            int value = 0;
            StringBuilder word = new StringBuilder();
            while (value != -1) { // this runs until the end of the file
                value = br.read();
                c = (char) value;
                while (Character.isLetter(c) == false) {
                    if (value == 32 || value == 10) { // if the character is a space or end of line
                        if (word.length() != 0) {
                            wordList().add(word.toString());
                            word.setLength(0);
                        }
                    }
                    value = br.read();
                    c = (char) value;
                    if (value == -1) { // if it is the end of the file
                        if (word.length() != 0) // if file ends where there is an ed of a word
                            list.add(word.toString());
                        br.close();
                        return;
                    }
                }
                c = Character.toLowerCase(c);
                word.append(c);
            }
        }
    }

    // returns the word list obtained with all the words normalized
    public ArrayList<String> wordList() {
        return this.list;
    }
}