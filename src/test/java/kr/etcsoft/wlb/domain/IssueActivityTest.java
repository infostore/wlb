package kr.etcsoft.wlb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import kr.etcsoft.wlb.web.rest.TestUtil;

public class IssueActivityTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IssueActivity.class);
        IssueActivity issueActivity1 = new IssueActivity();
        issueActivity1.setId(1L);
        IssueActivity issueActivity2 = new IssueActivity();
        issueActivity2.setId(issueActivity1.getId());
        assertThat(issueActivity1).isEqualTo(issueActivity2);
        issueActivity2.setId(2L);
        assertThat(issueActivity1).isNotEqualTo(issueActivity2);
        issueActivity1.setId(null);
        assertThat(issueActivity1).isNotEqualTo(issueActivity2);
    }
}
