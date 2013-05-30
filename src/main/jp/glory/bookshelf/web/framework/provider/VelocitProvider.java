package jp.glory.bookshelf.web.framework.provider;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.ext.Provider;

import jp.glory.bookshelf.web.common.constant.ApplicationConstant;
import jp.glory.bookshelf.web.framework.provider.config.VelocityConfig;
import jp.glory.bookshelf.web.framework.provider.handler.VelocityHandler;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.event.EventCartridge;
import org.apache.velocity.context.Context;

import com.sun.jersey.api.view.Viewable;
import com.sun.jersey.spi.template.ViewProcessor;

/**
 * Velocity用のプロバイダ
 * 
 * @author Junki Yamada
 * 
 */
@Provider
@RequestScoped
public class VelocitProvider implements ViewProcessor<Template> {

	/** Velocity設定 */
	private final VelocityConfig velocityConfig = new VelocityConfig();

	/**
	 * コンストラクタ<br>
	 * Velocityの設定を行います。
	 */
	public VelocitProvider() {

		Velocity.init(velocityConfig.createProperties());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Template resolve(final String path) {
		Template template = Velocity.getTemplate(path, ApplicationConstant.ENCODING);
		return template;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeTo(final Template t, final Viewable vwbl, final OutputStream out) throws IOException {

		final Writer writer = new OutputStreamWriter(out, ApplicationConstant.ENCODING);
		final Context context = new VelocityContext();

		context.put("it", vwbl.getModel());

		EventCartridge ec = new EventCartridge();
		ec.attachToContext(context);
		ec.addEventHandler(new VelocityHandler());

		t.merge(context, writer);

		writer.flush();
	}
}
