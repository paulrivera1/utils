import com.nicta.scoobi.Scoobi._

object MeanCalculator extends ScoobiApp {
  def run() {
    val lines: DList[Int] = fromTextFile("scoobi-testData-getSimpleMean/*") collect { case AnInt(i) => i}

    val total = lines.sum
    val count = lines.size
    val aTotal = persist(total)
    val aCount = persist(count)
    val mean = aTotal / aCount
    
    println("mean is " + mean)
  }
}


object TotalByYearCalculator extends ScoobiApp {
  def run() {
    val lines: DList[String] = fromTextFile("data/scoobi-testData-getMeanByYear/*")
    
    val results: DList[(String, Int)] = lines.map(line => {
      val fields = line.split(",")
      (fields(0), fields(1).toInt)
    })
      .groupByKey
      .combine((a: Int, b: Int) => a + b)
    
    persist(toTextFile(results, "results-by-year"))
    println("Results-by-year computation done!")
  }
}

object MeanByYearCalculator extends ScoobiApp {
  def run() {
    val lines: DList[String] = fromTextFile("data/scoobi-testData-getMeanByYear/*")
    
    //count total per year
    val totalsPerYear: DList[(String, Int)] = lines.map(line => {
      val fields = line.split(",")
      (fields(0), fields(1).toInt)
    })
      .groupByKey
      .combine((a: Int, b: Int) => a + b)
    
    //count lines per year
    val countPerYear: DList[(String, Int)] = lines.map(line => {
      val fields = line.split(",")
      (fields(0), 1)
    }).groupByKey
      .combine(_+_)
      
    //compute average per year
    //(totalsPerYear join countPerYear).map( 
    //  (year: String, (total: Int, count: Int)) => (year1)
    //)
      
    val totalsAndCountsList = totalsPerYear join countPerYear
    //val averages = totalsAndCountsList.map(x => {(x._1, x._2._1 / x._2._2)});
    val averages = totalsAndCountsList.map({    //why do I need curly braces?
        //(k: String, (a: Int, b: Int)) => (k, a/b)    -> this doesn't work, have to use case below
        case (k, (a,b)) => (k, a/b)
    });
    
    //sum the yearly averages
    val averagesSum = averages.map({
      case (k: String, v: Int) => ("a", v)
    }).groupByKey
      .combine({(a:Int,b:Int) => a+b}) //why _+_ doesnt work here?
    
    //get the sum computed above
    val localAveragesSum = persist(averagesSum.materialise)
    val yearCount = averages.size
    println("avg: " + (localAveragesSum.head))
    
    //persist(toTextFile(totalsPerYear, "results/totals-by-year"))
    //persist(toTextFile(countPerYear, "results/counts-by-year"))
    persist(toTextFile(averages, "results/averages-by-year"))
    persist(toTextFile(averagesSum, "results/averagesSum"))
    println("Results-by-year computation done!")
  }
}