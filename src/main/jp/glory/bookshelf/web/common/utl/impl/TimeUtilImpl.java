package jp.glory.bookshelf.web.common.utl.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import javax.inject.Singleton;
import jp.glory.bookshelf.web.common.annotation.DefaultTime;
import jp.glory.bookshelf.web.common.utl.TimeUtil;

/**
 * 時間ユーティリティ
 *
 * @author Junki Yamada
 *
 */
@Singleton
@DefaultTime
public class TimeUtilImpl implements TimeUtil {

	/**
	 * タイムスタンプを取得する
	 */
	@Override
	public Timestamp getSysTimestamp() {

		return new Timestamp(getCalendar().getTimeInMillis());
	}

	/**
	 * カレンダーを取得する
	 *
	 * @return カレンダー
	 */
	private Calendar getCalendar() {

		return Calendar.getInstance();
	}
}
