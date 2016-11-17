package ContractAnalysis.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Vivek Srikumar
 *
 */
public class DocumentData {

    private final String label;
    private final List<String> words;
    private final List<String> filteredWords = new ArrayList<>();

    private final String vocabulary[] = {"bad"};

    private String guid;

    /**
     * Create a new document
     *
     * @throws java.io.IOException
     */


    public DocumentData(File file, String label) throws IOException {
        this.label = label;
        BufferedReader reader = new BufferedReader(new FileReader(file));
        this.guid=file.getName();
        words = new ArrayList<>();

        String line;
        String trimmedWord;
        while ((line = reader.readLine()) != null) {
            for (String word : line.split("\\s+"))
            {
                trimmedWord = word.trim();
                words.add(trimmedWord);
            }
        }

        reader.close();
    }

    public DocumentData(File file) throws IOException {
        this(file, "unknown");
    }

    public DocumentData(List<String> words) {

        this(words, "unknown");
    }

    public void setGUID(String guid) {
        this.guid = guid;
    }

    public String getGUID(){
        return this.guid;
    }


    public DocumentData(List<String> words, String label)
    {
        this.words = words;
        this.label = label;
        for(String prestripWord : words)
        {
            String word = prestripWord.replaceAll("[^a-zA-Z]", "").toLowerCase();
            for(String v : vocabulary)
            {
                if (word.equals(v))
                {
                    filteredWords.add(word);
                    System.out.println(word + " added");
                }
            }
        }
    }

    public String getLabel() {
        return label;
    }

    public List<String> getWords() {
        return Collections.unmodifiableList(words);
    }

    public List<String> getFilteredWords() {
        return Collections.unmodifiableList(filteredWords);
    }


    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return label + ", " + words;
    }

}
