package jp.glory.bookshelf.web.application.common.constant;

/**
 * コンテンツ名
 * 
 * @author Junki Yamada
 * 
 */
public enum ContentName {

	TOP("top.vm"),

	BOOK_VIEW("bookView.vm"),
	BOOK_EDIT_VIEW("bookEditView.vm"),
	BOOK_CREATE_VIEW("bookCreateView.vm"),

	SHELF_VIEW("shelfView.vm"),
	SHELF_EDIT_VIEW("shelfEditView.vm"),

	ERROR("error.vm");

	/** 値 */
	private final String value;

	/**
	 * コンストラクタ
	 * 
	 * @param value 値
	 */
	private ContentName(final String value) {

		this.value = value;

	}

	/**
	 * @return value
	 */
	public String getValue() {
		return value;
	}
}
