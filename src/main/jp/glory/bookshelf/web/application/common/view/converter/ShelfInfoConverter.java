package jp.glory.bookshelf.web.application.common.view.converter;

import java.util.ArrayList;
import java.util.List;

import jp.glory.bookshelf.domain.shelf.entity.Shelf;
import jp.glory.bookshelf.domain.shelf.value.ShelfId;
import jp.glory.bookshelf.web.application.common.view.bean.ShelfInfoBean;

/**
 * 本棚情報コンバータ
 * 
 * @author Junki Yamada
 * 
 */
public final class ShelfInfoConverter {

	/**
	 * コンストラクタ
	 */
	private ShelfInfoConverter() {

	}

	/**
	 * 本棚情報Bean二変換する
	 * 
	 * @param shelf 本棚
	 * @return 本棚情報Bean
	 */
	public static ShelfInfoBean convertToViewInfo(final Shelf shelf) {

		return convertToViewInfo(shelf, false);
	}

	/**
	 * 本棚情報Beanに変換する
	 * 
	 * @param shelf 本棚
	 * @param checkedValue 選択値
	 * @return 本棚情報Bean
	 */
	public static ShelfInfoBean convertToViewInfo(final Shelf shelf, final boolean checkedValue) {

		if (shelf == null) {

			return null;
		}

		final ShelfInfoBean infoBean = new ShelfInfoBean();

		infoBean.setShelfId(shelf.getShelfId().getValue());
		infoBean.setName(shelf.getName().getValue());
		infoBean.setChecked(checkedValue);

		return infoBean;
	}

	/**
	 * 本棚情報Beanリストに変換する
	 * 
	 * @param shelfList 本棚リスト
	 * @param selectedShelfIdList 選択済本棚IDリスト
	 * @return 本棚情報Beanリスト
	 */
	public static List<ShelfInfoBean> convertToViewInfoList(final List<Shelf> shelfList,
			final List<ShelfId> selectedShelfIdList) {

		if (shelfList == null) {

			return null;
		}

		final List<ShelfInfoBean> beanList = new ArrayList<>();

		final List<Long> longShelfIdList = ShelfIdConverter.convertToLongList(selectedShelfIdList);
		for (final Shelf shelf : shelfList) {

			final boolean isChecked = longShelfIdList.contains(shelf.getShelfId().getValue());
			final ShelfInfoBean shlefBean = convertToViewInfo(shelf, isChecked);
			beanList.add(shlefBean);
		}

		return beanList;
	}
}
