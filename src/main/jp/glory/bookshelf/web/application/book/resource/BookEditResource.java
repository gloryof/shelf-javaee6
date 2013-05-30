package jp.glory.bookshelf.web.application.book.resource;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import jp.glory.bookshelf.common.expection.BusinessException;
import jp.glory.bookshelf.domain.book.entity.Book;
import jp.glory.bookshelf.domain.book.repository.BookRepository;
import jp.glory.bookshelf.domain.book.service.BookService;
import jp.glory.bookshelf.domain.book.value.BookId;
import jp.glory.bookshelf.domain.book.value.IsbnCode;
import jp.glory.bookshelf.domain.book.value.Price;
import jp.glory.bookshelf.domain.book.value.Title;
import jp.glory.bookshelf.domain.shelf.value.ShelfId;
import jp.glory.bookshelf.web.application.book.view.BookEditView;
import jp.glory.bookshelf.web.application.common.constant.ResourceUrlConst;
import jp.glory.bookshelf.web.application.common.constant.UrlParameterNameConst;
import jp.glory.bookshelf.web.application.common.response.ResponseCreator;

/**
 * 本編集リソース
 *
 * @author Junki Yamada
 *
 */
@Path(ResourceUrlConst.BOOK_EDIT + "{" + UrlParameterNameConst.BOOK_ID + "}")
@Stateless
public class BookEditResource {

	/**
	 * 本リポジトリ
	 */
	private final BookRepository repository;;

	/**
	 * コンストラクタ
	 *
	 * @param repository リポジトリ
	 */
	@Inject
	public BookEditResource(final BookRepository repository) {

		this.repository = repository;
	}

	/**
	 * IDで本を取得する
	 *
	 * @param bookIdParam 本ID
	 * @return レスポンス
	 */
	@GET
	public Response initView(@PathParam(UrlParameterNameConst.BOOK_ID) final long bookIdParam) {

		final BookService service = createService();
		final BookId bookId = new BookId(bookIdParam);

		final BookEditView view = new BookEditView(service.findById(bookId));
		final ResponseCreator creator = new ResponseCreator(view);

		return creator.getResponse();
	}

	/**
	 * 本を更新する
	 *
	 * @param bookIdFormParam 本ID（フォーム）
	 * @param bookIdPathParam 本ID（パス）
	 * @param titleParam タイトル
	 * @param isbnCodeParam ISBNコード
	 * @param priceParam 値段
	 * @return
	 */
	@POST
	public Response update(@FormParam(UrlParameterNameConst.BOOK_ID) final long bookIdFormParam,
			@PathParam(UrlParameterNameConst.BOOK_ID) final long bookIdPathParam,
			@FormParam(UrlParameterNameConst.TITLE) final String titleParam,
			@FormParam(UrlParameterNameConst.ISBN_CODE) final String isbnCodeParam,
			@FormParam(UrlParameterNameConst.PRICE) final int priceParam,
			@FormParam(UrlParameterNameConst.PARENT_SHELF_ID) final long parentShelfIdParam) {

		if (bookIdFormParam != bookIdPathParam) {

			final ResponseCreator badRequestResponse = new ResponseCreator(Response.Status.BAD_REQUEST);

			return badRequestResponse.getResponse();
		}

		final BookId bookId = new BookId(bookIdFormParam);
		final Book book = new Book(bookId);
		book.setTitle(new Title(titleParam));
		book.setIsbnCode(new IsbnCode(isbnCodeParam));
		book.setPrice(new Price(priceParam));
		book.setParentShelfId(new ShelfId(parentShelfIdParam));

		final BookService service = createService();
		try {
			service.update(book);
		} catch (final BusinessException e) {

			final BookEditView errorView = new BookEditView(book);
			errorView.addErrors(e.getErrors());

			final ResponseCreator errorResponse = new ResponseCreator(errorView);
			return errorResponse.getResponse();
		}

		final String viewUrl = ResourceUrlConst.BOOK_ID_VIEW + bookId.getValue();
		final ResponseCreator successResponse = new ResponseCreator(viewUrl);

		return successResponse.getResponse();
	}

	/**
	 * サービスを作成する
	 *
	 * @return サービス
	 */
	private BookService createService() {

		return new BookService(repository);
	}
}
