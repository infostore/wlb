import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILabelGroup } from 'app/shared/model/label-group.model';
import { LabelGroupService } from './label-group.service';

@Component({
  templateUrl: './label-group-delete-dialog.component.html'
})
export class LabelGroupDeleteDialogComponent {
  labelGroup?: ILabelGroup;

  constructor(
    protected labelGroupService: LabelGroupService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.labelGroupService.delete(id).subscribe(() => {
      this.eventManager.broadcast('labelGroupListModification');
      this.activeModal.close();
    });
  }
}
