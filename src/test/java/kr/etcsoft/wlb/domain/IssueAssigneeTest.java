package kr.etcsoft.wlb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import kr.etcsoft.wlb.web.rest.TestUtil;

public class IssueAssigneeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IssueAssignee.class);
        IssueAssignee issueAssignee1 = new IssueAssignee();
        issueAssignee1.setId(1L);
        IssueAssignee issueAssignee2 = new IssueAssignee();
        issueAssignee2.setId(issueAssignee1.getId());
        assertThat(issueAssignee1).isEqualTo(issueAssignee2);
        issueAssignee2.setId(2L);
        assertThat(issueAssignee1).isNotEqualTo(issueAssignee2);
        issueAssignee1.setId(null);
        assertThat(issueAssignee1).isNotEqualTo(issueAssignee2);
    }
}
