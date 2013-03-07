package mypackage.myapp

import com.nicta.scoobi.Scoobi._

object WordCount extends ScoobiApp {
  def run() {
    println("opening " + args(0))

    val lines = fromTextFile(args(0))

    val counts = lines.flatMap(_.split(" "))
                      .map(word => (word,1))
                      .groupByKey
                      .combine((a: Int, b: Int) => a + b)
    persist(toTextFile(counts, args(1)))
  }
}
