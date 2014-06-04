package scalawork

// MultipleConstructors
/**
 * The main/primary constructor is defined when you define your class.
 */
class Person(val firstName: String, val lastName: String, val age: Int) {
  
  /**
   * A secondary constructor.
   */
  def this(firstName: String) {
    this(firstName, "", 0);
    println("\nNo last name or age given.")
  }
  
  /**
   * Another secondary constructor.
   */
  def this(firstName: String, lastName: String) {
    this(firstName, lastName, 0);
    println("\nNo age given.")
  }
  
  override def toString: String = {
    return "%s %s, age %d".format(firstName, lastName, age)
  }

}

case class Result(Code: Int, Message: String, Result: Map[String, Any])


object ClassStuff {
	// MultipleConstructors
  // (1) use the primary constructor
    val al = new Person("Alvin", "Alexander", 20) //> al  : scalawork.Person = Alvin Alexander, age 20
    println(al)                                   //> Alvin Alexander, age 20

    // (2) use a secondary constructor
    val fred = new Person("Fred", "Flinstone")    //> 
                                                  //| No age given.
                                                  //| fred  : scalawork.Person = Fred Flinstone, age 0
    println(fred)                                 //> Fred Flinstone, age 0

    // (3) use a secondary constructor
    val barney = new Person("Barney")             //> 
                                                  //| No last name or age given.
                                                  //| barney  : scalawork.Person = Barney , age 0
    println(barney)                               //> Barney , age 0

	  val result = Result(200,"",null)        //> result  : scalawork.Result = Result(200,,null)
	  println(result)                         //> Result(200,,null)

}