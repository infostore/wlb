import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProjectMember } from 'app/shared/model/project-member.model';
import { ProjectMemberService } from './project-member.service';

@Component({
  templateUrl: './project-member-delete-dialog.component.html'
})
export class ProjectMemberDeleteDialogComponent {
  projectMember?: IProjectMember;

  constructor(
    protected projectMemberService: ProjectMemberService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.projectMemberService.delete(id).subscribe(() => {
      this.eventManager.broadcast('projectMemberListModification');
      this.activeModal.close();
    });
  }
}
