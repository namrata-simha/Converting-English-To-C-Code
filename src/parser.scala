package parser

import syntax._
import scala.collection.mutable.ArrayBuffer

class parser {
  var ifFlag = 0
  
  //Function to remove spaces from a given sentence and split it into an array
  //Input - Sentence string
  def removeSpaces(str:String):Array[String] = {
    var a = str.split(" ")
    a
  }

  //Function to make a string out of array of sentence words by adding spaces
  //Input - Array of sentence words
  def makeString(sentence:ArrayBuffer[String]):String = {
    var str:String = ""
    for(word<-sentence){
      str+=word
      str+= " "
    }
    str = str.substring(0, str.length-1)
    str
  }
  
  //Function to split the sentence at the given word and return two split parts of the original sentence
  //Input - Sentence to split and the word to split at
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
  
  //Function to preprocess the sentence
  //Input - Sentence string
  def preprocess(sentence:String):String = {
    var s = sentence.replaceAll(",", "")
    var s1 = s.replaceAll("\\.", " \\.")
    var s2 = s1.toLowerCase
    s2
  }
  
  //Function to find an else part to the if-condition and parse it
  //Input - Sentence substring and dummy else condition
  def findElse(s:String, expr:Eng):(String,Eng) = {
    var str = s.replaceAll("otherwise", "else")
    val numPattern = "else".r
    val match1 = numPattern.findFirstIn(str)
    if (match1 != None){
      var (str1,str2) = splitSentenceAt(str,"else")
      str2 = "else " + str2
      var tempEng = parseSentence(str2)
      (str1,tempEng)
    }
    else{
      (str,expr)
    }
  }
  
  //Function to remove first two words from a given sentence string
  //Input - Sentence string
  def removeFirstTwoWords(str:String):String = {
    var temp = removeSpaces(str)
    temp = temp.drop(2)
    var arr = ArrayBuffer[String]()
    for (t<-temp){
      arr+=t
    }
    makeString(arr)
  }
  
  //Function to parse the sentence and generate the syntax parse object
  //Input - Sentence string
  def parseSentence(e: String): Eng = e match {
		case x if (x.startsWith("if"))  => {
		  ifFlag = 1
		  var str = x.substring(3, x.length)
		  var (str1,str2) = splitSentenceAt(str,"then")
		  var (thenString,elseExpr) = findElse(str2,Expr(str2))
		  if(Else(Expr(str2)) == Else(elseExpr)){
		    If(parseSentence(str1),Then(parseSentence(thenString)))
		  }
		  else{
		    Apply(If(parseSentence(str1),Then(parseSentence(thenString))),elseExpr)
		  }
		}
		case x if (x.startsWith("else"))  => {
		  var str = x.substring(5, x.length)
		  Else(parseSentence(str))
		}
		case x if (x.startsWith("i have")||x.startsWith("we have")||x.startsWith("she has")||x.startsWith("he has")||x.startsWith("they have")||x.startsWith("it has")||x.startsWith("I had")||x.startsWith("they had")||x.startsWith("she had")||x.startsWith("he had")||x.startsWith("we had")) => {
		  if(ifFlag == 0){
  		  var str = removeFirstTwoWords(x)
  		  val numPattern = "and".r
        val match1 = numPattern.findFirstIn(str)
        if (match1 == None){
          var res = removeSpaces(str)
          var num = res(0).toInt
          var variable = res(1)
          var finalString = "\nint "+variable+"="+num+";"
          Expr(finalString)
        }
        else{
          var (str1,str2) = splitSentenceAt(str,"and")
          Other(parseSentence("i have "+str1),parseSentence(str2))
        }
		  }
		  else{
		    var str = removeFirstTwoWords(x)
		    var res = removeSpaces(str)
		    var comparator = "=="
		    if(res.length>2){
		      if (res(0) == "more"){
		        comparator = ">"
		      }
		      else{
		        comparator = "<"
		      }
		      res = res.drop(2)
		    }
        var num = res(0).toInt
        var variable = res(1)
        var finalString = variable+comparator+num
        Expr(finalString)
		  }
		}
		case x if (x.startsWith("say"))  => {
		  var str = x.substring(4, x.length)
		  Expr("\nprintf(\""+str+"\");")
		}
		case _ => Expr(e)
  }
}