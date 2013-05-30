package jp.glory.bookshelf.web.infrastructure.repository.converter;

import java.util.ArrayList;
import java.util.List;

import jp.glory.bookshelf.domain.book.entity.Book;
import jp.glory.bookshelf.domain.shelf.entity.Shelf;
import jp.glory.bookshelf.domain.shelf.value.Name;
import jp.glory.bookshelf.domain.shelf.value.ShelfId;
import jp.glory.bookshelf.web.infrastructure.repository.bean.ShelfsTableBean;

/**
 * 本棚エンティティと本棚テーブルBeanの変換を行う。
 * 
 * @author Junki Yamada
 * 
 */
public final class ShelfConverter {

	/**
	 * コンストラクタ
	 */
	private ShelfConverter() {

	}

	/**
	 * テーブルBeanからエンティティに変換する
	 * 
	 * @param paramTableBean テーブルBean
	 * @return エンティティ
	 */
	public static Shelf convertToEntity(final ShelfsTableBean paramTableBean) {

		if (paramTableBean == null) {

			return null;
		}

		final ShelfId shelfId = new ShelfId(paramTableBean.getShelfId());
		final Shelf entity = new Shelf(shelfId, new ArrayList<Book>());

		entity.setName(new Name(paramTableBean.getName()));

		return entity;
	}

	/**
	 * エンティティからテーブルBeanに変換する
	 * 
	 * @param paramEntity エンティティ
	 * @return テーブルBean
	 */
	public static ShelfsTableBean convertToTableBean(final Shelf paramEntity) {

		if (paramEntity == null) {

			return null;
		}

		final ShelfsTableBean tableBean = new ShelfsTableBean();

		tableBean.setShelfId(paramEntity.getShelfId().getValue());
		tableBean.setName(paramEntity.getName().getValue());

		return tableBean;
	}

	/**
	 * テーブルBeanリストとからエンティティリストに変換する
	 * 
	 * @param tableBeanList テーブルBean
	 * @return エンティティリスト
	 */
	public static List<Shelf> convertToEntityList(final List<ShelfsTableBean> tableBeanList) {

		if (tableBeanList == null) {

			return null;
		}

		final List<Shelf> returnList = new ArrayList<>();

		for (final ShelfsTableBean tableBean : tableBeanList) {

			returnList.add(convertToEntity(tableBean));
		}

		return returnList;
	}
}
