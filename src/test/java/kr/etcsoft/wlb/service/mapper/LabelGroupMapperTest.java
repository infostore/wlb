package kr.etcsoft.wlb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LabelGroupMapperTest {

    private LabelGroupMapper labelGroupMapper;

    @BeforeEach
    public void setUp() {
        labelGroupMapper = new LabelGroupMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(labelGroupMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(labelGroupMapper.fromId(null)).isNull();
    }
}
