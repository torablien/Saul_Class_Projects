package ContractAnalysis.data;

import ContractAnalysis.DoubleReader;
import ContractAnalysis.StringReader;
import edu.stanford.nlp.util.ArrayUtils;

import java.io.IOException;
import java.util.*;

import static ContractAnalysis.TFIDF.tf;

public class DocumentData {

    private final String label;
    private final List<String> words;
    private final int docNumber;
    private final List<String> filteredWords = new ArrayList<>();
    private final List<String> lexiconWords = new ArrayList<>();

    private final String[] stopwords = {"a", "as", "able", "about", "above", "according", "accordingly", "across", "actually", "after", "afterwards", "again", "against", "aint", "all", "allow", "allows", "almost", "alone", "along", "already", "also", "although", "always", "am", "among", "amongst", "an", "and", "another", "any", "anybody", "anyhow", "anyone", "anything", "anyway", "anyways", "anywhere", "apart", "appear", "appreciate", "appropriate", "are", "arent", "around", "as", "aside", "ask", "asking", "associated", "at", "available", "away", "awfully", "be", "became", "because", "become", "becomes", "becoming", "been", "before", "beforehand", "behind", "being", "believe", "below", "beside", "besides", "best", "better", "between", "beyond", "both", "brief", "but", "by", "cmon", "cs", "came", "can", "cant", "cannot", "cant", "cause", "causes", "certain", "certainly", "changes", "clearly", "co", "com", "come", "comes", "concerning", "consequently", "consider", "considering", "contain", "containing", "contains", "corresponding", "could", "couldnt", "course", "currently", "definitely", "described", "despite", "did", "didnt", "different", "do", "does", "doesnt", "doing", "dont", "done", "down", "downwards", "during", "each", "edu", "eg", "eight", "either", "else", "elsewhere", "enough", "entirely", "especially", "et", "etc", "even", "ever", "every", "everybody", "everyone", "everything", "everywhere", "ex", "exactly", "example", "except", "far", "few", "ff", "fifth", "first", "five", "followed", "following", "follows", "for", "former", "formerly", "forth", "four", "from", "further", "furthermore", "get", "gets", "getting", "given", "gives", "go", "goes", "going", "gone", "got", "gotten", "greetings", "had", "hadnt", "happens", "hardly", "has", "hasnt", "have", "havent", "having", "he", "hes", "hello", "help", "hence", "her", "here", "heres", "hereafter", "hereby", "herein", "hereupon", "hers", "herself", "hi", "him", "himself", "his", "hither", "hopefully", "how", "howbeit", "however", "i", "id", "ill", "im", "ive", "ie", "if", "ignored", "immediate", "in", "inasmuch", "inc", "indeed", "indicate", "indicated", "indicates", "inner", "insofar", "instead", "into", "inward", "is", "isnt", "it", "itd", "itll", "its", "its", "itself", "just", "keep", "keeps", "kept", "know", "knows", "known", "last", "lately", "later", "latter", "latterly", "least", "less", "lest", "let", "lets", "like", "liked", "likely", "little", "look", "looking", "looks", "ltd", "mainly", "many", "may", "maybe", "me", "mean", "meanwhile", "merely", "might", "more", "moreover", "most", "mostly", "much", "must", "my", "myself", "name", "namely", "nd", "near", "nearly", "necessary", "need", "needs", "neither", "never", "nevertheless", "new", "next", "nine", "no", "nobody", "non", "none", "noone", "nor", "normally", "not", "nothing", "novel", "now", "nowhere", "obviously", "of", "off", "often", "oh", "ok", "okay", "old", "on", "once", "one", "ones", "only", "onto", "or", "other", "others", "otherwise", "ought", "our", "ours", "ourselves", "out", "outside", "over", "overall", "own", "particular", "particularly", "per", "perhaps", "placed", "please", "plus", "possible", "presumably", "probably", "provides", "que", "quite", "qv", "rather", "rd", "re", "really", "reasonably", "regarding", "regardless", "regards", "relatively", "respectively", "right", "said", "same", "saw", "say", "saying", "says", "second", "secondly", "see", "seeing", "seem", "seemed", "seeming", "seems", "seen", "self", "selves", "sensible", "sent", "serious", "seriously", "seven", "several", "shall", "she", "should", "shouldnt", "since", "six", "so", "some", "somebody", "somehow", "someone", "something", "sometime", "sometimes", "somewhat", "somewhere", "soon", "sorry", "specified", "specify", "specifying", "still", "sub", "such", "sup", "sure", "ts", "take", "taken", "tell", "tends", "th", "than", "thank", "thanks", "thanx", "that", "thats", "thats", "the", "their", "theirs", "them", "themselves", "then", "thence", "there", "theres", "thereafter", "thereby", "therefore", "therein", "theres", "thereupon", "these", "they", "theyd", "theyll", "theyre", "theyve", "think", "third", "this", "thorough", "thoroughly", "those", "though", "three", "through", "throughout", "thru", "thus", "to", "together", "too", "took", "toward", "towards", "tried", "tries", "truly", "try", "trying", "twice", "two", "un", "under", "unfortunately", "unless", "unlikely", "until", "unto", "up", "upon", "us", "use", "used", "useful", "uses", "using", "usually", "value", "various", "very", "via", "viz", "vs", "want", "wants", "was", "wasnt", "way", "we", "wed", "well", "were", "weve", "welcome", "well", "went", "were", "werent", "what", "whats", "whatever", "when", "whence", "whenever", "where", "wheres", "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", "whether", "which", "while", "whither", "who", "whos", "whoever", "whole", "whom", "whose", "why", "will", "willing", "wish", "with", "within", "without", "wont", "wonder", "would", "would", "wouldnt", "yes", "yet", "you", "youd", "youll", "youre", "youve", "your", "yours", "yourself", "yourselves", "zero"};
    private final Set<String> stopWordSet = new HashSet<String>(Arrays.asList(stopwords));

