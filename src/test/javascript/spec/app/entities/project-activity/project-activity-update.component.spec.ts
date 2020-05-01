import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { WlbTestModule } from '../../../test.module';
import { ProjectActivityUpdateComponent } from 'app/entities/project-activity/project-activity-update.component';
import { ProjectActivityService } from 'app/entities/project-activity/project-activity.service';
import { ProjectActivity } from 'app/shared/model/project-activity.model';

describe('Component Tests', () => {
  describe('ProjectActivity Management Update Component', () => {
    let comp: ProjectActivityUpdateComponent;
    let fixture: ComponentFixture<ProjectActivityUpdateComponent>;
    let service: ProjectActivityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WlbTestModule],
        declarations: [ProjectActivityUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProjectActivityUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProjectActivityUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProjectActivityService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProjectActivity(123);
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
        const entity = new ProjectActivity();
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
