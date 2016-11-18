package ContractAnalysis;

import ContractAnalysis.data.DocumentData;

import java.io.IOException;
import java.util.*;

public class TFIDF {

    public static Map<String, Double> tfidfMap = new HashMap<String, Double>();
    public TFIDF(ArrayList<DocumentData> document) {

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


    public static void main(String[] args) throws IOException {
        String document1 = new NeilEntireDocumentReader("C:\\Users\\Neil\\Desktop\\CMPS3240\\Saul\\Saul_Class_Projects\\src\\main\\scala\\ContractAnalysis\\data\\Contracts\\(Collab) series-seed---certificate-of-incorporation-v-3-2.doc", "test").docText;
        String doc1Arr[] = document1.split("\\s+");

        String document2 = new NeilEntireDocumentReader("C:\\Users\\Neil\\Desktop\\CMPS3240\\Saul\\Saul_Class_Projects\\src\\main\\scala\\ContractAnalysis\\data\\Contracts\\(Arms Length) NVCA-Voting-Agt-with-Bad-Actor-Provisions-Feb-2014.doc", "test").docText;
        String doc2Arr[] = document2.split("\\s+");
        String docsArr[][] = {doc1Arr, doc2Arr};

        for(String word: doc2Arr) {
            if(!tfidfMap.containsKey(word))
                tfidfMap.put(word, getTFIDF(word, doc2Arr, docsArr));
        }

        System.out.println(tfidfMap);


    }
}