package formatCrawl

import org.apache.spark.sql.{DataFrame, SparkSession}

object App
{
  val spark: SparkSession = SparkSession.builder.master("local").getOrCreate

  def main(args:Array[String]){
    println("Hello Scala")
    val rdd = ImportHelper.ReadFromFile("monsters.json", spark)
    println(">>IMPORT FINISHED")

    println("before :", rdd.first())
    val swappedRDD =Computing.SwapIndexesAndContent(rdd)
    println("after :", swappedRDD.first())
  }
}