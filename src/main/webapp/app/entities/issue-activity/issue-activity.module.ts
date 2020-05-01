import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WlbSharedModule } from 'app/shared/shared.module';
import { IssueActivityComponent } from './issue-activity.component';
import { IssueActivityDetailComponent } from './issue-activity-detail.component';
import { IssueActivityUpdateComponent } from './issue-activity-update.component';
import { IssueActivityDeleteDialogComponent } from './issue-activity-delete-dialog.component';
import { issueActivityRoute } from './issue-activity.route';

@NgModule({
  imports: [WlbSharedModule, RouterModule.forChild(issueActivityRoute)],
  declarations: [IssueActivityComponent, IssueActivityDetailComponent, IssueActivityUpdateComponent, IssueActivityDeleteDialogComponent],
  entryComponents: [IssueActivityDeleteDialogComponent]
})
export class WlbIssueActivityModule {}
