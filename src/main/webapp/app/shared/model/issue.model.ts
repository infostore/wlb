import { Moment } from 'moment';
import { IIssueAssignee } from 'app/shared/model/issue-assignee.model';
import { IIssueWatcher } from 'app/shared/model/issue-watcher.model';
import { IComment } from 'app/shared/model/comment.model';
import { IIssueLabel } from 'app/shared/model/issue-label.model';
import { IIssueAttachment } from 'app/shared/model/issue-attachment.model';
import { IIssueActivity } from 'app/shared/model/issue-activity.model';
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
  assignees?: IIssueAssignee[];
  watchers?: IIssueWatcher[];
  comments?: IComment[];
  issueLabels?: IIssueLabel[];
  issueAttachments?: IIssueAttachment[];
  issueActivities?: IIssueActivity[];
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
    public assignees?: IIssueAssignee[],
    public watchers?: IIssueWatcher[],
    public comments?: IComment[],
    public issueLabels?: IIssueLabel[],
    public issueAttachments?: IIssueAttachment[],
    public issueActivities?: IIssueActivity[],
    public projectName?: string,
    public projectId?: number,
    public milestoneName?: string,
    public milestoneId?: number
  ) {}
}
