package graph

import io.prophecy.libs._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import config.ConfigStore._

object Orders {

  def apply(spark: SparkSession): DataFrame = {
    Config.fabricName match {
      case "dev" =>
        spark.read
          .format("csv")
          .option("header", true)
          .option("sep",    ",")
          .schema(
            StructType(
              Array(
                StructField("order_id",       StringType, true),
                StructField("customer_id",    StringType, true),
                StructField("order_status",   StringType, true),
                StructField("order_category", StringType, true),
                StructField("order_date",     StringType, true),
                StructField("amount",         StringType, true)
              )
            )
          )
          .load(
            "dbfs:/Prophecy/maciej+transpiler-demo@prophecy.io/OrdersDatasetInput.csv"
          )
      case _ =>
        throw new Exception("No valid dataset present to read fabric")
    }
  }

}
