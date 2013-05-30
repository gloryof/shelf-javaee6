package jp.glory.bookshelf.web.infrastructure.repository.bean;

import java.sql.Timestamp;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import jp.glory.bookshelf.web.infrastructure.repository.constant.ShelfsSQLConst;

/**
 * 本棚テーブルBean
 * 
 * @author Junki Yamada
 * 
 */
@Entity
@Table(name = "shelfs")
@Cacheable(value = false)
@NamedQueries({
		@NamedQuery(name = ShelfsSQLConst.NAME_FIND_BY_PK, query = ShelfsSQLConst.QUERY_FIND_BY_PK),
		@NamedQuery(name = ShelfsSQLConst.NAME_FIND_ALL, query = ShelfsSQLConst.QUERY_FIND_ALL)
})
public class ShelfsTableBean {

	/** 本棚ID */
	@Id
	@Column(name = "shelf_id")
	private long shelfId = 0;

	/** 名前 */
	@Column
	private String name = null;

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
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name セットする name
	 */
	public void setName(final String name) {
		this.name = name;
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
