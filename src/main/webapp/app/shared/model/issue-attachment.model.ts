export interface IIssueAttachment {
  id?: number;
  attachmentId?: number;
  issueId?: number;
}

export class IssueAttachment implements IIssueAttachment {
  constructor(public id?: number, public attachmentId?: number, public issueId?: number) {}
}
