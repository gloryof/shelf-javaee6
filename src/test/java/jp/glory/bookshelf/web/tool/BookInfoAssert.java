package jp.glory.bookshelf.web.tool;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import jp.glory.bookshelf.domain.book.entity.Book;
import jp.glory.bookshelf.web.application.common.view.bean.BookInfoBean;

public class BookInfoAssert {

	private final BookInfoBean actualBookInfo;

	private final Book expectedBook;

	public BookInfoAssert(final BookInfoBean actualBookInfo, final Book expectedBook) {

		this.actualBookInfo = actualBookInfo;
		this.expectedBook = expectedBook;
	}

	public void assertBook() {

		assertThat(actualBookInfo, is(not(nullValue())));

		assertThat(actualBookInfo.getBookId(), is(expectedBook.getBookId().getValue()));
		assertThat(actualBookInfo.getParentShelfId(), is(expectedBook.getParentShelfId().getValue()));
		assertThat(actualBookInfo.getIsbnCode(), is(expectedBook.getIsbnCode().getValue()));
		assertThat(actualBookInfo.getIsbnOnlyCode(), is(expectedBook.getIsbnCode().getOnlyCodes()));
		assertThat(actualBookInfo.getTitle(), is(expectedBook.getTitle().getValue()));
		assertThat(actualBookInfo.getPrice(), is(expectedBook.getPrice().getValue()));
	}
}
