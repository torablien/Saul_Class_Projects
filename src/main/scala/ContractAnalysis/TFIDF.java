package ContractAnalysis;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TFIDF {

    private final Map<String, Double> tfidfMap = new HashMap<String, Double>();
    public static Map<String, Double> dfMap = new HashMap<String, Double>();

    private final String document1 = new NeilEntireDocumentReader("C:\\Users\\Neil\\Desktop\\CMPS3240\\Saul\\Saul_Class_Projects\\src\\main\\scala\\ContractAnalysis\\data\\Contracts\\(Collab) series-seed---certificate-of-incorporation-v-3-2.doc", "test", 1).docText;
    private final String doc1Arr[] = document1.split("\\s+");

    private final String document2 = new NeilEntireDocumentReader("C:\\Users\\Neil\\Desktop\\CMPS3240\\Saul\\Saul_Class_Projects\\src\\main\\scala\\ContractAnalysis\\data\\Contracts\\(Arms Length) NVCA-Voting-Agt-with-Bad-Actor-Provisions-Feb-2014.doc", "test", 2).docText;
    private final String doc2Arr[] = document2.split("\\s+");

    private final String document3 = new NeilEntireDocumentReader("C:\\Users\\Neil\\Desktop\\CMPS3240\\Saul\\Saul_Class_Projects\\src\\main\\scala\\ContractAnalysis\\data\\Contracts\\(Collab) series-seed---stock-investment-agreement-v-3-2.doc", "test", 3).docText;
    private final String doc3Arr[] = document3.split("\\s+");

    private final String document4 = new NeilEntireDocumentReader("C:\\Users\\Neil\\Desktop\\CMPS3240\\Saul\\Saul_Class_Projects\\src\\main\\scala\\ContractAnalysis\\data\\Contracts\\(Arms Length) SPA-with-Bad-Actor-Provisions-Feb-2014.doc", "test", 4).docText;
    private final String doc4Arr[] = document4.split("\\s+");

    private final String docsArr[][] = {doc1Arr, doc2Arr, doc3Arr, doc4Arr};

    private final String combinedDocs[] = (document1 + document2 + document3 + document4).split("\\s+");


    public TFIDF(int whichDocument) throws IOException {
        String currDoc[];
        if(whichDocument == 1)
            currDoc = doc1Arr;
        else if(whichDocument == 2)
            currDoc = doc2Arr;
        else if(whichDocument == 3)
            currDoc = doc3Arr;
        else
            currDoc = doc4Arr;

        System.out.println("Calculating TFIDF Map for document " + whichDocument + "...");

        long startTime = System.currentTimeMillis();
        for(String word: combinedDocs) {
            word = word.replaceAll("[^a-zA-Z]", "").toLowerCase();
            if(!dfMap.containsKey(word))
                dfMap.put(word, idf(word, docsArr));
        }
        long endTime = System.currentTimeMillis();

        System.out.println("Calculated DF Map in " + (endTime - startTime) + " milliseconds");

        startTime = System.currentTimeMillis();

        for(String word: combinedDocs) {
            word = word.replaceAll("[^a-zA-Z]", "").toLowerCase();
            if(!tfidfMap.containsKey(word))
                tfidfMap.put(word, tf(word, currDoc) * dfMap.get(word));
        }

        endTime = System.currentTimeMillis();

        System.out.println("Calculated full TFIDF Map for document " + whichDocument + " in " + (endTime - startTime) + " milliseconds");


    }

   public static double tf(String term, String[] document) {
       double count = 0;
       for (String word : document)
       {
           if (term.equals(word.replaceAll("[^a-zA-Z]", "").toLowerCase()))
           {
               count++;
           }
       }
       return count / document.length;
   }

    public static double idf(String term, String[][] documents) {
        double count = 0;
        for (String[] document : documents) {
            for (String word : document) {
                if (term.equals(word.replaceAll("[^a-zA-Z]", "").toLowerCase())){
                    count++;
                    break;
                }
            }
        }
        return Math.log(documents.length/(1+count));
    }

    public static double getTFIDF(String term, String[] document, String[][] documents) {
        return tf(term, document) * idf(term, documents);
    }

    public Map<String, Double> getTFIDFMap(){
        return tfidfMap;
    }

    public List<Double> getTFIDFValues(){
        List<Double> values = new ArrayList<>(tfidfMap.values());
        return (values);
    }

    public static void writeToFile(String filepath, List<Double> values){
        try {
            File file = new File(filepath);

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            for(double value: values)
                bw.write(String.valueOf(value) + '\n');
            bw.close();

            System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws IOException {

        TFIDF d1 = new TFIDF(1);
        TFIDF d2 = new TFIDF(2);
        TFIDF d3 = new TFIDF(3);
        TFIDF d4 = new TFIDF(4);


        System.out.println(d1.getTFIDFValues());
        System.out.println(d2.getTFIDFValues());
        System.out.println(d3.getTFIDFValues());
        System.out.println(d4.getTFIDFValues());

        writeToFile("C:\\Users\\Neil\\Desktop\\CMPS3240\\Saul\\Saul_Class_Projects\\src\\main\\scala\\ContractAnalysis\\data\\TFIDF\\TFIDFDoc1.txt",d1.getTFIDFValues());
        writeToFile("C:\\Users\\Neil\\Desktop\\CMPS3240\\Saul\\Saul_Class_Projects\\src\\main\\scala\\ContractAnalysis\\data\\TFIDF\\TFIDFDoc2.txt",d2.getTFIDFValues());
        writeToFile("C:\\Users\\Neil\\Desktop\\CMPS3240\\Saul\\Saul_Class_Projects\\src\\main\\scala\\ContractAnalysis\\data\\TFIDF\\TFIDFDoc3.txt",d3.getTFIDFValues());
        writeToFile("C:\\Users\\Neil\\Desktop\\CMPS3240\\Saul\\Saul_Class_Projects\\src\\main\\scala\\ContractAnalysis\\data\\TFIDF\\TFIDFDoc4.txt",d4.getTFIDFValues());



    }
}