package P3;

import java.util.ArrayList;

public class Person {
	private ArrayList<Person> friends = new ArrayList<Person>();
	private String Name;
	private boolean flag = false;

	public ArrayList<Person> getFriends() {
		return friends;
	}

	public String getName() {
		return this.Name;
	}

	public void addfriends(Person friend) {
		if(!findfriend(friend)) {
			friends.add(friend);
		}
	}

	public boolean findfriend(Person someone) {
		for (Person friend : this.friends) {
			if (friend.getName().equals(someone.getName())) {
				return true;
			}
		}
		return false;
	}

	public Person(String name) {
		super();
		Name = name;
	}

	public boolean getFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
}
