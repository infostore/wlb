export interface IProject {
  id?: number;
  name?: string;
  description?: any;
}

export class Project implements IProject {
  constructor(public id?: number, public name?: string, public description?: any) {}
}
