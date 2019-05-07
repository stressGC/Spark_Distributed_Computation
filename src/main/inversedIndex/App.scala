/**
  * @author Georges Cosson
  */
package formatCrawl

import org.apache.spark.sql.{SparkSession}

object App
{
  val spark: SparkSession = SparkSession.builder.master("local").getOrCreate

  /**
    * entry of our program, does the RDD manipulation for the second part of the first exercice
    * @param args
    */
  def main(args:Array[String]){
    // lets read our file
    val RDDFromJSON = FSHelper.ReadJSONFromFile("monsters.json", spark)

    // lets swap indexes
    val swappedRDDFromJSON = Computing.SwapIndexesAndContent(RDDFromJSON)

    // lets reduce our RDD
    val finalRDD = Computing.reduce(swappedRDDFromJSON)

    // and write it to filesystem
    FSHelper.RDDToFileSystem(finalRDD, "swappedRDD")
  }
}