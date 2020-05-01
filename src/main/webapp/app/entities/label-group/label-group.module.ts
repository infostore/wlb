import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WlbSharedModule } from 'app/shared/shared.module';
import { LabelGroupComponent } from './label-group.component';
import { LabelGroupDetailComponent } from './label-group-detail.component';
import { LabelGroupUpdateComponent } from './label-group-update.component';
import { LabelGroupDeleteDialogComponent } from './label-group-delete-dialog.component';
import { labelGroupRoute } from './label-group.route';

@NgModule({
  imports: [WlbSharedModule, RouterModule.forChild(labelGroupRoute)],
  declarations: [LabelGroupComponent, LabelGroupDetailComponent, LabelGroupUpdateComponent, LabelGroupDeleteDialogComponent],
  entryComponents: [LabelGroupDeleteDialogComponent]
})
export class WlbLabelGroupModule {}
