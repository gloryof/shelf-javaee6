package jp.glory.bookshelf.web.framework.provider.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import jp.glory.bookshelf.web.common.constant.ApplicationConstant;

/**
 * Velocity設置値クラス
 * 
 * @author Junki Yamada
 * 
 */
public class VelocityConfig {

	/** 設定値リスト */
	private final List<ConfigValue> configList;

	/**
	 * コンストラクタ
	 */
	public VelocityConfig() {

		configList = Collections.unmodifiableList(createConfigList());
	}

	/**
	 * 設定値リスト作成
	 * 
	 * @return 設定値リスト
	 */
	private List<ConfigValue> createConfigList() {

		final List<ConfigValue> returnList = new ArrayList<>();
		returnList.add(new ConfigValue("input.encoding", ApplicationConstant.ENCODING));
		returnList.add(new ConfigValue("output.encoding", ApplicationConstant.ENCODING));
		returnList.add(new ConfigValue("resource.loader", "CLASSPATH"));
		returnList.add(new ConfigValue("CLASSPATH.resource.loader.class",
				"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader"));

		return returnList;
	}

	/**
	 * プロパティオブジェクト作成処理
	 * 
	 * @return プロパティオブジェクト
	 */
	public Properties createProperties() {

		final Properties property = new Properties();

		for (final ConfigValue config : configList) {

			property.setProperty(config.getKey(), config.getValue());
		}

		return property;
	}
}
