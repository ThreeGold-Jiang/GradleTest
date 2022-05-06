package P3;

import java.util.ArrayList;

public class FriendshipGraph {
	private ArrayList<Person> persons = new ArrayList<Person>();
	
	public void addVertex(Person person) {
		if(person.getName()==null) {
			return;
		}
		for(Person k: persons) {
			if(k.equals(person)) {
				System.out.println("FriendSameNameWorng");
				return;
			}
		}
		persons.add(person);
	}
	public ArrayList<Person> getPersons() {
		return persons;
	}
	
	public void addEdge(Person person1, Person person2) {	
		if(person1.equals(person2)) {
			return;
		}
		person1.addfriends(person2);
		
	}

	public int getDistance(Person person1, Person person2) {
		int distance = -1;
		if (person1.findfriend(person2) || person2.findfriend(person2)) {
			return 1;
		}
		if (person1.equals(person2)) {
			return 0;
		}
		fresh();
		person1.setFlag(true);
		distance = BFS(person1, person2);
		return distance;
	}

	public void fresh() {
		for (Person k : persons) {
			k.setFlag(false);
		}
	}

	public int BFS(Person person1, Person person2) {
		int tempt = 0;
		int min = Integer.MAX_VALUE;
		ArrayList<Person> friends = person1.getFriends();
		if (!person1.getFlag()) {
			if (person1.findfriend(person2) || person2.findfriend(person2)) {
				return 1;
			}
		}
		person1.setFlag(true);
		for (Person k : friends) {
			if (!k.getFlag()) {
				tempt = BFS(k, person2);
			}
			if (tempt > 0 && tempt < min) {
				min = tempt;
			}
		}
		if (min<Integer.MAX_VALUE) {
			return min + 1;
		}
		return -1;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("DON'T PANIC");
		FriendshipGraph graph = new FriendshipGraph();
		Person kramer = new Person("Kramer");
		Person ross = new Person("Ross");
		Person rachel = new Person("Rachel");
		Person ben = new Person("Ben");
		graph.addVertex(rachel);
		graph.addVertex(ross);
		graph.addVertex(ben);
		graph.addVertex(kramer);
		graph.addEdge(rachel, ross);
		graph.addEdge(ross, rachel);
		graph.addEdge(ross, ben);
		graph.addEdge(ben, ross);
		graph.addEdge(rachel, ben);
		graph.addEdge(ben, rachel);
		System.out.println(graph.getDistance(rachel, ross));
		// should print 1
		System.out.println(graph.getDistance(rachel, ben));
		// should print 2
		System.out.println(graph.getDistance(rachel, rachel));
		// should print 0
		System.out.println(graph.getDistance(rachel, kramer));
		// should print -1
		System.out.println(ben.findfriend(kramer));
		System.out.println(ben.findfriend(kramer));
	}

}
