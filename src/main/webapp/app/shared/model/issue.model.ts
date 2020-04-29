import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { IComment } from 'app/shared/model/comment.model';
import { IssueType } from 'app/shared/model/enumerations/issue-type.model';
import { IssueStatus } from 'app/shared/model/enumerations/issue-status.model';
import { Priority } from 'app/shared/model/enumerations/priority.model';
import { Resolution } from 'app/shared/model/enumerations/resolution.model';

export interface IIssue {
  id?: number;
  name?: string;
  description?: any;
  issueType?: IssueType;
  issueStatus?: IssueStatus;
  priority?: Priority;
  resolution?: Resolution;
  dueDate?: Moment;
  assignees?: IUser[];
  watchers?: IUser[];
  comments?: IComment[];
  projectName?: string;
  projectId?: number;
  milestoneName?: string;
  milestoneId?: number;
}

export class Issue implements IIssue {
  constructor(
    public id?: number,
    public name?: string,
    public description?: any,
    public issueType?: IssueType,
    public issueStatus?: IssueStatus,
    public priority?: Priority,
    public resolution?: Resolution,
    public dueDate?: Moment,
    public assignees?: IUser[],
    public watchers?: IUser[],
    public comments?: IComment[],
    public projectName?: string,
    public projectId?: number,
    public milestoneName?: string,
    public milestoneId?: number
  ) {}
}
