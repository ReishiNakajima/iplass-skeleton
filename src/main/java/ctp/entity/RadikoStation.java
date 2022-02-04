package ctp.entity;

import org.iplass.mtp.entity.GenericEntity;

/**
 * ラジオ局 Entity。
 */
public class RadikoStation extends GenericEntity {

	private static final long serialVersionUID = 1L;

	/** Entity Definition Name */
	public static final String DEFINITION_NAME = "ctp.master.radikoStation";

	/** 略称 */
	public static final String CALL_SIGN = "callSign";
	/** 局名 */
	public static final String STATION_NAME = "stationName";

	public RadikoStation() {
		setDefinitionName(DEFINITION_NAME);
	}

	/**
	 * 略称を返します。
	 * 
	 * @return 略称
	 */
	public String getCallSign() {
		return getValue(CALL_SIGN);
	}

	/**
	 * 略称を設定します。
	 * 
	 * @param callSign 略称
	 */
	public void setCallSign(String callSign) {
		setValue(CALL_SIGN, callSign);
	}

	/**
	 * 局名を返します。
	 * 
	 * @return 局名
	 */
	public String getStationName() {
		return getValue(STATION_NAME);
	}

	/**
	 * 局名を設定します。
	 * 
	 * @param stationName 局名
	 */
	public void setStationName(String stationName) {
		setValue(STATION_NAME, stationName);
	}

}
