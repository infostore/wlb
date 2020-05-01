export interface IAttachment {
  id?: number;
  name?: string;
  path?: string;
}

export class Attachment implements IAttachment {
  constructor(public id?: number, public name?: string, public path?: string) {}
}
