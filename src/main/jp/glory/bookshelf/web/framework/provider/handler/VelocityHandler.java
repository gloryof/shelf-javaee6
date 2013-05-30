package jp.glory.bookshelf.web.framework.provider.handler;

import org.apache.velocity.app.event.ReferenceInsertionEventHandler;
import org.apache.velocity.tools.generic.EscapeTool;

/**
 * Velocityのハンドラ
 * 
 * @author Junki Yamada
 * 
 */
public class VelocityHandler implements ReferenceInsertionEventHandler {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object referenceInsert(final String reference, final Object value) {

		final EscapeTool tool = new EscapeTool();

		return tool.html(value);
	}
}
