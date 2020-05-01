import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIssueAssignee } from 'app/shared/model/issue-assignee.model';
import { IssueAssigneeService } from './issue-assignee.service';

@Component({
  templateUrl: './issue-assignee-delete-dialog.component.html'
})
export class IssueAssigneeDeleteDialogComponent {
  issueAssignee?: IIssueAssignee;

  constructor(
    protected issueAssigneeService: IssueAssigneeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.issueAssigneeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('issueAssigneeListModification');
      this.activeModal.close();
    });
  }
}
