package jp.glory.bookshelf.web.common.exception;

/**
 * システム例外
 * 
 * @author Junki Yamada
 * 
 */
public class SystemException extends RuntimeException {

	/**
	 * シリアルバージョンUID
	 */
	private static final long serialVersionUID = -7355934690586171645L;

	/**
	 * コンストラクタ
	 * 
	 * @param message メッセージ
	 */
	public SystemException(final String message) {

		super(message);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param message メッセージ
	 * @param cause 原因例外
	 */
	public SystemException(final String message, final Throwable cause) {

		super(message, cause);
	}
}
