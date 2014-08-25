package alertForWw;

import java.io.Serializable;

public class Repeatable implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String[] REPEATABLESTRS = new String[]{"不重复","每周重复"};
	public static final String[] WEEKDAYSTRS = new String[]{"周日","周一","周二","周三","周四","周五","周六"};
	private boolean repeatable;
	private boolean[] repeatable_weekdays;
	
	public Repeatable() {
		repeatable_weekdays = new boolean[WEEKDAYSTRS.length];
	}
	public void setRepeatable(boolean repeatable){
		this.repeatable = repeatable;
	}
	public boolean isRepeatable(){
		return this.repeatable;
	}
	public void setRepeatable(int weekday, boolean repeatable){
		if(weekday < 0 || weekday > 6) return ;
		else this.repeatable_weekdays[weekday] = repeatable;
	}
	public boolean isRepeatable(int weekday){
		if(weekday < 0 || weekday > 6) return false;
		else return this.repeatable_weekdays[weekday];
	}
	public String toString(){
		StringBuilder sb = new StringBuilder(); 
		if(repeatable){
			boolean everyday = true;
			for(int i = 0 ; i < WEEKDAYSTRS.length ; i++){
				if(repeatable_weekdays[i]){
					sb.append(WEEKDAYSTRS[i]);
					sb.append(" ");
				}
				else
					everyday = false;
			}
			if(everyday)
				return "每天";
		}else{
			return REPEATABLESTRS[0];
		}
		return sb.toString();
	}
	public Repeatable clone(){
		Repeatable repeatable = new Repeatable();
		repeatable.setRepeatable(isRepeatable());
		for(int i = 0 ; i < WEEKDAYSTRS.length ; i++)
			repeatable.setRepeatable(i, isRepeatable(i));
		return repeatable;
	}
}
