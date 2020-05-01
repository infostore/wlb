import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { WlbTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { IssueWatcherDeleteDialogComponent } from 'app/entities/issue-watcher/issue-watcher-delete-dialog.component';
import { IssueWatcherService } from 'app/entities/issue-watcher/issue-watcher.service';

describe('Component Tests', () => {
  describe('IssueWatcher Management Delete Component', () => {
    let comp: IssueWatcherDeleteDialogComponent;
    let fixture: ComponentFixture<IssueWatcherDeleteDialogComponent>;
    let service: IssueWatcherService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WlbTestModule],
        declarations: [IssueWatcherDeleteDialogComponent]
      })
        .overrideTemplate(IssueWatcherDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IssueWatcherDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IssueWatcherService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
