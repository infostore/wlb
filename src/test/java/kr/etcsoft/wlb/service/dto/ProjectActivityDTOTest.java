package kr.etcsoft.wlb.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import kr.etcsoft.wlb.web.rest.TestUtil;

public class ProjectActivityDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectActivityDTO.class);
        ProjectActivityDTO projectActivityDTO1 = new ProjectActivityDTO();
        projectActivityDTO1.setId(1L);
        ProjectActivityDTO projectActivityDTO2 = new ProjectActivityDTO();
        assertThat(projectActivityDTO1).isNotEqualTo(projectActivityDTO2);
        projectActivityDTO2.setId(projectActivityDTO1.getId());
        assertThat(projectActivityDTO1).isEqualTo(projectActivityDTO2);
        projectActivityDTO2.setId(2L);
        assertThat(projectActivityDTO1).isNotEqualTo(projectActivityDTO2);
        projectActivityDTO1.setId(null);
        assertThat(projectActivityDTO1).isNotEqualTo(projectActivityDTO2);
    }
}
