import { IProjectMember } from 'app/shared/model/project-member.model';
import { IProjectActivity } from 'app/shared/model/project-activity.model';

export interface IProject {
  id?: number;
  name?: string;
  description?: any;
  projectMembers?: IProjectMember[];
  projectActivities?: IProjectActivity[];
}

export class Project implements IProject {
  constructor(
    public id?: number,
    public name?: string,
    public description?: any,
    public projectMembers?: IProjectMember[],
    public projectActivities?: IProjectActivity[]
  ) {}
}
