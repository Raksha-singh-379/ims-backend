import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ITransferRecieved, TransferRecieved } from '../transfer-recieved.model';

import { TransferRecievedService } from './transfer-recieved.service';

describe('TransferRecieved Service', () => {
  let service: TransferRecievedService;
  let httpMock: HttpTestingController;
  let elemDefault: ITransferRecieved;
  let expectedResult: ITransferRecieved | ITransferRecieved[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TransferRecievedService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      transferDate: currentDate,
      qtyTransfered: 0,
      qtyReceived: 0,
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
          transferDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a TransferRecieved', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          transferDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          transferDate: currentDate,
        },
        returnedFromService
      );

      service.create(new TransferRecieved()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TransferRecieved', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          transferDate: currentDate.format(DATE_TIME_FORMAT),
          qtyTransfered: 1,
          qtyReceived: 1,
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
          transferDate: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TransferRecieved', () => {
      const patchObject = Object.assign(
        {
          transferDate: currentDate.format(DATE_TIME_FORMAT),
          qtyTransfered: 1,
          qtyReceived: 1,
          freeField1: 'BBBBBB',
          lastModifiedBy: 'BBBBBB',
          isDeleted: true,
        },
        new TransferRecieved()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          transferDate: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TransferRecieved', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          transferDate: currentDate.format(DATE_TIME_FORMAT),
          qtyTransfered: 1,
          qtyReceived: 1,
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
          transferDate: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a TransferRecieved', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTransferRecievedToCollectionIfMissing', () => {
      it('should add a TransferRecieved to an empty array', () => {
        const transferRecieved: ITransferRecieved = { id: 123 };
        expectedResult = service.addTransferRecievedToCollectionIfMissing([], transferRecieved);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(transferRecieved);
      });

      it('should not add a TransferRecieved to an array that contains it', () => {
        const transferRecieved: ITransferRecieved = { id: 123 };
        const transferRecievedCollection: ITransferRecieved[] = [
          {
            ...transferRecieved,
          },
          { id: 456 },
        ];
        expectedResult = service.addTransferRecievedToCollectionIfMissing(transferRecievedCollection, transferRecieved);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TransferRecieved to an array that doesn't contain it", () => {
        const transferRecieved: ITransferRecieved = { id: 123 };
        const transferRecievedCollection: ITransferRecieved[] = [{ id: 456 }];
        expectedResult = service.addTransferRecievedToCollectionIfMissing(transferRecievedCollection, transferRecieved);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(transferRecieved);
      });

      it('should add only unique TransferRecieved to an array', () => {
        const transferRecievedArray: ITransferRecieved[] = [{ id: 123 }, { id: 456 }, { id: 25054 }];
        const transferRecievedCollection: ITransferRecieved[] = [{ id: 123 }];
        expectedResult = service.addTransferRecievedToCollectionIfMissing(transferRecievedCollection, ...transferRecievedArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const transferRecieved: ITransferRecieved = { id: 123 };
        const transferRecieved2: ITransferRecieved = { id: 456 };
        expectedResult = service.addTransferRecievedToCollectionIfMissing([], transferRecieved, transferRecieved2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(transferRecieved);
        expect(expectedResult).toContain(transferRecieved2);
      });

      it('should accept null and undefined values', () => {
        const transferRecieved: ITransferRecieved = { id: 123 };
        expectedResult = service.addTransferRecievedToCollectionIfMissing([], null, transferRecieved, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(transferRecieved);
      });

      it('should return initial array if no TransferRecieved is added', () => {
        const transferRecievedCollection: ITransferRecieved[] = [{ id: 123 }];
        expectedResult = service.addTransferRecievedToCollectionIfMissing(transferRecievedCollection, undefined, null);
        expect(expectedResult).toEqual(transferRecievedCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
