import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { WlbTestModule } from '../../../test.module';
import { IssueActivityUpdateComponent } from 'app/entities/issue-activity/issue-activity-update.component';
import { IssueActivityService } from 'app/entities/issue-activity/issue-activity.service';
import { IssueActivity } from 'app/shared/model/issue-activity.model';

describe('Component Tests', () => {
  describe('IssueActivity Management Update Component', () => {
    let comp: IssueActivityUpdateComponent;
    let fixture: ComponentFixture<IssueActivityUpdateComponent>;
    let service: IssueActivityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WlbTestModule],
        declarations: [IssueActivityUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(IssueActivityUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IssueActivityUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IssueActivityService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new IssueActivity(123);
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
        const entity = new IssueActivity();
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
