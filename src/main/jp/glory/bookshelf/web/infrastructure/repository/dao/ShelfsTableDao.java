package jp.glory.bookshelf.web.infrastructure.repository.dao;

import java.sql.Timestamp;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import jp.glory.bookshelf.web.common.utl.TimeUtil;
import jp.glory.bookshelf.web.infrastructure.repository.bean.ShelfsTableBean;
import jp.glory.bookshelf.web.infrastructure.repository.constant.RepositoryConst;
import jp.glory.bookshelf.web.infrastructure.repository.constant.ShelfsSQLConst;

/**
 * 本棚テーブルDAO
 *
 * @author Junki Yamada
 *
 */
public class ShelfsTableDao {

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
	public void insert(final ShelfsTableBean tableBean) {

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
	public void update(final ShelfsTableBean tableBean) {

		final ShelfsTableBean baseBean = findById(tableBean.getShelfId());
		final Timestamp sysTimestamp = timeUtil.getSysTimestamp();

		baseBean.setName(tableBean.getName());
		baseBean.setUpdateDate(sysTimestamp);

		manager.persist(baseBean);
	}

	/**
	 * 削除を行う
	 *
	 * @param shelfId 本棚ID
	 */
	public void delete(final long shelfId) {

		final ShelfsTableBean baseBean = findById(shelfId);
		final Timestamp sysTimestamp = timeUtil.getSysTimestamp();

		baseBean.setDeleteFlag(RepositoryConst.FLAG_ON);
		baseBean.setUpdateDate(sysTimestamp);

		manager.persist(baseBean);
	}

	/**
	 * 本棚IDの採番を行う
	 *
	 * @return 本棚ID
	 */
	public long createNewNumbering() {

		final Query query = manager.createNativeQuery(ShelfsSQLConst.QUERY_CREATE_NEW_NUMBERING);

		return (Long) query.getSingleResult();
	}

	/**
	 * PKによる検索を行う
	 *
	 * @param shelfId 本棚ID
	 * @return テーブルBean
	 */
	public ShelfsTableBean findById(final long shelfId) {

		final TypedQuery<ShelfsTableBean> query = manager.createNamedQuery(ShelfsSQLConst.NAME_FIND_BY_PK,
				ShelfsTableBean.class);

		query.setParameter(ShelfsSQLConst.PARAM_SHELF_ID, shelfId);
		query.setParameter(ShelfsSQLConst.PARAM_DELETE_FLAG, RepositoryConst.FLAG_OFF);

		return query.getSingleResult();
	}

	/**
	 * 全件検索
	 *
	 * @return テーブルBeanリスト
	 */
	public List<ShelfsTableBean> findAll() {

		final TypedQuery<ShelfsTableBean> query = manager.createNamedQuery(ShelfsSQLConst.NAME_FIND_ALL,
				ShelfsTableBean.class);

		return query.getResultList();
	}
}
