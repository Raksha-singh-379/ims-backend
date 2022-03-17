import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IGoodsRecived, GoodsRecived } from '../goods-recived.model';

import { GoodsRecivedService } from './goods-recived.service';

describe('GoodsRecived Service', () => {
  let service: GoodsRecivedService;
  let httpMock: HttpTestingController;
  let elemDefault: IGoodsRecived;
  let expectedResult: IGoodsRecived | IGoodsRecived[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(GoodsRecivedService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      grDate: currentDate,
      qtyOrdered: 0,
      qtyRecieved: 0,
      manufacturingDate: currentDate,
      expiryDate: currentDate,
      lotNo: 'AAAAAAA',
      freeField1: 'AAAAAAA',
      freeField2: 'AAAAAAA',
      freeField3: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          grDate: currentDate.format(DATE_TIME_FORMAT),
          manufacturingDate: currentDate.format(DATE_TIME_FORMAT),
          expiryDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a GoodsRecived', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          grDate: currentDate.format(DATE_TIME_FORMAT),
          manufacturingDate: currentDate.format(DATE_TIME_FORMAT),
          expiryDate: currentDate.format(DATE_TIME_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          grDate: currentDate,
          manufacturingDate: currentDate,
          expiryDate: currentDate,
        },
        returnedFromService
      );

      service.create(new GoodsRecived()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a GoodsRecived', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          grDate: currentDate.format(DATE_TIME_FORMAT),
          qtyOrdered: 1,
          qtyRecieved: 1,
          manufacturingDate: currentDate.format(DATE_TIME_FORMAT),
          expiryDate: currentDate.format(DATE_TIME_FORMAT),
          lotNo: 'BBBBBB',
          freeField1: 'BBBBBB',
          freeField2: 'BBBBBB',
          freeField3: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          grDate: currentDate,
          manufacturingDate: currentDate,
          expiryDate: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a GoodsRecived', () => {
      const patchObject = Object.assign(
        {
          manufacturingDate: currentDate.format(DATE_TIME_FORMAT),
          expiryDate: currentDate.format(DATE_TIME_FORMAT),
          lotNo: 'BBBBBB',
          freeField1: 'BBBBBB',
        },
        new GoodsRecived()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          grDate: currentDate,
          manufacturingDate: currentDate,
          expiryDate: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of GoodsRecived', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          grDate: currentDate.format(DATE_TIME_FORMAT),
          qtyOrdered: 1,
          qtyRecieved: 1,
          manufacturingDate: currentDate.format(DATE_TIME_FORMAT),
          expiryDate: currentDate.format(DATE_TIME_FORMAT),
          lotNo: 'BBBBBB',
          freeField1: 'BBBBBB',
          freeField2: 'BBBBBB',
          freeField3: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          grDate: currentDate,
          manufacturingDate: currentDate,
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

    it('should delete a GoodsRecived', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addGoodsRecivedToCollectionIfMissing', () => {
      it('should add a GoodsRecived to an empty array', () => {
        const goodsRecived: IGoodsRecived = { id: 123 };
        expectedResult = service.addGoodsRecivedToCollectionIfMissing([], goodsRecived);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(goodsRecived);
      });

      it('should not add a GoodsRecived to an array that contains it', () => {
        const goodsRecived: IGoodsRecived = { id: 123 };
        const goodsRecivedCollection: IGoodsRecived[] = [
          {
            ...goodsRecived,
          },
          { id: 456 },
        ];
        expectedResult = service.addGoodsRecivedToCollectionIfMissing(goodsRecivedCollection, goodsRecived);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a GoodsRecived to an array that doesn't contain it", () => {
        const goodsRecived: IGoodsRecived = { id: 123 };
        const goodsRecivedCollection: IGoodsRecived[] = [{ id: 456 }];
        expectedResult = service.addGoodsRecivedToCollectionIfMissing(goodsRecivedCollection, goodsRecived);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(goodsRecived);
      });

      it('should add only unique GoodsRecived to an array', () => {
        const goodsRecivedArray: IGoodsRecived[] = [{ id: 123 }, { id: 456 }, { id: 66614 }];
        const goodsRecivedCollection: IGoodsRecived[] = [{ id: 123 }];
        expectedResult = service.addGoodsRecivedToCollectionIfMissing(goodsRecivedCollection, ...goodsRecivedArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const goodsRecived: IGoodsRecived = { id: 123 };
        const goodsRecived2: IGoodsRecived = { id: 456 };
        expectedResult = service.addGoodsRecivedToCollectionIfMissing([], goodsRecived, goodsRecived2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(goodsRecived);
        expect(expectedResult).toContain(goodsRecived2);
      });

      it('should accept null and undefined values', () => {
        const goodsRecived: IGoodsRecived = { id: 123 };
        expectedResult = service.addGoodsRecivedToCollectionIfMissing([], null, goodsRecived, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(goodsRecived);
      });

      it('should return initial array if no GoodsRecived is added', () => {
        const goodsRecivedCollection: IGoodsRecived[] = [{ id: 123 }];
        expectedResult = service.addGoodsRecivedToCollectionIfMissing(goodsRecivedCollection, undefined, null);
        expect(expectedResult).toEqual(goodsRecivedCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
