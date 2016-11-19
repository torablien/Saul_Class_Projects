package ContractAnalysis;

import ContractAnalysis.data.DocumentData;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Neil on 10/26/2016.
 */
public class NeilWordReader {
    public ArrayList<DocumentData> docs = new ArrayList<>();

    public NeilWordReader(String fileName, String label, int docNumber) throws IOException {
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
                    ArrayList<String> paragraphsList = new ArrayList<>(Arrays.asList(paragraphs[i].split("\\s+")));
                    docs.add(new DocumentData(paragraphsList, label, docNumber));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}