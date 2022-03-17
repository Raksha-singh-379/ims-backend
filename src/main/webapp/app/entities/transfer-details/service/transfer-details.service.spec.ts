import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ITransferDetails, TransferDetails } from '../transfer-details.model';

import { TransferDetailsService } from './transfer-details.service';

describe('TransferDetails Service', () => {
  let service: TransferDetailsService;
  let httpMock: HttpTestingController;
  let elemDefault: ITransferDetails;
  let expectedResult: ITransferDetails | ITransferDetails[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TransferDetailsService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      approvalDate: currentDate,
      qty: 0,
      comment: 'AAAAAAA',
      freeField1: 'AAAAAAA',
      freeField2: 'AAAAAAA',
      lastModified: 'AAAAAAA',
      lastModifiedBy: 'AAAAAAA',
      isDeleted: false,
      isActive: false,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          approvalDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a TransferDetails', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          approvalDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          approvalDate: currentDate,
        },
        returnedFromService
      );

      service.create(new TransferDetails()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TransferDetails', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          approvalDate: currentDate.format(DATE_TIME_FORMAT),
          qty: 1,
          comment: 'BBBBBB',
          freeField1: 'BBBBBB',
          freeField2: 'BBBBBB',
          lastModified: 'BBBBBB',
          lastModifiedBy: 'BBBBBB',
          isDeleted: true,
          isActive: true,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          approvalDate: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TransferDetails', () => {
      const patchObject = Object.assign(
        {
          comment: 'BBBBBB',
          lastModifiedBy: 'BBBBBB',
        },
        new TransferDetails()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          approvalDate: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TransferDetails', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          approvalDate: currentDate.format(DATE_TIME_FORMAT),
          qty: 1,
          comment: 'BBBBBB',
          freeField1: 'BBBBBB',
          freeField2: 'BBBBBB',
          lastModified: 'BBBBBB',
          lastModifiedBy: 'BBBBBB',
          isDeleted: true,
          isActive: true,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          approvalDate: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a TransferDetails', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTransferDetailsToCollectionIfMissing', () => {
      it('should add a TransferDetails to an empty array', () => {
        const transferDetails: ITransferDetails = { id: 123 };
        expectedResult = service.addTransferDetailsToCollectionIfMissing([], transferDetails);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(transferDetails);
      });

      it('should not add a TransferDetails to an array that contains it', () => {
        const transferDetails: ITransferDetails = { id: 123 };
        const transferDetailsCollection: ITransferDetails[] = [
          {
            ...transferDetails,
          },
          { id: 456 },
        ];
        expectedResult = service.addTransferDetailsToCollectionIfMissing(transferDetailsCollection, transferDetails);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TransferDetails to an array that doesn't contain it", () => {
        const transferDetails: ITransferDetails = { id: 123 };
        const transferDetailsCollection: ITransferDetails[] = [{ id: 456 }];
        expectedResult = service.addTransferDetailsToCollectionIfMissing(transferDetailsCollection, transferDetails);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(transferDetails);
      });

      it('should add only unique TransferDetails to an array', () => {
        const transferDetailsArray: ITransferDetails[] = [{ id: 123 }, { id: 456 }, { id: 96262 }];
        const transferDetailsCollection: ITransferDetails[] = [{ id: 123 }];
        expectedResult = service.addTransferDetailsToCollectionIfMissing(transferDetailsCollection, ...transferDetailsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const transferDetails: ITransferDetails = { id: 123 };
        const transferDetails2: ITransferDetails = { id: 456 };
        expectedResult = service.addTransferDetailsToCollectionIfMissing([], transferDetails, transferDetails2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(transferDetails);
        expect(expectedResult).toContain(transferDetails2);
      });

      it('should accept null and undefined values', () => {
        const transferDetails: ITransferDetails = { id: 123 };
        expectedResult = service.addTransferDetailsToCollectionIfMissing([], null, transferDetails, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(transferDetails);
      });

      it('should return initial array if no TransferDetails is added', () => {
        const transferDetailsCollection: ITransferDetails[] = [{ id: 123 }];
        expectedResult = service.addTransferDetailsToCollectionIfMissing(transferDetailsCollection, undefined, null);
        expect(expectedResult).toEqual(transferDetailsCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
