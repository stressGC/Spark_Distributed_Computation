package formatCrawl

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions.explode


object FSHelper
{
  val PROJECT_ROOT = System.getProperty("user.dir") + "\\"

  def ReadJSONFromFile(path: String, sc: SparkSession): RDD[String] = {
    import sc.implicits._
    val file = PROJECT_ROOT + path
    val fileAsDF = sc.read.format("json").json(file)
    val fileAsRDD = fileAsDF.withColumn("spells", explode($"spells")).rdd.map(_.mkString(","))
    return fileAsRDD
  }

  def ReadCSVFromFile(path: String, sc: SparkSession): RDD[(String, String)] = {
    val file : String = PROJECT_ROOT + "out.csv"
    val df : DataFrame = sc.read.format("csv").csv(file)
    val rdd : RDD[String] = df.rdd.map(_.mkString(","))
    val finalRdd : RDD[(String, String)] = rdd.map(line => (line.split(",")(0).toLowerCase(), line.split(",")(1)) )

    return finalRdd
  }

  def RDDToFileSystem(rdd : RDD[(String, String)], path: String): Unit = {
    println(">> WRITTING TO FILE")
    // export to gephi format
    rdd.map(x => x._1 + "," + x._2).coalesce(1).saveAsTextFile(PROJECT_ROOT + "out/" + path)
  }
}
