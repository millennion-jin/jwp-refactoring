package kitchenpos.menu.domain;

import kitchenpos.menu.exception.IllegalMenuPriceException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
public class MenuPrice {
    @Column(nullable = false)
    private BigDecimal price;

    protected MenuPrice() {
    }

    private MenuPrice(BigDecimal price) {
        validate(price);
        this.price = price;
    }

    private void validate(BigDecimal price) {
        if (isLessThenZero(price)) {
            throw new IllegalMenuPriceException("0보다 작을 수 없습니다.");
        }
    }

    private boolean isLessThenZero(BigDecimal price) {
        return price.compareTo(BigDecimal.ZERO) < 0;
    }

    public static MenuPrice of(int price) {
        return new MenuPrice(BigDecimal.valueOf(price));
    }

    public static MenuPrice of(BigDecimal price) {
        return new MenuPrice(price);
    }


    public BigDecimal getPrice() {
        return price;
    }

    public boolean matchPrice(int targetPrice) {
        return price.compareTo(BigDecimal.valueOf(targetPrice)) == 0;
    }
}
