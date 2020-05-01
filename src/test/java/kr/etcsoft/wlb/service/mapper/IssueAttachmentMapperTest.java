package kr.etcsoft.wlb.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class IssueAttachmentMapperTest {

    private IssueAttachmentMapper issueAttachmentMapper;

    @BeforeEach
    public void setUp() {
        issueAttachmentMapper = new IssueAttachmentMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(issueAttachmentMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(issueAttachmentMapper.fromId(null)).isNull();
    }
}
