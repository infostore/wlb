import { IIssueLabel } from 'app/shared/model/issue-label.model';

export interface ILabel {
  id?: number;
  name?: string;
  issueLabels?: IIssueLabel[];
  labelGroupName?: string;
  labelGroupId?: number;
}

export class Label implements ILabel {
  constructor(
    public id?: number,
    public name?: string,
    public issueLabels?: IIssueLabel[],
    public labelGroupName?: string,
    public labelGroupId?: number
  ) {}
}
