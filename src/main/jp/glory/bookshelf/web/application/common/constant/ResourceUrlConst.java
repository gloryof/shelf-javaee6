package jp.glory.bookshelf.web.application.common.constant;

/**
 * リソースURL定数
 * 
 * @author Junki Yamada
 * 
 */
public interface ResourceUrlConst {

	/** コンテキストパス */
	String CONTEXT_ROOT = "bookshelf-web";

	/** ルート */
	String ROOT = "/";

	/** 本棚 */
	String SHELF = ROOT + "shelf/";

	/** 本棚編集 */
	String SHELF_EDIT = SHELF + "editView/";

	/** 本棚作成 */
	String SHELF_CREATE = SHELF + "create/";

	/** 本棚削除 */
	String SHELF_DELETE = SHELF + "delete/";

	/** 本ルート */
	String BOOK = ROOT + "book/";

	/** 本表示（本ID） */
	String BOOK_ID_VIEW = BOOK + "id/";

	/** 本表示（ISBN） */
	String BOOK_ISBN_VIEW = BOOK + "isbn/";

	/** 本編集 */
	String BOOK_EDIT = BOOK + "editView/";

	/** 本作成 */
	String BOOK_CREATE = BOOK + "create/";

	/** 本移動 */
	String BOOK_MOVE = BOOK + "move/";

	/** 本削除 */
	String BOOK_DELETE = BOOK + "delete/";

}
