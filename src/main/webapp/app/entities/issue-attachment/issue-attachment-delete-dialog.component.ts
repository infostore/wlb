import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIssueAttachment } from 'app/shared/model/issue-attachment.model';
import { IssueAttachmentService } from './issue-attachment.service';

@Component({
  templateUrl: './issue-attachment-delete-dialog.component.html'
})
export class IssueAttachmentDeleteDialogComponent {
  issueAttachment?: IIssueAttachment;

  constructor(
    protected issueAttachmentService: IssueAttachmentService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.issueAttachmentService.delete(id).subscribe(() => {
      this.eventManager.broadcast('issueAttachmentListModification');
      this.activeModal.close();
    });
  }
}
