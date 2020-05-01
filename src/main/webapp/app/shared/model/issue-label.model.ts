export interface IIssueLabel {
  id?: number;
  issueId?: number;
  labelId?: number;
}

export class IssueLabel implements IIssueLabel {
  constructor(public id?: number, public issueId?: number, public labelId?: number) {}
}
