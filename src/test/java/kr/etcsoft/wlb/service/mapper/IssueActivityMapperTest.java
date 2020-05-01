package kr.etcsoft.wlb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class IssueActivityMapperTest {

    private IssueActivityMapper issueActivityMapper;

    @BeforeEach
    public void setUp() {
        issueActivityMapper = new IssueActivityMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(issueActivityMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(issueActivityMapper.fromId(null)).isNull();
    }
}
