import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIssueWatcher } from 'app/shared/model/issue-watcher.model';
import { IssueWatcherService } from './issue-watcher.service';

@Component({
  templateUrl: './issue-watcher-delete-dialog.component.html'
})
export class IssueWatcherDeleteDialogComponent {
  issueWatcher?: IIssueWatcher;

  constructor(
    protected issueWatcherService: IssueWatcherService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.issueWatcherService.delete(id).subscribe(() => {
      this.eventManager.broadcast('issueWatcherListModification');
      this.activeModal.close();
    });
  }
}
