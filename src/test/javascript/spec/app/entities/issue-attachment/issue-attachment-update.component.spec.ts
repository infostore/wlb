import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { WlbTestModule } from '../../../test.module';
import { IssueAttachmentUpdateComponent } from 'app/entities/issue-attachment/issue-attachment-update.component';
import { IssueAttachmentService } from 'app/entities/issue-attachment/issue-attachment.service';
import { IssueAttachment } from 'app/shared/model/issue-attachment.model';

describe('Component Tests', () => {
  describe('IssueAttachment Management Update Component', () => {
    let comp: IssueAttachmentUpdateComponent;
    let fixture: ComponentFixture<IssueAttachmentUpdateComponent>;
    let service: IssueAttachmentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WlbTestModule],
        declarations: [IssueAttachmentUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(IssueAttachmentUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IssueAttachmentUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IssueAttachmentService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new IssueAttachment(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new IssueAttachment();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
