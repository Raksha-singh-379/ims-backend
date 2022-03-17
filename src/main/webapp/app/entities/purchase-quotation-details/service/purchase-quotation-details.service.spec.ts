import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPurchaseQuotationDetails, PurchaseQuotationDetails } from '../purchase-quotation-details.model';

import { PurchaseQuotationDetailsService } from './purchase-quotation-details.service';

describe('PurchaseQuotationDetails Service', () => {
  let service: PurchaseQuotationDetailsService;
  let httpMock: HttpTestingController;
  let elemDefault: IPurchaseQuotationDetails;
  let expectedResult: IPurchaseQuotationDetails | IPurchaseQuotationDetails[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PurchaseQuotationDetailsService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      qtyordered: 0,
      gstTaxPercentage: 0,
      pricePerUnit: 0,
      totalPrice: 0,
      discount: 0,
      lastModified: 'AAAAAAA',
      lastModifiedBy: 'AAAAAAA',
      freeField1: 'AAAAAAA',
      freeField2: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a PurchaseQuotationDetails', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new PurchaseQuotationDetails()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PurchaseQuotationDetails', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          qtyordered: 1,
          gstTaxPercentage: 1,
          pricePerUnit: 1,
          totalPrice: 1,
          discount: 1,
          lastModified: 'BBBBBB',
          lastModifiedBy: 'BBBBBB',
          freeField1: 'BBBBBB',
          freeField2: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PurchaseQuotationDetails', () => {
      const patchObject = Object.assign(
        {
          gstTaxPercentage: 1,
          pricePerUnit: 1,
          discount: 1,
          lastModified: 'BBBBBB',
          lastModifiedBy: 'BBBBBB',
        },
        new PurchaseQuotationDetails()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PurchaseQuotationDetails', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          qtyordered: 1,
          gstTaxPercentage: 1,
          pricePerUnit: 1,
          totalPrice: 1,
          discount: 1,
          lastModified: 'BBBBBB',
          lastModifiedBy: 'BBBBBB',
          freeField1: 'BBBBBB',
          freeField2: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a PurchaseQuotationDetails', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPurchaseQuotationDetailsToCollectionIfMissing', () => {
      it('should add a PurchaseQuotationDetails to an empty array', () => {
        const purchaseQuotationDetails: IPurchaseQuotationDetails = { id: 123 };
        expectedResult = service.addPurchaseQuotationDetailsToCollectionIfMissing([], purchaseQuotationDetails);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(purchaseQuotationDetails);
      });

      it('should not add a PurchaseQuotationDetails to an array that contains it', () => {
        const purchaseQuotationDetails: IPurchaseQuotationDetails = { id: 123 };
        const purchaseQuotationDetailsCollection: IPurchaseQuotationDetails[] = [
          {
            ...purchaseQuotationDetails,
          },
          { id: 456 },
        ];
        expectedResult = service.addPurchaseQuotationDetailsToCollectionIfMissing(
          purchaseQuotationDetailsCollection,
          purchaseQuotationDetails
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PurchaseQuotationDetails to an array that doesn't contain it", () => {
        const purchaseQuotationDetails: IPurchaseQuotationDetails = { id: 123 };
        const purchaseQuotationDetailsCollection: IPurchaseQuotationDetails[] = [{ id: 456 }];
        expectedResult = service.addPurchaseQuotationDetailsToCollectionIfMissing(
          purchaseQuotationDetailsCollection,
          purchaseQuotationDetails
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(purchaseQuotationDetails);
      });

      it('should add only unique PurchaseQuotationDetails to an array', () => {
        const purchaseQuotationDetailsArray: IPurchaseQuotationDetails[] = [{ id: 123 }, { id: 456 }, { id: 20580 }];
        const purchaseQuotationDetailsCollection: IPurchaseQuotationDetails[] = [{ id: 123 }];
        expectedResult = service.addPurchaseQuotationDetailsToCollectionIfMissing(
          purchaseQuotationDetailsCollection,
          ...purchaseQuotationDetailsArray
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const purchaseQuotationDetails: IPurchaseQuotationDetails = { id: 123 };
        const purchaseQuotationDetails2: IPurchaseQuotationDetails = { id: 456 };
        expectedResult = service.addPurchaseQuotationDetailsToCollectionIfMissing([], purchaseQuotationDetails, purchaseQuotationDetails2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(purchaseQuotationDetails);
        expect(expectedResult).toContain(purchaseQuotationDetails2);
      });

      it('should accept null and undefined values', () => {
        const purchaseQuotationDetails: IPurchaseQuotationDetails = { id: 123 };
        expectedResult = service.addPurchaseQuotationDetailsToCollectionIfMissing([], null, purchaseQuotationDetails, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(purchaseQuotationDetails);
      });

      it('should return initial array if no PurchaseQuotationDetails is added', () => {
        const purchaseQuotationDetailsCollection: IPurchaseQuotationDetails[] = [{ id: 123 }];
        expectedResult = service.addPurchaseQuotationDetailsToCollectionIfMissing(purchaseQuotationDetailsCollection, undefined, null);
        expect(expectedResult).toEqual(purchaseQuotationDetailsCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
