package P3;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class FriednshipGraphTest {
	/**
	 * 
	 * Tests that assertions are enabled.
	 * 
	 * */
	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false;
	}
	/**
	 * Tests addVertx.
	 */
	@Test
	public void addVertxTest() {
		FriendshipGraph graph = new FriendshipGraph();
		ArrayList<Person> persons  = new ArrayList<Person>();
		Person ross = new Person("Ross");
		Person ben = new Person("Ben");
		Person kramer = new Person("Kramer");

		assertEquals(persons,graph.getPersons());
		
		graph.addVertex(kramer);
		persons.add(kramer);
		assertEquals(persons,graph.getPersons());
		
		graph.addVertex(kramer);
		assertEquals(persons,graph.getPersons());
		
		graph.addVertex(ben);
		persons.add(ben);
		assertEquals(persons,graph.getPersons());
		
		graph.addVertex(ross);
		persons.add(ross);
		assertEquals(persons,graph.getPersons());
		
		
	}
	/**
	 * Tests addEdge.
	 */
	@Test
	public void addEdgeTest() {
		FriendshipGraph graph = new FriendshipGraph();
		ArrayList<Person> persons  = new ArrayList<Person>();
		Person ross = new Person("Ross");
		Person ben = new Person("Ben");
		Person kramer = new Person("Kramer");
		Person rachel= new Person("Rachel");
		graph.addVertex(kramer);
		graph.addVertex(ben);
		graph.addVertex(ross);
		graph.addVertex(rachel);
		
		graph.addEdge(ben, ben);
		assertEquals(persons,ben.getFriends());
		
		graph.addEdge(ben, kramer);
		assertEquals(persons,kramer.getFriends());
		
		graph.addEdge(kramer, ben);
		persons.add(kramer);
		assertEquals(persons,ben.getFriends());
		
		graph.addEdge(kramer, ben);
		graph.addEdge(ben, kramer);
		assertEquals(persons,ben.getFriends());
		
		graph.addEdge(ben, ross);
		graph.addEdge(ross, ben);
		graph.addEdge(ben, rachel);
		graph.addEdge(rachel, ben);
		persons.add(ross);
		persons.add(rachel);
		assertEquals(persons,ben.getFriends());
	}
	/**
	 * Tests getDistance.
	 */
	@Test
	public void getDistanceTest() {
		FriendshipGraph graph = new FriendshipGraph();
		Person ross = new Person("Ross");
		Person ben = new Person("Ben");
		Person kramer = new Person("Kramer");
		Person rachel= new Person("Rachel");
		Person mike= new Person("Mike");
		Person jake= new Person("Jake");
		
	
		graph.addVertex(kramer);
		graph.addVertex(ben);
		graph.addVertex(ross);
		graph.addVertex(rachel);
		
		graph.addEdge(ben, kramer);
		graph.addEdge(kramer, ben);
		graph.addEdge(ben, ross);
		graph.addEdge(ross, ben);
		graph.addEdge(ben, rachel);
		graph.addEdge(rachel, ben);
		
		graph.addEdge(kramer, ross);
		graph.addEdge(ross, kramer);
		graph.addEdge(ross, rachel);
		graph.addEdge(rachel, ross);
		
		assertEquals(0,graph.getDistance(ben, ben));
		assertEquals(0,graph.getDistance(kramer, kramer));
		assertEquals(0,graph.getDistance(ross, ross));
		assertEquals(0,graph.getDistance(rachel, rachel));
		
		assertEquals(1,graph.getDistance(ben, rachel));
		assertEquals(1,graph.getDistance(rachel, ben));
		assertEquals(1,graph.getDistance(ben, ross));
		assertEquals(1,graph.getDistance(ross, ben));
		assertEquals(1,graph.getDistance(ben, kramer));
		assertEquals(1,graph.getDistance(kramer, ben));
		

		assertEquals(2,graph.getDistance(rachel, kramer));
		assertEquals(2,graph.getDistance(kramer, rachel));
		
		graph.addVertex(mike);
		graph.addEdge(rachel, mike);
		graph.addEdge(mike, rachel);
		assertEquals(3,graph.getDistance(kramer, mike));
		assertEquals(3,graph.getDistance(mike, kramer));
		

		graph.addEdge(ben, mike);
		graph.addEdge(mike, ben);
		assertEquals(2,graph.getDistance(kramer, mike));
		assertEquals(2,graph.getDistance(mike, kramer));
		
		graph.addVertex(jake);
		assertEquals(-1,graph.getDistance(jake, kramer));		
	}
	
}
