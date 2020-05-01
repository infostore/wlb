import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WlbSharedModule } from 'app/shared/shared.module';
import { IssueAssigneeComponent } from './issue-assignee.component';
import { IssueAssigneeDetailComponent } from './issue-assignee-detail.component';
import { IssueAssigneeUpdateComponent } from './issue-assignee-update.component';
import { IssueAssigneeDeleteDialogComponent } from './issue-assignee-delete-dialog.component';
import { issueAssigneeRoute } from './issue-assignee.route';

@NgModule({
  imports: [WlbSharedModule, RouterModule.forChild(issueAssigneeRoute)],
  declarations: [IssueAssigneeComponent, IssueAssigneeDetailComponent, IssueAssigneeUpdateComponent, IssueAssigneeDeleteDialogComponent],
  entryComponents: [IssueAssigneeDeleteDialogComponent]
})
export class WlbIssueAssigneeModule {}
