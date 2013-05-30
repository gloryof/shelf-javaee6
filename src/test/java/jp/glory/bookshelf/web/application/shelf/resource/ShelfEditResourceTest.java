package jp.glory.bookshelf.web.application.shelf.resource;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import jp.glory.bookshelf.domain.book.entity.Book;
import jp.glory.bookshelf.domain.book.value.BookId;
import jp.glory.bookshelf.domain.shelf.entity.Shelf;
import jp.glory.bookshelf.domain.shelf.repository.ShelfRepositoryStub;
import jp.glory.bookshelf.domain.shelf.value.Name;
import jp.glory.bookshelf.domain.shelf.value.ShelfId;
import jp.glory.bookshelf.web.application.common.constant.ResourceUrlConst;
import jp.glory.bookshelf.web.application.common.view.bean.ShelfInfoBean;
import jp.glory.bookshelf.web.application.shelf.view.ShelfEditView;
import jp.glory.bookshelf.web.tool.ResponseUrlAssert;
import jp.glory.bookshelf.web.tool.ResponseViewAssert;
import jp.glory.bookshelf.web.tool.ResponseViewExtractor;
import jp.glory.bookshelf.web.tool.ShelfInfoAssert;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class ShelfEditResourceTest {

	private static ShelfEditResource sut = null;

	private static ShelfRepositoryStub stub = null;

	public static class initViewのテスト {

		@Before
		public void setUp() {
			stub = new ShelfRepositoryStub();
			stub.addShelf(TestTools.createShelf());

			sut = new ShelfEditResource(stub);
		}

		@Test
		public void 設定した本棚IDに紐付く本棚が表示される() {

			final Shelf expectedShelf = TestTools.createShelf();

			final Response actualResponse = sut.initView(expectedShelf.getShelfId().getValue());

			final ResponseViewAssert responseAssert = new ResponseViewAssert(actualResponse, ShelfEditView.class);
			responseAssert.assertResponse();

			final ResponseViewExtractor<ShelfEditView> extractor = new ResponseViewExtractor<>(actualResponse);
			final ShelfEditView actualView = extractor.extractView();
			assertThat(actualView.isUpdateMode(), is(true));

			final ShelfInfoBean actualShelf = actualView.getShelf();

			final ShelfInfoAssert shelfInforAssert = new ShelfInfoAssert(actualShelf, expectedShelf);
			shelfInforAssert.assertShelf();
		}
	}

	public static class updateのテスト {

		@Before
		public void setUp() {
			stub = new ShelfRepositoryStub();
			stub.addShelf(TestTools.createShelf());

			sut = new ShelfEditResource(stub);
		}

		@Test
		public void 正常終了した場合_表示用のURLが返却される() {

			final long shelfIdParam = TestTools.createShelf().getShelfId().getValue();
			final String shelfName = "テスト";

			final Response actualResponse = sut.update(shelfIdParam, shelfIdParam, shelfName);

			final String expectedUrl = ResourceUrlConst.SHELF + shelfIdParam;

			final ResponseUrlAssert urlAssert = new ResponseUrlAssert(actualResponse, expectedUrl);
			urlAssert.assertResponse();
		}

		@Test
		public void ドメインのチェックエラーの場合_エラーメッセージが追加されて再表示される() {

			final Shelf expectedShelf = TestTools.createShelf();
			expectedShelf.setName(new Name(""));

			final long shelfIdParam = expectedShelf.getShelfId().getValue();

			final Response actualResponse = sut.update(shelfIdParam, shelfIdParam, expectedShelf.getName().getValue());

			final ResponseViewAssert viewAssert = new ResponseViewAssert(actualResponse, ShelfEditView.class);
			viewAssert.assertResponse();

			final ResponseViewExtractor<ShelfEditView> extractor = new ResponseViewExtractor<>(actualResponse);
			final ShelfEditView actualView = extractor.extractView();

			assertThat(actualView.isUpdateMode(), is(true));
			assertThat(actualView.getErrors().hasError(), is(true));

			final ShelfInfoBean actualShelfInfo = actualView.getShelf();

			final ShelfInfoAssert shelfInforAssert = new ShelfInfoAssert(actualShelfInfo, expectedShelf);
			shelfInforAssert.assertShelf();
		}

		@Test
		public void パラメータで送られてくる本棚IDがPOSTとPATHで違う場合_BAD_REQUESTが返却される() {

			final long shelfIdFormParam = 1000;
			final long shelfIdPathParam = 1001;
			final String name = "テスト２";

			final Response actualResponse = sut.update(shelfIdPathParam, shelfIdFormParam, name);

			assertThat(actualResponse.getStatus(), is(Response.Status.BAD_REQUEST.getStatusCode()));
		}
	}

	private static class TestTools {
		private static Shelf createShelf() {

			final ShelfId shelfId = new ShelfId(1L);
			final List<Book> bookList = new ArrayList<>();
			bookList.add(new Book(new BookId(1001L)));

			final Shelf shelf = new Shelf(shelfId, bookList);
			shelf.setName(new Name("更新前"));

			return shelf;
		}
	}
}
