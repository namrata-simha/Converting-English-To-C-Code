import core._

object main{
  def main(args: Array[String]): Unit = {
    
    //Input the Natural Language Sentence
    println("Enter your sentence:")
	  var sentence: String = scala.io.StdIn.readLine()
	  println("-----------------------------------------------------")
	  
    //finalString to write to C file
	  var finalString: String = ""
	  val c = new core
	
	  //add C headers and main method to finalString
	  finalString = finalString + "#include<stdio.h>\n#include<string.h>\nvoid main(){\nprintf(\"Output from C program:\\n\");"
	
	  //Parse the sentence and add code to finalString
	  finalString = finalString + c.parse(sentence)
	  
	  //ending the C program
	  finalString = finalString + "\n}"
	
	  //write finalString to C file
	  c.writeToFile(finalString)  /*WRITE DHAWAL'S NAME IN REPORT FOR THIS ONE*/
	  
	  //run generated C code to see final output
	  c.run("test.c", 'C')
  }
}