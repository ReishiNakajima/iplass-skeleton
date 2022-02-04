package ctp.dao;

import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.iplass.mtp.entity.query.Query;
import org.iplass.mtp.entity.query.condition.predicate.Between;

import ctp.entity.RadikoProgram;
import ctp.entity.RadikoSchedule;
import ctp.entity.RadikoStation;


public class RadikoProgramDao extends Dao<RadikoProgram> {

	protected RadikoProgramDao(String defName) {
		super(RadikoProgram.DEFINITION_NAME);
	}

	/**
	 * 指定期間内に予約したラジオ番組を検索する
	 * @param fromDate 開始日
	 * @param toDate　終了日
	 * @return　予約したラジオ番組リスト
	 */
	public List<RadikoProgram> findBetweenStartDate(Timestamp fromDate, Timestamp toDate) {
		Query query = new Query();
		query.select(
				StringUtils.joinWith(DOT, RadikoProgram.PARENT_SCHEDULE, RadikoSchedule.OID),
				StringUtils.joinWith(DOT, RadikoProgram.RADIKO_STATION, RadikoStation.CALL_SIGN),
				StringUtils.joinWith(DOT, RadikoProgram.RADIKO_STATION, RadikoStation.STATION_NAME),
				RadikoProgram.PROGRAM_NAME,
				RadikoProgram.START_DATETIME, 
				RadikoProgram.NOTE,
				RadikoProgram.RADIKO_URL, 
				RadikoProgram.DEADLINE, 
				RadikoProgram.LISTEN_STATUS)
			.where(new Between(RadikoProgram.START_DATETIME, fromDate, toDate));
		return search(query);
	}
	
	
}
