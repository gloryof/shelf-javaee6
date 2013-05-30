package jp.glory.bookshelf.web.application.common.response;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.net.URI;
import java.util.ArrayList;

import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import jp.glory.bookshelf.domain.shelf.entity.Shelf;
import jp.glory.bookshelf.web.application.common.view.AbstractView;
import jp.glory.bookshelf.web.application.top.view.TopView;
import jp.glory.bookshelf.web.common.exception.SystemException;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class ResponseCreatorTest {

	public static class コンストラクタパラメータにViewオブジェクトを指定した場合 {

		@Test
		public void Null以外を指定した場合_getResponseでResponseが返却される() {

			final ResponseCreator sut = new ResponseCreator(new TopView(new ArrayList<Shelf>()));

			final Response actualResponse = sut.getResponse();
			assertCommon(actualResponse);

			assertThat(actualResponse.getStatus(), is(Response.Status.OK.getStatusCode()));

			final MultivaluedMap<String, Object> valueMap = actualResponse.getMetadata();
			final MediaType actualContentType = (MediaType) valueMap.getFirst("Content-Type");
			assertThat(actualContentType, is(MediaType.TEXT_HTML_TYPE));
		}

		@Test(expected = SystemException.class)
		public void Nullを指定した場合_システムエラーになる() {

			final AbstractView view = null;
			new ResponseCreator(view);
		}
	}

	public static class コンストラクタパラメータにStringオブジェクトを指定した場合 {

		@Test
		public void 正規なURLを指定した場合_getResponseでResponseが返却される() {

			final String exceptedUrl = "http://localhost:8080";
			final ResponseCreator sut = new ResponseCreator(exceptedUrl);

			final Response actualResponse = sut.getResponse();
			assertCommon(actualResponse);

			assertThat(actualResponse.getStatus(), is(Response.Status.SEE_OTHER.getStatusCode()));

			final MultivaluedMap<String, Object> valueMap = actualResponse.getMetadata();
			final URI actualUri = (URI) valueMap.getFirst("Location");

			assertThat(actualUri.toString(), is(exceptedUrl));
		}

		@Test(expected = SystemException.class)
		public void 不正なURLを指定した場合_getResponseでResponseが返却される() {

			final String exceptedUrl = "test<>";
			new ResponseCreator(exceptedUrl);

		}

		@Test(expected = SystemException.class)
		public void Nullを指定した場合_システムエラーになる() {

			final String url = null;
			new ResponseCreator(url);
		}

		@Test(expected = SystemException.class)
		public void 空文字を指定した場合_システムエラーになる() {

			new ResponseCreator("");
		}
	}

	public static class コンストラクタパラメータにStatusを指定した場合 {

		@Test
		public void Null以外を指定した場合_getResponseでResponseが返却される() {

			final Response.Status expectedStatus = Response.Status.UNAUTHORIZED;
			final ResponseCreator sut = new ResponseCreator(expectedStatus);

			final Response actualResponse = sut.getResponse();
			assertCommon(actualResponse);

			assertThat(actualResponse.getStatus(), is(expectedStatus.getStatusCode()));
		}

		@Test(expected = SystemException.class)
		public void Nullを指定した場合_システムエラーになる() {

			final Response.Status status = null;
			new ResponseCreator(status);
		}
	}

	private static void assertCommon(final Response actualResponse) {

		assertThat(actualResponse, is(not(nullValue())));

		final MultivaluedMap<String, Object> valueMap = actualResponse.getMetadata();

		final String actualPragma = (String) valueMap.getFirst("Pragma");
		assertThat(actualPragma, is("no-cache"));

		final CacheControl actualCahceControl = (CacheControl) valueMap.getFirst("Cache-Control");
		assertThat(actualCahceControl.getMaxAge(), is(-1));
		assertThat(actualCahceControl.isMustRevalidate(), is(true));
		assertThat(actualCahceControl.isNoCache(), is(true));
		assertThat(actualCahceControl.isNoStore(), is(true));
		assertThat(actualCahceControl.isNoTransform(), is(true));
		assertThat(actualCahceControl.isPrivate(), is(false));
		assertThat(actualCahceControl.isProxyRevalidate(), is(false));
		assertThat(actualCahceControl.getSMaxAge(), is(-1));
	}
}
