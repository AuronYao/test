package threadlocal;

public class MyNumber {
	private static ThreadLocal<Integer> tl = new ThreadLocal<Integer>(){
		@Override
		public Integer initialValue(){
			return 0;
		}
	};
	
	public int getNextNum(){
		tl.set(tl.get()+1);
		return tl.get();
	}
	
	private static class TestClient extends Thread{
		private MyNumber number;
		public TestClient(MyNumber number){
			this.number = number;
		}
		public void run(){
			for(int i=0;i<3;i++){
				System.out.println(Thread.currentThread().getName()+"  SN: "+number.getNextNum());
			}
		}
	}
	
	public static void main(String[] args){
		MyNumber myNumber = new MyNumber();
		TestClient t1 = new TestClient(myNumber);
		TestClient t2 = new TestClient(myNumber);
		TestClient t3 = new TestClient(myNumber);
		t1.start();
		t2.start();
		t3.start();
	}
}
