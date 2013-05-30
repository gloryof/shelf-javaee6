package jp.glory.bookshelf.web.infrastructure.repository.constant;

/**
 * 本棚テーブルSQL定数
 * 
 * @author Junki Yamada
 * 
 */
public final class ShelfsSQLConst {

	/** 接頭語 */
	private static final String NAME_PREFIX = "Shelfs.";

	/** パラメータ名：本棚ID */
	public static final String PARAM_SHELF_ID = "shelfId";

	/** パラメータ名：削除フラグ */
	public static final String PARAM_DELETE_FLAG = "deleteFlag";

	/** クエリ名：PK検索 */
	public static final String NAME_FIND_BY_PK = NAME_PREFIX + "findByPk";

	/** クエリ：PK検索 */
	public static final String QUERY_FIND_BY_PK = "SELECT shelfs FROM ShelfsTableBean shelfs WHERE shelfs.shelfId = :"
			+ PARAM_SHELF_ID + " AND shelfs.deleteFlag = :" + PARAM_DELETE_FLAG;

	/** クエリ名：全件検索 */
	public static final String NAME_FIND_ALL = NAME_PREFIX + "findAll";

	/** クエリ：全件検索 */
	public static final String QUERY_FIND_ALL = "SELECT shelfs FROM ShelfsTableBean shelfs WHERE shelfs.deleteFlag = '0' ORDER BY shelfs.shelfId ASC";

	/** クエリ：本棚ID採番 */
	public static final String QUERY_CREATE_NEW_NUMBERING = "SELECT nextval('shelf_id_seq')";

	/**
	 * コンストラクタ
	 */
	private ShelfsSQLConst() {

	}

}
