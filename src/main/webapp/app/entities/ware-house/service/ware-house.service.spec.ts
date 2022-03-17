import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IWareHouse, WareHouse } from '../ware-house.model';

import { WareHouseService } from './ware-house.service';

describe('WareHouse Service', () => {
  let service: WareHouseService;
  let httpMock: HttpTestingController;
  let elemDefault: IWareHouse;
  let expectedResult: IWareHouse | IWareHouse[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(WareHouseService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      whName: 'AAAAAAA',
      address: 'AAAAAAA',
      pincode: 0,
      city: 'AAAAAAA',
      state: 'AAAAAAA',
      country: 'AAAAAAA',
      gSTDetails: 'AAAAAAA',
      managerName: 'AAAAAAA',
      managerEmail: 'AAAAAAA',
      managerContact: 'AAAAAAA',
      contact: 'AAAAAAA',
      isDeleted: false,
      isActive: false,
      wareHouseId: 0,
      lastModified: 'AAAAAAA',
      lastModifiedBy: 'AAAAAAA',
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

    it('should create a WareHouse', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new WareHouse()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a WareHouse', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          whName: 'BBBBBB',
          address: 'BBBBBB',
          pincode: 1,
          city: 'BBBBBB',
          state: 'BBBBBB',
          country: 'BBBBBB',
          gSTDetails: 'BBBBBB',
          managerName: 'BBBBBB',
          managerEmail: 'BBBBBB',
          managerContact: 'BBBBBB',
          contact: 'BBBBBB',
          isDeleted: true,
          isActive: true,
          wareHouseId: 1,
          lastModified: 'BBBBBB',
          lastModifiedBy: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a WareHouse', () => {
      const patchObject = Object.assign(
        {
          whName: 'BBBBBB',
          address: 'BBBBBB',
          pincode: 1,
          state: 'BBBBBB',
          managerName: 'BBBBBB',
          managerContact: 'BBBBBB',
          lastModified: 'BBBBBB',
          lastModifiedBy: 'BBBBBB',
        },
        new WareHouse()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of WareHouse', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          whName: 'BBBBBB',
          address: 'BBBBBB',
          pincode: 1,
          city: 'BBBBBB',
          state: 'BBBBBB',
          country: 'BBBBBB',
          gSTDetails: 'BBBBBB',
          managerName: 'BBBBBB',
          managerEmail: 'BBBBBB',
          managerContact: 'BBBBBB',
          contact: 'BBBBBB',
          isDeleted: true,
          isActive: true,
          wareHouseId: 1,
          lastModified: 'BBBBBB',
          lastModifiedBy: 'BBBBBB',
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

    it('should delete a WareHouse', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addWareHouseToCollectionIfMissing', () => {
      it('should add a WareHouse to an empty array', () => {
        const wareHouse: IWareHouse = { id: 123 };
        expectedResult = service.addWareHouseToCollectionIfMissing([], wareHouse);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(wareHouse);
      });

      it('should not add a WareHouse to an array that contains it', () => {
        const wareHouse: IWareHouse = { id: 123 };
        const wareHouseCollection: IWareHouse[] = [
          {
            ...wareHouse,
          },
          { id: 456 },
        ];
        expectedResult = service.addWareHouseToCollectionIfMissing(wareHouseCollection, wareHouse);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a WareHouse to an array that doesn't contain it", () => {
        const wareHouse: IWareHouse = { id: 123 };
        const wareHouseCollection: IWareHouse[] = [{ id: 456 }];
        expectedResult = service.addWareHouseToCollectionIfMissing(wareHouseCollection, wareHouse);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(wareHouse);
      });

      it('should add only unique WareHouse to an array', () => {
        const wareHouseArray: IWareHouse[] = [{ id: 123 }, { id: 456 }, { id: 39263 }];
        const wareHouseCollection: IWareHouse[] = [{ id: 123 }];
        expectedResult = service.addWareHouseToCollectionIfMissing(wareHouseCollection, ...wareHouseArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const wareHouse: IWareHouse = { id: 123 };
        const wareHouse2: IWareHouse = { id: 456 };
        expectedResult = service.addWareHouseToCollectionIfMissing([], wareHouse, wareHouse2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(wareHouse);
        expect(expectedResult).toContain(wareHouse2);
      });

      it('should accept null and undefined values', () => {
        const wareHouse: IWareHouse = { id: 123 };
        expectedResult = service.addWareHouseToCollectionIfMissing([], null, wareHouse, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(wareHouse);
      });

      it('should return initial array if no WareHouse is added', () => {
        const wareHouseCollection: IWareHouse[] = [{ id: 123 }];
        expectedResult = service.addWareHouseToCollectionIfMissing(wareHouseCollection, undefined, null);
        expect(expectedResult).toEqual(wareHouseCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
