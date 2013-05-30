package jp.glory.bookshelf.web.infrastructure.repository.constant;

/**
 * 本棚-本テーブルSQL定数
 * 
 * @author Junki Yamada
 * 
 */
public final class ShelfBooksSQLConst {

	/** パラメータ：本棚ID */
	public static final String PARAM_SHELF_ID = "shelfId";

	/** パラメータ：本ID */
	public static final String PARAM_BOOK_ID = "bookId";

	/** 接頭語 */
	private static final String NAME_PREFIX = "ShelfBooks.";

	/** クエリ名：本ID削除 */
	public static final String NAME_DELETE_BOOK_ID = NAME_PREFIX + "deleteBook";

	/** クエリ：本ID削除 */
	public static final String QUERY_DELETE_BOOK_ID = "DELETE FROM ShelfBooksTableBean shelfBooks WHERE shelfBooks.key.bookId = :"
			+ PARAM_BOOK_ID;

	/** クエリ名：本棚ID削除 */
	public static final String NAME_DELETE_SHELF_ID = NAME_PREFIX + "deleteBooksByShelfId";

	/** クエリ：本棚ID削除 */
	public static final String QUERY_DELETE_SHELF_ID = "DELETE FROM ShelfBooksTableBean shelfBooks WHERE shelfBooks.key.shelfId = :"
			+ PARAM_SHELF_ID;

	/** クエリ名：本ID検索 */
	public static final String NAME_FIND_BY_BOOK_ID = NAME_PREFIX + "findByBookId";

	/** クエリ：本ID検索 */
	public static final String QUERY_FIND_BY_BOOK_ID = "SELECT shelfBooks FROM ShelfBooksTableBean shelfBooks WHERE shelfBooks.key.bookId = :"
			+ PARAM_BOOK_ID;

	/**
	 * コンストラクタ
	 */
	private ShelfBooksSQLConst() {

	}

}
