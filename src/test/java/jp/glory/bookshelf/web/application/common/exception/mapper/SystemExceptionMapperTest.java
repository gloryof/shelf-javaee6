package jp.glory.bookshelf.web.application.common.exception.mapper;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import javax.ws.rs.core.Response;

import jp.glory.bookshelf.web.application.common.view.ErrorView;
import jp.glory.bookshelf.web.common.exception.SystemException;
import jp.glory.bookshelf.web.tool.ResponseViewAssert;
import jp.glory.bookshelf.web.tool.ResponseViewExtractor;

import org.junit.Test;

public class SystemExceptionMapperTest {

	@Test
	public void toResponseでシステムエラーのメッセージが設定されたエラー画面が返却される() {

		final String expectedMessage = "テスト";
		final SystemException expection = new SystemException(expectedMessage);

		final SystemExceptionMapper sut = new SystemExceptionMapper();
		final Response actualResponse = sut.toResponse(expection);

		final ResponseViewAssert viewAssert = new ResponseViewAssert(actualResponse, ErrorView.class);
		viewAssert.assertResponse();

		final ResponseViewExtractor<ErrorView> extractor = new ResponseViewExtractor<>(actualResponse);
		final ErrorView actualView = extractor.extractView();
		final String actualMessage = actualView.getMessage();

		assertThat(actualMessage, is(expectedMessage));
	}
}
