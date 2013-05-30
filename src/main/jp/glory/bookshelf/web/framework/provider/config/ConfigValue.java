package jp.glory.bookshelf.web.framework.provider.config;

/**
 * 設定値クラス
 * 
 * @author Junki Yamada
 * 
 */
public class ConfigValue {

	/** キー */
	private final String key;

	/** 値 */
	private final String value;

	/**
	 * コンストラクタ
	 * 
	 * @param key キー
	 * @param value 値
	 */
	public ConfigValue(final String key, final String value) {

		this.key = key;
		this.value = value;
	}

	/**
	 * @return key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @return value
	 */
	public String getValue() {
		return value;
	}
}
