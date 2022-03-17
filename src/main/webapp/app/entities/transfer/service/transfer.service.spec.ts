import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { Status } from 'app/entities/enumerations/status.model';
import { ITransfer, Transfer } from '../transfer.model';

import { TransferService } from './transfer.service';

describe('Transfer Service', () => {
  let service: TransferService;
  let httpMock: HttpTestingController;
  let elemDefault: ITransfer;
  let expectedResult: ITransfer | ITransfer[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TransferService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      tranferDate: currentDate,
      comment: 'AAAAAAA',
      status: Status.REQUESTED,
      sourceWareHouse: 'AAAAAAA',
      destinationWareHouse: 'AAAAAAA',
      freeField1: 'AAAAAAA',
      lastModified: 'AAAAAAA',
      lastModifiedBy: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          tranferDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Transfer', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          tranferDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          tranferDate: currentDate,
        },
        returnedFromService
      );

      service.create(new Transfer()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Transfer', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          tranferDate: currentDate.format(DATE_TIME_FORMAT),
          comment: 'BBBBBB',
          status: 'BBBBBB',
          sourceWareHouse: 'BBBBBB',
          destinationWareHouse: 'BBBBBB',
          freeField1: 'BBBBBB',
          lastModified: 'BBBBBB',
          lastModifiedBy: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          tranferDate: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Transfer', () => {
      const patchObject = Object.assign(
        {
          tranferDate: currentDate.format(DATE_TIME_FORMAT),
          comment: 'BBBBBB',
          status: 'BBBBBB',
          sourceWareHouse: 'BBBBBB',
          destinationWareHouse: 'BBBBBB',
          freeField1: 'BBBBBB',
          lastModifiedBy: 'BBBBBB',
        },
        new Transfer()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          tranferDate: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Transfer', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          tranferDate: currentDate.format(DATE_TIME_FORMAT),
          comment: 'BBBBBB',
          status: 'BBBBBB',
          sourceWareHouse: 'BBBBBB',
          destinationWareHouse: 'BBBBBB',
          freeField1: 'BBBBBB',
          lastModified: 'BBBBBB',
          lastModifiedBy: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          tranferDate: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Transfer', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTransferToCollectionIfMissing', () => {
      it('should add a Transfer to an empty array', () => {
        const transfer: ITransfer = { id: 123 };
        expectedResult = service.addTransferToCollectionIfMissing([], transfer);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(transfer);
      });

      it('should not add a Transfer to an array that contains it', () => {
        const transfer: ITransfer = { id: 123 };
        const transferCollection: ITransfer[] = [
          {
            ...transfer,
          },
          { id: 456 },
        ];
        expectedResult = service.addTransferToCollectionIfMissing(transferCollection, transfer);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Transfer to an array that doesn't contain it", () => {
        const transfer: ITransfer = { id: 123 };
        const transferCollection: ITransfer[] = [{ id: 456 }];
        expectedResult = service.addTransferToCollectionIfMissing(transferCollection, transfer);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(transfer);
      });

      it('should add only unique Transfer to an array', () => {
        const transferArray: ITransfer[] = [{ id: 123 }, { id: 456 }, { id: 80859 }];
        const transferCollection: ITransfer[] = [{ id: 123 }];
        expectedResult = service.addTransferToCollectionIfMissing(transferCollection, ...transferArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const transfer: ITransfer = { id: 123 };
        const transfer2: ITransfer = { id: 456 };
        expectedResult = service.addTransferToCollectionIfMissing([], transfer, transfer2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(transfer);
        expect(expectedResult).toContain(transfer2);
      });

      it('should accept null and undefined values', () => {
        const transfer: ITransfer = { id: 123 };
        expectedResult = service.addTransferToCollectionIfMissing([], null, transfer, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(transfer);
      });

      it('should return initial array if no Transfer is added', () => {
        const transferCollection: ITransfer[] = [{ id: 123 }];
        expectedResult = service.addTransferToCollectionIfMissing(transferCollection, undefined, null);
        expect(expectedResult).toEqual(transferCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
