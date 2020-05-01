import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { WlbTestModule } from '../../../test.module';
import { LabelGroupUpdateComponent } from 'app/entities/label-group/label-group-update.component';
import { LabelGroupService } from 'app/entities/label-group/label-group.service';
import { LabelGroup } from 'app/shared/model/label-group.model';

describe('Component Tests', () => {
  describe('LabelGroup Management Update Component', () => {
    let comp: LabelGroupUpdateComponent;
    let fixture: ComponentFixture<LabelGroupUpdateComponent>;
    let service: LabelGroupService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WlbTestModule],
        declarations: [LabelGroupUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(LabelGroupUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LabelGroupUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LabelGroupService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new LabelGroup(123);
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
        const entity = new LabelGroup();
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
