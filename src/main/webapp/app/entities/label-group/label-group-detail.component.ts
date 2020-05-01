import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILabelGroup } from 'app/shared/model/label-group.model';

@Component({
  selector: 'jhi-label-group-detail',
  templateUrl: './label-group-detail.component.html'
})
export class LabelGroupDetailComponent implements OnInit {
  labelGroup: ILabelGroup | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ labelGroup }) => (this.labelGroup = labelGroup));
  }

  previousState(): void {
    window.history.back();
  }
}
