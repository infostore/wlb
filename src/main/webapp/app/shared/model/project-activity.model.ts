export interface IProjectActivity {
  id?: number;
  activity?: string;
  projectId?: number;
}

export class ProjectActivity implements IProjectActivity {
  constructor(public id?: number, public activity?: string, public projectId?: number) {}
}
