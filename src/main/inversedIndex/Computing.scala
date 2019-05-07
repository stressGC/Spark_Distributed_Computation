/**
  * @author Georges Cosson
  */
package formatCrawl

import org.apache.spark.rdd.RDD

object Computing
{

  /**
    * swaps the indexes and the content of an RDD
    * @param RDD
    * @return
    */
  def SwapIndexesAndContent(RDD: RDD[String]): RDD[(String, String)] = {
    return RDD.map(ConvertToTuple).map(SwapTuple)
  }

  /**
    * converts an entry into a Tuple
    * @param entry
    * @return
    */
  def ConvertToTuple(entry: String): (String, String) = {
    val splittedEntry = entry.split(",")
    val index = splittedEntry(0)
    val spell = splittedEntry(1)
    return (index, spell)
  }

  /**
    * swaps a tuple
    * @param entry
    * @return
    */
  def SwapTuple(entry: (String, String)): (String, String) = {
    return (entry._2, entry._1)
  }

  /**
    * reduces an RDD
    * @param RDD
    * @return
    */
  def reduce(RDD: RDD[(String, String)]) : RDD[(String, String)] = {
    return RDD.reduceByKey((acc, entry) => acc + ", " + entry)
  }
}
