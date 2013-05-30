package jp.glory.bookshelf.web.application.book.resource;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import javax.ws.rs.core.Response;

import jp.glory.bookshelf.domain.book.entity.Book;
import jp.glory.bookshelf.domain.book.repository.BookRepositoryStub;
import jp.glory.bookshelf.domain.book.value.BookId;
import jp.glory.bookshelf.domain.book.value.IsbnCode;
import jp.glory.bookshelf.domain.book.value.Price;
import jp.glory.bookshelf.domain.book.value.Title;
import jp.glory.bookshelf.domain.shelf.value.ShelfId;
import jp.glory.bookshelf.web.application.book.view.BookEditView;
import jp.glory.bookshelf.web.application.common.constant.ResourceUrlConst;
import jp.glory.bookshelf.web.application.common.view.bean.BookInfoBean;
import jp.glory.bookshelf.web.tool.BookInfoAssert;
import jp.glory.bookshelf.web.tool.ResponseUrlAssert;
import jp.glory.bookshelf.web.tool.ResponseViewAssert;
import jp.glory.bookshelf.web.tool.ResponseViewExtractor;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class BookEditResourceTest {

	private static BookEditResource sut = null;

	private static BookRepositoryStub stub = null;

	public static class initViewのテスト {

		@Before
		public void setUp() {

			stub = new BookRepositoryStub();
			stub.addBook(createBook());

			sut = new BookEditResource(stub);
		}

		@Test
		public void 設定した本IDに紐付く本が表示される() {

			final Book expectedBook = createBook();

			final Response actualResponse = sut.initView(expectedBook.getBookId().getValue());

			final ResponseViewAssert responseAssert = new ResponseViewAssert(actualResponse, BookEditView.class);
			responseAssert.assertResponse();

			final ResponseViewExtractor<BookEditView> extractor = new ResponseViewExtractor<>(actualResponse);
			final BookEditView actualView = extractor.extractView();

			final BookInfoBean actualBook = actualView.getBook();

			final BookInfoAssert bookInfoAssert = new BookInfoAssert(actualBook, expectedBook);
			bookInfoAssert.assertBook();
		}
	}

	public static class updateのテスト {

		@Before
		public void setUp() {

			stub = new BookRepositoryStub();
			stub.addBook(createBook());

			sut = new BookEditResource(stub);
		}

		@Test
		public void 正常終了した場合_表示用のURLが返却される() {

			final Book beforeBook = createBook();
			final long bookIdFormParam = beforeBook.getBookId().getValue();
			final long bookIdPathParam = bookIdFormParam;
			final String titleParam = "テスト２";
			final String isbnCodeParam = "9784274068850";
			final int priceParam = 2000;
			final long parentShelfId = 120L;

			final Response actualResponse = sut.update(bookIdFormParam, bookIdPathParam, titleParam, isbnCodeParam,
					priceParam, parentShelfId);

			final String expectedUrl = ResourceUrlConst.BOOK_ID_VIEW + bookIdFormParam;

			final ResponseUrlAssert urlAssert = new ResponseUrlAssert(actualResponse, expectedUrl);
			urlAssert.assertResponse();
		}

		@Test
		public void ドメインのチェックでエラーがある場合_エラーメッセージが追加されて再表示される() {

			final Book beforeBook = createBook();
			final long bookIdFormParam = beforeBook.getBookId().getValue();
			final long bookIdPathParam = bookIdFormParam;
			final String titleParam = "テスト２";
			final String isbnCodeParam = "9784274068851"; // ISBNコードエラー
			final int priceParam = 2000;
			final long parentShelfId = 120L;

			final Book expectedBook = new Book(new BookId(bookIdFormParam));
			expectedBook.setTitle(new Title(titleParam));
			expectedBook.setIsbnCode(new IsbnCode(isbnCodeParam));
			expectedBook.setPrice(new Price(priceParam));
			expectedBook.setParentShelfId(new ShelfId(parentShelfId));

			final Response actualResponse = sut.update(bookIdFormParam, bookIdPathParam, titleParam, isbnCodeParam,
					priceParam, parentShelfId);

			final ResponseViewAssert viewAssert = new ResponseViewAssert(actualResponse, BookEditView.class);
			viewAssert.assertResponse();

			final ResponseViewExtractor<BookEditView> extractor = new ResponseViewExtractor<>(actualResponse);
			final BookEditView actualView = extractor.extractView();

			assertThat(actualView.getErrors().hasError(), is(true));

			final BookInfoBean actualBook = actualView.getBook();

			final BookInfoAssert bookInfoAssert = new BookInfoAssert(actualBook, expectedBook);
			bookInfoAssert.assertBook();
		}

		@Test
		public void パラメータで送られてくる本IDがPOSTとPATH出違う場合_BAD_REQUESTが返却される() {

			final long bookIdFormParam = 1000;
			final long bookIdPathParam = 1001;
			final String titleParam = "テスト２";
			final String isbnCodeParam = "9784274068850";
			final int priceParam = 2000;
			final long parentShelfId = 120L;

			final Response actualResponse = sut.update(bookIdFormParam, bookIdPathParam, titleParam, isbnCodeParam,
					priceParam, parentShelfId);

			assertThat(actualResponse.getStatus(), is(Response.Status.BAD_REQUEST.getStatusCode()));
		}
	}

	private static Book createBook() {

		final BookId bookId = new BookId(10001L);
		final Book book = new Book(bookId);

		book.setTitle(new Title("テスト"));
		book.setIsbnCode(new IsbnCode("978-4822229917"));
		book.setPrice(new Price(1500));

		return book;
	}
}
