import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIssueLabel } from 'app/shared/model/issue-label.model';

@Component({
  selector: 'jhi-issue-label-detail',
  templateUrl: './issue-label-detail.component.html'
})
export class IssueLabelDetailComponent implements OnInit {
  issueLabel: IIssueLabel | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ issueLabel }) => (this.issueLabel = issueLabel));
  }

  previousState(): void {
    window.history.back();
  }
}
