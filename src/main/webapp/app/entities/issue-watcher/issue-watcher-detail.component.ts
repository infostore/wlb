import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIssueWatcher } from 'app/shared/model/issue-watcher.model';

@Component({
  selector: 'jhi-issue-watcher-detail',
  templateUrl: './issue-watcher-detail.component.html'
})
export class IssueWatcherDetailComponent implements OnInit {
  issueWatcher: IIssueWatcher | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ issueWatcher }) => (this.issueWatcher = issueWatcher));
  }

  previousState(): void {
    window.history.back();
  }
}
