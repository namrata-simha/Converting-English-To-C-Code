package parser

import syntax._
import scala.collection.mutable.ArrayBuffer

class parser {
  def removeSpaces(str:String):Array[String] = {
    var a = str.split(" ")
    a
  }

  def makeString(sentence:ArrayBuffer[String]):String = {
    var str:String = ""
    sentence.foreach(println)
    for(word<-sentence){
      str+=word
      str+= " "
    }
    println(str)
    str = str.substring(0, str.length-1)
    str
  }
  
  def splitSentenceAt(sentence:String, word:String):(String,String) = {
    var res = removeSpaces(sentence)
    var flag = 0
    var str1 = ArrayBuffer[String]()
		var str2 = ArrayBuffer[String]()
		  for (result<-res){
		    if(flag==0 && !(result == word)){
		      str1+=result
		    }else if(result == word){
		      flag=1
		    }else{
		      str2+=result
		    }
		  }
		  (makeString(str1),makeString(str2))
  }
  
  def findElse(s:String, expr:Eng):(String,Eng) = {
    var str = s.replaceAll("otherwise", "else")
    println("Str here: "+str)
    val numPattern = "else".r
    val match1 = numPattern.findFirstIn(str)
    if (match1 != None){
      println("here")
      var (str1,str2) = splitSentenceAt(str,"else")
      str2 = "else " + str2
      println("str2"+str2)
      var tempEng = parseSentence(str2)
      println(tempEng)
      (str1,tempEng)
    }
    else{
      (str,expr)
    }
  }
  
  def parseSentence(e: String): Eng = e match {
		case x if (x.startsWith("if"))  => {
		  var str = x.substring(3, x.length)
		  var (str1,str2) = splitSentenceAt(str,"then")
		  var (thenString,elseExpr) = findElse(str2,Expr(str2))
		  if(Else(Expr(str2)) == Else(elseExpr)){
		    If(parseSentence(str1),Then(Expr(thenString)))
		  }
		  else{
		    Apply(If(parseSentence(str1),Then(Expr(thenString))),elseExpr)
		  }
		}
		case x if (x.startsWith("else"))  => {
		  var str = x.substring(5, x.length)
		  Else(parseSentence(str))
		}
		case _ => Expr(e)
//		case x if x(0) == ('\\') => Lambda( Var(x(1)), parse(x.substring(x.indexOf('.')+1)))
//		case x if x(0) == ('(') && x.last == (')') => parse(x.substring(1,x.length-1))
//		case x if x.contains('(') => Apply( parse( x.substring(x.indexOf('('), x.lastIndexOf(')')+1 ) ), parse(x.substring(x.lastIndexOf(')')+1)))
//		case _ => Apply(parseSentence(e.substring(0, e.length-1)), Var(e.last))
  }
}