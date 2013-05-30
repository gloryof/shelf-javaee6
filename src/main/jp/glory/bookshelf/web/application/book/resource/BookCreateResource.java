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
import jp.glory.bookshelf.domain.shelf.repository.ShelfRepository;
import jp.glory.bookshelf.domain.shelf.sevice.ShelfReferenceService;
import jp.glory.bookshelf.domain.shelf.sevice.ShelfUpdateService;
import jp.glory.bookshelf.domain.shelf.value.ShelfId;
import jp.glory.bookshelf.web.application.book.view.BookCreateView;
import jp.glory.bookshelf.web.application.common.constant.ResourceUrlConst;
import jp.glory.bookshelf.web.application.common.constant.UrlParameterNameConst;
import jp.glory.bookshelf.web.application.common.response.ResponseCreator;
import jp.glory.bookshelf.web.common.exception.SystemException;

/**
 * 本作成リソース
 *
 * @author Junki Yamada
 *
 */
@Path(ResourceUrlConst.BOOK_CREATE + "{" + UrlParameterNameConst.PARENT_SHELF_ID + "}")
@Stateless
public class BookCreateResource {

	/** 本リポジトリ */
	private final BookRepository bookRepository;

	/** 本棚リポジトリ */
	private final ShelfRepository shelfRepository;

	/**
	 * コンストラクタ
	 *
	 * @param bookRepository 本リポジトリ
	 * @param shelfRepository 本棚リポジトリ
	 */
	@Inject
	public BookCreateResource(final BookRepository bookRepository, final ShelfRepository shelfRepository) {

		this.bookRepository = bookRepository;
		this.shelfRepository = shelfRepository;
	}

	/**
	 * 初期表示
	 *
	 * @param parentShelfIdParam 親本棚ID
	 * @return レスポンス
	 */
	@GET
	public Response initView(@PathParam(UrlParameterNameConst.PARENT_SHELF_ID) final long parentShelfIdParam) {

		final Book book = new Book();
		book.setParentShelfId(new ShelfId(parentShelfIdParam));
		final BookCreateView view = new BookCreateView(book);
		final ResponseCreator creator = new ResponseCreator(view);

		return creator.getResponse();
	}

	/**
	 * 本を新規登録する
	 *
	 * @param titleParam タイトル
	 * @param isbnCodeParam ISBNコード
	 * @param priceParam 価格
	 * @param parentShelfIdFormParam 親本棚ID（フォーム）
	 * @param parentShelfIdPathParam 親本棚ID（パス）
	 * @return レスポンス
	 */
	@POST
	public Response create(@FormParam(UrlParameterNameConst.TITLE) final String titleParam,
			@FormParam(UrlParameterNameConst.ISBN_CODE) final String isbnCodeParam,
			@FormParam(UrlParameterNameConst.PRICE) final int priceParam,
			@FormParam(UrlParameterNameConst.PARENT_SHELF_ID) final long parentShelfIdFormParam,
			@FormParam(UrlParameterNameConst.PARENT_SHELF_ID) final long parentShelfIdPathParam) {

		if (parentShelfIdFormParam != parentShelfIdPathParam) {

			final ResponseCreator badRequestResponse = new ResponseCreator(Response.Status.BAD_REQUEST);

			return badRequestResponse.getResponse();
		}

		if (isNotExistParentShelf(parentShelfIdFormParam)) {

			throw new SystemException("対象の本棚が存在しません[本棚ID:" + parentShelfIdFormParam + "]");
		}

		final Book book = new Book();
		book.setParentShelfId(new ShelfId(parentShelfIdFormParam));
		book.setTitle(new Title(titleParam));
		book.setIsbnCode(new IsbnCode(isbnCodeParam));
		book.setPrice(new Price(priceParam));

		try {

			final BookService bookServie = createBookService();
			final BookId bookId = bookServie.create(book);

			final ShelfId shelfId = new ShelfId(parentShelfIdFormParam);
			final ShelfUpdateService shelfService = createUpdateShelfService();
			shelfService.add(shelfId, bookId);

			return createNextUrlResponse(bookId);
		} catch (final BusinessException e) {

			return createRedisplayResponse(book);
		}

	}

	/**
	 * 次画面のURLレスポンスを作成する
	 *
	 * @param bookId 本ID
	 * @return URLレスポンス
	 */
	private Response createNextUrlResponse(final BookId bookId) {

		final String url = ResourceUrlConst.BOOK_ID_VIEW + bookId.getValue();

		final ResponseCreator nextUrlResponse = new ResponseCreator(url);

		return nextUrlResponse.getResponse();
	}

	/**
	 * 再表示用のレスポンスを作成する
	 *
	 * @param book 本
	 * @return 再表示レスポンス
	 */
	private Response createRedisplayResponse(final Book book) {

		final BookCreateView view = new BookCreateView(book);

		final ResponseCreator errorResponse = new ResponseCreator(view);

		return errorResponse.getResponse();
	}

	/**
	 * 指定した本棚IDの本棚が存在していないか判定する
	 *
	 * @param parentShelfIdParam 本棚ID
	 * @return 存在していない場合：true、存在している場合：false
	 */
	private boolean isNotExistParentShelf(final long parentShelfIdParam) {

		final ShelfId parentShelfId = new ShelfId(parentShelfIdParam);
		final ShelfReferenceService shelfService = createReferenceShelfService();

		return !shelfService.isExist(parentShelfId);
	}

	/**
	 * 本サービスを作成する
	 *
	 * @return 本サービス
	 */
	private BookService createBookService() {

		return new BookService(bookRepository);
	}

	/**
	 * 本棚参照サービスを作成する
	 *
	 * @return 本棚サービス
	 */
	private ShelfReferenceService createReferenceShelfService() {

		return new ShelfReferenceService(shelfRepository);
	}

	/**
	 * 本棚更新サービスを作成する
	 *
	 * @return 本棚更新サービス
	 */
	private ShelfUpdateService createUpdateShelfService() {

		return new ShelfUpdateService(shelfRepository);
	}
}
