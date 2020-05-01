import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IProjectMember, ProjectMember } from 'app/shared/model/project-member.model';
import { ProjectMemberService } from './project-member.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IProject } from 'app/shared/model/project.model';
import { ProjectService } from 'app/entities/project/project.service';

type SelectableEntity = IUser | IProject;

@Component({
  selector: 'jhi-project-member-update',
  templateUrl: './project-member-update.component.html'
})
export class ProjectMemberUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  projects: IProject[] = [];

  editForm = this.fb.group({
    id: [],
    role: [null, [Validators.required]],
    userId: [],
    projectId: []
  });

  constructor(
    protected projectMemberService: ProjectMemberService,
    protected userService: UserService,
    protected projectService: ProjectService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ projectMember }) => {
      this.updateForm(projectMember);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.projectService.query().subscribe((res: HttpResponse<IProject[]>) => (this.projects = res.body || []));
    });
  }

  updateForm(projectMember: IProjectMember): void {
    this.editForm.patchValue({
      id: projectMember.id,
      role: projectMember.role,
      userId: projectMember.userId,
      projectId: projectMember.projectId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const projectMember = this.createFromForm();
    if (projectMember.id !== undefined) {
      this.subscribeToSaveResponse(this.projectMemberService.update(projectMember));
    } else {
      this.subscribeToSaveResponse(this.projectMemberService.create(projectMember));
    }
  }

  private createFromForm(): IProjectMember {
    return {
      ...new ProjectMember(),
      id: this.editForm.get(['id'])!.value,
      role: this.editForm.get(['role'])!.value,
      userId: this.editForm.get(['userId'])!.value,
      projectId: this.editForm.get(['projectId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProjectMember>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
