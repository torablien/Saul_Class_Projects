package ContractAnalysis

import edu.illinois.cs.cogcomp.lbjava.learn._
import edu.illinois.cs.cogcomp.saul.classifier.Learnable
import edu.illinois.cs.cogcomp.saul.learn.SaulWekaWrapper
import ContractAnalysis.ContractDataModel._
import ContractAnalysis.data.DocumentData
import weka.classifiers.bayes.{BayesNet, NaiveBayes}
import weka.classifiers.trees.RandomForest


object ContractClassifiers {
  object ContractClassifier extends Learnable[DocumentData](docs) {
    def label = contractLabel
    override lazy val classifier = new SupportVectorMachine()
    override def feature = using(wordFeature, bigramFeature)
  }

  object ContractClassifierWithCache extends Learnable[DocumentData](docs) {
    def label = contractLabel
    override lazy val classifier = new SupportVectorMachine()
    override def feature = using(wordFeature, bigramFeature)
    override val useCache = true
  }

  object DeserializedContractClassifier extends Learnable[DocumentData](docs) {
    def label = contractLabel
    override lazy val classifier = new SupportVectorMachine()
    override def feature = using(wordFeature, bigramFeature)
  }
  object ContractClassifierNaiveBayes extends Learnable[DocumentData](docs) {
    def label = contractLabel
    override lazy val classifier = new SaulWekaWrapper(new NaiveBayes())
    override def feature = using(filteredWordFeature, bigramFeature)
  }

  object SparseNetworkContractClassifier extends Learnable[DocumentData](docs) {
    def label = contractLabel
    override lazy val classifier = new SparseNetworkLearner()
    override def feature = using(filteredWordFeature, lexiconWordFeature, bigramFeature, trigramFeature, tfidfFeature)
    //override def feature = using(tfidfFeature)

  }

  object RandomForestContractClassifier extends Learnable[DocumentData](docs) {
    def label = contractLabel
    override lazy val classifier = new SaulWekaWrapper(new RandomForest())
    override def feature = using(wordFeature, bigramFeature)
  }

  object SparseAveragedPerceptronClassifier extends Learnable[DocumentData](docs) {
    def label = contractLabel
    override lazy val classifier = new SparseAveragedPerceptron();
    override def feature = using(filteredWordFeature, lexiconWordFeature, bigramFeature, trigramFeature)
  }

  object AdaBoostClassifier extends Learnable[DocumentData](docs) {
    def label = contractLabel
    override lazy val classifier = new AdaBoost();
    override def feature = using(filteredWordFeature, lexiconWordFeature, bigramFeature, trigramFeature)
  }

}
