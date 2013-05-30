package jp.glory.bookshelf.web.application.book.resource;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;

import javax.ws.rs.core.Response;

import jp.glory.bookshelf.domain.book.entity.Book;
import jp.glory.bookshelf.domain.book.repository.BookRepositoryStub;
import jp.glory.bookshelf.domain.book.value.IsbnCode;
import jp.glory.bookshelf.domain.book.value.Price;
import jp.glory.bookshelf.domain.book.value.Title;
import jp.glory.bookshelf.domain.shelf.entity.Shelf;
import jp.glory.bookshelf.domain.shelf.repository.ShelfRepositoryStub;
import jp.glory.bookshelf.domain.shelf.value.ShelfId;
import jp.glory.bookshelf.web.application.book.view.BookCreateView;
import jp.glory.bookshelf.web.application.common.constant.ResourceUrlConst;
import jp.glory.bookshelf.web.application.common.view.bean.BookInfoBean;
import jp.glory.bookshelf.web.common.exception.SystemException;
import jp.glory.bookshelf.web.tool.BookInfoAssert;
import jp.glory.bookshelf.web.tool.ResponseUrlAssert;
import jp.glory.bookshelf.web.tool.ResponseViewAssert;
import jp.glory.bookshelf.web.tool.ResponseViewExtractor;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class BookCreateResourceTest {

	private static BookCreateResource sut = null;

	private static BookRepositoryStub bookReposStub = null;

	private static ShelfRepositoryStub shelfReposStub = null;

	public static class initViewのテスト {

		@Before
		public void setUp() {

			bookReposStub = new BookRepositoryStub();
			shelfReposStub = new ShelfRepositoryStub();

			sut = new BookCreateResource(bookReposStub, shelfReposStub);
		}

		@Test
		public void 未入力状態の本が表示される() {

			final ShelfId expecteShelfId = new ShelfId(1500L);
			final Book expectedBook = new Book();
			expectedBook.setParentShelfId(expecteShelfId);

			final Response actualReponse = sut.initView(expecteShelfId.getValue());

			final ResponseViewAssert viewAssert = new ResponseViewAssert(actualReponse, BookCreateView.class);
			viewAssert.assertResponse();

			final ResponseViewExtractor<BookCreateView> extractor = new ResponseViewExtractor<>(actualReponse);
			final BookCreateView actualView = extractor.extractView();

			final BookInfoBean actualBook = actualView.getBook();
			final BookInfoAssert bookInfoAssert = new BookInfoAssert(actualBook, expectedBook);
			bookInfoAssert.assertBook();
		}
	}

	public static class createのテスト {

		@Before
		public void setUp() {

			bookReposStub = new BookRepositoryStub();
			bookReposStub.setCurrentNumber(1500);

			shelfReposStub = new ShelfRepositoryStub();
			shelfReposStub.addShelf(createParentShelf());

			sut = new BookCreateResource(bookReposStub, shelfReposStub);
		}

		@Test
		public void 正常終了した場合_表示用のURLが返却される() {

			final String titleParam = "テスト";
			final String isbnCodeParam = "9784274067815";
			final int priceParam = 1500;
			final long parentShelfId = createParentShelf().getShelfId().getValue();

			final long expectedBookId = bookReposStub.getCurrentNumber();

			final Response actualResponse = sut.create(titleParam, isbnCodeParam, priceParam, parentShelfId,
					parentShelfId);

			final String expectedUrl = ResourceUrlConst.BOOK_ID_VIEW + expectedBookId;

			final ResponseUrlAssert urlAsseret = new ResponseUrlAssert(actualResponse, expectedUrl);
			urlAsseret.assertResponse();
		}

		@Test
		public void ドメインのチェックエラーの場合_エラーメッセージが追加されて再表示される() {

			final String titleParam = "テスト";
			final String isbnCodeParam = "9784274067810"; // ISBNコードエラー
			final int priceParam = 1500;
			final long parentShelfId = createParentShelf().getShelfId().getValue();

			final Book expectedBook = new Book();
			expectedBook.setTitle(new Title(titleParam));
			expectedBook.setIsbnCode(new IsbnCode(isbnCodeParam));
			expectedBook.setPrice(new Price(priceParam));
			expectedBook.setParentShelfId(new ShelfId(parentShelfId));

			final Response actualResponse = sut.create(titleParam, isbnCodeParam, priceParam, parentShelfId,
					parentShelfId);

			final ResponseViewAssert viewAssert = new ResponseViewAssert(actualResponse, BookCreateView.class);
			viewAssert.assertResponse();

			final ResponseViewExtractor<BookCreateView> extractor = new ResponseViewExtractor<>(actualResponse);
			final BookCreateView actualView = extractor.extractView();

			final BookInfoBean actualBook = actualView.getBook();
			final BookInfoAssert bookInfoAssert = new BookInfoAssert(actualBook, expectedBook);
			bookInfoAssert.assertBook();
		}

		@Test(expected = SystemException.class)
		public void 存在しない本棚IDが指定された場合_システムエラーになる() {

			final String titleParam = "テスト";
			final String isbnCodeParam = "9784274067815";
			final int priceParam = 1500;
			final long parentShelfId = createParentShelf().getShelfId().getValue() + 1;

			sut.create(titleParam, isbnCodeParam, priceParam, parentShelfId, parentShelfId);
		}

		@Test
		public void パラメータの本棚IDが一致していない場合_BAD_REQUESTが返却される() {

			final String titleParam = "テスト";
			final String isbnCodeParam = "9784274067815";
			final int priceParam = 1500;
			final long parentShelfFormId = createParentShelf().getShelfId().getValue();
			final long parentShelfPathId = parentShelfFormId + 1;

			final Response actualResponse = sut.create(titleParam, isbnCodeParam, priceParam, parentShelfFormId,
					parentShelfPathId);

			assertThat(actualResponse.getStatus(), is(Response.Status.BAD_REQUEST.getStatusCode()));
		}

		private Shelf createParentShelf() {

			final ShelfId parentShelfId = new ShelfId(5L);
			return new Shelf(parentShelfId, new ArrayList<Book>());
		}
	}
}
