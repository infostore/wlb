package kr.etcsoft.wlb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import kr.etcsoft.wlb.web.rest.TestUtil;

public class IssueWatcherTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IssueWatcher.class);
        IssueWatcher issueWatcher1 = new IssueWatcher();
        issueWatcher1.setId(1L);
        IssueWatcher issueWatcher2 = new IssueWatcher();
        issueWatcher2.setId(issueWatcher1.getId());
        assertThat(issueWatcher1).isEqualTo(issueWatcher2);
        issueWatcher2.setId(2L);
        assertThat(issueWatcher1).isNotEqualTo(issueWatcher2);
        issueWatcher1.setId(null);
        assertThat(issueWatcher1).isNotEqualTo(issueWatcher2);
    }
}
