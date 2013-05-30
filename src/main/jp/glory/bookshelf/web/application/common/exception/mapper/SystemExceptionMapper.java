package jp.glory.bookshelf.web.application.common.exception.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import jp.glory.bookshelf.web.application.common.response.ResponseCreator;
import jp.glory.bookshelf.web.application.common.view.ErrorView;
import jp.glory.bookshelf.web.common.exception.SystemException;

/**
 * システム例外マッピング
 * 
 * @author JUnki Yamada
 * 
 */
@Provider
public class SystemExceptionMapper implements ExceptionMapper<SystemException> {

	/**
	 * 例外処理
	 */
	@Override
	public Response toResponse(final SystemException exception) {

		final ErrorView view = new ErrorView(exception.getMessage());
		final ResponseCreator response = new ResponseCreator(view);

		return response.getResponse();
	}

}
