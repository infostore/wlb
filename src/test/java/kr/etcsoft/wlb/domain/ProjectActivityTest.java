package kr.etcsoft.wlb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import kr.etcsoft.wlb.web.rest.TestUtil;

public class ProjectActivityTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectActivity.class);
        ProjectActivity projectActivity1 = new ProjectActivity();
        projectActivity1.setId(1L);
        ProjectActivity projectActivity2 = new ProjectActivity();
        projectActivity2.setId(projectActivity1.getId());
        assertThat(projectActivity1).isEqualTo(projectActivity2);
        projectActivity2.setId(2L);
        assertThat(projectActivity1).isNotEqualTo(projectActivity2);
        projectActivity1.setId(null);
        assertThat(projectActivity1).isNotEqualTo(projectActivity2);
    }
}
