package ContractAnalysis;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class TFIDF {

    private final Map<String, Double> tfidfMap = new HashMap<String, Double>();
    public static Map<String, Double> dfMap = new HashMap<String, Double>();

    private final String document1 = new NeilEntireDocumentReader("C:\\Users\\Neil\\Desktop\\CMPS3240\\Saul\\Saul_Class_Projects\\src\\main\\scala\\ContractAnalysis\\data\\Contracts\\(Collab) series-seed---certificate-of-incorporation-v-3-2.doc", "test", 1).docText;
    private final String doc1Arr[] = filterWords(document1.split("\\s+"));

    private final String document2 = new NeilEntireDocumentReader("C:\\Users\\Neil\\Desktop\\CMPS3240\\Saul\\Saul_Class_Projects\\src\\main\\scala\\ContractAnalysis\\data\\Contracts\\(Arms Length) NVCA-Voting-Agt-with-Bad-Actor-Provisions-Feb-2014.doc", "test", 2).docText;
    private final String doc2Arr[] = filterWords(document2.split("\\s+"));

    private final String document3 = new NeilEntireDocumentReader("C:\\Users\\Neil\\Desktop\\CMPS3240\\Saul\\Saul_Class_Projects\\src\\main\\scala\\ContractAnalysis\\data\\Contracts\\(Collab) series-seed---stock-investment-agreement-v-3-2.doc", "test", 3).docText;
    private final String doc3Arr[] = filterWords(document3.split("\\s+"));

    private final String document4 = new NeilEntireDocumentReader("C:\\Users\\Neil\\Desktop\\CMPS3240\\Saul\\Saul_Class_Projects\\src\\main\\scala\\ContractAnalysis\\data\\Contracts\\(Arms Length) SPA-with-Bad-Actor-Provisions-Feb-2014.doc", "test", 4).docText;
    private final String doc4Arr[] = filterWords(document4.split("\\s+"));

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

        String[] fCombinedDocs = filterWords(combinedDocs);

        long startTime = System.currentTimeMillis();
        for(String word: fCombinedDocs) {
            if(!dfMap.containsKey(word) && !word.equals(""))
                dfMap.put(word, idf(word, docsArr));
        }
        long endTime = System.currentTimeMillis();

        System.out.println("Calculated DF Map in " + (endTime - startTime) + " milliseconds");

        startTime = System.currentTimeMillis();

        for(String word: fCombinedDocs) {
            if(!tfidfMap.containsKey(word) && !word.equals(""))
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


    public String[] filterWords(String[] words){
        String[] stopwords = {"a", "as", "able", "about", "above", "according", "accordingly", "across", "actually", "after", "afterwards", "again", "against", "aint", "all", "allow", "allows", "almost", "alone", "along", "already", "also", "although", "always", "am", "among", "amongst", "an", "and", "another", "any", "anybody", "anyhow", "anyone", "anything", "anyway", "anyways", "anywhere", "apart", "appear", "appreciate", "appropriate", "are", "arent", "around", "as", "aside", "ask", "asking", "associated", "at", "available", "away", "awfully", "be", "became", "because", "become", "becomes", "becoming", "been", "before", "beforehand", "behind", "being", "believe", "below", "beside", "besides", "best", "better", "between", "beyond", "both", "brief", "but", "by", "cmon", "cs", "came", "can", "cant", "cannot", "cant", "cause", "causes", "certain", "certainly", "changes", "clearly", "co", "com", "come", "comes", "concerning", "consequently", "consider", "considering", "contain", "containing", "contains", "corresponding", "could", "couldnt", "course", "currently", "definitely", "described", "despite", "did", "didnt", "different", "do", "does", "doesnt", "doing", "dont", "done", "down", "downwards", "during", "each", "edu", "eg", "eight", "either", "else", "elsewhere", "enough", "entirely", "especially", "et", "etc", "even", "ever", "every", "everybody", "everyone", "everything", "everywhere", "ex", "exactly", "example", "except", "far", "few", "ff", "fifth", "first", "five", "followed", "following", "follows", "for", "former", "formerly", "forth", "four", "from", "further", "furthermore", "get", "gets", "getting", "given", "gives", "go", "goes", "going", "gone", "got", "gotten", "greetings", "had", "hadnt", "happens", "hardly", "has", "hasnt", "have", "havent", "having", "he", "hes", "hello", "help", "hence", "her", "here", "heres", "hereafter", "hereby", "herein", "hereupon", "hers", "herself", "hi", "him", "himself", "his", "hither", "hopefully", "how", "howbeit", "however", "i", "id", "ill", "im", "ive", "ie", "if", "ignored", "immediate", "in", "inasmuch", "inc", "indeed", "indicate", "indicated", "indicates", "inner", "insofar", "instead", "into", "inward", "is", "isnt", "it", "itd", "itll", "its", "its", "itself", "just", "keep", "keeps", "kept", "know", "knows", "known", "last", "lately", "later", "latter", "latterly", "least", "less", "lest", "let", "lets", "like", "liked", "likely", "little", "look", "looking", "looks", "ltd", "mainly", "many", "may", "maybe", "me", "mean", "meanwhile", "merely", "might", "more", "moreover", "most", "mostly", "much", "must", "my", "myself", "name", "namely", "nd", "near", "nearly", "necessary", "need", "needs", "neither", "never", "nevertheless", "new", "next", "nine", "no", "nobody", "non", "none", "noone", "nor", "normally", "not", "nothing", "novel", "now", "nowhere", "obviously", "of", "off", "often", "oh", "ok", "okay", "old", "on", "once", "one", "ones", "only", "onto", "or", "other", "others", "otherwise", "ought", "our", "ours", "ourselves", "out", "outside", "over", "overall", "own", "particular", "particularly", "per", "perhaps", "placed", "please", "plus", "possible", "presumably", "probably", "provides", "que", "quite", "qv", "rather", "rd", "re", "really", "reasonably", "regarding", "regardless", "regards", "relatively", "respectively", "right", "said", "same", "saw", "say", "saying", "says", "second", "secondly", "see", "seeing", "seem", "seemed", "seeming", "seems", "seen", "self", "selves", "sensible", "sent", "serious", "seriously", "seven", "several", "shall", "she", "should", "shouldnt", "since", "six", "so", "some", "somebody", "somehow", "someone", "something", "sometime", "sometimes", "somewhat", "somewhere", "soon", "sorry", "specified", "specify", "specifying", "still", "sub", "such", "sup", "sure", "ts", "take", "taken", "tell", "tends", "th", "than", "thank", "thanks", "thanx", "that", "thats", "thats", "the", "their", "theirs", "them", "themselves", "then", "thence", "there", "theres", "thereafter", "thereby", "therefore", "therein", "theres", "thereupon", "these", "they", "theyd", "theyll", "theyre", "theyve", "think", "third", "this", "thorough", "thoroughly", "those", "though", "three", "through", "throughout", "thru", "thus", "to", "together", "too", "took", "toward", "towards", "tried", "tries", "truly", "try", "trying", "twice", "two", "un", "under", "unfortunately", "unless", "unlikely", "until", "unto", "up", "upon", "us", "use", "used", "useful", "uses", "using", "usually", "value", "various", "very", "via", "viz", "vs", "want", "wants", "was", "wasnt", "way", "we", "wed", "well", "were", "weve", "welcome", "well", "went", "were", "werent", "what", "whats", "whatever", "when", "whence", "whenever", "where", "wheres", "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", "whether", "which", "while", "whither", "who", "whos", "whoever", "whole", "whom", "whose", "why", "will", "willing", "wish", "with", "within", "without", "wont", "wonder", "would", "would", "wouldnt", "yes", "yet", "you", "youd", "youll", "youre", "youve", "your", "yours", "yourself", "yourselves", "zero"};
        Set<String> stopWordSet = new HashSet<String>(Arrays.asList(stopwords));
        List<String> filteredWords = new ArrayList<>();
        for(String prestripWord : words)
        {
            String word = prestripWord.replaceAll("[^a-zA-Z]", "").toLowerCase();
            if(!stopWordSet.contains(word))
                filteredWords.add(word);
        }
        String[] filteredArr = new String[filteredWords.size()];
        return filteredWords.toArray(filteredArr);
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


        System.out.println(d1.getTFIDFMap());
        System.out.println(d2.getTFIDFMap());
        System.out.println(d3.getTFIDFMap());
        System.out.println(d4.getTFIDFMap());


        writeToFile("C:\\Users\\Neil\\Desktop\\CMPS3240\\Saul\\Saul_Class_Projects\\src\\main\\scala\\ContractAnalysis\\data\\TFIDF\\TFIDFDoc1.txt",d1.getTFIDFValues());
        writeToFile("C:\\Users\\Neil\\Desktop\\CMPS3240\\Saul\\Saul_Class_Projects\\src\\main\\scala\\ContractAnalysis\\data\\TFIDF\\TFIDFDoc2.txt",d2.getTFIDFValues());
        writeToFile("C:\\Users\\Neil\\Desktop\\CMPS3240\\Saul\\Saul_Class_Projects\\src\\main\\scala\\ContractAnalysis\\data\\TFIDF\\TFIDFDoc3.txt",d3.getTFIDFValues());
        writeToFile("C:\\Users\\Neil\\Desktop\\CMPS3240\\Saul\\Saul_Class_Projects\\src\\main\\scala\\ContractAnalysis\\data\\TFIDF\\TFIDFDoc4.txt",d4.getTFIDFValues());

    }
}