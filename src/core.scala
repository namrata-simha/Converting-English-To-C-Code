package core

import java.io._
import sys.process._
import parser._
import syntax._

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
  
  //Function to create string to append to C file from the obtained Eng syntax parse of the input sentence(s)
  //Input - Eng syntax parse object
  def makeFinalString(res:Eng):String = res match{
    case Apply(ifExpr,thenExpr) => {
      makeFinalString(ifExpr)+makeFinalString(thenExpr)
    }
    case Other(e1,e2) => {
      makeFinalString(e1)+makeFinalString(e2)
    }
    case If(e,t) => {
      "\nif("+makeFinalString(e)+")"+makeFinalString(t)
    }
    case Else(e) => {
      "\nelse{"+makeFinalString(e)+"\n}"
    }
    case Then(e) => {
      "{"+makeFinalString(e)+"\n}"
    }
    case Expr(e) => {
      e
    }
  }
  
  //Function to parse the input sentence and return a parsed sentence object
  //Input - Input sentence string
  def parse(sentence: String): String = {
    val p = new parser
    var s = p.preprocess(sentence)
    var finalAppend = ""
    val numPattern = "\\.".r
    val match1 = numPattern.findFirstIn(s)
    if (match1 != None){
      var (str1,str2) = p.splitSentenceAt(s,""".""")
      var res1 = p.parseSentence(str1)
      var res2 = p.parseSentence(str2)
      finalAppend += makeFinalString(res1)
      finalAppend += makeFinalString(res2)
    }else{
      var res = p.parseSentence(s)
      finalAppend += makeFinalString(res)
    }
    finalAppend
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