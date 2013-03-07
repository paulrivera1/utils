import java.io.PrintWriter
import scala.util.Random
import java.io.File


object DataGenerator {
  def main(args: Array[String]) {
    println("generating data...")
    genMeanByYearData("data/scoobi-testData-getMeanByYear")
    println("Done!")
  }
  
  def genSimpleMeanData(folderName: String) {
    var r = new Random
    
    for(i <- (1 to 10)) {
      var data = (1 to 1000).map(a => r.nextInt)
      var formattedData = data.mkString("\n")
    
      var file: File = new File(folderName + "/data" + i + ".txt")
      var out: PrintWriter = new PrintWriter(file);
    
      out.print(formattedData)
      out.close()
    }
  }
  
  def genMeanByYearData(folderName: String) {
    var r = new Random
    
    for(i <- (1 to 10)) {
      var data = (1 to 1000).map(a => ((1990+i) + "," + r.nextInt))
      var formattedData = data.mkString("\n")
    
      var file: File = new File(folderName + "/data" + i + ".txt")
      var out: PrintWriter = new PrintWriter(file);
    
      out.print(formattedData)
      out.close()
    }
  }
}