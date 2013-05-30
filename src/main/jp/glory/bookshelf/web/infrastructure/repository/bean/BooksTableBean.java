package jp.glory.bookshelf.web.infrastructure.repository.bean;

import java.sql.Timestamp;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import jp.glory.bookshelf.web.infrastructure.repository.constant.BooksSQLConst;

/**
 * 本棚テーブルBean
 * 
 * @author Junki Yamada
 * 
 */
@Entity
@Table(name = "books")
@NamedQueries({
		@NamedQuery(name = BooksSQLConst.NAME_FIND_BY_PK, query = BooksSQLConst.QUERY_FIND_BY_PK),
		@NamedQuery(name = BooksSQLConst.NAME_FIND_BY_SHELF_ID, query = BooksSQLConst.QUERY_FIND_BY_SHELF_ID),
		@NamedQuery(name = BooksSQLConst.NAME_FIND_BY_ISBN_CODE, query = BooksSQLConst.QUERY_FIND_BY_ISBN_CODE)
})
@Cacheable(value = false)
public class BooksTableBean {

	/** 本ID */
	@Id
	@Column(name = "book_id")
	private long bookId = 0;

	/** ISBNコード */
	@Column(name = "isbn_code")
	private String isbnCode = null;

	/** タイトル */
	@Column
	private String title = null;

	/** 価格 */
	@Column
	private int price = 0;

	/** 削除フラグ */
	@Column(name = "delete_flag")
	private String deleteFlag = null;

	/** 登録日 */
	@Column(name = "registration_date")
	private Timestamp registrationDate = null;

	/** 更新日 */
	@Column(name = "update_date")
	private Timestamp updateDate = null;

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
	 * @return deleteFlag
	 */
	public String getDeleteFlag() {
		return deleteFlag;
	}

	/**
	 * @param deleteFlag セットする deleteFlag
	 */
	public void setDeleteFlag(final String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	/**
	 * @return registrationDate
	 */
	public Timestamp getRegistrationDate() {
		return registrationDate;
	}

	/**
	 * @param registrationDate セットする registrationDate
	 */
	public void setRegistrationDate(final Timestamp registrationDate) {
		this.registrationDate = registrationDate;
	}

	/**
	 * @return updateDate
	 */
	public Timestamp getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate セットする updateDate
	 */
	public void setUpdateDate(final Timestamp updateDate) {
		this.updateDate = updateDate;
	}

}
