import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IProductInventory, ProductInventory } from '../product-inventory.model';

import { ProductInventoryService } from './product-inventory.service';

describe('ProductInventory Service', () => {
  let service: ProductInventoryService;
  let httpMock: HttpTestingController;
  let elemDefault: IProductInventory;
  let expectedResult: IProductInventory | IProductInventory[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ProductInventoryService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      inwardOutwardDate: currentDate,
      inwardQty: 'AAAAAAA',
      outwardQty: 'AAAAAAA',
      totalQuanity: 'AAAAAAA',
      pricePerUnit: 0,
      lotNo: 'AAAAAAA',
      expiryDate: currentDate,
      inventoryTypeId: 'AAAAAAA',
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
          inwardOutwardDate: currentDate.format(DATE_TIME_FORMAT),
          expiryDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a ProductInventory', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          inwardOutwardDate: currentDate.format(DATE_TIME_FORMAT),
          expiryDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          inwardOutwardDate: currentDate,
          expiryDate: currentDate,
        },
        returnedFromService
      );

      service.create(new ProductInventory()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ProductInventory', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          inwardOutwardDate: currentDate.format(DATE_TIME_FORMAT),
          inwardQty: 'BBBBBB',
          outwardQty: 'BBBBBB',
          totalQuanity: 'BBBBBB',
          pricePerUnit: 1,
          lotNo: 'BBBBBB',
          expiryDate: currentDate.format(DATE_TIME_FORMAT),
          inventoryTypeId: 'BBBBBB',
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
          inwardOutwardDate: currentDate,
          expiryDate: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ProductInventory', () => {
      const patchObject = Object.assign(
        {
          inwardOutwardDate: currentDate.format(DATE_TIME_FORMAT),
          inwardQty: 'BBBBBB',
          outwardQty: 'BBBBBB',
          pricePerUnit: 1,
          inventoryTypeId: 'BBBBBB',
          freeField2: 'BBBBBB',
          isDeleted: true,
          isActive: true,
        },
        new ProductInventory()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          inwardOutwardDate: currentDate,
          expiryDate: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ProductInventory', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          inwardOutwardDate: currentDate.format(DATE_TIME_FORMAT),
          inwardQty: 'BBBBBB',
          outwardQty: 'BBBBBB',
          totalQuanity: 'BBBBBB',
          pricePerUnit: 1,
          lotNo: 'BBBBBB',
          expiryDate: currentDate.format(DATE_TIME_FORMAT),
          inventoryTypeId: 'BBBBBB',
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
          inwardOutwardDate: currentDate,
          expiryDate: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a ProductInventory', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addProductInventoryToCollectionIfMissing', () => {
      it('should add a ProductInventory to an empty array', () => {
        const productInventory: IProductInventory = { id: 123 };
        expectedResult = service.addProductInventoryToCollectionIfMissing([], productInventory);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(productInventory);
      });

      it('should not add a ProductInventory to an array that contains it', () => {
        const productInventory: IProductInventory = { id: 123 };
        const productInventoryCollection: IProductInventory[] = [
          {
            ...productInventory,
          },
          { id: 456 },
        ];
        expectedResult = service.addProductInventoryToCollectionIfMissing(productInventoryCollection, productInventory);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ProductInventory to an array that doesn't contain it", () => {
        const productInventory: IProductInventory = { id: 123 };
        const productInventoryCollection: IProductInventory[] = [{ id: 456 }];
        expectedResult = service.addProductInventoryToCollectionIfMissing(productInventoryCollection, productInventory);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(productInventory);
      });

      it('should add only unique ProductInventory to an array', () => {
        const productInventoryArray: IProductInventory[] = [{ id: 123 }, { id: 456 }, { id: 26797 }];
        const productInventoryCollection: IProductInventory[] = [{ id: 123 }];
        expectedResult = service.addProductInventoryToCollectionIfMissing(productInventoryCollection, ...productInventoryArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const productInventory: IProductInventory = { id: 123 };
        const productInventory2: IProductInventory = { id: 456 };
        expectedResult = service.addProductInventoryToCollectionIfMissing([], productInventory, productInventory2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(productInventory);
        expect(expectedResult).toContain(productInventory2);
      });

      it('should accept null and undefined values', () => {
        const productInventory: IProductInventory = { id: 123 };
        expectedResult = service.addProductInventoryToCollectionIfMissing([], null, productInventory, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(productInventory);
      });

      it('should return initial array if no ProductInventory is added', () => {
        const productInventoryCollection: IProductInventory[] = [{ id: 123 }];
        expectedResult = service.addProductInventoryToCollectionIfMissing(productInventoryCollection, undefined, null);
        expect(expectedResult).toEqual(productInventoryCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
