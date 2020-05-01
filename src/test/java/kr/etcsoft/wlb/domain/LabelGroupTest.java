package kr.etcsoft.wlb.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import kr.etcsoft.wlb.web.rest.TestUtil;

public class LabelGroupTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LabelGroup.class);
        LabelGroup labelGroup1 = new LabelGroup();
        labelGroup1.setId(1L);
        LabelGroup labelGroup2 = new LabelGroup();
        labelGroup2.setId(labelGroup1.getId());
        assertThat(labelGroup1).isEqualTo(labelGroup2);
        labelGroup2.setId(2L);
        assertThat(labelGroup1).isNotEqualTo(labelGroup2);
        labelGroup1.setId(null);
        assertThat(labelGroup1).isNotEqualTo(labelGroup2);
    }
}
