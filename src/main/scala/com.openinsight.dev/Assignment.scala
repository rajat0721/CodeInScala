package com.openinsight.dev

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._

import scala.collection.mutable
import scala.util.control.Breaks._

object Assignment {

  def getReveseString(str: String): Unit={
    println("Ques 1 ; Input = " + str)
    println((for(i <- str.length until 0 by -1) yield str(i-1)).mkString)
  }

  def validParenthesis(str:String): Unit ={
    println("Ques 2 ; Input = " + str)
    var stack=mutable.Stack[Char]()
    val map:Map[Char, Char] = Map(')'->'(',']'->'[','}'->'{')
    var flag = true
    breakable{ for(i <- 0 to str.length-1){
      if(str(i) == '(' || str(i) == '[' || str(i) == '{'){
        stack.push(str(i))
      }
      else{
        if(stack.top == map.apply(str(i)))
          stack.pop()
        else{
         flag = false
          break()
        }}}}
    println(flag)
  }

  def Revenue(spark:SparkSession): Unit ={
    var prodRevenueDF = spark.read.option("header","true")
      .option("inferschema","true").csv("E:/rajat/productRevenue.csv")
    //prodRevenueDF.show(false)

    val win = Window.partitionBy("category").orderBy(desc("revenue"))
    println("Ques part 3a")
    prodRevenueDF.withColumn("popularityRank",dense_rank().over(win))
      .where(col("popularityRank").rlike("1|2")).show(false)

    println("Ques part 3b")
    prodRevenueDF.withColumn("revenueDiff",
      max("revenue").over(win)-col("revenue")).show(false)
  }

  def websiteData(spark:SparkSession): Unit ={
    var websiteDataDF = spark.read.option("header","true")
      .option("inferschema","true").csv("E:/rajat/websiteData.csv")
    //websiteDataDF.show(false)
    println("Ques 4")
    val a=websiteDataDF.groupBy("website").pivot("traffic type").agg(count(col("traffic type")))

    a.show()
  }

}
