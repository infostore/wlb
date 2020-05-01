package kr.etcsoft.wlb.web.rest.vm;

import kr.etcsoft.wlb.domain.enumeration.MilestoneStatus;
import kr.etcsoft.wlb.service.dto.MilestoneDTO;

import java.util.ArrayList;
import java.util.List;

public class MilestoneVM extends MilestoneDTO {
    private List<MilestoneStatus> milestoneStatuses = new ArrayList<>();

    public List<MilestoneStatus> getMilestoneStatuses() {
        return milestoneStatuses;
    }

    public void setMilestoneStatuses(List<MilestoneStatus> milestoneStatuses) {
        this.milestoneStatuses = milestoneStatuses;
    }
}
