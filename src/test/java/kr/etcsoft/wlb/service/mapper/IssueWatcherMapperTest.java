package kr.etcsoft.wlb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class IssueWatcherMapperTest {

    private IssueWatcherMapper issueWatcherMapper;

    @BeforeEach
    public void setUp() {
        issueWatcherMapper = new IssueWatcherMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(issueWatcherMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(issueWatcherMapper.fromId(null)).isNull();
    }
}
