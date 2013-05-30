package jp.glory.bookshelf.web.tool;

import javax.ws.rs.core.Response;

import com.sun.jersey.api.view.Viewable;

/**
 * Viewオブジェクト抽出クラス
 * 
 * @author Junki Yamada
 * 
 */
public class ResponseViewExtractor<T> {

	/** レスポンス */
	private final Response response;

	/**
	 * コンストラクタ
	 * 
	 * @param response レスポンス
	 */
	public ResponseViewExtractor(final Response response) {

		this.response = response;
	}

	/**
	 * Viewオブジェクトを抽出する
	 * 
	 * @return Viewオブジェクト
	 */
	@SuppressWarnings("unchecked")
	public T extractView() {

		if (response == null) {

			return null;
		}

		final Object entity = response.getEntity();
		if (entity == null) {

			return null;
		}

		final Viewable view = (Viewable) entity;

		return (T) view.getModel();

	}
}
