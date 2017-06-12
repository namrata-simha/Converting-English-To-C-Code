package core

import java.io._
import sys.process._
import parser._

class core {
  //Function to run a file and return it's output 
  //Input - Full filename and Compiler/Interpreter for the code - P/C
  def run(filename: String, compiler: Char): String = {
    var comp = ""    
    if (compiler == 'P'){
      comp = "python "
      var run = comp+filename
      var result = run.!!
      result
    }
    else{
      comp = "C:/TDM-GCC-64/bin/gcc "
      var run = comp+filename
      var temp = run.!
      var result = "a".!
      ""
    }
  }
  
  //Function to parse the input sentence and return a parsed sentence object
  //Input - Input sentence string
  def parse(sentence: String): String = {
    val p = new parser
    var s = sentence.replaceAll(",", "")
    var res = p.parseSentence(s)
    println(res)
    //returning generic print with sentence for now
    "\nprintf(\""+ sentence +"\");"
  }
  
  //Function to write code string to file
  //Input - String to be written to the C file
  def writeToFile(finalStr: String): Unit = {
    println("Generated C file:\n")
    println(finalStr)
    println("-----------------------------------------------------")
    val pw = new PrintWriter(new File("test.c"))
    pw.write(finalStr)
    pw.close
  }
}