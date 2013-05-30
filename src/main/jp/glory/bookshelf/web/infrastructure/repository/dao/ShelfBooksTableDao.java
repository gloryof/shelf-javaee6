package jp.glory.bookshelf.web.infrastructure.repository.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import jp.glory.bookshelf.web.infrastructure.repository.bean.ShelfBooksTableBean;
import jp.glory.bookshelf.web.infrastructure.repository.bean.key.ShelfBooksKey;
import jp.glory.bookshelf.web.infrastructure.repository.constant.RepositoryConst;
import jp.glory.bookshelf.web.infrastructure.repository.constant.ShelfBooksSQLConst;

/**
 * 本棚-本テーブルDAO
 * 
 * @author Junki Yamada
 * 
 */
public class ShelfBooksTableDao {

	/** エンティティマネージャ */
	@PersistenceContext(unitName = RepositoryConst.JPA_UNIT_NAME)
	private EntityManager manager = null;

	/**
	 * 本棚IDをキーに本を削除する
	 * 
	 * @param shelfId 本棚ID
	 */
	public void deleteBooksByShelfId(final long shelfId) {

		final Query query = manager.createNamedQuery(ShelfBooksSQLConst.NAME_DELETE_SHELF_ID);
		query.setParameter(ShelfBooksSQLConst.PARAM_SHELF_ID, shelfId);

		query.executeUpdate();
	}

	/**
	 * 本を削除する
	 * 
	 * @param bookId 本ID
	 */
	public void deleteBook(final long bookId) {

		final Query query = manager.createNamedQuery(ShelfBooksSQLConst.NAME_DELETE_BOOK_ID);
		query.setParameter(ShelfBooksSQLConst.PARAM_BOOK_ID, bookId);

		query.executeUpdate();
	}

	/**
	 * 登録を行う
	 * 
	 * @param shelfId 本棚ID
	 * @param bookId 本ID
	 */
	public void insert(final long shelfId, final long bookId) {

		final ShelfBooksTableBean tableBean = new ShelfBooksTableBean();
		final ShelfBooksKey key = new ShelfBooksKey();
		key.setShelfId(shelfId);
		key.setBookId(bookId);
		tableBean.setKey(key);

		manager.persist(tableBean);
	}

	/**
	 * 本棚IDを本IDから取得する
	 * 
	 * @param bookId 本ID
	 * @return 本棚ID
	 */
	public long findShelfIdByBookId(final long bookId) {

		final TypedQuery<ShelfBooksTableBean> query = manager.createNamedQuery(ShelfBooksSQLConst.NAME_FIND_BY_BOOK_ID,
				ShelfBooksTableBean.class);
		query.setParameter(ShelfBooksSQLConst.PARAM_BOOK_ID, bookId);

		return query.getSingleResult().getKey().getShelfId();
	}
}
