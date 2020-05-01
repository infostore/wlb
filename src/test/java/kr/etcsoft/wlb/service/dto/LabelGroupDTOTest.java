package kr.etcsoft.wlb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import kr.etcsoft.wlb.web.rest.TestUtil;

public class LabelGroupDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LabelGroupDTO.class);
        LabelGroupDTO labelGroupDTO1 = new LabelGroupDTO();
        labelGroupDTO1.setId(1L);
        LabelGroupDTO labelGroupDTO2 = new LabelGroupDTO();
        assertThat(labelGroupDTO1).isNotEqualTo(labelGroupDTO2);
        labelGroupDTO2.setId(labelGroupDTO1.getId());
        assertThat(labelGroupDTO1).isEqualTo(labelGroupDTO2);
        labelGroupDTO2.setId(2L);
        assertThat(labelGroupDTO1).isNotEqualTo(labelGroupDTO2);
        labelGroupDTO1.setId(null);
        assertThat(labelGroupDTO1).isNotEqualTo(labelGroupDTO2);
    }
}
