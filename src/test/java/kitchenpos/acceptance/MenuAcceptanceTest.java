package kitchenpos.acceptance;

import static kitchenpos.acceptance.MenuGroupRestAssured.메뉴그룹_등록_요청;
import static kitchenpos.acceptance.MenuRestAssured.메뉴_등록_요청;
import static kitchenpos.acceptance.ProductRestAssured.상품_등록_요청;
import static kitchenpos.acceptance.ProductRestAssured.상품_목록_조회_요청;
import static kitchenpos.utils.DomainFixtureFactory.createMenu;
import static kitchenpos.utils.DomainFixtureFactory.createMenuGroup;
import static kitchenpos.utils.DomainFixtureFactory.createMenuProduct;
import static kitchenpos.utils.DomainFixtureFactory.createProduct;
import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.math.BigDecimal;
import java.util.List;
import kitchenpos.domain.Menu;
import kitchenpos.domain.MenuGroup;
import kitchenpos.domain.MenuProduct;
import kitchenpos.domain.Product;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@DisplayName("상품 관련 기능")
class MenuAcceptanceTest extends AcceptanceTest {
    private Menu 양념치킨;
    private MenuGroup 한마리메뉴;
    private Product 양념;
    private MenuProduct 양념치킨상품;

    @BeforeEach
    public void setUp() {
        super.setUp();

        한마리메뉴 = 메뉴그룹_등록_요청(createMenuGroup(1L, "한마리메뉴")).as(MenuGroup.class);
        양념 = 상품_등록_요청(createProduct(1L, "양념", BigDecimal.valueOf(20000L))).as(Product.class);
        양념치킨상품 = createMenuProduct(1L, null, 양념.getId(), 2L);
        양념치킨 = createMenu(1L, "양념치킨", BigDecimal.valueOf(40000L), 한마리메뉴.getId(), Lists.newArrayList(양념치킨상품));
    }

    /**
     * When 메뉴를 등록 요청하면
     * Then 메뉴가 등록 됨
     */
    @DisplayName("메뉴를 등록한다.")
    @Test
    void create() {
        // when
        ExtractableResponse<Response> response = 메뉴_등록_요청(양념치킨);

        // then
        메뉴_등록됨(response);
    }

    /**
     * Given 메뉴를 등록하고
     * When 메뉴 목록을 조회 하면
     * Then 메뉴 목록 조회 됨
     */
    @DisplayName("메뉴 목록을 조회 한다.")
    @Test
    void getProducts() {
        // given
        Menu 등록한_양념치킨 = 메뉴_등록_요청(양념치킨).as(Menu.class);

        // when
        ExtractableResponse<Response> response = 상품_목록_조회_요청();

        // then
        메뉴_목록_조회됨(response, Lists.newArrayList(등록한_양념치킨));
    }

    private void 메뉴_등록됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    private void 메뉴_목록_조회됨(ExtractableResponse<Response> response, List<Menu> expectedMenus) {
        List<Menu> menus = response.jsonPath().getList(".", Menu.class);
        assertThat(menus).containsExactlyElementsOf(expectedMenus);
    }
}
