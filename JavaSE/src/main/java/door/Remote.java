package door;
/**
 * 遥控器
 * @author Eminem
 */
public class Remote {
	private DogDoor door;
	public Remote(DogDoor door){
		this.door = door;
	}
	public void pressButton(){
		System.out.println("Pressing the remote control button");
		if(!door.getState()){
			door.open();
		}else{
			door.close();
		}
	}
}
