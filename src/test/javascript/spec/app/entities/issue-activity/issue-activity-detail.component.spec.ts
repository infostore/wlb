import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WlbTestModule } from '../../../test.module';
import { IssueActivityDetailComponent } from 'app/entities/issue-activity/issue-activity-detail.component';
import { IssueActivity } from 'app/shared/model/issue-activity.model';

describe('Component Tests', () => {
  describe('IssueActivity Management Detail Component', () => {
    let comp: IssueActivityDetailComponent;
    let fixture: ComponentFixture<IssueActivityDetailComponent>;
    const route = ({ data: of({ issueActivity: new IssueActivity(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WlbTestModule],
        declarations: [IssueActivityDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(IssueActivityDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IssueActivityDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load issueActivity on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.issueActivity).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
