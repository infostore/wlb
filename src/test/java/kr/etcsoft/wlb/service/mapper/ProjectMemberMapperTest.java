package kr.etcsoft.wlb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ProjectMemberMapperTest {

    private ProjectMemberMapper projectMemberMapper;

    @BeforeEach
    public void setUp() {
        projectMemberMapper = new ProjectMemberMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(projectMemberMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(projectMemberMapper.fromId(null)).isNull();
    }
}
