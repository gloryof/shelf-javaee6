package jp.glory.bookshelf.web.application.book.view;

import jp.glory.bookshelf.domain.book.entity.Book;
import jp.glory.bookshelf.web.application.common.constant.ContentName;
import jp.glory.bookshelf.web.application.common.constant.PageTitle;
import jp.glory.bookshelf.web.application.common.view.AbstractView;
import jp.glory.bookshelf.web.application.common.view.bean.BookInfoBean;
import jp.glory.bookshelf.web.application.common.view.converter.BookInfoConverter;

/**
 * 本作成ビュー
 *
 * @author Junki Yamada
 *
 */
public class BookCreateView extends AbstractView {

	/** 本 */
	private final BookInfoBean book;

	/**
	 * コンストラクタ
	 *
	 * @param book 本
	 */
	public BookCreateView(final Book book) {

		this.book = createBookInfo(book);
	}

	/**
	 * 本情報Bean作成
	 *
	 * @param book 本
	 * @return 本情報Bean
	 */
	private BookInfoBean createBookInfo(final Book book) {

		if (book == null) {

			return new BookInfoBean();
		}

		return BookInfoConverter.convertToBookInfo(book);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PageTitle getTitle() {

		return PageTitle.BOOK_EDIT_VIEW;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ContentName getContentName() {

		return ContentName.BOOK_CREATE_VIEW;
	}

	/**
	 * @return book
	 */
	public BookInfoBean getBook() {
		return book;
	}

}
