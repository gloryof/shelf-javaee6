package jp.glory.bookshelf.web.application.book.view;

import jp.glory.bookshelf.domain.book.entity.Book;
import jp.glory.bookshelf.web.application.common.constant.ContentName;
import jp.glory.bookshelf.web.application.common.constant.PageTitle;
import jp.glory.bookshelf.web.application.common.view.AbstractView;
import jp.glory.bookshelf.web.application.common.view.bean.BookInfoBean;
import jp.glory.bookshelf.web.application.common.view.converter.BookInfoConverter;

/**
 * 本編集ビュー
 *
 * @author Junki Yamada
 *
 */
public class BookEditView extends AbstractView {

	/** 本情報Bean */
	private final BookInfoBean book;

	/**
	 * コンストラクタ
	 *
	 * @param book 本
	 * @param updateMode 更新モード
	 */
	public BookEditView(final Book book) {

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

		return ContentName.BOOK_EDIT_VIEW;
	}

	/**
	 * @return book
	 */
	public BookInfoBean getBook() {
		return book;
	}

}
