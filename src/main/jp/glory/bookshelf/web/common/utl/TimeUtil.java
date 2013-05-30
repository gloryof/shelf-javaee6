package jp.glory.bookshelf.web.common.utl;

import java.sql.Timestamp;

/**
 * 時間ユーティリティ
 *
 * @author Junki Yamada
 *
 */
public interface TimeUtil {

	/**
	 * タイムスタンプを取得する
	 *
	 * @return タイムスタンプ
	 */
	Timestamp getSysTimestamp();
}
