package jp.glory.bookshelf.web.application.book.resource;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import jp.glory.bookshelf.domain.book.entity.Book;
import jp.glory.bookshelf.domain.book.repository.BookRepository;
import jp.glory.bookshelf.domain.book.service.BookService;
import jp.glory.bookshelf.domain.book.value.BookId;
import jp.glory.bookshelf.domain.book.value.IsbnCode;
import jp.glory.bookshelf.web.application.book.view.BookView;
import jp.glory.bookshelf.web.application.common.constant.ResourceUrlConst;
import jp.glory.bookshelf.web.application.common.constant.UrlParameterNameConst;
import jp.glory.bookshelf.web.application.common.response.ResponseCreator;

/**
 * 本表示リソース
 *
 * @author Junki Yamada
 *
 */
@Path(ResourceUrlConst.BOOK)
@Stateless
public class BookViewResource {

	/**
	 * 本リポジトリ
	 */
	private final BookRepository bookRepository;

	/**
	 * コンストラクタ
	 *
	 * @param bookRepository 本リポジトリ
	 * @param shelfRepository 本棚リポジトリ
	 */
	@Inject
	public BookViewResource(final BookRepository bookRepository) {

		this.bookRepository = bookRepository;
	}

	/**
	 * IDで本を取得する
	 *
	 * @param bookIdParam 本ID
	 * @return レスポンス
	 */
	@GET
	@Path("id/{" + UrlParameterNameConst.BOOK_ID + "}")
	public Response findById(@PathParam(UrlParameterNameConst.BOOK_ID) final long bookIdParam) {

		final BookService service = createBookService();

		final Book book = service.findById(new BookId(bookIdParam));

		return createResponse(book);
	}

	/**
	 * ISBNコードで本リストを取得する
	 *
	 * @param isbnCode ISBNコード
	 * @return レスポンス
	 */
	@GET
	@Path("isbn/{" + UrlParameterNameConst.ISBN_CODE + "}")
	public Response findListByIsbnCode(@PathParam(UrlParameterNameConst.ISBN_CODE) final String isbnCode) {

		final BookService service = createBookService();

		final List<Book> bookList = service.findListByIsbnCode(new IsbnCode(isbnCode));

		return createResponse(bookList);
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
	 * レスポンス作成
	 *
	 * @param book 本
	 * @return レスポンス
	 */
	private Response createResponse(final Book book) {

		final BookView view = new BookView(book);

		final ResponseCreator creator = new ResponseCreator(view);

		return creator.getResponse();
	}

	/**
	 * レスポンス作成
	 *
	 * @param bookList 本リスト
	 * @return レスポンス
	 */
	private Response createResponse(final List<Book> bookList) {

		final BookView view = new BookView(bookList);

		final ResponseCreator creator = new ResponseCreator(view);

		return creator.getResponse();
	}
}
