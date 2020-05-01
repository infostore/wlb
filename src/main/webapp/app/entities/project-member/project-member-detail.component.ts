import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProjectMember } from 'app/shared/model/project-member.model';

@Component({
  selector: 'jhi-project-member-detail',
  templateUrl: './project-member-detail.component.html'
})
export class ProjectMemberDetailComponent implements OnInit {
  projectMember: IProjectMember | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ projectMember }) => (this.projectMember = projectMember));
  }

  previousState(): void {
    window.history.back();
  }
}
