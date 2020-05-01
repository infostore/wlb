import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ILabelGroup, LabelGroup } from 'app/shared/model/label-group.model';
import { LabelGroupService } from './label-group.service';

@Component({
  selector: 'jhi-label-group-update',
  templateUrl: './label-group-update.component.html'
})
export class LabelGroupUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(10)]]
  });

  constructor(protected labelGroupService: LabelGroupService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ labelGroup }) => {
      this.updateForm(labelGroup);
    });
  }

  updateForm(labelGroup: ILabelGroup): void {
    this.editForm.patchValue({
      id: labelGroup.id,
      name: labelGroup.name
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const labelGroup = this.createFromForm();
    if (labelGroup.id !== undefined) {
      this.subscribeToSaveResponse(this.labelGroupService.update(labelGroup));
    } else {
      this.subscribeToSaveResponse(this.labelGroupService.create(labelGroup));
    }
  }

  private createFromForm(): ILabelGroup {
    return {
      ...new LabelGroup(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILabelGroup>>): void {
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
}
