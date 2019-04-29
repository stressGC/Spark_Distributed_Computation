package formatCrawl

import org.apache.spark.sql.{DataFrame, SparkSession}

object App
{
  val spark: SparkSession = SparkSession.builder.master("local").getOrCreate

  def main(args:Array[String]){
    println("Hello Scala")
    val RDDFromJSON = FSHelper.ReadJSONFromFile("monsters.json", spark)
    val swappedRDDFromJSON =Computing.SwapIndexesAndContent(RDDFromJSON)

    val RDDFromCSV = FSHelper.ReadCSVFromFile("out.csv", spark)

    val joinedRDD = Computing.join(RDDFromCSV, swappedRDDFromJSON)

    val finalRDD = Computing.reduce(joinedRDD)
    FSHelper.RDDToFileSystem(finalRDD, "swappedRDD")
  }
}