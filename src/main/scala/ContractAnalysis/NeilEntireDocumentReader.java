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
public class NeilEntireDocumentReader {
    public ArrayList<DocumentData> docs = new ArrayList<>();
    public String docText;

    public NeilEntireDocumentReader(String fileName, String label) throws IOException {
        File file = null;
        WordExtractor extractor = null;

        try {

            file = new File(fileName);
            FileInputStream fis = new FileInputStream(file.getAbsolutePath());
            HWPFDocument document = new HWPFDocument(fis);
            extractor = new WordExtractor(document);
            docText = extractor.getText();
            ArrayList<String> text = new ArrayList<>(Arrays.asList(extractor.getText()));
            docs.add(new DocumentData(text, label));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}