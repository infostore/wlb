import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { WlbTestModule } from '../../../test.module';
import { IssueAttachmentComponent } from 'app/entities/issue-attachment/issue-attachment.component';
import { IssueAttachmentService } from 'app/entities/issue-attachment/issue-attachment.service';
import { IssueAttachment } from 'app/shared/model/issue-attachment.model';

describe('Component Tests', () => {
  describe('IssueAttachment Management Component', () => {
    let comp: IssueAttachmentComponent;
    let fixture: ComponentFixture<IssueAttachmentComponent>;
    let service: IssueAttachmentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WlbTestModule],
        declarations: [IssueAttachmentComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: {
                subscribe: (fn: (value: Data) => void) =>
                  fn({
                    pagingParams: {
                      predicate: 'id',
                      reverse: false,
                      page: 0
                    }
                  })
              }
            }
          }
        ]
      })
        .overrideTemplate(IssueAttachmentComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IssueAttachmentComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IssueAttachmentService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new IssueAttachment(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.issueAttachments && comp.issueAttachments[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new IssueAttachment(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.issueAttachments && comp.issueAttachments[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
