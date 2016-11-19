package ContractAnalysis;

import java.io.IOException;
import java.util.*;

public class TFIDF {

    public static Map<String, Double> tfidfMap = new HashMap<String, Double>();

    public TFIDF(int whichDocument) throws IOException {
        String document1 = new NeilEntireDocumentReader("C:\\Users\\Neil\\Desktop\\CMPS3240\\Saul\\Saul_Class_Projects\\src\\main\\scala\\ContractAnalysis\\data\\Contracts\\(Collab) series-seed---certificate-of-incorporation-v-3-2.doc", "test", 1).docText;
        String doc1Arr[] = document1.split("\\s+");

        String document2 = new NeilEntireDocumentReader("C:\\Users\\Neil\\Desktop\\CMPS3240\\Saul\\Saul_Class_Projects\\src\\main\\scala\\ContractAnalysis\\data\\Contracts\\(Arms Length) NVCA-Voting-Agt-with-Bad-Actor-Provisions-Feb-2014.doc", "test", 2).docText;
        String doc2Arr[] = document2.split("\\s+");

        String document3 = new NeilEntireDocumentReader("C:\\Users\\Neil\\Desktop\\CMPS3240\\Saul\\Saul_Class_Projects\\src\\main\\scala\\ContractAnalysis\\data\\Contracts\\(Collab) series-seed---stock-investment-agreement-v-3-2.doc", "test", 3).docText;
        String doc3Arr[] = document3.split("\\s+");

        String document4 = new NeilEntireDocumentReader("C:\\Users\\Neil\\Desktop\\CMPS3240\\Saul\\Saul_Class_Projects\\src\\main\\scala\\ContractAnalysis\\data\\Contracts\\(Arms Length) SPA-with-Bad-Actor-Provisions-Feb-2014.doc", "test", 4).docText;
        String doc4Arr[] = document4.split("\\s+");

        String docsArr[][] = {doc1Arr, doc2Arr, doc3Arr, doc4Arr};

        String combinedDocs[] = (document1 + document2 + document3 + document4).split("\\s+");

        String currDoc[];
        if(whichDocument == 1)
            currDoc = doc1Arr;
        else if(whichDocument == 2)
            currDoc = doc2Arr;
        else if(whichDocument == 3)
            currDoc = doc3Arr;
        else
            currDoc = doc4Arr;

        long startTime = System.currentTimeMillis();

        Map<String, Double> dfMap = new HashMap<String, Double>();
        for(String word: combinedDocs) {
            if(!dfMap.containsKey(word))
                dfMap.put(word, idf(word, docsArr));
        }
        long endTime = System.currentTimeMillis();

        System.out.println("Calculated DF Map in " + (endTime - startTime) + "milliseconds");

        startTime = System.currentTimeMillis();

        for(String word: combinedDocs) {
            if(!tfidfMap.containsKey(word))
                tfidfMap.put(word, tf(word, currDoc) * dfMap.get(word));
        }

        endTime = System.currentTimeMillis();

        System.out.println("Calculated full TFIDF Map in " + (endTime - startTime) + "milliseconds");


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


    public static void main(String[] args) throws IOException {

        TFIDF x = new TFIDF(1);
        System.out.println(x.getTFIDFValues());

    }
}