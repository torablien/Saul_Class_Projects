package ContractAnalysis

import edu.illinois.cs.cogcomp.lbjava.learn.SupportVectorMachine
import edu.illinois.cs.cogcomp.saul.classifier.Learnable
import edu.illinois.cs.cogcomp.saul.learn.SaulWekaWrapper
import edu.illinois.cs.cogcomp.saulexamples.data.Document
import ContractAnalysis.ContractDataModel._
import edu.illinois.cs.cogcomp.lbjava.learn.SparseNetworkLearner
import weka.classifiers.bayes.NaiveBayes


object ContractClassifiers {
  object ContractClassifier extends Learnable[Document](docs) {
    def label = contractLabel
    override lazy val classifier = new SupportVectorMachine()
    override def feature = using(wordFeature)
  }

  object ContractClassifierWithCache extends Learnable[Document](docs) {
    def label = contractLabel
    override lazy val classifier = new SupportVectorMachine()
    override def feature = using(wordFeature)
    override val useCache = true
  }

  object DeserializedContractClassifier extends Learnable[Document](docs) {
    def label = contractLabel
    override lazy val classifier = new SupportVectorMachine()
    override def feature = using(wordFeature)
  }
  object ContractClassifierWeka extends Learnable[Document](docs) {
    def label = contractLabel
    //override lazy val classifier = new SaulWekaWrapper(new NaiveBayes())
    override lazy val classifier = new SparseNetworkLearner()
    override def feature = using(wordFeature, bigramFeature)
  }

  object SparseNetworkContractClassifier extends Learnable[Document](docs) {
    def label = contractLabel
    override lazy val classifier = new SparseNetworkLearner()
    override def feature = using(wordFeature, bigramFeature)
  }


}
