import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WlbSharedModule } from 'app/shared/shared.module';
import { IssueLabelComponent } from './issue-label.component';
import { IssueLabelDetailComponent } from './issue-label-detail.component';
import { IssueLabelUpdateComponent } from './issue-label-update.component';
import { IssueLabelDeleteDialogComponent } from './issue-label-delete-dialog.component';
import { issueLabelRoute } from './issue-label.route';

@NgModule({
  imports: [WlbSharedModule, RouterModule.forChild(issueLabelRoute)],
  declarations: [IssueLabelComponent, IssueLabelDetailComponent, IssueLabelUpdateComponent, IssueLabelDeleteDialogComponent],
  entryComponents: [IssueLabelDeleteDialogComponent]
})
export class WlbIssueLabelModule {}
