package jp.glory.bookshelf.web.tool;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.net.URI;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

/**
 * レスポンス検証（URL）
 * 
 * @author Junki Yamada
 * 
 */
public class ResponseUrlAssert {

	/** レスポンス実測値 */
	private final Response actualResponse;

	/** URLの期待値 */
	private final String expectedUrl;

	/**
	 * コンストラクタ
	 * 
	 * @param actualResponse レスポンス実測値
	 * @param expectedUrl URLの期待値
	 */
	public ResponseUrlAssert(final Response actualResponse, final String expectedUrl) {

		this.actualResponse = actualResponse;
		this.expectedUrl = expectedUrl;
	}

	/**
	 * レスポンス検証
	 */
	public void assertResponse() {

		assertThat(actualResponse, is(not(nullValue())));
		assertThat(actualResponse.getStatus(), is(Response.Status.SEE_OTHER.getStatusCode()));

		final MultivaluedMap<String, Object> mapValue = actualResponse.getMetadata();
		final Object urlObject = mapValue.getFirst("Location");

		assertThat(urlObject, is(not(nullValue())));
		assertThat(urlObject, is(instanceOf(URI.class)));

		final URI actualUrl = (URI) urlObject;

		assertThat(actualUrl.getPath(), is(expectedUrl));
	}
}
