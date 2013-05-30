package jp.glory.bookshelf.web.application.common.constant;

/**
 * ページタイトル
 * 
 * @author Junki Yamada
 * 
 */
public enum PageTitle {

	TOP("トップメニュー"),

	BOOK_VIEW("本"),
	BOOK_EDIT_VIEW("本編集"),

	SHELF_VIEW("本棚"),
	SHELF_EDIT_VIEW("本棚編集"),

	ERROR("エラー");

	/** 値 */
	private final String value;

	/**
	 * コンストラクタ
	 * 
	 * @param value 値
	 */
	private PageTitle(final String value) {

		this.value = value;
	}

	/**
	 * @return value
	 */
	public String getValue() {
		return value;
	}
}
