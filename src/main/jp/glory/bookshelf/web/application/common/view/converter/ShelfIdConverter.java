package jp.glory.bookshelf.web.application.common.view.converter;

import java.util.ArrayList;
import java.util.List;

import jp.glory.bookshelf.domain.shelf.value.ShelfId;

/**
 * 本棚IDコンバータ
 * 
 * @author Junki Yamada
 * 
 */
public final class ShelfIdConverter {

	/**
	 * コンストラクタ
	 */
	private ShelfIdConverter() {

	}

	/**
	 * 本棚IDのリストに変換する
	 * 
	 * @param longIdList Long型リスト
	 * @return 本棚IDリスト
	 */
	public static List<ShelfId> convertToShelfIdList(final List<Long> longIdList) {

		if (longIdList == null) {

			return new ArrayList<>();
		}

		final List<ShelfId> shelfIdList = new ArrayList<>();

		for (final Long longId : longIdList) {

			shelfIdList.add(new ShelfId(longId));
		}

		return shelfIdList;
	}

	/**
	 * Long型のリストに変換する
	 * 
	 * @param shelfIdList 本棚IDリスト
	 * @return Long型リスト
	 */
	public static List<Long> convertToLongList(final List<ShelfId> shelfIdList) {

		if (shelfIdList == null) {

			return new ArrayList<>();
		}

		final List<Long> convertList = new ArrayList<>();

		for (final ShelfId shelfId : shelfIdList) {

			convertList.add(shelfId.getValue());
		}

		return convertList;
	}

}
