package jp.glory.bookshelf.web.application.shelf.resource;

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
import jp.glory.bookshelf.web.application.shelf.view.ShelfView;
import jp.glory.bookshelf.web.tool.ResponseViewAssert;
import jp.glory.bookshelf.web.tool.ResponseViewExtractor;
import jp.glory.bookshelf.web.tool.ShelfInfoAssert;

import org.junit.Test;

public class ShelfViewResourceTest {

	@Test
	public void initViewでShelfViewのレスポンスが返却される() {

		final Shelf expectedShelf = createShelfData();
		final List<Shelf> expectedMoveList = createMoveList();

		final ShelfRepositoryStub stub = new ShelfRepositoryStub();
		stub.addShelf(expectedShelf);

		final ShelfViewResource sut = new ShelfViewResource(stub);

		final Response actualResponse = sut.initView(expectedShelf.getShelfId().getValue());

		final ResponseViewAssert viewAssert = new ResponseViewAssert(actualResponse, ShelfView.class);
		viewAssert.assertResponse();

		final ResponseViewExtractor<ShelfView> extractor = new ResponseViewExtractor<>(actualResponse);
		final ShelfView actualView = extractor.extractView();
		final ShelfInfoBean actualShelfInfo = actualView.getShelf();

		final ShelfInfoAssert shelfInfoAssert = new ShelfInfoAssert(actualShelfInfo, expectedShelf);
		shelfInfoAssert.assertShelf();

		final List<ShelfInfoBean> actualMoveList = actualView.getMoveShelfList();

		assertThat(actualMoveList, is(not(nullValue())));
		assertThat(actualMoveList.size(), is(expectedMoveList.size()));

		for (int i = 0; i < actualMoveList.size(); i++) {

			final Shelf expectedListShelf = expectedMoveList.get(i);
			final ShelfInfoBean actualListShelf = actualMoveList.get(i);

			final ShelfInfoAssert listShelfInfoAssert = new ShelfInfoAssert(actualListShelf, expectedListShelf);
			listShelfInfoAssert.assertShelf();
		}
	}

	private Shelf createShelfData() {

		final ShelfId shelfId = new ShelfId(100L);
		final Shelf shelf = new Shelf(shelfId, new ArrayList<Book>());

		return shelf;
	}

	private List<Shelf> createMoveList() {

		final List<Shelf> moveList = new ArrayList<>();

		return moveList;
	}
}
