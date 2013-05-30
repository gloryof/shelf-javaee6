package jp.glory.bookshelf.web.infrastructure.repository.bean;

import javax.persistence.Cacheable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import jp.glory.bookshelf.web.infrastructure.repository.bean.key.ShelfBooksKey;
import jp.glory.bookshelf.web.infrastructure.repository.constant.ShelfBooksSQLConst;

/**
 * 本棚-本テーブルBean
 * 
 * @author Junki Yamada
 * 
 */
@Entity
@Table(name = "shelf_books")
@NamedQueries({
		@NamedQuery(name = ShelfBooksSQLConst.NAME_DELETE_SHELF_ID, query = ShelfBooksSQLConst.QUERY_DELETE_SHELF_ID),
		@NamedQuery(name = ShelfBooksSQLConst.NAME_FIND_BY_BOOK_ID, query = ShelfBooksSQLConst.QUERY_FIND_BY_BOOK_ID)
})
@Cacheable(value = false)
public class ShelfBooksTableBean {

	/** キー値 */
	@EmbeddedId
	private ShelfBooksKey key = null;

	/**
	 * @return key
	 */
	public ShelfBooksKey getKey() {
		return key;
	}

	/**
	 * @param key セットする key
	 */
	public void setKey(final ShelfBooksKey key) {
		this.key = key;
	}
}
