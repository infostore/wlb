import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WlbTestModule } from '../../../test.module';
import { LabelGroupDetailComponent } from 'app/entities/label-group/label-group-detail.component';
import { LabelGroup } from 'app/shared/model/label-group.model';

describe('Component Tests', () => {
  describe('LabelGroup Management Detail Component', () => {
    let comp: LabelGroupDetailComponent;
    let fixture: ComponentFixture<LabelGroupDetailComponent>;
    const route = ({ data: of({ labelGroup: new LabelGroup(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WlbTestModule],
        declarations: [LabelGroupDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(LabelGroupDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LabelGroupDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load labelGroup on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.labelGroup).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
