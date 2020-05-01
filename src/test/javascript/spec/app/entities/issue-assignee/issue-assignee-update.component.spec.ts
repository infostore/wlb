import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { WlbTestModule } from '../../../test.module';
import { IssueAssigneeUpdateComponent } from 'app/entities/issue-assignee/issue-assignee-update.component';
import { IssueAssigneeService } from 'app/entities/issue-assignee/issue-assignee.service';
import { IssueAssignee } from 'app/shared/model/issue-assignee.model';

describe('Component Tests', () => {
  describe('IssueAssignee Management Update Component', () => {
    let comp: IssueAssigneeUpdateComponent;
    let fixture: ComponentFixture<IssueAssigneeUpdateComponent>;
    let service: IssueAssigneeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WlbTestModule],
        declarations: [IssueAssigneeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(IssueAssigneeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IssueAssigneeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IssueAssigneeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new IssueAssignee(123);
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
        const entity = new IssueAssignee();
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
