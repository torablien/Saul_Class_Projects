package ContractAnalysis

import ContractAnalysis.data.DocumentData
import edu.illinois.cs.cogcomp.saul.datamodel.DataModel

import scala.collection.JavaConversions._

object ContractDataModel extends DataModel{
  val docs = node[DocumentData]

  val wordFeature = property(docs, "wordF") {
    x: DocumentData => x.getWords.toList
  }

  val filteredWordFeature = property(docs, "filteredWordF") {
    x: DocumentData => x.getFilteredWords.toList
  }

  val lexicobWordFeature = property(docs, "lexiconWordF") {
    x: DocumentData => x.getLexiconWords.toList
  }

  val bigramFeature = property(docs, "bigram") {
    x: DocumentData =>
      val words = x.getFilteredWords.toList
      words.sliding(2).map(_.mkString("-")).toList
  }

  val trigramFeature = property(docs, "trigram") {
    x: DocumentData =>
      val words = x.getFilteredWords.toList
      words.sliding(3).map(_.mkString("-")).toList
  }

  val contractLabel = property(docs, "label") {
    x: DocumentData => x.getLabel
  }


}
