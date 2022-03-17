import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IConsumptionDetails, ConsumptionDetails } from '../consumption-details.model';

import { ConsumptionDetailsService } from './consumption-details.service';

describe('ConsumptionDetails Service', () => {
  let service: ConsumptionDetailsService;
  let httpMock: HttpTestingController;
  let elemDefault: IConsumptionDetails;
  let expectedResult: IConsumptionDetails | IConsumptionDetails[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ConsumptionDetailsService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      comsumptionDate: currentDate,
      qtyConsumed: 0,
      freeField1: 'AAAAAAA',
      freeField2: 'AAAAAAA',
      lastModified: 'AAAAAAA',
      lastModifiedBy: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          comsumptionDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a ConsumptionDetails', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          comsumptionDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          comsumptionDate: currentDate,
        },
        returnedFromService
      );

      service.create(new ConsumptionDetails()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ConsumptionDetails', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          comsumptionDate: currentDate.format(DATE_TIME_FORMAT),
          qtyConsumed: 1,
          freeField1: 'BBBBBB',
          freeField2: 'BBBBBB',
          lastModified: 'BBBBBB',
          lastModifiedBy: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          comsumptionDate: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ConsumptionDetails', () => {
      const patchObject = Object.assign(
        {
          lastModified: 'BBBBBB',
          lastModifiedBy: 'BBBBBB',
        },
        new ConsumptionDetails()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          comsumptionDate: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ConsumptionDetails', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          comsumptionDate: currentDate.format(DATE_TIME_FORMAT),
          qtyConsumed: 1,
          freeField1: 'BBBBBB',
          freeField2: 'BBBBBB',
          lastModified: 'BBBBBB',
          lastModifiedBy: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          comsumptionDate: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a ConsumptionDetails', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addConsumptionDetailsToCollectionIfMissing', () => {
      it('should add a ConsumptionDetails to an empty array', () => {
        const consumptionDetails: IConsumptionDetails = { id: 123 };
        expectedResult = service.addConsumptionDetailsToCollectionIfMissing([], consumptionDetails);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(consumptionDetails);
      });

      it('should not add a ConsumptionDetails to an array that contains it', () => {
        const consumptionDetails: IConsumptionDetails = { id: 123 };
        const consumptionDetailsCollection: IConsumptionDetails[] = [
          {
            ...consumptionDetails,
          },
          { id: 456 },
        ];
        expectedResult = service.addConsumptionDetailsToCollectionIfMissing(consumptionDetailsCollection, consumptionDetails);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ConsumptionDetails to an array that doesn't contain it", () => {
        const consumptionDetails: IConsumptionDetails = { id: 123 };
        const consumptionDetailsCollection: IConsumptionDetails[] = [{ id: 456 }];
        expectedResult = service.addConsumptionDetailsToCollectionIfMissing(consumptionDetailsCollection, consumptionDetails);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(consumptionDetails);
      });

      it('should add only unique ConsumptionDetails to an array', () => {
        const consumptionDetailsArray: IConsumptionDetails[] = [{ id: 123 }, { id: 456 }, { id: 14792 }];
        const consumptionDetailsCollection: IConsumptionDetails[] = [{ id: 123 }];
        expectedResult = service.addConsumptionDetailsToCollectionIfMissing(consumptionDetailsCollection, ...consumptionDetailsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const consumptionDetails: IConsumptionDetails = { id: 123 };
        const consumptionDetails2: IConsumptionDetails = { id: 456 };
        expectedResult = service.addConsumptionDetailsToCollectionIfMissing([], consumptionDetails, consumptionDetails2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(consumptionDetails);
        expect(expectedResult).toContain(consumptionDetails2);
      });

      it('should accept null and undefined values', () => {
        const consumptionDetails: IConsumptionDetails = { id: 123 };
        expectedResult = service.addConsumptionDetailsToCollectionIfMissing([], null, consumptionDetails, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(consumptionDetails);
      });

      it('should return initial array if no ConsumptionDetails is added', () => {
        const consumptionDetailsCollection: IConsumptionDetails[] = [{ id: 123 }];
        expectedResult = service.addConsumptionDetailsToCollectionIfMissing(consumptionDetailsCollection, undefined, null);
        expect(expectedResult).toEqual(consumptionDetailsCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
