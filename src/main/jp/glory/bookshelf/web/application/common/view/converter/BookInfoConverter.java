package jp.glory.bookshelf.web.application.common.view.converter;

import java.util.ArrayList;
import java.util.List;

import jp.glory.bookshelf.domain.book.entity.Book;
import jp.glory.bookshelf.domain.book.value.BookId;
import jp.glory.bookshelf.web.application.common.view.bean.BookInfoBean;

/**
 * 本情報コンバータ
 * 
 * @author Junki Yamada
 * 
 */
public final class BookInfoConverter {

	/**
	 * コンストラクタ
	 */
	private BookInfoConverter() {

	}

	/**
	 * 本情報Beanに変換する
	 * 
	 * @param book 本
	 * @return 本情報Bean
	 */
	public static BookInfoBean convertToBookInfo(final Book book) {

		if (book == null) {

			return null;
		}

		final BookInfoBean bookInfo = new BookInfoBean();

		bookInfo.setBookId(book.getBookId().getValue());
		bookInfo.setIsbnCode(book.getIsbnCode().getValue());
		bookInfo.setIsbnOnlyCode(book.getIsbnCode().getOnlyCodes());
		bookInfo.setPrice(book.getPrice().getValue());
		bookInfo.setTitle(book.getTitle().getValue());
		bookInfo.setParentShelfId(book.getParentShelfId().getValue());

		return bookInfo;
	}

	/**
	 * 
	 * 本リストを未選択状態の本情報Beanリストに変換する
	 * 
	 * @param bookList 本リスト
	 * @return 未選択状態の本情報Beanリスト
	 */
	public static List<BookInfoBean> convertToBookInfoList(final List<Book> bookList) {

		return convertToBookInfoList(bookList, new ArrayList<BookId>());
	}

	/**
	 * 本リストを本情報Beanリストに変換する
	 * 
	 * @param bookList 本リスト
	 * @param selectedBookIdList 選択済み本IDリスト
	 * @return 本情報Beanリスト
	 */
	public static List<BookInfoBean> convertToBookInfoList(final List<Book> bookList,
			final List<BookId> selectedBookIdList) {

		final List<BookInfoBean> bookInfoBeanList = new ArrayList<>();

		for (final Book book : bookList) {

			final BookInfoBean bookInfoBean = convertToBookInfo(book);
			bookInfoBean.setChecked(containsBookId(selectedBookIdList, book.getBookId()));

			bookInfoBeanList.add(bookInfoBean);
		}

		return bookInfoBeanList;
	}

	/**
	 * 本IDが存在するかチェックする
	 * 
	 * @param bookIdList
	 * @param bookId
	 * @return 存在する場合：true、存在しない場合：false
	 */
	private static boolean containsBookId(final List<BookId> bookIdList, final BookId bookId) {

		for (final BookId baseBookId : bookIdList) {

			if (baseBookId.isSame(bookId)) {

				return true;
			}
		}

		return false;
	}
}
