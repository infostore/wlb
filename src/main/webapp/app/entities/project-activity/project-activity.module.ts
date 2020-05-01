import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WlbSharedModule } from 'app/shared/shared.module';
import { ProjectActivityComponent } from './project-activity.component';
import { ProjectActivityDetailComponent } from './project-activity-detail.component';
import { ProjectActivityUpdateComponent } from './project-activity-update.component';
import { ProjectActivityDeleteDialogComponent } from './project-activity-delete-dialog.component';
import { projectActivityRoute } from './project-activity.route';

@NgModule({
  imports: [WlbSharedModule, RouterModule.forChild(projectActivityRoute)],
  declarations: [
    ProjectActivityComponent,
    ProjectActivityDetailComponent,
    ProjectActivityUpdateComponent,
    ProjectActivityDeleteDialogComponent
  ],
  entryComponents: [ProjectActivityDeleteDialogComponent]
})
export class WlbProjectActivityModule {}
