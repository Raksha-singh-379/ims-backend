import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ITransferDetailsApprovals, TransferDetailsApprovals } from '../transfer-details-approvals.model';

import { TransferDetailsApprovalsService } from './transfer-details-approvals.service';

describe('TransferDetailsApprovals Service', () => {
  let service: TransferDetailsApprovalsService;
  let httpMock: HttpTestingController;
  let elemDefault: ITransferDetailsApprovals;
  let expectedResult: ITransferDetailsApprovals | ITransferDetailsApprovals[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TransferDetailsApprovalsService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      approvalDate: currentDate,
      qtyRequested: 0,
      qtyApproved: 0,
      comment: 'AAAAAAA',
      freeField1: 'AAAAAAA',
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

    it('should create a TransferDetailsApprovals', () => {
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

      service.create(new TransferDetailsApprovals()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TransferDetailsApprovals', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          approvalDate: currentDate.format(DATE_TIME_FORMAT),
          qtyRequested: 1,
          qtyApproved: 1,
          comment: 'BBBBBB',
          freeField1: 'BBBBBB',
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

    it('should partial update a TransferDetailsApprovals', () => {
      const patchObject = Object.assign(
        {
          approvalDate: currentDate.format(DATE_TIME_FORMAT),
          qtyApproved: 1,
          comment: 'BBBBBB',
          freeField1: 'BBBBBB',
          lastModified: 'BBBBBB',
          lastModifiedBy: 'BBBBBB',
          isActive: true,
        },
        new TransferDetailsApprovals()
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

    it('should return a list of TransferDetailsApprovals', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          approvalDate: currentDate.format(DATE_TIME_FORMAT),
          qtyRequested: 1,
          qtyApproved: 1,
          comment: 'BBBBBB',
          freeField1: 'BBBBBB',
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

    it('should delete a TransferDetailsApprovals', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTransferDetailsApprovalsToCollectionIfMissing', () => {
      it('should add a TransferDetailsApprovals to an empty array', () => {
        const transferDetailsApprovals: ITransferDetailsApprovals = { id: 123 };
        expectedResult = service.addTransferDetailsApprovalsToCollectionIfMissing([], transferDetailsApprovals);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(transferDetailsApprovals);
      });

      it('should not add a TransferDetailsApprovals to an array that contains it', () => {
        const transferDetailsApprovals: ITransferDetailsApprovals = { id: 123 };
        const transferDetailsApprovalsCollection: ITransferDetailsApprovals[] = [
          {
            ...transferDetailsApprovals,
          },
          { id: 456 },
        ];
        expectedResult = service.addTransferDetailsApprovalsToCollectionIfMissing(
          transferDetailsApprovalsCollection,
          transferDetailsApprovals
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TransferDetailsApprovals to an array that doesn't contain it", () => {
        const transferDetailsApprovals: ITransferDetailsApprovals = { id: 123 };
        const transferDetailsApprovalsCollection: ITransferDetailsApprovals[] = [{ id: 456 }];
        expectedResult = service.addTransferDetailsApprovalsToCollectionIfMissing(
          transferDetailsApprovalsCollection,
          transferDetailsApprovals
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(transferDetailsApprovals);
      });

      it('should add only unique TransferDetailsApprovals to an array', () => {
        const transferDetailsApprovalsArray: ITransferDetailsApprovals[] = [{ id: 123 }, { id: 456 }, { id: 68496 }];
        const transferDetailsApprovalsCollection: ITransferDetailsApprovals[] = [{ id: 123 }];
        expectedResult = service.addTransferDetailsApprovalsToCollectionIfMissing(
          transferDetailsApprovalsCollection,
          ...transferDetailsApprovalsArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const transferDetailsApprovals: ITransferDetailsApprovals = { id: 123 };
        const transferDetailsApprovals2: ITransferDetailsApprovals = { id: 456 };
        expectedResult = service.addTransferDetailsApprovalsToCollectionIfMissing([], transferDetailsApprovals, transferDetailsApprovals2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(transferDetailsApprovals);
        expect(expectedResult).toContain(transferDetailsApprovals2);
      });

      it('should accept null and undefined values', () => {
        const transferDetailsApprovals: ITransferDetailsApprovals = { id: 123 };
        expectedResult = service.addTransferDetailsApprovalsToCollectionIfMissing([], null, transferDetailsApprovals, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(transferDetailsApprovals);
      });

      it('should return initial array if no TransferDetailsApprovals is added', () => {
        const transferDetailsApprovalsCollection: ITransferDetailsApprovals[] = [{ id: 123 }];
        expectedResult = service.addTransferDetailsApprovalsToCollectionIfMissing(transferDetailsApprovalsCollection, undefined, null);
        expect(expectedResult).toEqual(transferDetailsApprovalsCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
