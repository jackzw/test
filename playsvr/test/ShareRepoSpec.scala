import org.scalatest._
import org.scalatest.matchers._
import org.anormcypher._
import scala.collection.JavaConverters._

class ShareRepoSpec extends FlatSpec with ShouldMatchers with BeforeAndAfterEach {
  override def beforeEach() {
    Neo4jREST.setServer(scala.util.Properties.envOrElse("NEO4J_SERVER", "localhost"))
    Cypher("""
      CREATE (n {anormcyphername:'n'}), 
      (n2 {anormcyphername:'n2'}), 
      (n3 {anormcyphername:'n3'}), 
      n-[:test {name:'r'}]->n2, 
      n2-[:test {name:'r2'}]->n3;
      """)()
    Cypher("""
      CREATE (n5 {anormcyphername:'n5'}), 
        (n6 {anormcyphername:'n6'}), 
        n5-[:test {name:'r', i:1, arr:[1,2,3], arrc:["a","b","c"]}]->n6;
      """)()
    Cypher("""
      CREATE (n7 {anormcyphername:'nprops', i:1, arr:[1,2,3], arrc:['a','b','c']});
      """)()
  }

  override def afterEach() {
    Cypher("match (n) optional match (n)-[r]-() where has(n.anormcyphername) DELETE n,r;")()
  }

  "Neo4jREST" should "be able to retrieve properties of nodes" in {
    val results = Cypher("START n=node(*) where n.anormcyphername = 'nprops' RETURN n;")()
    results.size should equal (1)
    val node = results.map { row =>
      row[NeoNode]("n")
    }.head
    node.props("anormcyphername") should equal ("nprops")
    node.props("i") should equal (1)
    node.props("arr").asInstanceOf[Seq[Int]] should equal (Vector(1,2,3))
    node.props("arrc").asInstanceOf[Seq[String]] should equal (Vector("a","b","c"))
  }
}
