package ContractAnalysis.data;

import ContractAnalysis.DoubleReader;

import java.util.*;


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

    private static final List<Double> tfidfDoc1 = new DoubleReader("C:\\Users\\Neil\\Desktop\\CMPS3240\\Saul\\Saul_Class_Projects\\src\\main\\scala\\ContractAnalysis\\data\\TFIDF\\TFIDFDoc1.txt").doubles;
    private static final List<Double> tfidfDoc2 = new DoubleReader("C:\\Users\\Neil\\Desktop\\CMPS3240\\Saul\\Saul_Class_Projects\\src\\main\\scala\\ContractAnalysis\\data\\TFIDF\\TFIDFDoc2.txt").doubles;
    private static final List<Double> tfidfDoc3 = new DoubleReader("C:\\Users\\Neil\\Desktop\\CMPS3240\\Saul\\Saul_Class_Projects\\src\\main\\scala\\ContractAnalysis\\data\\TFIDF\\TFIDFDoc3.txt").doubles;
    private static final List<Double> tfidfDoc4 = new DoubleReader("C:\\Users\\Neil\\Desktop\\CMPS3240\\Saul\\Saul_Class_Projects\\src\\main\\scala\\ContractAnalysis\\data\\TFIDF\\TFIDFDoc4.txt").doubles;


    public DocumentData(List<String> words, String label, int docNumber)
    {

        this.words = words;
        this.label = label;
        this.docNumber = docNumber;

        filterWords(words);
        buildLexiconSet(filteredWords);

    }

    public DocumentData(List<String> words, String label)
    {
        this(words, label, -1);
    }


    public DocumentData(List<String> words) {

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
        for(String word : filteredWords) {
            if (vocabularySet.contains(word)) {
                lexiconWords.add(word);

            }
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

    public List<Double> getTFIDFList() {
        if(docNumber == 1)
            return Collections.unmodifiableList(tfidfDoc1);
        else if(docNumber == 2)
            return Collections.unmodifiableList(tfidfDoc2);
        else if(docNumber == 3)
            return Collections.unmodifiableList(tfidfDoc3);
        else if(docNumber == 4)
            return Collections.unmodifiableList(tfidfDoc4);
        else{
            System.out.println("ERROR GETTING TFIDF LIST!");
            return Collections.unmodifiableList(tfidfDoc1);
        }
    }



    @Override
    public String toString() {
        return docNumber + ", " + label + ", " + words;
    }

}
