import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIssueActivity } from 'app/shared/model/issue-activity.model';
import { IssueActivityService } from './issue-activity.service';

@Component({
  templateUrl: './issue-activity-delete-dialog.component.html'
})
export class IssueActivityDeleteDialogComponent {
  issueActivity?: IIssueActivity;

  constructor(
    protected issueActivityService: IssueActivityService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.issueActivityService.delete(id).subscribe(() => {
      this.eventManager.broadcast('issueActivityListModification');
      this.activeModal.close();
    });
  }
}
