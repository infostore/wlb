package kr.etcsoft.wlb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class IssueLabelMapperTest {

    private IssueLabelMapper issueLabelMapper;

    @BeforeEach
    public void setUp() {
        issueLabelMapper = new IssueLabelMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(issueLabelMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(issueLabelMapper.fromId(null)).isNull();
    }
}
