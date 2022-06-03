import io.prophecy.libs._
import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import config.ConfigStore._
import udfs.UDFs._
import udfs._
import graph._

object Main {

  def apply(spark: SparkSession): Unit = {
    val df_Orders           = Orders(spark)
    val df_SortOrders       = SortOrders(spark,       df_Orders)
    val df_Customers        = Customers(spark)
    val df_SortCustomers    = SortCustomers(spark,    df_Customers)
    val df_ByCustomerId     = ByCustomerId(spark,     df_SortOrders, df_SortCustomers)
    val df_SumAmounts       = SumAmounts(spark,       df_ByCustomerId)
    val df_Cleanup_Reformat = Cleanup_Reformat(spark, df_SumAmounts)
    val df_ByAmounts        = ByAmounts(spark,        df_Cleanup_Reformat)
    Report(spark, df_ByAmounts)
  }

  def main(args: Array[String]): Unit = {
    import config._
    ConfigStore.Config = ConfigurationFactoryImpl.fromCLI(args)
    val spark: SparkSession = SparkSession
      .builder()
      .appName("Prophecy Pipeline")
      .config("spark.default.parallelism",             "4")
      .config("spark.sql.legacy.allowUntypedScalaUDF", "true")
      .enableHiveSupport()
      .getOrCreate()
    apply(spark)
  }

}
