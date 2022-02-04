package ctp.entity;

import java.sql.Time;
import org.iplass.mtp.entity.Entity;
import org.iplass.mtp.entity.GenericEntity;
import org.iplass.mtp.entity.SelectValue;

/**
 * radiko定期予約 Entity。
 */
public class RadikoSchedule extends GenericEntity {

	private static final long serialVersionUID = 1L;

	/** Entity Definition Name */
	public static final String DEFINITION_NAME = "ctp.master.radikoSchedule";

	/** ラジオ局 */
	public static final String STATION = "station";
	/** 番組名 */
	public static final String PROGRAM_NAME = "programName";
	/** 曜日 */
	public static final String WEEK_DAY = "weekDay";
	/** 開始時刻 */
	public static final String START_TIME = "startTime";
	/** メモ */
	public static final String NOTES = "notes";
	/** 評価レート */
	public static final String FAVO_RATE = "FavoRate";
	/** 番組リスト */
	public static final String CHILD_PROGRAM = "childProgram";

	public RadikoSchedule() {
		setDefinitionName(DEFINITION_NAME);
	}

	/**
	 * ラジオ局を返します。
	 * 
	 * @return ラジオ局
	 */
	public Entity getStation() {
		return getValue(STATION);
	}

	/**
	 * ラジオ局を設定します。
	 * 
	 * @param station ラジオ局
	 */
	public void setStation(Entity station) {
		setValue(STATION, station);
	}

	/**
	 * 番組名を返します。
	 * 
	 * @return 番組名
	 */
	public String getProgramName() {
		return getValue(PROGRAM_NAME);
	}

	/**
	 * 番組名を設定します。
	 * 
	 * @param programName 番組名
	 */
	public void setProgramName(String programName) {
		setValue(PROGRAM_NAME, programName);
	}

	/**
	 * 曜日を返します。
	 * 
	 * @return 曜日
	 */
	public SelectValue getWeekDay() {
		return getValue(WEEK_DAY);
	}

	/**
	 * 曜日を設定します。
	 * 
	 * @param weekDay 曜日
	 */
	public void setWeekDay(SelectValue weekDay) {
		setValue(WEEK_DAY, weekDay);
	}

	/**
	 * 開始時刻を返します。
	 * 
	 * @return 開始時刻
	 */
	public Time getStartTime() {
		return getValue(START_TIME);
	}

	/**
	 * 開始時刻を設定します。
	 * 
	 * @param startTime 開始時刻
	 */
	public void setStartTime(Time startTime) {
		setValue(START_TIME, startTime);
	}

	/**
	 * メモを返します。
	 * 
	 * @return メモ
	 */
	public String getNotes() {
		return getValue(NOTES);
	}

	/**
	 * メモを設定します。
	 * 
	 * @param notes メモ
	 */
	public void setNotes(String notes) {
		setValue(NOTES, notes);
	}

	/**
	 * 評価レートを返します。
	 * 
	 * @return 評価レート
	 */
	public Long getFavoRate() {
		return getValue(FAVO_RATE);
	}

	/**
	 * 評価レートを設定します。
	 * 
	 * @param FavoRate 評価レート
	 */
	public void setFavoRate(Long FavoRate) {
		setValue(FAVO_RATE, FavoRate);
	}

	/**
	 * 番組リストを返します。
	 * 
	 * @return 番組リスト
	 */
	public Entity[] getChildProgram() {
		Object value = getValue(CHILD_PROGRAM);
		if (value instanceof Entity) {
			return new Entity[]{(Entity)value};	//for search
		} else {
			return (Entity[])value;	//for load
		}
	}

	/**
	 * 番組リストを設定します。
	 * 
	 * @param childProgram 番組リスト
	 */
	public void setChildProgram(Entity[] childProgram) {
		setValue(CHILD_PROGRAM, childProgram);
	}

}
