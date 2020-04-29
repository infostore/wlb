import { Moment } from 'moment';
import { MilestoneStatus } from 'app/shared/model/enumerations/milestone-status.model';

export interface IMilestone {
  id?: number;
  name?: string;
  description?: any;
  milestoneStatus?: MilestoneStatus;
  dueDate?: Moment;
  projectName?: string;
  projectId?: number;
}

export class Milestone implements IMilestone {
  constructor(
    public id?: number,
    public name?: string,
    public description?: any,
    public milestoneStatus?: MilestoneStatus,
    public dueDate?: Moment,
    public projectName?: string,
    public projectId?: number
  ) {}
}
