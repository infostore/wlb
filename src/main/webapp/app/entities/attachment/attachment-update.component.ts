import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAttachment, Attachment } from 'app/shared/model/attachment.model';
import { AttachmentService } from './attachment.service';

@Component({
  selector: 'jhi-attachment-update',
  templateUrl: './attachment-update.component.html'
})
export class AttachmentUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    path: [null, [Validators.required]]
  });

  constructor(protected attachmentService: AttachmentService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ attachment }) => {
      this.updateForm(attachment);
    });
  }

  updateForm(attachment: IAttachment): void {
    this.editForm.patchValue({
      id: attachment.id,
      name: attachment.name,
      path: attachment.path
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const attachment = this.createFromForm();
    if (attachment.id !== undefined) {
      this.subscribeToSaveResponse(this.attachmentService.update(attachment));
    } else {
      this.subscribeToSaveResponse(this.attachmentService.create(attachment));
    }
  }

  private createFromForm(): IAttachment {
    return {
      ...new Attachment(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      path: this.editForm.get(['path'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAttachment>>): void {
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
