export interface IIssueWatcher {
  id?: number;
  userId?: number;
  issueId?: number;
}

export class IssueWatcher implements IIssueWatcher {
  constructor(public id?: number, public userId?: number, public issueId?: number) {}
}
