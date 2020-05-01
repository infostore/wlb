export interface IIssueAssignee {
  id?: number;
  userId?: number;
  issueId?: number;
}

export class IssueAssignee implements IIssueAssignee {
  constructor(public id?: number, public userId?: number, public issueId?: number) {}
}
