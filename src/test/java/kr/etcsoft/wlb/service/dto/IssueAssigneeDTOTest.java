package kr.etcsoft.wlb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import kr.etcsoft.wlb.web.rest.TestUtil;

public class IssueAssigneeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IssueAssigneeDTO.class);
        IssueAssigneeDTO issueAssigneeDTO1 = new IssueAssigneeDTO();
        issueAssigneeDTO1.setId(1L);
        IssueAssigneeDTO issueAssigneeDTO2 = new IssueAssigneeDTO();
        assertThat(issueAssigneeDTO1).isNotEqualTo(issueAssigneeDTO2);
        issueAssigneeDTO2.setId(issueAssigneeDTO1.getId());
        assertThat(issueAssigneeDTO1).isEqualTo(issueAssigneeDTO2);
        issueAssigneeDTO2.setId(2L);
        assertThat(issueAssigneeDTO1).isNotEqualTo(issueAssigneeDTO2);
        issueAssigneeDTO1.setId(null);
        assertThat(issueAssigneeDTO1).isNotEqualTo(issueAssigneeDTO2);
    }
}
