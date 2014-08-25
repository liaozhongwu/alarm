package alertForWw;

import java.io.Serializable;


public class TimingAlarm implements Serializable, Comparable<TimingAlarm>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int ID;
	private int hour;
	private int minute;
	private Repeatable repeatable;
	private boolean shake;
	private String remark;
	private boolean awake;
	
	public TimingAlarm(){}
	public TimingAlarm(int hour, int minute, Repeatable repeatable,boolean shake,String remark, boolean awake){
		this.setHour(hour);
		this.setMinute(minute);
		this.setRepeatable(repeatable);
		this.setShake(shake);
		this.setRemark(remark);
		this.setAwake(awake);
	}

	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
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
	public Repeatable getRepeatable() {
		return repeatable;
	}
	public void setRepeatable(Repeatable repeatable) {
		this.repeatable = repeatable;
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
	public boolean isAwake() {
		return awake;
	}
	public void setAwake(boolean awake) {
		this.awake = awake;
	}
	@Override
	public boolean equals(Object o) {
		if(o instanceof TimingAlarm)
			return getID() == ((TimingAlarm)o).getID();
		return super.equals(o);
	}
	@Override
	public int compareTo(TimingAlarm another) {
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
