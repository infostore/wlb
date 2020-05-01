import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { WlbTestModule } from '../../../test.module';
import { IssueWatcherDetailComponent } from 'app/entities/issue-watcher/issue-watcher-detail.component';
import { IssueWatcher } from 'app/shared/model/issue-watcher.model';

describe('Component Tests', () => {
  describe('IssueWatcher Management Detail Component', () => {
    let comp: IssueWatcherDetailComponent;
    let fixture: ComponentFixture<IssueWatcherDetailComponent>;
    const route = ({ data: of({ issueWatcher: new IssueWatcher(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [WlbTestModule],
        declarations: [IssueWatcherDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(IssueWatcherDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IssueWatcherDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load issueWatcher on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.issueWatcher).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
