package graph

import io.prophecy.libs._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import config.ConfigStore._
import udfs.UDFs._
import udfs._

object SumAmounts {

  def apply(spark: SparkSession, in: DataFrame): DataFrame =
    in.groupBy(col("customer_id"))
      .agg(first(col("first_name")).as("first_name"),
           first(col("last_name")).as("last_name"),
           sum(col("amount")).as("amount")
      )

}
