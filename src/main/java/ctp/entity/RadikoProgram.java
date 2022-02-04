package ctp.entity;

import java.sql.Timestamp;
import org.iplass.mtp.entity.Entity;
import org.iplass.mtp.entity.GenericEntity;
import org.iplass.mtp.entity.SelectValue;

/**
 * radiko番組 Entity。
 */
public class RadikoProgram extends GenericEntity {

	private static final long serialVersionUID = 1L;

	/** Entity Definition Name */
	public static final String DEFINITION_NAME = "ctp.transaction.radikoProgram";

	/** スケジュール設定 */
	public static final String PARENT_SCHEDULE = "parentSchedule";
	/** ラジオ局 */
	public static final String RADIKO_STATION = "radikoStation";
	/** 番組名 */
	public static final String PROGRAM_NAME = "programName";
	/** 開始日時 */
	public static final String START_DATETIME = "startDatetime";
	/** メモ */
	public static final String NOTE = "note";
	/** 視聴用URL */
	public static final String RADIKO_URL = "radikoUrl";
	/** 視聴期限 */
	public static final String DEADLINE = "deadline";
	/** 視聴ステータス */
	public static final String LISTEN_STATUS = "listenStatus";

	public RadikoProgram() {
		setDefinitionName(DEFINITION_NAME);
	}

	/**
	 * スケジュール設定を返します。
	 * 
	 * @return スケジュール設定
	 */
	public Entity getParentSchedule() {
		return getValue(PARENT_SCHEDULE);
	}

	/**
	 * スケジュール設定を設定します。
	 * 
	 * @param parentSchedule スケジュール設定
	 */
	public void setParentSchedule(Entity parentSchedule) {
		setValue(PARENT_SCHEDULE, parentSchedule);
	}

	/**
	 * ラジオ局を返します。
	 * 
	 * @return ラジオ局
	 */
	public Entity getRadikoStation() {
		return getValue(RADIKO_STATION);
	}

	/**
	 * ラジオ局を設定します。
	 * 
	 * @param radikoStation ラジオ局
	 */
	public void setRadikoStation(Entity radikoStation) {
		setValue(RADIKO_STATION, radikoStation);
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
	 * 開始日時を返します。
	 * 
	 * @return 開始日時
	 */
	public Timestamp getStartDatetime() {
		return getValue(START_DATETIME);
	}

	/**
	 * 開始日時を設定します。
	 * 
	 * @param startDatetime 開始日時
	 */
	public void setStartDatetime(Timestamp startDatetime) {
		setValue(START_DATETIME, startDatetime);
	}

	/**
	 * メモを返します。
	 * 
	 * @return メモ
	 */
	public String getNote() {
		return getValue(NOTE);
	}

	/**
	 * メモを設定します。
	 * 
	 * @param note メモ
	 */
	public void setNote(String note) {
		setValue(NOTE, note);
	}

	/**
	 * 視聴用URLを返します。
	 * 
	 * @return 視聴用URL
	 */
	public String getRadikoUrl() {
		return getValue(RADIKO_URL);
	}

	/**
	 * 視聴用URLを設定します。
	 * 
	 * @param radikoUrl 視聴用URL
	 */
	public void setRadikoUrl(String radikoUrl) {
		setValue(RADIKO_URL, radikoUrl);
	}

	/**
	 * 視聴期限を返します。
	 * 
	 * @return 視聴期限
	 */
	public Timestamp getDeadline() {
		return getValue(DEADLINE);
	}

	/**
	 * 視聴期限を設定します。
	 * 
	 * @param deadline 視聴期限
	 */
	public void setDeadline(Timestamp deadline) {
		setValue(DEADLINE, deadline);
	}

	/**
	 * 視聴ステータスを返します。
	 * 
	 * @return 視聴ステータス
	 */
	public SelectValue getListenStatus() {
		return getValue(LISTEN_STATUS);
	}

	/**
	 * 視聴ステータスを設定します。
	 * 
	 * @param listenStatus 視聴ステータス
	 */
	public void setListenStatus(SelectValue listenStatus) {
		setValue(LISTEN_STATUS, listenStatus);
	}

}
