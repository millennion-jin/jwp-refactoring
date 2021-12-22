package kitchenpos.order.domain;

import kitchenpos.menu.application.MenuService;
import kitchenpos.order.exception.IllegalMenuIdsException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MenuCountOrderValidator {
    private final MenuService menuService;

    public MenuCountOrderValidator(MenuService menuService) {
        this.menuService = menuService;
    }

    public void validate(List<Long> menuIds) {
        if (menuIds.size() != menuService.countByIdIn(menuIds)) {
            throw new IllegalMenuIdsException("메뉴 아이디 목록이 잘못 되었습니다.");
        }
    }
}
