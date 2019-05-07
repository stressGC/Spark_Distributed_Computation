/**
  * @author Georges Cosson
  */
package formatCrawl

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions.explode

object FSHelper
{
  // const
  val PROJECT_ROOT = System.getProperty("user.dir") + "\\"

  /**
    * reads a JSON file from the filesystem
    * @param path
    * @param sc
    * @return
    */
  def ReadJSONFromFile(path: String, sc: SparkSession): RDD[String] = {
    import sc.implicits._
    val file = PROJECT_ROOT + path

    /* https://code.dblock.org/2017/03/21/whats-the-simplest-way-to-parse-json-in-scala.html */
    val fileAsDF = sc.read.format("json").json(file)
    val fileAsRDD = fileAsDF.withColumn("spells", explode($"spells")).rdd.map(_.mkString(","))
    return fileAsRDD
  }

  /**
    * reads a CSV file from the filesystem
    * @param path
    * @param sc
    * @return
    */
  def ReadCSVFromFile(path: String, sc: SparkSession): RDD[(String, String)] = {
    val file : String = PROJECT_ROOT + "out.csv"
    val df : DataFrame = sc.read.format("csv").csv(file)

    // split on ','
    val rdd : RDD[String] = df.rdd.map(_.mkString(","))

    // some formatting
    val finalRdd : RDD[(String, String)] = rdd.map(line => (line.split(",")(0).toLowerCase(), line.split(",")(1)) )

    return finalRdd
  }

  /**
    * writes an RDD to the filesystem
    * @param rdd
    * @param path
    */
  def RDDToFileSystem(rdd : RDD[(String, String)], path: String): Unit = {
    println(">> WRITTING TO FILE")
    rdd.map(x => x._1 + "," + x._2).coalesce(1).saveAsTextFile(PROJECT_ROOT + "out/" + path)
  }
}
