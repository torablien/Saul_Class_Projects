package App.Neil

import edu.illinois.cs.cogcomp.saulexamples.data.Document;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class NeilWordReader {
    public ArrayList<Document> docs = new ArrayList<>();

    public NeilWordReader(String fileName, String label) throws IOException {
        File file = null;
        WordExtractor extractor = null;

        try {

            file = new File(fileName);
            FileInputStream fis = new FileInputStream(file.getAbsolutePath());
            HWPFDocument document = new HWPFDocument(fis);
            extractor = new WordExtractor(document);
            String[] paragraphs = extractor.getParagraphText();

            for (int i = 0; i < paragraphs.length; i++) {
                if (paragraphs[i] != null) {
                    ArrayList<String> paragraphsList = new ArrayList<>(Arrays.asList(paragraphs[i]));
                    docs.add(new Document(paragraphsList, label));
                }
            }

    } catch (Exception e) {
            e.printStackTrace();
        }
    }
}