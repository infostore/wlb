import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIssueActivity } from 'app/shared/model/issue-activity.model';

@Component({
  selector: 'jhi-issue-activity-detail',
  templateUrl: './issue-activity-detail.component.html'
})
export class IssueActivityDetailComponent implements OnInit {
  issueActivity: IIssueActivity | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ issueActivity }) => (this.issueActivity = issueActivity));
  }

  previousState(): void {
    window.history.back();
  }
}
