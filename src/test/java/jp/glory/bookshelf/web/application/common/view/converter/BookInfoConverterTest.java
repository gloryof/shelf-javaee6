package jp.glory.bookshelf.web.application.common.view.converter;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import jp.glory.bookshelf.domain.book.entity.Book;
import jp.glory.bookshelf.domain.book.value.BookId;
import jp.glory.bookshelf.domain.book.value.IsbnCode;
import jp.glory.bookshelf.domain.book.value.Price;
import jp.glory.bookshelf.domain.book.value.Title;
import jp.glory.bookshelf.domain.shelf.value.ShelfId;
import jp.glory.bookshelf.web.application.common.view.bean.BookInfoBean;
import jp.glory.bookshelf.web.tool.BookInfoAssert;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class BookInfoConverterTest {

	public static class convertToBookInfoのテスト {

		@Test
		public void 本と親本棚IDを指定した場合_設定された値が保持された本情報Beanが返却される() {

			final Book expectedBook = TestTools.createBook01();

			final BookInfoBean actualBean = BookInfoConverter.convertToBookInfo(expectedBook);

			TestTools.assertBook(actualBean, false, expectedBook);
		}

		@Test
		public void 本にNullを指定した場合_Nullが返却される() {

			assertThat(BookInfoConverter.convertToBookInfo(null), is(nullValue()));
		}

	}

	public static class convertToBookInfoListのテスト {

		@Test
		public void 本と親本棚IDを指定したした場合_未選択状態の本情報リストが返却される() {

			final List<Book> expectedBookList = TestTools.createExpectedList();

			final List<BookInfoBean> actualBookList = BookInfoConverter.convertToBookInfoList(expectedBookList);

			assertThat(actualBookList, is(not(nullValue())));
			assertThat(actualBookList.size(), is(expectedBookList.size()));

			for (int i = 0; i < expectedBookList.size(); i++) {

				final Book expectedBook = expectedBookList.get(i);
				final BookInfoBean actualBean = actualBookList.get(i);

				TestTools.assertBook(actualBean, false, expectedBook);
			}
		}

		@Test
		public void 本と親本棚IDと選択済み本IDを指定した場合_選択状態の本情報リストが返却される() {

			final List<Book> expectedBookList = TestTools.createExpectedList();
			final List<BookId> selectedBookIdList = TestTools.createSelectedList();

			final List<BookInfoBean> actualBookList = BookInfoConverter.convertToBookInfoList(expectedBookList,
					selectedBookIdList);

			assertThat(actualBookList, is(not(nullValue())));
			assertThat(actualBookList.size(), is(expectedBookList.size()));

			final List<Long> selectedLongIdList = TestTools.createLongList(selectedBookIdList);
			for (int i = 0; i < expectedBookList.size(); i++) {

				final Book expectedBook = expectedBookList.get(i);
				final BookInfoBean actualBean = actualBookList.get(i);
				final boolean isChecked = selectedLongIdList.contains(actualBean.getBookId());

				TestTools.assertBook(actualBean, isChecked, expectedBook);
			}
		}
	}

	private static class TestTools {

		private static void assertBook(final BookInfoBean actualBook, final boolean actualChecked,
				final Book expectedBook) {

			final BookInfoAssert bookInfoAssert = new BookInfoAssert(actualBook, expectedBook);
			bookInfoAssert.assertBook();

			assertThat(actualBook.isChecked(), is(actualChecked));
		}

		private static List<Book> createExpectedList() {

			final List<Book> expectedBookList = new ArrayList<>();
			expectedBookList.add(TestTools.createBook01());
			expectedBookList.add(TestTools.createBook02());
			expectedBookList.add(TestTools.createBook03());

			return expectedBookList;
		}

		private static List<BookId> createSelectedList() {

			final List<BookId> selectedBookIdList = new ArrayList<>();
			selectedBookIdList.add(TestTools.createBook01().getBookId());
			selectedBookIdList.add(TestTools.createBook03().getBookId());

			return selectedBookIdList;
		}

		private static Book createBook01() {

			final BookId bookId = new BookId(101101L);

			final Book book = new Book(bookId);
			book.setIsbnCode(new IsbnCode("1234567890001"));
			book.setTitle(new Title("タイトル１"));
			book.setPrice(new Price(1500));
			book.setParentShelfId(new ShelfId(101L));

			return book;
		}

		private static Book createBook02() {

			final BookId bookId = new BookId(101102L);

			final Book book = new Book(bookId);
			book.setIsbnCode(new IsbnCode("1234567890002"));
			book.setTitle(new Title("タイトル２"));
			book.setPrice(new Price(2500));
			book.setParentShelfId(new ShelfId(101L));

			return book;
		}

		private static Book createBook03() {

			final BookId bookId = new BookId(101103L);

			final Book book = new Book(bookId);
			book.setIsbnCode(new IsbnCode("1234567890003"));
			book.setTitle(new Title("タイトル３"));
			book.setPrice(new Price(3500));
			book.setParentShelfId(new ShelfId(101L));

			return book;
		}

		private static List<Long> createLongList(final List<BookId> bookIdList) {

			final List<Long> longList = new ArrayList<>();

			for (final BookId bookId : bookIdList) {

				longList.add(bookId.getValue());
			}

			return longList;
		}
	}
}
