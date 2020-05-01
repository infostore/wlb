package kr.etcsoft.wlb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProjectActivityMapperTest {

    private ProjectActivityMapper projectActivityMapper;

    @BeforeEach
    public void setUp() {
        projectActivityMapper = new ProjectActivityMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(projectActivityMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(projectActivityMapper.fromId(null)).isNull();
    }
}
