import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { OrderType } from 'app/entities/enumerations/order-type.model';
import { Status } from 'app/entities/enumerations/status.model';
import { IPurchaseQuotation, PurchaseQuotation } from '../purchase-quotation.model';

import { PurchaseQuotationService } from './purchase-quotation.service';

describe('PurchaseQuotation Service', () => {
  let service: PurchaseQuotationService;
  let httpMock: HttpTestingController;
  let elemDefault: IPurchaseQuotation;
  let expectedResult: IPurchaseQuotation | IPurchaseQuotation[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PurchaseQuotationService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      refrenceNumber: 'AAAAAAA',
      totalPOAmount: 0,
      totalGSTAmount: 0,
      expectedDeliveryDate: currentDate,
      poDate: currentDate,
      orderType: OrderType.PURCHASE_ORDER,
      orderStatus: Status.REQUESTED,
      termsAndCondition: 'AAAAAAA',
      notes: 'AAAAAAA',
      lastModified: 'AAAAAAA',
      lastModifiedBy: 'AAAAAAA',
      freeField1: 'AAAAAAA',
      freeField2: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          expectedDeliveryDate: currentDate.format(DATE_TIME_FORMAT),
          poDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a PurchaseQuotation', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          expectedDeliveryDate: currentDate.format(DATE_TIME_FORMAT),
          poDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          expectedDeliveryDate: currentDate,
          poDate: currentDate,
        },
        returnedFromService
      );

      service.create(new PurchaseQuotation()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PurchaseQuotation', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          refrenceNumber: 'BBBBBB',
          totalPOAmount: 1,
          totalGSTAmount: 1,
          expectedDeliveryDate: currentDate.format(DATE_TIME_FORMAT),
          poDate: currentDate.format(DATE_TIME_FORMAT),
          orderType: 'BBBBBB',
          orderStatus: 'BBBBBB',
          termsAndCondition: 'BBBBBB',
          notes: 'BBBBBB',
          lastModified: 'BBBBBB',
          lastModifiedBy: 'BBBBBB',
          freeField1: 'BBBBBB',
          freeField2: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          expectedDeliveryDate: currentDate,
          poDate: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PurchaseQuotation', () => {
      const patchObject = Object.assign(
        {
          totalGSTAmount: 1,
          expectedDeliveryDate: currentDate.format(DATE_TIME_FORMAT),
          lastModified: 'BBBBBB',
          freeField2: 'BBBBBB',
        },
        new PurchaseQuotation()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          expectedDeliveryDate: currentDate,
          poDate: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PurchaseQuotation', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          refrenceNumber: 'BBBBBB',
          totalPOAmount: 1,
          totalGSTAmount: 1,
          expectedDeliveryDate: currentDate.format(DATE_TIME_FORMAT),
          poDate: currentDate.format(DATE_TIME_FORMAT),
          orderType: 'BBBBBB',
          orderStatus: 'BBBBBB',
          termsAndCondition: 'BBBBBB',
          notes: 'BBBBBB',
          lastModified: 'BBBBBB',
          lastModifiedBy: 'BBBBBB',
          freeField1: 'BBBBBB',
          freeField2: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          expectedDeliveryDate: currentDate,
          poDate: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a PurchaseQuotation', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPurchaseQuotationToCollectionIfMissing', () => {
      it('should add a PurchaseQuotation to an empty array', () => {
        const purchaseQuotation: IPurchaseQuotation = { id: 123 };
        expectedResult = service.addPurchaseQuotationToCollectionIfMissing([], purchaseQuotation);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(purchaseQuotation);
      });

      it('should not add a PurchaseQuotation to an array that contains it', () => {
        const purchaseQuotation: IPurchaseQuotation = { id: 123 };
        const purchaseQuotationCollection: IPurchaseQuotation[] = [
          {
            ...purchaseQuotation,
          },
          { id: 456 },
        ];
        expectedResult = service.addPurchaseQuotationToCollectionIfMissing(purchaseQuotationCollection, purchaseQuotation);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PurchaseQuotation to an array that doesn't contain it", () => {
        const purchaseQuotation: IPurchaseQuotation = { id: 123 };
        const purchaseQuotationCollection: IPurchaseQuotation[] = [{ id: 456 }];
        expectedResult = service.addPurchaseQuotationToCollectionIfMissing(purchaseQuotationCollection, purchaseQuotation);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(purchaseQuotation);
      });

      it('should add only unique PurchaseQuotation to an array', () => {
        const purchaseQuotationArray: IPurchaseQuotation[] = [{ id: 123 }, { id: 456 }, { id: 84439 }];
        const purchaseQuotationCollection: IPurchaseQuotation[] = [{ id: 123 }];
        expectedResult = service.addPurchaseQuotationToCollectionIfMissing(purchaseQuotationCollection, ...purchaseQuotationArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const purchaseQuotation: IPurchaseQuotation = { id: 123 };
        const purchaseQuotation2: IPurchaseQuotation = { id: 456 };
        expectedResult = service.addPurchaseQuotationToCollectionIfMissing([], purchaseQuotation, purchaseQuotation2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(purchaseQuotation);
        expect(expectedResult).toContain(purchaseQuotation2);
      });

      it('should accept null and undefined values', () => {
        const purchaseQuotation: IPurchaseQuotation = { id: 123 };
        expectedResult = service.addPurchaseQuotationToCollectionIfMissing([], null, purchaseQuotation, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(purchaseQuotation);
      });

      it('should return initial array if no PurchaseQuotation is added', () => {
        const purchaseQuotationCollection: IPurchaseQuotation[] = [{ id: 123 }];
        expectedResult = service.addPurchaseQuotationToCollectionIfMissing(purchaseQuotationCollection, undefined, null);
        expect(expectedResult).toEqual(purchaseQuotationCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
