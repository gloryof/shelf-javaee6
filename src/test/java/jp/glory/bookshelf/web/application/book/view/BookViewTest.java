package jp.glory.bookshelf.web.application.book.view;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import jp.glory.bookshelf.domain.book.entity.Book;
import jp.glory.bookshelf.domain.book.value.BookId;
import jp.glory.bookshelf.domain.book.value.IsbnCode;
import jp.glory.bookshelf.domain.book.value.Price;
import jp.glory.bookshelf.domain.book.value.Title;
import jp.glory.bookshelf.web.application.common.constant.ContentName;
import jp.glory.bookshelf.web.application.common.constant.PageTitle;
import jp.glory.bookshelf.web.application.common.view.bean.BookInfoBean;
import jp.glory.bookshelf.web.tool.BookInfoAssert;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class BookViewTest {

	private static BookView sut = null;

	public static class 共通設定のテスト {

		@Before
		public void setUp() {

			sut = new BookView(TestTools.createBook01());
		}

		@Test
		public void getTitleでページタイトルが返却される() {

			assertThat(sut.getTitle(), is(PageTitle.BOOK_VIEW));
		}

		@Test
		public void getContentNameでコンテンツ名が返却される() {

			assertThat(sut.getContentName(), is(ContentName.BOOK_VIEW));
		}
	}

	public static class 単一の本が設定される場合 {

		@Before
		public void setUp() {

			sut = new BookView(TestTools.createBook01());
		}

		@Test
		public void getBookList_一つの本が入ったリストが返却される() {

			final List<BookInfoBean> actualList = sut.getBookList();

			assertThat(actualList, is(not(nullValue())));
			assertThat(actualList.size(), is(1));

			final BookInfoAssert bookAssert = new BookInfoAssert(actualList.get(0), TestTools.createBook01());
			bookAssert.assertBook();
		}

		@Test
		public void isViewBackLink_trueが返却される() {

			assertThat(sut.isViewBackLink(), is(true));
		}

	}

	public static class 本にNullを指定した場合 {

		@Before
		public void setUp() {

			sut = new BookView((Book) null);
		}

		@Test
		public void getBookList_空リストで返却される() {

			final List<BookInfoBean> actualInfoList = sut.getBookList();

			assertThat(actualInfoList, is(not(nullValue())));
			assertThat(actualInfoList.isEmpty(), is(true));
		}

		@Test
		public void isViewBackLink_trueが返却される() {

			assertThat(sut.isViewBackLink(), is(true));
		}

	}

	public static class 本リストを指定した場合 {

		@Before
		public void setUp() {

			sut = new BookView(TestTools.createBookList());
		}

		@Test
		public void getBookList_変換されたリストが返却される() {

			final List<BookInfoBean> actualInfoList = sut.getBookList();

			assertThat(actualInfoList, is(not(nullValue())));

			final List<Book> expectedInfoList = TestTools.createBookList();

			assertThat(actualInfoList.size(), is(expectedInfoList.size()));

			for (int i = 0; i < actualInfoList.size(); i++) {

				final BookInfoAssert bookAssert = new BookInfoAssert(actualInfoList.get(i), expectedInfoList.get(i));
				bookAssert.assertBook();
			}
		}

		@Test
		public void isViewBackLink_falseが返却される() {

			assertThat(sut.isViewBackLink(), is(false));
		}
	}

	public static class 本リストにNullを指定した場合 {

		@Before
		public void setUp() {

			sut = new BookView((List<Book>) null);
		}

		@Test
		public void getBookList_空リストで返却される() {

			final List<BookInfoBean> actualInfoList = sut.getBookList();

			assertThat(actualInfoList, is(not(nullValue())));
			assertThat(actualInfoList.isEmpty(), is(true));
		}

		@Test
		public void isViewBackLink_falseが返却される() {

			assertThat(sut.isViewBackLink(), is(false));
		}

	}

	private static class TestTools {

		private static List<Book> createBookList() {

			final List<Book> bookList = new ArrayList<>();

			bookList.add(createBook01());
			bookList.add(createBook02());
			bookList.add(createBook03());

			return bookList;
		}

		private static Book createBook01() {

			final Book book = new Book(new BookId(201L));
			book.setIsbnCode(new IsbnCode("1234567890001"));
			book.setTitle(new Title("タイトル０１"));
			book.setPrice(new Price(1500));

			return book;
		}

		private static Book createBook02() {

			final Book book = new Book(new BookId(202L));
			book.setIsbnCode(new IsbnCode("1234567890002"));
			book.setTitle(new Title("タイトル０２"));
			book.setPrice(new Price(2500));

			return book;
		}

		private static Book createBook03() {

			final Book book = new Book(new BookId(203L));
			book.setIsbnCode(new IsbnCode("1234567890003"));
			book.setTitle(new Title("タイトル０３"));
			book.setPrice(new Price(3500));

			return book;
		}
	}
}
