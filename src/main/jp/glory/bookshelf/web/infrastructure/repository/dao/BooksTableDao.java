package jp.glory.bookshelf.web.infrastructure.repository.dao;

import java.sql.Timestamp;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import jp.glory.bookshelf.web.common.utl.TimeUtil;
import jp.glory.bookshelf.web.infrastructure.repository.bean.BooksTableBean;
import jp.glory.bookshelf.web.infrastructure.repository.constant.BooksSQLConst;
import jp.glory.bookshelf.web.infrastructure.repository.constant.RepositoryConst;

/**
 * 本テーブルDAO
 *
 * @author Junki Yamada
 *
 */
public class BooksTableDao {

	/** エンティティマネージャ */
	@PersistenceContext(unitName = RepositoryConst.JPA_UNIT_NAME)
	private EntityManager manager = null;

	/** 時間ユーティリティ */
	@Inject
	private TimeUtil timeUtil = null;

	/**
	 * 登録を行う
	 *
	 * @param tableBean テーブルBean
	 */
	public void insert(final BooksTableBean tableBean) {

		final Timestamp sysTimestamp = timeUtil.getSysTimestamp();

		tableBean.setRegistrationDate(sysTimestamp);
		tableBean.setUpdateDate(sysTimestamp);
		tableBean.setDeleteFlag(RepositoryConst.FLAG_OFF);

		manager.persist(tableBean);
	}

	/**
	 * 更新を行う
	 *
	 * @param tableBean テーブルBean
	 */
	public void update(final BooksTableBean tableBean) {

		final BooksTableBean updateBean = getBooksTableById(tableBean.getBookId());
		final Timestamp sysTimestamp = timeUtil.getSysTimestamp();

		updateBean.setTitle(tableBean.getTitle());
		updateBean.setIsbnCode(tableBean.getIsbnCode());
		updateBean.setPrice(tableBean.getPrice());
		tableBean.setUpdateDate(sysTimestamp);

		manager.persist(updateBean);
	}

	/**
	 * 削除を行う
	 *
	 * @param bookId 本ID
	 */
	public void delete(final long bookId) {

		final BooksTableBean deleteBean = getBooksTableById(bookId);
		final Timestamp sysTimestamp = timeUtil.getSysTimestamp();

		deleteBean.setDeleteFlag(RepositoryConst.FLAG_ON);
		deleteBean.setUpdateDate(sysTimestamp);

		manager.persist(deleteBean);
	}

	/**
	 * IDで検索する
	 *
	 * @param bookId 本ID
	 * @return 本テーブルBean
	 */
	public BooksTableBean findById(final long bookId) {

		return getBooksTableById(bookId);
	}

	/**
	 * 本棚IDで検索し、リストを取得する
	 *
	 * @param shelfId 本棚ID
	 * @return 本リスト
	 */
	public List<BooksTableBean> findListByShlefId(final long shelfId) {

		final TypedQuery<BooksTableBean> query = manager.createNamedQuery(BooksSQLConst.NAME_FIND_BY_SHELF_ID,
				BooksTableBean.class);
		query.setParameter(BooksSQLConst.PARAM_SHELF_ID, shelfId);
		query.setParameter(BooksSQLConst.PARAM_DELETE_FLAG, RepositoryConst.FLAG_OFF);

		return query.getResultList();
	}

	/**
	 * ISBNコードで検索し、リストを取得する
	 *
	 * @param isbnCode ISBNコード
	 * @return 本リスト
	 */
	public List<BooksTableBean> findListByIsbnCode(final String isbnCode) {

		final TypedQuery<BooksTableBean> query = manager.createNamedQuery(BooksSQLConst.NAME_FIND_BY_ISBN_CODE,
				BooksTableBean.class);

		query.setParameter(BooksSQLConst.PARAM_ISBN_CODE, isbnCode);
		query.setParameter(BooksSQLConst.PARAM_DELETE_FLAG, RepositoryConst.FLAG_OFF);

		return query.getResultList();
	}

	/**
	 * 本IDの採番を行う
	 *
	 * @return 本ID
	 */
	public long createNewNumbering() {

		final Query query = manager.createNativeQuery(BooksSQLConst.QUERY_CREATE_NEW_NUMBERING);

		return (Long) query.getSingleResult();
	}

	/**
	 * テーブルBeanを本IDを取得する
	 *
	 * @param bookId 本ID
	 * @return テーブルBean
	 */
	private BooksTableBean getBooksTableById(final long bookId) {

		final TypedQuery<BooksTableBean> query = manager.createNamedQuery(BooksSQLConst.NAME_FIND_BY_PK,
				BooksTableBean.class);
		query.setParameter(BooksSQLConst.PARAM_BOOK_ID, bookId);
		query.setParameter(BooksSQLConst.PARAM_DELETE_FLAG, RepositoryConst.FLAG_OFF);

		return query.getSingleResult();
	}
}
