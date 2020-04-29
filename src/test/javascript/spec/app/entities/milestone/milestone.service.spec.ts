import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { MilestoneService } from 'app/entities/milestone/milestone.service';
import { IMilestone, Milestone } from 'app/shared/model/milestone.model';
import { MilestoneStatus } from 'app/shared/model/enumerations/milestone-status.model';

describe('Service Tests', () => {
  describe('Milestone Service', () => {
    let injector: TestBed;
    let service: MilestoneService;
    let httpMock: HttpTestingController;
    let elemDefault: IMilestone;
    let expectedResult: IMilestone | IMilestone[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(MilestoneService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Milestone(0, 'AAAAAAA', 'AAAAAAA', MilestoneStatus.OPEN, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dueDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Milestone', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dueDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dueDate: currentDate
          },
          returnedFromService
        );

        service.create(new Milestone()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Milestone', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            description: 'BBBBBB',
            milestoneStatus: 'BBBBBB',
            dueDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dueDate: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Milestone', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            description: 'BBBBBB',
            milestoneStatus: 'BBBBBB',
            dueDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dueDate: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Milestone', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
