package kr.etcsoft.wlb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import kr.etcsoft.wlb.web.rest.TestUtil;

public class IssueWatcherDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IssueWatcherDTO.class);
        IssueWatcherDTO issueWatcherDTO1 = new IssueWatcherDTO();
        issueWatcherDTO1.setId(1L);
        IssueWatcherDTO issueWatcherDTO2 = new IssueWatcherDTO();
        assertThat(issueWatcherDTO1).isNotEqualTo(issueWatcherDTO2);
        issueWatcherDTO2.setId(issueWatcherDTO1.getId());
        assertThat(issueWatcherDTO1).isEqualTo(issueWatcherDTO2);
        issueWatcherDTO2.setId(2L);
        assertThat(issueWatcherDTO1).isNotEqualTo(issueWatcherDTO2);
        issueWatcherDTO1.setId(null);
        assertThat(issueWatcherDTO1).isNotEqualTo(issueWatcherDTO2);
    }
}
