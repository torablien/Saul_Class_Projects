package Readers.Neil;

// Uses PDFBox API: see http://pdfbox.apache.org/
// Based on various examples from Stack Overflow

import java.io.*;
import java.util.Scanner;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class NeilPDFReader {
    public static void main(String args[]) {

        PDFParser parser = null;
        PDDocument pdDoc = null;
        COSDocument cosDoc = null;
        PDFTextStripper pdfStripper;

        String outputText;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter a filepath to a .pdf file.");
        String fileName = scanner.nextLine();
        scanner.close();

        try
        {
            pdDoc = PDDocument.load(new File(fileName));
            pdfStripper = new PDFTextStripper();

            outputText = pdfStripper.getText(pdDoc);
            System.out.println(outputText);
            pdDoc.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
