export interface IComment {
  id?: number;
  description?: any;
  issueId?: number;
}

export class Comment implements IComment {
  constructor(public id?: number, public description?: any, public issueId?: number) {}
}
