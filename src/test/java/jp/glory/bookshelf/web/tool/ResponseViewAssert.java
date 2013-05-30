package jp.glory.bookshelf.web.tool;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import javax.ws.rs.core.Response;

import jp.glory.bookshelf.web.application.common.view.AbstractView;

import com.sun.jersey.api.view.Viewable;

/**
 * レスポンス検証（Viewオブジェクト）
 * 
 * @author Junki Yamada
 * 
 */
public class ResponseViewAssert {

	/** レスポンス実測値 */
	private final Response actualResponse;

	/** 期待値クラス */
	final Class<? extends AbstractView> expectedClass;

	/**
	 * コンストラクタ
	 * 
	 * @param actualResponse レスポンス実測値
	 * @param expectedClass 期待値クラス
	 */
	public ResponseViewAssert(final Response actualResponse, final Class<? extends AbstractView> expectedClass) {

		this.actualResponse = actualResponse;
		this.expectedClass = expectedClass;
	}

	/**
	 * レスポンス検証
	 */
	public void assertResponse() {

		assertThat(actualResponse, is(not(nullValue())));
		assertThat(actualResponse.getStatus(), is(Response.Status.OK.getStatusCode()));

		final Object actualEntity = actualResponse.getEntity();

		assertThat(actualEntity, is(not(nullValue())));
		assertThat(actualEntity, is(instanceOf(Viewable.class)));

		final Viewable view = (Viewable) actualEntity;

		final Object actualModel = view.getModel();

		assertThat(actualModel, is(not(nullValue())));
		assertThat(actualModel, is(instanceOf(expectedClass)));
	}
}
