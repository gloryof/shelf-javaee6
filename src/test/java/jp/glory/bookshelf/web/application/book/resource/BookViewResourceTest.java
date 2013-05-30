package jp.glory.bookshelf.web.application.book.resource;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import jp.glory.bookshelf.domain.book.entity.Book;
import jp.glory.bookshelf.domain.book.repository.BookRepositoryStub;
import jp.glory.bookshelf.domain.book.value.BookId;
import jp.glory.bookshelf.domain.book.value.IsbnCode;
import jp.glory.bookshelf.domain.book.value.Price;
import jp.glory.bookshelf.domain.book.value.Title;
import jp.glory.bookshelf.domain.shelf.entity.Shelf;
import jp.glory.bookshelf.domain.shelf.value.ShelfId;
import jp.glory.bookshelf.web.application.book.view.BookView;
import jp.glory.bookshelf.web.application.common.view.bean.BookInfoBean;
import jp.glory.bookshelf.web.tool.ResponseViewAssert;
import jp.glory.bookshelf.web.tool.ResponseViewExtractor;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class BookViewResourceTest {

	private static BookViewResource sut = null;

	private static BookRepositoryStub bookRepositoryStub = null;

	private static Map<String, List<Book>> isbnMap = null;

	public static class findByIdのテスト {

		@Before
		public void setUp() {

			sut = TestTools.createSut();
		}

		@Test
		public void 設定した本IDに紐付く本が表示される() {

			final Shelf shelf = TestTools.createShelf01();
			final Book book = shelf.getBookList().get(0);

			final BookView expectedView = new BookView(book);

			final Response actualResponse = sut.findById(book.getBookId().getValue());

			TestTools.assertResponse(actualResponse, expectedView);
		}
	}

	public static class findListByIsbnCodeのテスト {

		@Before
		public void setUp() {

			sut = TestTools.createSut();
		}

		@Test
		public void 設定した本ISBNに紐付く本が表示される() {

			final String isbnCodeValue = "978-4822229917";
			final List<Book> bookList = isbnMap.get(isbnCodeValue);

			final BookView expectedView = new BookView(bookList);

			final Response actualResponse = sut.findListByIsbnCode(isbnCodeValue);

			TestTools.assertResponse(actualResponse, expectedView);
		}
	}

	private static class TestTools {

		private static BookViewResource createSut() {

			final List<Shelf> shelfList = new ArrayList<>();
			shelfList.add(TestTools.createShelf01());
			shelfList.add(TestTools.createShelf02());

			bookRepositoryStub = new BookRepositoryStub();
			isbnMap = new LinkedHashMap<>();

			for (final Shelf shelf : shelfList) {

				for (final Book book : shelf.getBookList()) {

					bookRepositoryStub.addBook(book);

					final String isbnValue = book.getIsbnCode().getValue();
					List<Book> bookList = isbnMap.get(isbnValue);

					if (bookList == null) {

						bookList = new ArrayList<>();
					}

					bookList.add(book);
					isbnMap.put(isbnValue, bookList);
				}
			}

			return new BookViewResource(bookRepositoryStub);
		}

		private static void assertResponse(final Response actualResponse, final BookView expectedView) {

			final ResponseViewAssert responseAssert = new ResponseViewAssert(actualResponse, BookView.class);

			responseAssert.assertResponse();

			final ResponseViewExtractor<BookView> extractor = new ResponseViewExtractor<>(actualResponse);
			final BookView actualView = extractor.extractView();

			final List<BookInfoBean> expectedBookInfoList = expectedView.getBookList();
			final List<BookInfoBean> actualBookInfoList = actualView.getBookList();

			assertThat(actualBookInfoList, is(not(nullValue())));
			assertThat(actualBookInfoList.size(), is(expectedBookInfoList.size()));

			for (int i = 0; i < actualBookInfoList.size(); i++) {

				final BookInfoBean actualBookInfo = actualBookInfoList.get(i);
				final BookInfoBean expectedBookInfo = expectedBookInfoList.get(i);

				assertBookInfo(actualBookInfo, expectedBookInfo);
			}
		}

		private static void assertBookInfo(final BookInfoBean actualBookInfo, final BookInfoBean expectedBookInfo) {

			assertThat(actualBookInfo, is(not(nullValue())));

			assertThat(actualBookInfo.getBookId(), is(expectedBookInfo.getBookId()));
			assertThat(actualBookInfo.getIsbnCode(), is(expectedBookInfo.getIsbnCode()));
			assertThat(actualBookInfo.getIsbnOnlyCode(), is(expectedBookInfo.getIsbnOnlyCode()));
			assertThat(actualBookInfo.getParentShelfId(), is(expectedBookInfo.getParentShelfId()));
			assertThat(actualBookInfo.getTitle(), is(expectedBookInfo.getTitle()));
			assertThat(actualBookInfo.getPrice(), is(expectedBookInfo.getPrice()));
			assertThat(actualBookInfo.isChecked(), is(expectedBookInfo.isChecked()));
		}

		private static Shelf createShelf01() {

			final ShelfId shelfId = new ShelfId(1000L);
			final List<Book> bookList = new ArrayList<>();

			bookList.add(createBook01());
			bookList.add(createBook02());

			return new Shelf(shelfId, bookList);
		}

		private static Shelf createShelf02() {

			final ShelfId shelfId = new ShelfId(2000L);
			final List<Book> bookList = new ArrayList<>();

			bookList.add(createBook03());

			return new Shelf(shelfId, bookList);
		}

		private static Book createBook01() {

			final BookId bookId = new BookId(1L);
			final Book book = new Book(bookId);

			book.setTitle(new Title("テスト"));
			book.setIsbnCode(new IsbnCode("978-4822229917"));
			book.setPrice(new Price(1500));
			book.setParentShelfId(new ShelfId(101L));

			return book;
		}

		private static Book createBook02() {

			final BookId bookId = new BookId(2L);
			final Book book = new Book(bookId);

			book.setTitle(new Title("テスト２"));
			book.setIsbnCode(new IsbnCode("978-4822229917"));
			book.setPrice(new Price(1500));
			book.setParentShelfId(new ShelfId(101L));

			return book;
		}

		private static Book createBook03() {

			final BookId bookId = new BookId(3L);
			final Book book = new Book(bookId);

			book.setTitle(new Title("テスト３"));
			book.setIsbnCode(new IsbnCode("978-4-274-06896-6"));
			book.setPrice(new Price(1500));
			book.setParentShelfId(new ShelfId(101L));

			return book;
		}
	}
}
