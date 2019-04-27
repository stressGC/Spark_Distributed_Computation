package formatCrawl

import org.apache.spark.rdd.RDD


object Computing
{

  def SwapIndexesAndContent(RDD: RDD[String]): RDD[(String, String)] = {
    return RDD.map(ConvertToTuple).map(SwapTuple)
  }

  def ConvertToTuple(entry: String): (String, String) = {
    val splittedEntry = entry.split(",")
    val index = splittedEntry(0)
    val spell = splittedEntry(1)
    return (index, spell)
  }

  def SwapTuple(entry: (String, String)): (String, String) = {
    return (entry._2, entry._1)
  }
}
