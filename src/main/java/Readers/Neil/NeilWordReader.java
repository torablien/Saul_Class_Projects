package Readers.Neil;

// Uses the Apache POI API: see https://poi.apache.org/
// Based on various examples from Stack Overflow

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;

import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;

public class NeilWordReader {
    public static void main(String[] args)
    {
        File file = null;
        WordExtractor extractor = null;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter a filepath to a .doc file.");
        String fileName = scanner.nextLine();
        scanner.close();

        try
        {

            file = new File(fileName);
            FileInputStream fis = new FileInputStream(file.getAbsolutePath());
            HWPFDocument document = new HWPFDocument(fis);
            extractor = new WordExtractor(document);
            String[] paragraphs = extractor.getParagraphText();
            for (int i = 0; i < paragraphs.length; i++)
            {
                if (paragraphs[i] != null)
                    System.out.println(paragraphs[i]);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