    private final String vocabulary[] = {"bad", "bad-actor", "failure", "sale"};
    private final Set<String> vocabularySet = new HashSet<String>(Arrays.asList(vocabulary));

    private static final List<Double> dfValues = new DoubleReader("C:\\Users\\Neil\\Desktop\\CMPS3240\\Saul\\Saul_Class_Projects\\src\\main\\scala\\ContractAnalysis\\data\\TFIDF\\DFValues.txt").doubles;
    private static final List<String> dfKeys = new StringReader("C:\\Users\\Neil\\Desktop\\CMPS3240\\Saul\\Saul_Class_Projects\\src\\main\\scala\\ContractAnalysis\\data\\TFIDF\\DFKeys.txt").allStrings;

    private final Map<String, Double> tfMap = new HashMap<String, Double>();
    private static Map<String, Double> dfMap = new HashMap<String, Double>();
    private final Map<String, Double> tfidfMap = new HashMap<String, Double>();




    public DocumentData(List<String> words, String label, int docNumber) throws IOException
    {

        this.words = words;
        this.label = label;
        this.docNumber = docNumber;

        filterWords(words);
        buildLexiconSet(filteredWords);
        buildTFMap(filteredWords);
        buildDFMap(dfKeys, dfValues);
        //System.out.println(tfMap);
        for(String dfKey : dfMap.keySet()) {
            if(tfMap.containsKey(dfKey))
                tfidfMap.put(dfKey, dfMap.get(dfKey) * tfMap.get(dfKey));
            else
                tfidfMap.put(dfKey, 0.0);
        }

        /*
        for(String tfKeys: tfMap.keySet())
            System.out.print(tfKeys + ": " + tfidfMap.get(tfKeys) + " ");
        System.out.println();
        */
    }


    public DocumentData(List<String> words, String label) throws IOException
    {
        this(words, label, -1);
    }


    public DocumentData(List<String> words) throws IOException {

        this(words, "unknown");
    }

    public void filterWords(List<String> words){
        for(String prestripWord : words)
        {
            String word = prestripWord.replaceAll("[^a-zA-Z]", "").toLowerCase();
            if(!stopWordSet.contains(word))
                filteredWords.add(word);
        }
    }

    public void buildLexiconSet(List<String> words){
        for(String word : words) {
            if (vocabularySet.contains(word)) {
                lexiconWords.add(word);
            }
        }
    }

    public void buildTFMap(List<String> words){
        String[] wordsArr = words.toArray(new String[words.size()]);
        for(String word : wordsArr) {
            if (!tfMap.containsKey(word) && !word.equals(""))
                tfMap.put(word, tf(word, wordsArr));
        }

    }

    private void buildDFMap(List<String> keys, List<Double> values){
            for(int i = 0; i < keys.size(); i++){
                dfMap.put(keys.get(i), values.get(i));
            }
    }


    public String getLabel() {
        return label;
    }

    public int getDocNumber(){return docNumber;}

    public List<String> getWords() {
        return Collections.unmodifiableList(words);
    }

    public List<String> getFilteredWords() {return Collections.unmodifiableList(filteredWords);}

    public List<String> getLexiconWords() {
        return Collections.unmodifiableList(lexiconWords);
    }

    public double[] getTFIDFList() {
        double[] x = ArrayUtils.toPrimitive(tfidfMap.values().toArray(new Double[tfidfMap.values().size()]));
        return x;
    }

    @Override
    public String toString() {
        return docNumber + ", " + label + ", " + words;
    }

}
