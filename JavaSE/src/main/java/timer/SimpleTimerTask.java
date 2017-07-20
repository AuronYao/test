package timer;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class SimpleTimerTask extends TimerTask {
	private int count = 0;
	@Override
	public void run() {
		System.out.println("execute task");
		Date time = new Date(scheduledExecutionTime());
		System.out.println("本次任务安排执行时间点"+time);
		if(++count>10){
			cancel();
		}
	}
	public static void main(String[] args) {
		new Timer().schedule(new SimpleTimerTask(), 1000,3000);
	}
}
