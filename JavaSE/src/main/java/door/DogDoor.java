package door;

public class DogDoor {
	private boolean isOpen;
	public DogDoor(){
		
	}
	
	public void open(){
		System.out.println("door opens");
		isOpen = true;
	}
	
	public void close(){
		System.out.println("door closes");
		isOpen = false;
	}
	public boolean getState(){
		return isOpen;
	}
}
