package jp.glory.bookshelf.web.application.book.view;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import jp.glory.bookshelf.domain.book.entity.Book;
import jp.glory.bookshelf.domain.book.value.BookId;
import jp.glory.bookshelf.domain.book.value.IsbnCode;
import jp.glory.bookshelf.domain.book.value.Price;
import jp.glory.bookshelf.domain.book.value.Title;
import jp.glory.bookshelf.domain.shelf.value.ShelfId;
import jp.glory.bookshelf.web.application.common.constant.ContentName;
import jp.glory.bookshelf.web.application.common.constant.PageTitle;
import jp.glory.bookshelf.web.application.common.view.bean.BookInfoBean;
import jp.glory.bookshelf.web.tool.BookInfoAssert;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class BookEditViewTest {

	private static BookEditView sut = null;

	public static class 共通設定のテスト {

		@Before
		public void setUp() {

			sut = new BookEditView(new Book());
		}

		@Test
		public void getTitleでページタイトルが返却される() {

			assertThat(sut.getTitle(), is(PageTitle.BOOK_EDIT_VIEW));
		}

		@Test
		public void getContentNameでコンテンツ名が返却される() {

			assertThat(sut.getContentName(), is(ContentName.BOOK_EDIT_VIEW));
		}
	}

	public static class 本を指定した場合 {

		@Before
		public void setUp() {

			sut = new BookEditView(TestTools.createBook());
		}

		@Test
		public void getBook_本棚情報Beanが返却される() {

			final BookInfoBean actualBookInfo = sut.getBook();

			final BookInfoAssert bookAssert = new BookInfoAssert(actualBookInfo, TestTools.createBook());
			bookAssert.assertBook();
			assertThat(actualBookInfo.isChecked(), is(false));
		}
	}

	public static class 本にNullを指定した場合 {

		@Before
		public void setUp() {

			sut = new BookEditView(null);
		}

		@Test
		public void getBook_未設定の本棚情報Beanが返却される() {

			final BookInfoBean actualBookInfo = sut.getBook();

			assertThat(actualBookInfo, is(not(nullValue())));

			assertThat(actualBookInfo.getBookId(), is(0L));
			assertThat(actualBookInfo.getParentShelfId(), is(0L));
			assertThat(actualBookInfo.getIsbnCode(), is(nullValue()));
			assertThat(actualBookInfo.getIsbnOnlyCode(), is(nullValue()));
			assertThat(actualBookInfo.getTitle(), is(nullValue()));
			assertThat(actualBookInfo.getPrice(), is(0));
			assertThat(actualBookInfo.isChecked(), is(false));
		}
	}

	private static class TestTools {

		private static Book createBook() {

			final Book book = new Book(new BookId(1001L));

			book.setTitle(new Title("タイトル"));
			book.setIsbnCode(new IsbnCode("1234567890123"));
			book.setPrice(new Price(1000));
			book.setParentShelfId(new ShelfId(101L));

			return book;
		}
	}
}
