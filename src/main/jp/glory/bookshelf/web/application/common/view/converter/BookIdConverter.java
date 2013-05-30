package jp.glory.bookshelf.web.application.common.view.converter;

import java.util.ArrayList;
import java.util.List;

import jp.glory.bookshelf.domain.book.value.BookId;

/**
 * 本IDコンバータ
 * 
 * @author Junki Yamada
 * 
 */
public final class BookIdConverter {

	/**
	 * コンストラクタ
	 */
	private BookIdConverter() {

	}

	/**
	 * Long型のリストを本棚IDのリストに変換する
	 * 
	 * @param longList Long型のリスト
	 * @return 本棚IDリスト
	 */
	public static List<BookId> convertToBookList(final List<Long> longList) {

		if (longList == null) {

			return new ArrayList<>();
		}

		final List<BookId> bookIdList = new ArrayList<>();

		for (final Long longId : longList) {

			bookIdList.add(new BookId(longId));
		}

		return bookIdList;
	}

	/**
	 * 本棚IDリストをLong型のリストに変換する
	 * 
	 * @param bookIdList 本棚IDリスト
	 * @return Long型のリスト
	 */
	public static List<Long> convertToLongList(final List<BookId> bookIdList) {

		if (bookIdList == null) {

			return new ArrayList<>();
		}

		final List<Long> longList = new ArrayList<>();

		for (final BookId bookId : bookIdList) {

			longList.add(bookId.getValue());
		}

		return longList;
	}

}
