package jp.glory.bookshelf.web.application.common.view.bean;

/**
 * 本画面情報Bean
 * 
 * @author Junki Yamada
 * 
 */
public class BookInfoBean {

	/** 本ID */
	private long bookId = 0;

	/** 親となる本棚ID */
	private long parentShelfId = 0;

	/** ISBNコード */
	private String isbnCode = null;

	/** ISBNコード（コード値のみ） */
	private String isbnOnlyCode = null;

	/** タイトル */
	private String title = null;

	/** 価格 */
	private int price = 0;

	/** 選択済みフラグ */
	private boolean checked = false;

	/**
	 * @return bookId
	 */
	public long getBookId() {
		return bookId;
	}

	/**
	 * @param bookId セットする bookId
	 */
	public void setBookId(final long bookId) {
		this.bookId = bookId;
	}

	/**
	 * @return parentShelfId
	 */
	public long getParentShelfId() {
		return parentShelfId;
	}

	/**
	 * @param parentShelfId セットする parentShelfId
	 */
	public void setParentShelfId(final long parentShelfId) {
		this.parentShelfId = parentShelfId;
	}

	/**
	 * @return isbnCode
	 */
	public String getIsbnCode() {
		return isbnCode;
	}

	/**
	 * @param isbnCode セットする isbnCode
	 */
	public void setIsbnCode(final String isbnCode) {
		this.isbnCode = isbnCode;
	}

	/**
	 * @return isbnOnlyCode
	 */
	public String getIsbnOnlyCode() {
		return isbnOnlyCode;
	}

	/**
	 * @param isbnOnlyCode セットする isbnOnlyCode
	 */
	public void setIsbnOnlyCode(final String isbnOnlyCode) {
		this.isbnOnlyCode = isbnOnlyCode;
	}

	/**
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title セットする title
	 */
	public void setTitle(final String title) {
		this.title = title;
	}

	/**
	 * @return price
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * @param price セットする price
	 */
	public void setPrice(final int price) {
		this.price = price;
	}

	/**
	 * @return checked
	 */
	public boolean isChecked() {
		return checked;
	}

	/**
	 * @param checked セットする checked
	 */
	public void setChecked(final boolean checked) {
		this.checked = checked;
	}

}
