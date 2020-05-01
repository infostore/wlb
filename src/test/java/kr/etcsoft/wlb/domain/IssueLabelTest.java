package kr.etcsoft.wlb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import kr.etcsoft.wlb.web.rest.TestUtil;

public class IssueLabelTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IssueLabel.class);
        IssueLabel issueLabel1 = new IssueLabel();
        issueLabel1.setId(1L);
        IssueLabel issueLabel2 = new IssueLabel();
        issueLabel2.setId(issueLabel1.getId());
        assertThat(issueLabel1).isEqualTo(issueLabel2);
        issueLabel2.setId(2L);
        assertThat(issueLabel1).isNotEqualTo(issueLabel2);
        issueLabel1.setId(null);
        assertThat(issueLabel1).isNotEqualTo(issueLabel2);
    }
}
