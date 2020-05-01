import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IIssueLabel, IssueLabel } from 'app/shared/model/issue-label.model';
import { IssueLabelService } from './issue-label.service';
import { IIssue } from 'app/shared/model/issue.model';
import { IssueService } from 'app/entities/issue/issue.service';
import { ILabel } from 'app/shared/model/label.model';
import { LabelService } from 'app/entities/label/label.service';

type SelectableEntity = IIssue | ILabel;

@Component({
  selector: 'jhi-issue-label-update',
  templateUrl: './issue-label-update.component.html'
})
export class IssueLabelUpdateComponent implements OnInit {
  isSaving = false;
  issues: IIssue[] = [];
  labels: ILabel[] = [];

  editForm = this.fb.group({
    id: [],
    issueId: [],
    labelId: []
  });

  constructor(
    protected issueLabelService: IssueLabelService,
    protected issueService: IssueService,
    protected labelService: LabelService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ issueLabel }) => {
      this.updateForm(issueLabel);

      this.issueService.query().subscribe((res: HttpResponse<IIssue[]>) => (this.issues = res.body || []));

      this.labelService.query().subscribe((res: HttpResponse<ILabel[]>) => (this.labels = res.body || []));
    });
  }

  updateForm(issueLabel: IIssueLabel): void {
    this.editForm.patchValue({
      id: issueLabel.id,
      issueId: issueLabel.issueId,
      labelId: issueLabel.labelId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const issueLabel = this.createFromForm();
    if (issueLabel.id !== undefined) {
      this.subscribeToSaveResponse(this.issueLabelService.update(issueLabel));
    } else {
      this.subscribeToSaveResponse(this.issueLabelService.create(issueLabel));
    }
  }

  private createFromForm(): IIssueLabel {
    return {
      ...new IssueLabel(),
      id: this.editForm.get(['id'])!.value,
      issueId: this.editForm.get(['issueId'])!.value,
      labelId: this.editForm.get(['labelId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIssueLabel>>): void {
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
