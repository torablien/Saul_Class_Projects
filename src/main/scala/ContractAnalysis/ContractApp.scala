package ContractAnalysis

import ContractAnalysis.ContractClassifiers._
import edu.illinois.cs.cogcomp.saul.util.Logging

import scala.collection.JavaConversions._

object ContractApp extends Logging {
  val filepath = "C:\\Users\\Neil\\Desktop\\CMPS3240\\Saul\\Saul_Class_Projects\\src\\main\\scala\\ContractAnalysis\\data\\Contracts\\"
  val trainingFiles = Array(("(Collab) series-seed---stock-investment-agreement-v-3-2.doc", "collaborative"),("(Arms Length) SPA-with-Bad-Actor-Provisions-Feb-2014.doc", "arms-length"))


  val trainDataCollab = new NeilWordReader(filepath + "(Collab) series-seed---stock-investment-agreement-v-3-2.doc","collaborative").docs.toList
  val trainDataArmsLength = new NeilWordReader(filepath + "(Arms Length) SPA-with-Bad-Actor-Provisions-Feb-2014.doc","arms-length").docs.toList
  val trainData = trainDataCollab ++ trainDataArmsLength


  val testDataArmsLength = new NeilWordReader(filepath + "(Arms Length) NVCA-Voting-Agt-with-Bad-Actor-Provisions-Feb-2014.doc","arms-length").docs.toList
  val testDataCollab = new NeilWordReader(filepath + "(Collab) series-seed---certificate-of-incorporation-v-3-2.doc","collaborative").docs.toList
  val testData = testDataCollab ++ testDataArmsLength
  //println(testData)


  object ContractExperimentType extends Enumeration {
    val NaiveBayes, CacheGraph, TestUsingGraphCache, TestSerialization, SparseNetwork, RandomForest = Value
  }

  def main(args: Array[String]): Unit = {
    /** Choose the experiment you're interested in by changing the following line */
    val testType = ContractExperimentType.SparseNetwork

    testType match {
      case ContractExperimentType.NaiveBayes => BayesContractClassifier()
      case ContractExperimentType.CacheGraph => ContractClassifierWithGraphCache()
      case ContractExperimentType.TestUsingGraphCache => ContractClassifierFromCache()
      case ContractExperimentType.TestSerialization => ContractClassifierWithSerialization()
      case ContractExperimentType.SparseNetwork => ContractClassifierWithSparseNetwork()
      case ContractExperimentType.RandomForest => ContractClassifierWithRandomForest()
    }

  }

  /** A standard method for testing the Spam Classification problem. Simply training and testing the resulting model.*/
  def BayesContractClassifier(): Unit = {
    /** Defining the data and specifying it's location  */
    ContractDataModel.docs populate trainData
    ContractClassifierNaiveBayes.learn(1)
    ContractClassifierNaiveBayes.test(testData)
  }

  /** Spam Classifcation, followed by caching the data-model graph. */
  val graphCacheFile = "models/temp.model"
  def ContractClassifierWithGraphCache(): Unit = {
    /** Defining the data and specifying it's location  */
    ContractDataModel.docs populate trainData
    ContractDataModel.deriveInstances()
    ContractDataModel.write(graphCacheFile)
    ContractClassifierWithCache.learn(30)
    ContractClassifierWithCache.test(testData)
  }

  /** Testing the functionality of the cache. `SpamClassifierWithCache` produces the temporary model file need for
    * this methdd to run.
    */
  def ContractClassifierFromCache() {
    ContractDataModel.load(graphCacheFile)
    ContractClassifierWithCache.learn(30)
    ContractClassifierWithCache.test(testData)
  }

  /** Testing the serialization functionality of the model. We first train a model and save it. Then we load the model
    * and use it for prediction. We later check whether the predictions of the deserialized model are the same as the
    * predictions before serialization.
    */
  def ContractClassifierWithSerialization(): Unit = {
    ContractDataModel.docs populate trainData
    ContractClassifier.learn(30)
    ContractClassifier.save()
    DeserializedContractClassifier.load(ContractClassifier.lcFilePath, ContractClassifier.lexFilePath)
    val predictionsBeforeSerialization = testData.map(ContractClassifier(_))
    val predictionsAfterSerialization = testData.map(DeserializedContractClassifier(_))
    logger.info(predictionsBeforeSerialization.mkString("/"))
    logger.info(predictionsAfterSerialization.mkString("/"))
    logger.info(predictionsAfterSerialization.indices.forall(it => predictionsBeforeSerialization(it) == predictionsAfterSerialization(it)).toString)
  }

  def ContractClassifierWithSparseNetwork(): Unit = {
    /** Defining the data and specifying it's location  */
    ContractDataModel.docs populate trainData
    SparseNetworkContractClassifier.learn(10)
    SparseNetworkContractClassifier.test(testData)

  }

  def ContractClassifierWithRandomForest(): Unit = {
    /** Defining the data and specifying it's location  */
    ContractDataModel.docs populate trainData
    RandomForestContractClassifier.learn(5)
    RandomForestContractClassifier.test(testData)
  }

}


/*
1. Naive Bayes takes long tme
2. TFIDF implement to Saul
3. What is layout and length of paper? When is it due?
4. What is considered acceptable results?
5. What algorithm would you recommend?

 */