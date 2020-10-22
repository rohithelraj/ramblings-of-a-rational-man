import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JournalService } from 'app/entities/journal/journal.service';
import { IJournal, Journal } from 'app/shared/model/journal.model';
import { journalType } from 'app/shared/model/enumerations/journal-type.model';

describe('Service Tests', () => {
  describe('Journal Service', () => {
    let injector: TestBed;
    let service: JournalService;
    let httpMock: HttpTestingController;
    let elemDefault: IJournal;
    let expectedResult: IJournal | IJournal[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(JournalService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Journal(0, 'AAAAAAA', journalType.RAMBLING, currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            journalDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Journal', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            journalDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            journalDate: currentDate,
          },
          returnedFromService
        );

        service.create(new Journal()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Journal', () => {
        const returnedFromService = Object.assign(
          {
            title: 'BBBBBB',
            tags: 'BBBBBB',
            journalDate: currentDate.format(DATE_TIME_FORMAT),
            text: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            journalDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Journal', () => {
        const returnedFromService = Object.assign(
          {
            title: 'BBBBBB',
            tags: 'BBBBBB',
            journalDate: currentDate.format(DATE_TIME_FORMAT),
            text: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            journalDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Journal', () => {
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
