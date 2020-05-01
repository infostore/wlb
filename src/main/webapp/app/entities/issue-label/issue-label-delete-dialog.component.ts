import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIssueLabel } from 'app/shared/model/issue-label.model';
import { IssueLabelService } from './issue-label.service';

@Component({
  templateUrl: './issue-label-delete-dialog.component.html'
})
export class IssueLabelDeleteDialogComponent {
  issueLabel?: IIssueLabel;

  constructor(
    protected issueLabelService: IssueLabelService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.issueLabelService.delete(id).subscribe(() => {
      this.eventManager.broadcast('issueLabelListModification');
      this.activeModal.close();
    });
  }
}
