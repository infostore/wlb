package kr.etcsoft.wlb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class IssueAssigneeMapperTest {

    private IssueAssigneeMapper issueAssigneeMapper;

    @BeforeEach
    public void setUp() {
        issueAssigneeMapper = new IssueAssigneeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(issueAssigneeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(issueAssigneeMapper.fromId(null)).isNull();
    }
}
