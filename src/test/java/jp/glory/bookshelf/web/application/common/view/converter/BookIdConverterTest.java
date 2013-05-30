package jp.glory.bookshelf.web.application.common.view.converter;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import jp.glory.bookshelf.domain.book.value.BookId;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class BookIdConverterTest {

	public static class convertToBookListのテスト {

		@Test
		public void Long型リストを指定した場合_本IDに変換される() {

			final List<Long> expectedList = TestTools.createLongIdList();
			final List<BookId> actualList = BookIdConverter.convertToBookList(expectedList);

			assertThat(actualList, is(not(nullValue())));
			assertThat(actualList.size(), is(expectedList.size()));

			for (int i = 0; i < expectedList.size(); i++) {

				final Long expectedBookId = expectedList.get(i);
				final BookId actualBookId = actualList.get(i);

				assertThat(expectedBookId, is(actualBookId.getValue()));
			}
		}

		@Test
		public void 空のリストを指定した場合_空のリストが返却される() {

			final List<BookId> actualList = BookIdConverter.convertToBookList(new ArrayList<Long>());

			assertThat(actualList, is(not(nullValue())));
			assertThat(actualList.isEmpty(), is(true));
		}

		@Test
		public void Nullを指定した場合_空のリストが返却される() {

			final List<BookId> actualList = BookIdConverter.convertToBookList(null);

			assertThat(actualList, is(not(nullValue())));
			assertThat(actualList.isEmpty(), is(true));
		}
	}

	public static class convertToLongListのテスト {

		@Test
		public void 本IDリストを指定した場合_Long型に変換される() {

			final List<BookId> expectedList = TestTools.createBookIdList();

			final List<Long> actualList = BookIdConverter.convertToLongList(expectedList);

			assertThat(actualList, is(not(nullValue())));
			assertThat(actualList.size(), is(expectedList.size()));

			for (int i = 0; i < expectedList.size(); i++) {

				final BookId expectedBookId = expectedList.get(i);
				final Long actualBookId = actualList.get(i);

				assertThat(actualBookId, is(expectedBookId.getValue()));
			}
		}

		@Test
		public void 空のリストを指定した場合_空のリストが返却される() {

			final List<Long> actualList = BookIdConverter.convertToLongList(new ArrayList<BookId>());

			assertThat(actualList, is(not(nullValue())));
			assertThat(actualList.isEmpty(), is(true));
		}

		@Test
		public void Nullを指定した場合_空のリストが返却される() {

			final List<Long> actualList = BookIdConverter.convertToLongList(null);

			assertThat(actualList, is(not(nullValue())));
			assertThat(actualList.isEmpty(), is(true));
		}
	}

	private static class TestTools {

		private static List<BookId> createBookIdList() {

			final List<BookId> bookIdList = new ArrayList<>();

			bookIdList.add(new BookId(101L));
			bookIdList.add(new BookId(102L));
			bookIdList.add(new BookId(103L));

			return bookIdList;
		}

		private static List<Long> createLongIdList() {

			final List<BookId> bookIdList = createBookIdList();
			final List<Long> longIdList = new ArrayList<>();
			for (final BookId bookId : bookIdList) {

				longIdList.add(bookId.getValue());
			}

			return longIdList;
		}
	}
}
