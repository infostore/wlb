package kr.etcsoft.wlb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import kr.etcsoft.wlb.web.rest.TestUtil;

public class IssueAttachmentDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IssueAttachmentDTO.class);
        IssueAttachmentDTO issueAttachmentDTO1 = new IssueAttachmentDTO();
        issueAttachmentDTO1.setId(1L);
        IssueAttachmentDTO issueAttachmentDTO2 = new IssueAttachmentDTO();
        assertThat(issueAttachmentDTO1).isNotEqualTo(issueAttachmentDTO2);
        issueAttachmentDTO2.setId(issueAttachmentDTO1.getId());
        assertThat(issueAttachmentDTO1).isEqualTo(issueAttachmentDTO2);
        issueAttachmentDTO2.setId(2L);
        assertThat(issueAttachmentDTO1).isNotEqualTo(issueAttachmentDTO2);
        issueAttachmentDTO1.setId(null);
        assertThat(issueAttachmentDTO1).isNotEqualTo(issueAttachmentDTO2);
    }
}
