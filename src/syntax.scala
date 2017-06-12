package syntax

//Syntax of the (Natural Language) English sentences.
abstract class Eng
case class Expr(x:String) extends Eng {
  override def toString = x.toString
}
case class Else(e1:Eng) extends Eng {
  override def toString() = " ELSE(" + e1.toString + ")" 
}
case class Then(e1:Eng) extends Eng {
  override def toString() = " THEN{" + e1.toString + "}" 
}
case class If(e1:Eng, e2:Then) extends Eng {
  override def toString() = " IF(" + e1.toString + ")" + e2.toString 
}
case class Apply(e1:If, e2:Eng) extends Eng {
  override def toString() = "[" + e1.toString + e2.toString + "]" 
}