import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProjectActivity } from 'app/shared/model/project-activity.model';
import { ProjectActivityService } from './project-activity.service';

@Component({
  templateUrl: './project-activity-delete-dialog.component.html'
})
export class ProjectActivityDeleteDialogComponent {
  projectActivity?: IProjectActivity;

  constructor(
    protected projectActivityService: ProjectActivityService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.projectActivityService.delete(id).subscribe(() => {
      this.eventManager.broadcast('projectActivityListModification');
      this.activeModal.close();
    });
  }
}
