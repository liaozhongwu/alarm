package alertForWw;

import java.io.Serializable;
import java.util.HashSet;

public class SleepAlarm implements Serializable, Comparable<SleepAlarm>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int ID;
	private int hour;
	private int minute;
	private boolean shake;
	private String remark;
	private boolean awake;
	
	public SleepAlarm(){}
	public SleepAlarm(int hour, int minute, boolean shake, String remark, boolean awake){
		this.setHour(hour);
		this.setMinute(minute);
		this.setShake(shake);
		this.setRemark(remark);
		this.setAwake(awake);
	}
	public void setID(int ID){
		this.ID = ID;
	}
	public int getID() {
		return ID;
	}
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	public int getMinute() {
		return minute;
	}
	public void setMinute(int minute) {
		this.minute = minute;
	}
	public boolean isShake() {
		return shake;
	}
	public void setShake(boolean shake) {
		this.shake = shake;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public static HashSet<SleepAlarm> defaultSleepAlarms(){
		HashSet<SleepAlarm> alarms = new HashSet<SleepAlarm>();
		alarms.add(new SleepAlarm(6, 0, true, "", false));
		alarms.add(new SleepAlarm(8, 0, true, "", false));
		alarms.add(new SleepAlarm(10, 0, true, "", false));
		return alarms;
	}
	public boolean isAwake() {
		return awake;
	}
	public void setAwake(boolean awake) {
		this.awake = awake;
	}
	@Override
	public boolean equals(Object o) {
		if(o instanceof SleepAlarm)
			return getID() == ((SleepAlarm)o).getID();
		return super.equals(o);
	}
	@Override
	public int compareTo(SleepAlarm another) {
		int diff_awake = (another.isAwake() ? 1 : 0) - (isAwake() ? 1 : 0);
		if(diff_awake == 0){
			int diff_hour = another.getHour() - getHour();
			if(diff_hour == 0){
				int diff_minute = another.getMinute() - getMinute();
				if(diff_minute == 0){
					return another.getID() - getID();
				}
				else return diff_minute;
			}
			else return diff_hour;
		}
		else return diff_awake;
	}
}
