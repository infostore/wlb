import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { WlbTestModule } from '../../../test.module';
import { IssueLabelUpdateComponent } from 'app/entities/issue-label/issue-label-update.component';
import { IssueLabelService } from 'app/entities/issue-label/issue-label.service';
import { IssueLabel } from 'app/shared/model/issue-label.model';

describe('Component Tests', () => {
  describe('IssueLabel Management Update Component', () => {
    let comp: IssueLabelUpdateComponent;
    let fixture: ComponentFixture<IssueLabelUpdateComponent>;
    let service: IssueLabelService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WlbTestModule],
        declarations: [IssueLabelUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(IssueLabelUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IssueLabelUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IssueLabelService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new IssueLabel(123);
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
        const entity = new IssueLabel();
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
