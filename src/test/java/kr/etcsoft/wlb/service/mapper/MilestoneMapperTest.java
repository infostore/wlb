package kr.etcsoft.wlb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MilestoneMapperTest {

    private MilestoneMapper milestoneMapper;

    @BeforeEach
    public void setUp() {
        milestoneMapper = new MilestoneMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(milestoneMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(milestoneMapper.fromId(null)).isNull();
    }
}
