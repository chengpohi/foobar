package learn.mllib

import org.apache.spark.mllib.fpm.FPGrowth
import org.apache.spark.rdd.RDD

object FPGrowthUsage extends MLLibApp {
  val data = sc.textFile("data/mllib/sample_fpgrowth.txt".toMLLibPath)

  val transactions: RDD[Array[String]] = data.map(s => s.trim.split(' '))

  val fpg = new FPGrowth()
    .setMinSupport(0.2)
    .setNumPartitions(10)
  val model = fpg.run(transactions)

  model.freqItemsets.collect().foreach { itemset =>
    println(itemset.items.mkString("[", ",", "]") + ", " + itemset.freq)
  }

  val minConfidence = 0.8
  model.generateAssociationRules(minConfidence).collect().foreach { rule =>
    println(
      rule.antecedent.mkString("[", ",", "]")
        + " => " + rule.consequent.mkString("[", ",", "]")
        + ", " + rule.confidence)
  }
}
