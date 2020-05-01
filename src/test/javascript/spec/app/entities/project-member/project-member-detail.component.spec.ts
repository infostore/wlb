import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WlbTestModule } from '../../../test.module';
import { ProjectMemberDetailComponent } from 'app/entities/project-member/project-member-detail.component';
import { ProjectMember } from 'app/shared/model/project-member.model';

describe('Component Tests', () => {
  describe('ProjectMember Management Detail Component', () => {
    let comp: ProjectMemberDetailComponent;
    let fixture: ComponentFixture<ProjectMemberDetailComponent>;
    const route = ({ data: of({ projectMember: new ProjectMember(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WlbTestModule],
        declarations: [ProjectMemberDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProjectMemberDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProjectMemberDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load projectMember on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.projectMember).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
