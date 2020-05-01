import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { WlbTestModule } from '../../../test.module';
import { ProjectMemberUpdateComponent } from 'app/entities/project-member/project-member-update.component';
import { ProjectMemberService } from 'app/entities/project-member/project-member.service';
import { ProjectMember } from 'app/shared/model/project-member.model';

describe('Component Tests', () => {
  describe('ProjectMember Management Update Component', () => {
    let comp: ProjectMemberUpdateComponent;
    let fixture: ComponentFixture<ProjectMemberUpdateComponent>;
    let service: ProjectMemberService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WlbTestModule],
        declarations: [ProjectMemberUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProjectMemberUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProjectMemberUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProjectMemberService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProjectMember(123);
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
        const entity = new ProjectMember();
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
