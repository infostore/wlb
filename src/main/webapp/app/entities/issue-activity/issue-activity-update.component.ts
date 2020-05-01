import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IIssueActivity, IssueActivity } from 'app/shared/model/issue-activity.model';
import { IssueActivityService } from './issue-activity.service';
import { IIssue } from 'app/shared/model/issue.model';
import { IssueService } from 'app/entities/issue/issue.service';

@Component({
  selector: 'jhi-issue-activity-update',
  templateUrl: './issue-activity-update.component.html'
})
export class IssueActivityUpdateComponent implements OnInit {
  isSaving = false;
  issues: IIssue[] = [];

  editForm = this.fb.group({
    id: [],
    activity: [null, [Validators.required, Validators.maxLength(4000)]],
    issueId: []
  });

  constructor(
    protected issueActivityService: IssueActivityService,
    protected issueService: IssueService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ issueActivity }) => {
      this.updateForm(issueActivity);

      this.issueService.query().subscribe((res: HttpResponse<IIssue[]>) => (this.issues = res.body || []));
    });
  }

  updateForm(issueActivity: IIssueActivity): void {
    this.editForm.patchValue({
      id: issueActivity.id,
      activity: issueActivity.activity,
      issueId: issueActivity.issueId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const issueActivity = this.createFromForm();
    if (issueActivity.id !== undefined) {
      this.subscribeToSaveResponse(this.issueActivityService.update(issueActivity));
    } else {
      this.subscribeToSaveResponse(this.issueActivityService.create(issueActivity));
    }
  }

  private createFromForm(): IIssueActivity {
    return {
      ...new IssueActivity(),
      id: this.editForm.get(['id'])!.value,
      activity: this.editForm.get(['activity'])!.value,
      issueId: this.editForm.get(['issueId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIssueActivity>>): void {
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

  trackById(index: number, item: IIssue): any {
    return item.id;
  }
}
