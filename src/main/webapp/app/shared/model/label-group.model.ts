export interface ILabelGroup {
  id?: number;
  name?: string;
}

export class LabelGroup implements ILabelGroup {
  constructor(public id?: number, public name?: string) {}
}
