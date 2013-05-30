package jp.glory.bookshelf.web.infrastructure.repository.bean.key;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 本棚-本テーブルキー
 * 
 * @author Junki Yamada
 * 
 */
@Embeddable
public class ShelfBooksKey {

	/** 本棚ID */
	@Column(name = "shelf_id")
	private long shelfId = 0;

	/** 本ID */
	@Column(name = "book_id")
	private long bookId = 0;

	/**
	 * @return shelfId
	 */
	public long getShelfId() {
		return shelfId;
	}

	/**
	 * @param shelfId セットする shelfId
	 */
	public void setShelfId(final long shelfId) {
		this.shelfId = shelfId;
	}

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
}
