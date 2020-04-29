package kr.etcsoft.wlb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import kr.etcsoft.wlb.web.rest.TestUtil;

public class MilestoneTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Milestone.class);
        Milestone milestone1 = new Milestone();
        milestone1.setId(1L);
        Milestone milestone2 = new Milestone();
        milestone2.setId(milestone1.getId());
        assertThat(milestone1).isEqualTo(milestone2);
        milestone2.setId(2L);
        assertThat(milestone1).isNotEqualTo(milestone2);
        milestone1.setId(null);
        assertThat(milestone1).isNotEqualTo(milestone2);
    }
}
