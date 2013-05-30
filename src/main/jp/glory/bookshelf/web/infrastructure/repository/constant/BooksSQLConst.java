package jp.glory.bookshelf.web.infrastructure.repository.constant;

/**
 * 本テーブルSQL定数
 * 
 * @author Junki Yamada
 * 
 */
public final class BooksSQLConst {

	/** パラメータ名：本棚ID */
	public static final String PARAM_SHELF_ID = "shelfId";

	/** パラメータ名：本ID */
	public static final String PARAM_BOOK_ID = "bookId";

	/** パラメータ名：削除フラグ */
	public static final String PARAM_DELETE_FLAG = "deleteFlag";

	/** パラメータ名：ISBNコード */
	public static final String PARAM_ISBN_CODE = "isbnCode";

	/** 接頭語 */
	private static final String NAME_PREFIX = "Books.";

	/** クエリ名：PK検索 */
	public static final String NAME_FIND_BY_PK = NAME_PREFIX + "findByPk";

	/** クエリ：PK検索 */
	public static final String QUERY_FIND_BY_PK = "SELECT books FROM BooksTableBean books WHERE books.bookId = :"
			+ PARAM_BOOK_ID + " AND books.deleteFlag = :" + PARAM_DELETE_FLAG;

	/** クエリ名：本棚ID検索 */
	public static final String NAME_FIND_BY_SHELF_ID = NAME_PREFIX + "findByShelfId";

	/** クエリ：本棚ID検索 */
	public static final String QUERY_FIND_BY_SHELF_ID = "SELECT books FROM BooksTableBean books WHERE books.bookId IN ("
			+ "SELECT shelfBooks.key.bookId FROM ShelfBooksTableBean shelfBooks WHERE shelfBooks.key.shelfId = :"
			+ PARAM_SHELF_ID + ")" + " AND books.deleteFlag = :" + PARAM_DELETE_FLAG + " ORDER BY books.bookId ASC";

	/** クエリ名：ISBNコード検索 */
	public static final String NAME_FIND_BY_ISBN_CODE = NAME_PREFIX + "findByIsbnCode";

	/** クエリ：ISBNコード検索 */
	public static final String QUERY_FIND_BY_ISBN_CODE = "SELECT books FROM BooksTableBean books WHERE books.isbnCode = :"
			+ PARAM_ISBN_CODE + " AND books.deleteFlag = :" + PARAM_DELETE_FLAG + " ORDER BY books.bookId ASC";

	/** クエリ：本ID採番号 */
	public static final String QUERY_CREATE_NEW_NUMBERING = "SELECT nextval('book_id_seq')";

	/**
	 * コンストラクタ
	 */
	private BooksSQLConst() {

	}

}
