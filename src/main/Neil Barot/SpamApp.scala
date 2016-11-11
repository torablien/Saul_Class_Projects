
package App.Neil

import edu.illinois.cs.cogcomp.saul.util.Logging
import edu.illinois.cs.cogcomp.saulexamples.data.DocumentReader
import edu.illinois.cs.cogcomp.saulexamples.nlp.EmailSpam.SpamClassifiers._

import scala.collection.JavaConversions._

object SpamApp extends Logging {

  /*val trainData = new DocumentReader("src/main/Neil Barot/data/EmailSpam/train").docs.toList
  val testData = new DocumentReader("src/main/Neil Barot/data/EmailSpam/test").docs.toList
*/



  val trainDataCollab = new NeilWordReader("src/main/Neil Barot/data/Contracts/(Collab) series-seed---certificate-of-incorporation-v-3-2.doc","collaborative").docs.toList
  val trainDataArmsLength = new NeilWordReader("data/Neil/(Arms Length) NVCA-Voting-Agt-with-Bad-Actor-Provisions-Feb-2014.doc","arms-length").docs.toList
  val trainData = trainDataCollab ++ trainDataArmsLength
  //println(trainData)

  val testDataArmsLength = new NeilWordReader("data/Neil/(Arms Length) SPA-with-Bad-Actor-Provisions-Feb-2014.doc","arms-length").docs.toList
  val testDataCollab = new NeilWordReader("data/Neil/(Collab) series-seed---stock-investment-agreement-v-3-2.doc","collaborative").docs.toList
  val testData = testDataCollab ++ testDataArmsLength
  //println(testData)




  object SpamExperimentType extends Enumeration {
    val TrainAndTest, CacheGraph, TestUsingGraphCache, TestSerialization = Value
  }

  def main(args: Array[String]): Unit = {
    /** Choose the experiment you're interested in by changing the following line */
    val testType = SpamExperimentType.TrainAndTest

    testType match {
      case SpamExperimentType.TrainAndTest => TrainAndTestSpamClassifier()
      case SpamExperimentType.CacheGraph => SpamClassifierWithGraphCache()
      case SpamExperimentType.TestUsingGraphCache => SpamClassifierFromCache()
      case SpamExperimentType.TestSerialization => SpamClassifierWithSerialization()
    }

  }

  /** A standard method for testing the Spam Classification problem. Simply training and testing the resulting model.*/
  def TrainAndTestSpamClassifier(): Unit = {
    /** Defining the data and specifying it's location  */
    SpamDataModel.docs populate trainData
    SpamClassifierWeka.learn(30)
    SpamClassifierWeka.test(testData)
  }

  /** Spam Classifcation, followed by caching the data-model graph. */
  val graphCacheFile = "models/temp.model"
  def SpamClassifierWithGraphCache(): Unit = {
    /** Defining the data and specifying it's location  */
    SpamDataModel.docs populate trainData
    SpamDataModel.deriveInstances()
    SpamDataModel.write(graphCacheFile)
    SpamClassifierWithCache.learn(30)
    SpamClassifierWithCache.test(testData)
  }

  /** Testing the functionality of the cache. `SpamClassifierWithCache` produces the temporary model file need for
    * this methdd to run.
    */
  def SpamClassifierFromCache() {
    SpamDataModel.load(graphCacheFile)
    SpamClassifierWithCache.learn(30)
    SpamClassifierWithCache.test(testData)
  }

  /** Testing the serialization functionality of the model. We first train a model and save it. Then we load the model
    * and use it for prediction. We later check whether the predictions of the deserialized model are the same as the
    * predictions before serialization.
    */
  def SpamClassifierWithSerialization(): Unit = {
    SpamDataModel.docs populate trainData
    SpamClassifier.learn(30)
    SpamClassifier.save()
    DeserializedSpamClassifier.load(SpamClassifier.lcFilePath, SpamClassifier.lexFilePath)
    val predictionsBeforeSerialization = testData.map(SpamClassifier(_))
    val predictionsAfterSerialization = testData.map(DeserializedSpamClassifier(_))
    logger.info(predictionsBeforeSerialization.mkString("/"))
    logger.info(predictionsAfterSerialization.mkString("/"))
    logger.info(predictionsAfterSerialization.indices.forall(it => predictionsBeforeSerialization(it) == predictionsAfterSerialization(it)).toString)
  }
}
