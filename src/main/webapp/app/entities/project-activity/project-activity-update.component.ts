import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IProjectActivity, ProjectActivity } from 'app/shared/model/project-activity.model';
import { ProjectActivityService } from './project-activity.service';
import { IProject } from 'app/shared/model/project.model';
import { ProjectService } from 'app/entities/project/project.service';

@Component({
  selector: 'jhi-project-activity-update',
  templateUrl: './project-activity-update.component.html'
})
export class ProjectActivityUpdateComponent implements OnInit {
  isSaving = false;
  projects: IProject[] = [];

  editForm = this.fb.group({
    id: [],
    activity: [null, [Validators.required, Validators.maxLength(4000)]],
    projectId: []
  });

  constructor(
    protected projectActivityService: ProjectActivityService,
    protected projectService: ProjectService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ projectActivity }) => {
      this.updateForm(projectActivity);

      this.projectService.query().subscribe((res: HttpResponse<IProject[]>) => (this.projects = res.body || []));
    });
  }

  updateForm(projectActivity: IProjectActivity): void {
    this.editForm.patchValue({
      id: projectActivity.id,
      activity: projectActivity.activity,
      projectId: projectActivity.projectId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const projectActivity = this.createFromForm();
    if (projectActivity.id !== undefined) {
      this.subscribeToSaveResponse(this.projectActivityService.update(projectActivity));
    } else {
      this.subscribeToSaveResponse(this.projectActivityService.create(projectActivity));
    }
  }

  private createFromForm(): IProjectActivity {
    return {
      ...new ProjectActivity(),
      id: this.editForm.get(['id'])!.value,
      activity: this.editForm.get(['activity'])!.value,
      projectId: this.editForm.get(['projectId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProjectActivity>>): void {
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

  trackById(index: number, item: IProject): any {
    return item.id;
  }
}
