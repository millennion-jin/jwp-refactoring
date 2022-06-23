package kitchenpos.table.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TableGroup {
    private static final int MIN_TABLE_SIZE = 2;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDateTime createdDate;
    @Embedded
    private OrderTables orderTables = new OrderTables();

    protected TableGroup() {
    }

    public TableGroup(Long id, LocalDateTime createdDate) {
        this.id = id;
        this.createdDate = createdDate;
    }

    public TableGroup(Long id, LocalDateTime createdDate, OrderTables orderTables) {
        this.id = id;
        this.createdDate = createdDate;
        this.orderTables = orderTables;
    }

    public void ungroupingTableGroup() {
        orderTables.ungroupingTableGroup();
    }

    public void groupingTables(OrderTables emptyTables, int requestCount) {
        checkPossibleGrouping(emptyTables, requestCount);
        orderTables.groupingTableGroup(emptyTables, this);
    }

    private void checkPossibleGrouping(OrderTables emptyTables, int requestCount) {
        if (emptyTables.size() < MIN_TABLE_SIZE) {
            throw new IllegalArgumentException("[ERROR] 단체 지정에는 최소 2개의 테이블이 필요합니다.");
        }
        if (emptyTables.size() != requestCount) {
            throw new IllegalArgumentException("[ERROR] 등록 되어있지 않은 테이블이 존재합니다.");
        }
    }

    public void addOrderTable(OrderTable orderTable) {
        orderTables.addOrderTable(orderTable);
        orderTable.setTableGroup(this);
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public OrderTables getOrderTables() {
        return orderTables;
    }

}
