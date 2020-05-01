import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ILabel, Label } from 'app/shared/model/label.model';
import { LabelService } from './label.service';
import { ILabelGroup } from 'app/shared/model/label-group.model';
import { LabelGroupService } from 'app/entities/label-group/label-group.service';

@Component({
  selector: 'jhi-label-update',
  templateUrl: './label-update.component.html'
})
export class LabelUpdateComponent implements OnInit {
  isSaving = false;
  labelgroups: ILabelGroup[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(10)]],
    labelGroupId: []
  });

  constructor(
    protected labelService: LabelService,
    protected labelGroupService: LabelGroupService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ label }) => {
      this.updateForm(label);

      this.labelGroupService.query().subscribe((res: HttpResponse<ILabelGroup[]>) => (this.labelgroups = res.body || []));
    });
  }

  updateForm(label: ILabel): void {
    this.editForm.patchValue({
      id: label.id,
      name: label.name,
      labelGroupId: label.labelGroupId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const label = this.createFromForm();
    if (label.id !== undefined) {
      this.subscribeToSaveResponse(this.labelService.update(label));
    } else {
      this.subscribeToSaveResponse(this.labelService.create(label));
    }
  }

  private createFromForm(): ILabel {
    return {
      ...new Label(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      labelGroupId: this.editForm.get(['labelGroupId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILabel>>): void {
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

  trackById(index: number, item: ILabelGroup): any {
    return item.id;
  }
}
