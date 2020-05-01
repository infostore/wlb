import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IIssueWatcher, IssueWatcher } from 'app/shared/model/issue-watcher.model';
import { IssueWatcherService } from './issue-watcher.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IIssue } from 'app/shared/model/issue.model';
import { IssueService } from 'app/entities/issue/issue.service';

type SelectableEntity = IUser | IIssue;

@Component({
  selector: 'jhi-issue-watcher-update',
  templateUrl: './issue-watcher-update.component.html'
})
export class IssueWatcherUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  issues: IIssue[] = [];

  editForm = this.fb.group({
    id: [],
    userId: [],
    issueId: []
  });

  constructor(
    protected issueWatcherService: IssueWatcherService,
    protected userService: UserService,
    protected issueService: IssueService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ issueWatcher }) => {
      this.updateForm(issueWatcher);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.issueService.query().subscribe((res: HttpResponse<IIssue[]>) => (this.issues = res.body || []));
    });
  }

  updateForm(issueWatcher: IIssueWatcher): void {
    this.editForm.patchValue({
      id: issueWatcher.id,
      userId: issueWatcher.userId,
      issueId: issueWatcher.issueId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const issueWatcher = this.createFromForm();
    if (issueWatcher.id !== undefined) {
      this.subscribeToSaveResponse(this.issueWatcherService.update(issueWatcher));
    } else {
      this.subscribeToSaveResponse(this.issueWatcherService.create(issueWatcher));
    }
  }

  private createFromForm(): IIssueWatcher {
    return {
      ...new IssueWatcher(),
      id: this.editForm.get(['id'])!.value,
      userId: this.editForm.get(['userId'])!.value,
      issueId: this.editForm.get(['issueId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIssueWatcher>>): void {
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
