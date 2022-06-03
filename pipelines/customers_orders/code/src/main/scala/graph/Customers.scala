package graph

import io.prophecy.libs._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import config.ConfigStore._

object Customers {

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
                StructField("customer_id",       StringType, true),
                StructField("first_name",        StringType, true),
                StructField("last_name",         StringType, true),
                StructField("phone",             StringType, true),
                StructField("email",             StringType, true),
                StructField("country_code",      StringType, true),
                StructField("account_open_date", StringType, true),
                StructField("account_flags",     StringType, true)
              )
            )
          )
          .load(
            "dbfs:/Prophecy/maciej+transpiler-demo@prophecy.io/CustomersDatasetInput.csv"
          )
      case _ =>
        throw new Exception("No valid dataset present to read fabric")
    }
  }

}
