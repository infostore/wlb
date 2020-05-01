import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WlbSharedModule } from 'app/shared/shared.module';
import { IssueAttachmentComponent } from './issue-attachment.component';
import { IssueAttachmentDetailComponent } from './issue-attachment-detail.component';
import { IssueAttachmentUpdateComponent } from './issue-attachment-update.component';
import { IssueAttachmentDeleteDialogComponent } from './issue-attachment-delete-dialog.component';
import { issueAttachmentRoute } from './issue-attachment.route';

@NgModule({
  imports: [WlbSharedModule, RouterModule.forChild(issueAttachmentRoute)],
  declarations: [
    IssueAttachmentComponent,
    IssueAttachmentDetailComponent,
    IssueAttachmentUpdateComponent,
    IssueAttachmentDeleteDialogComponent
  ],
  entryComponents: [IssueAttachmentDeleteDialogComponent]
})
export class WlbIssueAttachmentModule {}
