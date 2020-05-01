import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WlbTestModule } from '../../../test.module';
import { ProjectActivityDetailComponent } from 'app/entities/project-activity/project-activity-detail.component';
import { ProjectActivity } from 'app/shared/model/project-activity.model';

describe('Component Tests', () => {
  describe('ProjectActivity Management Detail Component', () => {
    let comp: ProjectActivityDetailComponent;
    let fixture: ComponentFixture<ProjectActivityDetailComponent>;
    const route = ({ data: of({ projectActivity: new ProjectActivity(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WlbTestModule],
        declarations: [ProjectActivityDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProjectActivityDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProjectActivityDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load projectActivity on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.projectActivity).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
