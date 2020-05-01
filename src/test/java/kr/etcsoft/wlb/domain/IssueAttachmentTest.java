package kr.etcsoft.wlb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import kr.etcsoft.wlb.web.rest.TestUtil;

public class IssueAttachmentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IssueAttachment.class);
        IssueAttachment issueAttachment1 = new IssueAttachment();
        issueAttachment1.setId(1L);
        IssueAttachment issueAttachment2 = new IssueAttachment();
        issueAttachment2.setId(issueAttachment1.getId());
        assertThat(issueAttachment1).isEqualTo(issueAttachment2);
        issueAttachment2.setId(2L);
        assertThat(issueAttachment1).isNotEqualTo(issueAttachment2);
        issueAttachment1.setId(null);
        assertThat(issueAttachment1).isNotEqualTo(issueAttachment2);
    }
}
