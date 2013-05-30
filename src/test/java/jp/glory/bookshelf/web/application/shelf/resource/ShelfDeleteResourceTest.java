package jp.glory.bookshelf.web.application.shelf.resource;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import jp.glory.bookshelf.common.validate.ValidateErrors;
import jp.glory.bookshelf.domain.book.entity.Book;
import jp.glory.bookshelf.domain.shelf.entity.Shelf;
import jp.glory.bookshelf.domain.shelf.repository.ShelfRepositoryStub;
import jp.glory.bookshelf.domain.shelf.value.ShelfId;
import jp.glory.bookshelf.web.application.common.constant.ResourceUrlConst;
import jp.glory.bookshelf.web.application.common.view.bean.ShelfInfoBean;
import jp.glory.bookshelf.web.application.top.view.TopView;
import jp.glory.bookshelf.web.tool.ResponseUrlAssert;
import jp.glory.bookshelf.web.tool.ResponseViewAssert;
import jp.glory.bookshelf.web.tool.ResponseViewExtractor;

import org.junit.Before;
import org.junit.Test;

public class ShelfDeleteResourceTest {

	private ShelfRepositoryStub stub = null;

	@Before
	public void setUp() {

		stub = new ShelfRepositoryStub();
		stub.addShelf(createShelfData(10001L));
		stub.addShelf(createShelfData(10002L));
		stub.addShelf(createShelfData(10003L));
	}

	@Test
	public void 正常に削除された場合_トップ画面のURLが返却される() {

		final ShelfDeleteResource sut = new ShelfDeleteResource(stub);

		final List<Long> deleteList = new ArrayList<>();
		deleteList.add(10001L);
		deleteList.add(10002L);
		deleteList.add(10003L);

		final Response actualResponse = sut.deleteShelfs(deleteList);

		final ResponseUrlAssert urlAssert = new ResponseUrlAssert(actualResponse, ResourceUrlConst.ROOT);
		urlAssert.assertResponse();
	}

	@Test
	public void エラーがあった場合_トップ画面にエラーが表示される() {

		final ShelfDeleteResource sut = new ShelfDeleteResource(stub);

		final List<Long> deleteList = new ArrayList<>();
		deleteList.add(10001L);
		deleteList.add(null);
		deleteList.add(10003L);

		final Response actualResponse = sut.deleteShelfs(deleteList);

		final ResponseViewAssert viewAssert = new ResponseViewAssert(actualResponse, TopView.class);
		viewAssert.assertResponse();

		final ResponseViewExtractor<TopView> extraactor = new ResponseViewExtractor<>(actualResponse);
		final TopView actualView = extraactor.extractView();

		final ValidateErrors actualErrors = actualView.getErrors();
		assertThat(actualErrors, is(not(nullValue())));
		assertThat(actualErrors.hasError(), is(true));

		final List<ShelfInfoBean> actualShelfList = actualView.getShelfInfoList();
		for (final ShelfInfoBean shelfInfo : actualShelfList) {

			final boolean isChecked = deleteList.contains(shelfInfo.getShelfId());
			assertThat(shelfInfo.isChecked(), is(isChecked));
		}
	}

	private Shelf createShelfData(final long shelfIdValue) {

		final ShelfId shelfId = new ShelfId(shelfIdValue);
		final List<Book> bookList = new ArrayList<>();

		return new Shelf(shelfId, bookList);
	}
}
