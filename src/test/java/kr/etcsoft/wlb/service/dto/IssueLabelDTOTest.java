package kr.etcsoft.wlb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import kr.etcsoft.wlb.web.rest.TestUtil;

public class IssueLabelDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IssueLabelDTO.class);
        IssueLabelDTO issueLabelDTO1 = new IssueLabelDTO();
        issueLabelDTO1.setId(1L);
        IssueLabelDTO issueLabelDTO2 = new IssueLabelDTO();
        assertThat(issueLabelDTO1).isNotEqualTo(issueLabelDTO2);
        issueLabelDTO2.setId(issueLabelDTO1.getId());
        assertThat(issueLabelDTO1).isEqualTo(issueLabelDTO2);
        issueLabelDTO2.setId(2L);
        assertThat(issueLabelDTO1).isNotEqualTo(issueLabelDTO2);
        issueLabelDTO1.setId(null);
        assertThat(issueLabelDTO1).isNotEqualTo(issueLabelDTO2);
    }
}
