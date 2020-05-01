export interface IIssueActivity {
  id?: number;
  activity?: string;
  issueId?: number;
}

export class IssueActivity implements IIssueActivity {
  constructor(public id?: number, public activity?: string, public issueId?: number) {}
}
