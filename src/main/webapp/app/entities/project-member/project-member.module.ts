import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WlbSharedModule } from 'app/shared/shared.module';
import { ProjectMemberComponent } from './project-member.component';
import { ProjectMemberDetailComponent } from './project-member-detail.component';
import { ProjectMemberUpdateComponent } from './project-member-update.component';
import { ProjectMemberDeleteDialogComponent } from './project-member-delete-dialog.component';
import { projectMemberRoute } from './project-member.route';

@NgModule({
  imports: [WlbSharedModule, RouterModule.forChild(projectMemberRoute)],
  declarations: [ProjectMemberComponent, ProjectMemberDetailComponent, ProjectMemberUpdateComponent, ProjectMemberDeleteDialogComponent],
  entryComponents: [ProjectMemberDeleteDialogComponent]
})
export class WlbProjectMemberModule {}
