import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WlbTestModule } from '../../../test.module';
import { IssueLabelDetailComponent } from 'app/entities/issue-label/issue-label-detail.component';
import { IssueLabel } from 'app/shared/model/issue-label.model';

describe('Component Tests', () => {
  describe('IssueLabel Management Detail Component', () => {
    let comp: IssueLabelDetailComponent;
    let fixture: ComponentFixture<IssueLabelDetailComponent>;
    const route = ({ data: of({ issueLabel: new IssueLabel(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WlbTestModule],
        declarations: [IssueLabelDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(IssueLabelDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IssueLabelDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load issueLabel on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.issueLabel).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
