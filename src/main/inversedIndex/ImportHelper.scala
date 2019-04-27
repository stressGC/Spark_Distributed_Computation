package formatCrawl

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.explode


object ImportHelper
{
  val PROJECT_ROOT = System.getProperty("user.dir") + "\\"

  def ReadFromFile(path: String, sc: SparkSession): RDD[String] = {
    import sc.implicits._
    val file = PROJECT_ROOT + path
    val fileAsDF = sc.read.format("json").json(file)
    val fileAsRDD = fileAsDF.withColumn("spells", explode($"spells")).rdd.map(_.mkString(","))
    return fileAsRDD
  }
}
