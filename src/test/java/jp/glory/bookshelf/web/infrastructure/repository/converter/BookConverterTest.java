package jp.glory.bookshelf.web.infrastructure.repository.converter;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import jp.glory.bookshelf.domain.book.entity.Book;
import jp.glory.bookshelf.domain.book.value.BookId;
import jp.glory.bookshelf.domain.book.value.IsbnCode;
import jp.glory.bookshelf.domain.book.value.Price;
import jp.glory.bookshelf.domain.book.value.Title;
import jp.glory.bookshelf.web.infrastructure.repository.bean.BooksTableBean;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class BookConverterTest {

	public static class convertToEntityのテスト {

		@Test
		public void パラメータで指定されたテーブルBeanがエンティティに変換される() {

			final BooksTableBean expectedBean = createTableBean();
			final Book actualEntity = BookConverter.convertToEntity(expectedBean);

			TestTools.assertEntity(actualEntity, expectedBean);
		}

		@Test
		public void パラメータがNullの場合_Nullが返却される() {

			assertThat(BookConverter.convertToEntity(null), is(nullValue()));
		}

		private BooksTableBean createTableBean() {

			final BooksTableBean tableBean = new BooksTableBean();

			tableBean.setBookId(123);
			tableBean.setTitle("タイトル");
			tableBean.setIsbnCode("1234567890123");
			tableBean.setPrice(1500);
			tableBean.setDeleteFlag("0");

			return tableBean;
		}
	}

	public static class convertToTableBeanのテスト {

		@Test
		public void パラメータで指定されたエンティティがテーブルBeanに変換される() {

			final Book expectedBook = createEntity();
			final BooksTableBean actualBean = BookConverter.convertToTableBean(expectedBook);

			TestTools.assertTableBean(actualBean, expectedBook);
		}

		@Test
		public void エンティティがNullの場合() {

			assertThat(BookConverter.convertToTableBean(null), is(nullValue()));
		}

		private Book createEntity() {

			final BookId bookId = new BookId(1234L);
			final Book entity = new Book(bookId);

			entity.setTitle(new Title("タイトル"));
			entity.setIsbnCode(new IsbnCode("987-654-321-0987"));
			entity.setPrice(new Price(2500));

			return entity;
		}
	}

	public static class convertToEntityListのテスト {

		@Test
		public void パラメータで指定したテーブルBeanリストがエンティティリストに変換される() {

			final List<BooksTableBean> expectedList = createBooksList();
			final List<Book> actualList = BookConverter.convertToEntityList(expectedList);

			assertThat(actualList, is(not(nullValue())));
			assertThat(actualList.size(), is(expectedList.size()));
			for (int i = 0; i < actualList.size(); i++) {

				final BooksTableBean expectedTableBean = expectedList.get(i);
				final Book actualEntity = actualList.get(i);

				TestTools.assertEntity(actualEntity, expectedTableBean);
			}
		}

		@Test
		public void パラメータで指定したリストが空の場合_空のリストが返却される() {

			final List<BooksTableBean> expectedList = new ArrayList<>();
			final List<Book> actualList = BookConverter.convertToEntityList(expectedList);

			assertThat(actualList, is(not(nullValue())));
			assertThat(actualList.size(), is(0));
		}

		@Test
		public void パラメータNullの場合_Nullが返却される() {

			assertThat(BookConverter.convertToEntityList(null), is(nullValue()));
		}

		private List<BooksTableBean> createBooksList() {

			final List<BooksTableBean> returnList = new ArrayList<>();

			for (int i = 0; i < 3; i++) {

				returnList.add(createTableBean(i));
			}

			return returnList;
		}

		private BooksTableBean createTableBean(final int seqNo) {

			final BooksTableBean tableBean = new BooksTableBean();

			tableBean.setBookId(1000 + seqNo);
			tableBean.setTitle("タイトル" + seqNo);
			tableBean.setIsbnCode("123456789000" + seqNo);
			tableBean.setPrice(1000 + (seqNo * 100));
			tableBean.setDeleteFlag("0");

			return tableBean;
		}
	}

	public static class convertToTableBeanListのテスト {

		@Test
		public void パラメータで指定したエンティティリストがテーブルBeanリストに変換される() {

			final List<Book> expectedList = createEntityList();
			final List<BooksTableBean> actualList = BookConverter.convertToTableBeanList(expectedList);

			assertThat(actualList, is(not(nullValue())));
			assertThat(actualList.size(), is(expectedList.size()));
			for (int i = 0; i < actualList.size(); i++) {

				final Book expectedEntity = expectedList.get(i);
				final BooksTableBean actualTableBean = actualList.get(i);

				TestTools.assertTableBean(actualTableBean, expectedEntity);
			}
		}

		@Test
		public void パラメータで指定したリストが空の場合_空のリストが返却される() {

			final List<Book> expectedList = new ArrayList<>();
			final List<BooksTableBean> actualList = BookConverter.convertToTableBeanList(expectedList);

			assertThat(actualList, is(not(nullValue())));
			assertThat(actualList.size(), is(0));
		}

		@Test
		public void パラメータNullの場合_Nullが返却される() {

			assertThat(BookConverter.convertToTableBeanList(null), is(nullValue()));
		}

		private List<Book> createEntityList() {

			final List<Book> returnList = new ArrayList<>();

			for (int i = 0; i < returnList.size(); i++) {

				returnList.add(createEntity(i));
			}

			return returnList;
		}

		private Book createEntity(final int seqNo) {

			final BookId bookId = new BookId(1000L + seqNo);
			final Book entity = new Book(bookId);

			entity.setTitle(new Title("タイトル" + seqNo));
			entity.setIsbnCode(new IsbnCode("987654321000" + seqNo));
			entity.setPrice(new Price(1000 + (seqNo * 100)));

			return entity;
		}
	}

	private static class TestTools {
		private static void assertEntity(final Book entity, final BooksTableBean tableBean) {

			assertThat(entity, is(not(nullValue())));
			assertSameInfo(entity, tableBean);
		}

		private static void assertTableBean(final BooksTableBean tableBean, final Book entity) {

			assertThat(tableBean, is(not(nullValue())));
			assertSameInfo(entity, tableBean);
		}

		private static void assertSameInfo(final Book entity, final BooksTableBean tableBean) {

			assertThat(entity.getBookId().getValue(), is(tableBean.getBookId()));
			assertThat(entity.getTitle().getValue(), is(tableBean.getTitle()));
			assertThat(entity.getIsbnCode().getOnlyCodes(), is(tableBean.getIsbnCode()));
			assertThat(entity.getPrice().getValue(), is(tableBean.getPrice()));
		}
	}
}
