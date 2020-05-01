import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WlbSharedModule } from 'app/shared/shared.module';
import { MilestoneComponent } from './milestone.component';
import { MilestoneDetailComponent } from './milestone-detail.component';
import { MilestoneUpdateComponent } from './milestone-update.component';
import { MilestoneDeleteDialogComponent } from './milestone-delete-dialog.component';
import { milestoneRoute } from './milestone.route';

@NgModule({
  imports: [WlbSharedModule, RouterModule.forChild(milestoneRoute)],
  declarations: [MilestoneComponent, MilestoneDetailComponent, MilestoneUpdateComponent, MilestoneDeleteDialogComponent],
  entryComponents: [MilestoneDeleteDialogComponent]
})
export class WlbMilestoneModule {}
