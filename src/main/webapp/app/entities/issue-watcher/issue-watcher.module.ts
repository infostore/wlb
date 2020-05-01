import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WlbSharedModule } from 'app/shared/shared.module';
import { IssueWatcherComponent } from './issue-watcher.component';
import { IssueWatcherDetailComponent } from './issue-watcher-detail.component';
import { IssueWatcherUpdateComponent } from './issue-watcher-update.component';
import { IssueWatcherDeleteDialogComponent } from './issue-watcher-delete-dialog.component';
import { issueWatcherRoute } from './issue-watcher.route';

@NgModule({
  imports: [WlbSharedModule, RouterModule.forChild(issueWatcherRoute)],
  declarations: [IssueWatcherComponent, IssueWatcherDetailComponent, IssueWatcherUpdateComponent, IssueWatcherDeleteDialogComponent],
  entryComponents: [IssueWatcherDeleteDialogComponent]
})
export class WlbIssueWatcherModule {}
