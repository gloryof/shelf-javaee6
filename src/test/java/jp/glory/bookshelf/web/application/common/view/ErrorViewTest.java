package jp.glory.bookshelf.web.application.common.view;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import jp.glory.bookshelf.web.application.common.constant.ContentName;
import jp.glory.bookshelf.web.application.common.constant.PageTitle;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class ErrorViewTest {

	private static ErrorView sut = null;

	public static class 共通設定のテスト {

		@Before
		public void setUp() {

			sut = new ErrorView("");
		}

		@Test
		public void getTitleでページタイトルが返却される() {

			assertThat(sut.getTitle(), is(PageTitle.ERROR));
		}

		@Test
		public void getContentNameでコンテンツ名が返却される() {

			assertThat(sut.getContentName(), is(ContentName.ERROR));
		}
	}

	public static class コンストラクタに値を指定した場合 {

		private static final String EXPECTED_MESSAGE = "メッセージ";

		@Before
		public void setUp() {

			sut = new ErrorView(EXPECTED_MESSAGE);
		}

		@Test
		public void コンストラクタに設定したメッセーがgetMessageで取得できる() {

			assertThat(sut.getMessage(), is(EXPECTED_MESSAGE));
		}
	}
}
