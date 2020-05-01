import { Role } from 'app/shared/model/enumerations/role.model';

export interface IProjectMember {
  id?: number;
  role?: Role;
  userId?: number;
  projectId?: number;
}

export class ProjectMember implements IProjectMember {
  constructor(public id?: number, public role?: Role, public userId?: number, public projectId?: number) {}
}
