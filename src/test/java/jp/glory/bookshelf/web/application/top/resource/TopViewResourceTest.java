package jp.glory.bookshelf.web.application.top.resource;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import jp.glory.bookshelf.domain.book.entity.Book;
import jp.glory.bookshelf.domain.shelf.entity.Shelf;
import jp.glory.bookshelf.domain.shelf.repository.ShelfRepositoryStub;
import jp.glory.bookshelf.domain.shelf.value.ShelfId;
import jp.glory.bookshelf.web.application.common.view.bean.ShelfInfoBean;
import jp.glory.bookshelf.web.application.top.view.TopView;
import jp.glory.bookshelf.web.tool.ResponseViewAssert;
import jp.glory.bookshelf.web.tool.ResponseViewExtractor;

import org.junit.Test;

public class TopViewResourceTest {

	@Test
	public void initViewでTopViewが返却される() {

		final ShelfRepositoryStub stub = new ShelfRepositoryStub();

		stub.addShelf(TestTools.createShelf01());
		stub.addShelf(TestTools.createShelf02());
		stub.addShelf(TestTools.createShelf03());

		final TopViewResource sut = new TopViewResource(stub);
		final Response actualResponse = sut.viewShelfList();

		final ResponseViewAssert viewAssert = new ResponseViewAssert(actualResponse, TopView.class);
		viewAssert.assertResponse();

		final ResponseViewExtractor<TopView> extractor = new ResponseViewExtractor<>(actualResponse);
		final TopView actualView = extractor.extractView();

		final List<ShelfInfoBean> actualShelfList = actualView.getShelfInfoList();

		assertThat(actualShelfList, is(not(nullValue())));

		assertThat(actualShelfList.size(), is(3));
		TestTools.assertShelf(actualShelfList.get(0), TestTools.createShelf01());
		TestTools.assertShelf(actualShelfList.get(1), TestTools.createShelf02());
		TestTools.assertShelf(actualShelfList.get(2), TestTools.createShelf03());
	}

	private static class TestTools {
		private static void assertShelf(final ShelfInfoBean shelfInfoBean, final Shelf shelf) {

			assertThat(shelfInfoBean.getShelfId(), is(shelf.getShelfId().getValue()));
			assertThat(shelfInfoBean.isChecked(), is(false));
		}

		private static Shelf createShelf01() {

			final ShelfId shelfId = new ShelfId(1L);
			final List<Book> bookList = new ArrayList<>();

			return new Shelf(shelfId, bookList);
		}

		private static Shelf createShelf02() {

			final ShelfId shelfId = new ShelfId(2L);
			final List<Book> bookList = new ArrayList<>();

			return new Shelf(shelfId, bookList);
		}

		private static Shelf createShelf03() {

			final ShelfId shelfId = new ShelfId(3L);
			final List<Book> bookList = new ArrayList<>();

			return new Shelf(shelfId, bookList);
		}
	}
}
