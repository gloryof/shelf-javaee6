package jp.glory.bookshelf.web.application.common.view.bean;

/**
 * 本棚情報Bean
 * 
 * @author Junki Yamada
 * 
 */
public class ShelfInfoBean {

	/** 本棚ID */
	private long shelfId = 0;

	/** 本棚名 */
	private String name = null;

	/** 選択フラグ */
	private boolean checked = false;

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
