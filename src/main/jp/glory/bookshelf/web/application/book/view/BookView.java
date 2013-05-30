package jp.glory.bookshelf.web.application.book.view;

import java.util.ArrayList;
import java.util.List;

import jp.glory.bookshelf.domain.book.entity.Book;
import jp.glory.bookshelf.web.application.common.constant.ContentName;
import jp.glory.bookshelf.web.application.common.constant.PageTitle;
import jp.glory.bookshelf.web.application.common.view.AbstractView;
import jp.glory.bookshelf.web.application.common.view.bean.BookInfoBean;
import jp.glory.bookshelf.web.application.common.view.converter.BookInfoConverter;

/**
 * 本ビュー
 *
 * @author Junki Yamada
 *
 */
public class BookView extends AbstractView {

	/** 本 */
	private final List<BookInfoBean> bookList;

	/** 戻るフラグ */
	private final boolean isViewBackLink;

	/**
	 * コンストラクタ
	 *
	 * @param book 本
	 */
	public BookView(final Book book) {

		bookList = createBookInfoList(convertBookList(book));

		isViewBackLink = true;
	}

	/**
	 * コンストラクタ
	 *
	 * @param bookList 本リスト
	 */
	public BookView(final List<Book> bookList) {

		this.bookList = createBookInfoList(bookList);

		isViewBackLink = false;
	}

	/**
	 * 本情報リスト作成
	 *
	 * @param bookList 本リスト
	 * @return 本情報リスト
	 */
	private List<BookInfoBean> createBookInfoList(final List<Book> bookList) {

		if (bookList == null) {

			return new ArrayList<>();
		}

		return BookInfoConverter.convertToBookInfoList(bookList);
	}

	/**
	 * 本リストに変換する
	 *
	 * @param book 本
	 * @return 本リスト
	 */
	private List<Book> convertBookList(final Book book) {

		final List<Book> bookList = new ArrayList<>();

		if (book != null) {

			bookList.add(book);
		}

		return bookList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PageTitle getTitle() {

		return PageTitle.BOOK_VIEW;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ContentName getContentName() {

		return ContentName.BOOK_VIEW;
	}

	/**
	 * @return bookList
	 */
	public List<BookInfoBean> getBookList() {
		return bookList;
	}

	/**
	 * @return isViewBackLink
	 */
	public boolean isViewBackLink() {
		return isViewBackLink;
	}
}
