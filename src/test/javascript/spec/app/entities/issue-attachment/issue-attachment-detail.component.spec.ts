import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WlbTestModule } from '../../../test.module';
import { IssueAttachmentDetailComponent } from 'app/entities/issue-attachment/issue-attachment-detail.component';
import { IssueAttachment } from 'app/shared/model/issue-attachment.model';

describe('Component Tests', () => {
  describe('IssueAttachment Management Detail Component', () => {
    let comp: IssueAttachmentDetailComponent;
    let fixture: ComponentFixture<IssueAttachmentDetailComponent>;
    const route = ({ data: of({ issueAttachment: new IssueAttachment(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WlbTestModule],
        declarations: [IssueAttachmentDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(IssueAttachmentDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IssueAttachmentDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load issueAttachment on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.issueAttachment).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
