package door;

public class DoorTest {
	public static void main(String[] args) {
		DogDoor door = new DogDoor();
		Remote remote = new Remote(door);
		remote.pressButton();
		remote.pressButton();
		remote.pressButton();
		
	}
}
