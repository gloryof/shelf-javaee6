package jp.glory.bookshelf.web.application.common.response;

import java.net.URI;

import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import jp.glory.bookshelf.web.application.common.view.AbstractView;
import jp.glory.bookshelf.web.common.exception.SystemException;

import org.apache.commons.lang3.StringUtils;

/**
 * レスポンス作成クラス
 * 
 * @author Junki Yamada
 * 
 */
public class ResponseCreator {

	/** レスポンス */
	private final Response response;

	/**
	 * コンストラクタ
	 * 
	 * @param view Viewオブジェクト
	 */
	public ResponseCreator(final AbstractView view) {

		if (view == null) {

			throw new SystemException("Parameter view is null!");
		}

		final Response.ResponseBuilder builder = Response.ok(view.createViewable(), MediaType.TEXT_HTML);
		disabledCache(builder);

		response = builder.build();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param urlString URL
	 */
	public ResponseCreator(final String urlString) {

		if (StringUtils.isEmpty(urlString)) {

			throw new SystemException("Parameter urlString is null!");
		}

		final URI uri;

		try {

			uri = new URI(urlString);
		} catch (final Throwable th) {

			throw new SystemException("URLが不正です。", th);
		}

		final Response.ResponseBuilder builder = Response.seeOther(uri);
		disabledCache(builder);

		response = builder.build();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param status ステータス
	 */
	public ResponseCreator(final Response.Status status) {

		if (status == null) {

			throw new SystemException("Parameter status is null!");
		}

		final Response.ResponseBuilder builder = Response.status(status);
		disabledCache(builder);

		response = builder.build();
	}

	/**
	 * @return response
	 */
	public Response getResponse() {
		return response;
	}

	/**
	 * キャッシュを無効化する
	 * 
	 * @param builder レスポンスビルダー
	 */
	private void disabledCache(final Response.ResponseBuilder builder) {

		final CacheControl cacheControl = new CacheControl();
		cacheControl.setNoCache(true);
		cacheControl.setMustRevalidate(true);
		cacheControl.setNoStore(true);

		builder.cacheControl(cacheControl);

		builder.header("Pragma", "no-cache");
	}
}
