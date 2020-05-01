import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WlbTestModule } from '../../../test.module';
import { IssueAssigneeDetailComponent } from 'app/entities/issue-assignee/issue-assignee-detail.component';
import { IssueAssignee } from 'app/shared/model/issue-assignee.model';

describe('Component Tests', () => {
  describe('IssueAssignee Management Detail Component', () => {
    let comp: IssueAssigneeDetailComponent;
    let fixture: ComponentFixture<IssueAssigneeDetailComponent>;
    const route = ({ data: of({ issueAssignee: new IssueAssignee(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WlbTestModule],
        declarations: [IssueAssigneeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(IssueAssigneeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IssueAssigneeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load issueAssignee on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.issueAssignee).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
