package kitchenpos.fixture;

import kitchenpos.domain.TableGroup;
import kitchenpos.dto.TableGroupRequestDto;
import kitchenpos.dto.TableGroupResponseDto;

import java.time.LocalDateTime;
import java.util.Arrays;

public class TableGroupFixture {

    public static TableGroupRequestDto 단체_지정_데이터_생성(Long... ids) {
        return new TableGroupRequestDto(Arrays.asList(ids));
    }

    public static TableGroup 단체_데이터_생성(Long id) {
        return new TableGroup(id);
    }

    public static TableGroupResponseDto 단체_응답_데이터_생성(Long id) {
        return new TableGroupResponseDto(id, LocalDateTime.now());
    }

}
