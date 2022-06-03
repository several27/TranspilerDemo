package graph

import io.prophecy.libs._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import config.ConfigStore._
import udfs.UDFs._
import udfs._

object ByCustomerId {

  def apply(spark: SparkSession, right: DataFrame, left: DataFrame): DataFrame =
    right
      .as("right")
      .join(left.as("left"),
            col("left.customer_id") === col("right.customer_id"),
            "inner"
      )
      .select(
        col("left.first_name").as("first_name"),
        col("left.last_name").as("last_name"),
        col("left.customer_id").as("customer_id"),
        col("right.amount").as("amount")
      )

}
