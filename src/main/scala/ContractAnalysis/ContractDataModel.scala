package ContractAnalysis

import ContractAnalysis.data.DocumentData
import edu.illinois.cs.cogcomp.saul.datamodel.DataModel

import scala.collection.JavaConversions._

object ContractDataModel extends DataModel{
  val docs = node[DocumentData]

  val wordFeature = property(docs, "wordF") { // bag of words for ALL words (raw)
    x: DocumentData => x.getWords.toList
  }

  val filteredWordFeature = property(docs, "filteredWordF") { // bag of words for FILTERED words. Makes all lowercase, removes stopwords, and special characters
    x: DocumentData => x.getFilteredWords.toList
  }

  val lexiconWordFeature = property(docs, "lexiconWordF") { // only considered words part of defined vocabulary in DocumentData
    x: DocumentData => x.getLexiconWords.toList
  }

  val bigramFeature = property(docs, "bigram") { // two word phrases
    x: DocumentData =>
      val words = x.getFilteredWords.toList
      words.sliding(2).map(_.mkString("-")).toList
  }

  val trigramFeature = property(docs, "trigram") { // three word phrases
    x: DocumentData =>
      val words = x.getFilteredWords.toList
      words.sliding(3).map(_.mkString("-")).toList
  }

  val quadgramFeature = property(docs, "quadgram") { // four word phrases
    x: DocumentData =>
      val words = x.getFilteredWords.toList
      words.sliding(4).map(_.mkString("-")).toList
  }

  val tfidfFeature = property(docs, "tfidf") {
    x: DocumentData => x.getTFIDFList.toList
  }

  val contractLabel = property(docs)("arms-length","collaborative") {
    x: DocumentData => x.getLabel
  }


}
