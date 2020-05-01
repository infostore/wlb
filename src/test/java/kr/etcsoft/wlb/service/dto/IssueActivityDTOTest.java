package kr.etcsoft.wlb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import kr.etcsoft.wlb.web.rest.TestUtil;

public class IssueActivityDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IssueActivityDTO.class);
        IssueActivityDTO issueActivityDTO1 = new IssueActivityDTO();
        issueActivityDTO1.setId(1L);
        IssueActivityDTO issueActivityDTO2 = new IssueActivityDTO();
        assertThat(issueActivityDTO1).isNotEqualTo(issueActivityDTO2);
        issueActivityDTO2.setId(issueActivityDTO1.getId());
        assertThat(issueActivityDTO1).isEqualTo(issueActivityDTO2);
        issueActivityDTO2.setId(2L);
        assertThat(issueActivityDTO1).isNotEqualTo(issueActivityDTO2);
        issueActivityDTO1.setId(null);
        assertThat(issueActivityDTO1).isNotEqualTo(issueActivityDTO2);
    }
}
