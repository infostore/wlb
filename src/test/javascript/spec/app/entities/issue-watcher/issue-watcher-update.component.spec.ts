import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { WlbTestModule } from '../../../test.module';
import { IssueWatcherUpdateComponent } from 'app/entities/issue-watcher/issue-watcher-update.component';
import { IssueWatcherService } from 'app/entities/issue-watcher/issue-watcher.service';
import { IssueWatcher } from 'app/shared/model/issue-watcher.model';

describe('Component Tests', () => {
  describe('IssueWatcher Management Update Component', () => {
    let comp: IssueWatcherUpdateComponent;
    let fixture: ComponentFixture<IssueWatcherUpdateComponent>;
    let service: IssueWatcherService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WlbTestModule],
        declarations: [IssueWatcherUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(IssueWatcherUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IssueWatcherUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IssueWatcherService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new IssueWatcher(123);
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
        const entity = new IssueWatcher();
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
