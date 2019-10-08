package com.openinsight.dev
import utility.SparkUtility

object Main {

  var mystr = "rajat"

  def main(args: Array[String]): Unit = {

    Assignment.getReveseString(mystr)
    Assignment.validParenthesis("()")

    /**Test Cases for Ques 2
      * case1 = ({})
      * case2 = ([)]
      * case3 = (])[
      * case4 = ()
      */

    val spark = SparkUtility.init()
    Assignment.Revenue(spark)
    Assignment.websiteData(spark)

    spark.stop()
  }

}
