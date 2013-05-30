package jp.glory.bookshelf.web.tool;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import jp.glory.bookshelf.domain.shelf.entity.Shelf;
import jp.glory.bookshelf.web.application.common.view.bean.ShelfInfoBean;

public class ShelfInfoAssert {

	private final ShelfInfoBean actualShelfInfo;

	private final Shelf expectedShelf;

	public ShelfInfoAssert(final ShelfInfoBean actualShelfInfo, final Shelf expectedShelf) {

		this.actualShelfInfo = actualShelfInfo;
		this.expectedShelf = expectedShelf;
	}

	public void assertShelf() {

		assertThat(actualShelfInfo, is(not(nullValue())));

		assertThat(actualShelfInfo.getShelfId(), is(expectedShelf.getShelfId().getValue()));
		assertThat(actualShelfInfo.getName(), is(expectedShelf.getName().getValue()));
	}
}
