package jp.glory.bookshelf.web.application.shelf.resource;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import javax.ws.rs.core.Response;

import jp.glory.bookshelf.domain.shelf.entity.Shelf;
import jp.glory.bookshelf.domain.shelf.repository.ShelfRepositoryStub;
import jp.glory.bookshelf.domain.shelf.value.Name;
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
public class ShelfCreateResourceTest {

	private static ShelfCreateResource sut = null;

	private static ShelfRepositoryStub stub = null;

	public static class initViewのテスト {

		@Before
		public void setUp() {

			sut = new ShelfCreateResource(new ShelfRepositoryStub());
		}

		@Test
		public void 未入力状態の本棚が返却される() {

			final Shelf expecetedShelf = new Shelf();

			final Response actualResponse = sut.initView();

			final ResponseViewAssert viewAssert = new ResponseViewAssert(actualResponse, ShelfEditView.class);
			viewAssert.assertResponse();

			final ResponseViewExtractor<ShelfEditView> extractor = new ResponseViewExtractor<>(actualResponse);
			final ShelfEditView actualView = extractor.extractView();
			assertThat(actualView.isUpdateMode(), is(false));

			final ShelfInfoBean actualShelf = actualView.getShelf();

			final ShelfInfoAssert shelfInfoAssert = new ShelfInfoAssert(actualShelf, expecetedShelf);
			shelfInfoAssert.assertShelf();
		}
	}

	public static class createのテスト {

		@Before
		public void setUp() {

			stub = new ShelfRepositoryStub();
			stub.setCurrentNumber(1500);

			sut = new ShelfCreateResource(stub);
		}

		@Test
		public void 正常終了した場合_表示用のURLが返却される() {

			final long currentNumber = stub.getCurrentNumber();
			final String name = "テスト";

			final String expectedUrl = ResourceUrlConst.SHELF + currentNumber;

			final Response actualResponse = sut.create(name);

			final ResponseUrlAssert urlAssert = new ResponseUrlAssert(actualResponse, expectedUrl);
			urlAssert.assertResponse();
		}

		@Test
		public void ドメインのチェックエラーの場合_エラーメッセージが追加されて再表示される() {

			final String name = "";

			final Shelf expectedShelf = new Shelf();
			expectedShelf.setName(new Name(name));

			final Response actualResponse = sut.create(name);

			final ResponseViewAssert viewAssert = new ResponseViewAssert(actualResponse, ShelfEditView.class);
			viewAssert.assertResponse();

			final ResponseViewExtractor<ShelfEditView> extractor = new ResponseViewExtractor<>(actualResponse);
			final ShelfEditView actualView = extractor.extractView();
			assertThat(actualView.isUpdateMode(), is(false));
			assertThat(actualView.getErrors().hasError(), is(true));

			final ShelfInfoBean actualShelf = actualView.getShelf();

			final ShelfInfoAssert shelfInfoAssert = new ShelfInfoAssert(actualShelf, expectedShelf);
			shelfInfoAssert.assertShelf();
		}
	}
}
