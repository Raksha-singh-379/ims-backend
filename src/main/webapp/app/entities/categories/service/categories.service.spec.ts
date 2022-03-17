import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICategories, Categories } from '../categories.model';

import { CategoriesService } from './categories.service';

describe('Categories Service', () => {
  let service: CategoriesService;
  let httpMock: HttpTestingController;
  let elemDefault: ICategories;
  let expectedResult: ICategories | ICategories[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CategoriesService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      categoryName: 'AAAAAAA',
      categoryDescription: 'AAAAAAA',
      freeField1: 'AAAAAAA',
      lastModified: 'AAAAAAA',
      lastModifiedBy: 'AAAAAAA',
      isDeleted: false,
      isActive: false,
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

    it('should create a Categories', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Categories()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Categories', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          categoryName: 'BBBBBB',
          categoryDescription: 'BBBBBB',
          freeField1: 'BBBBBB',
          lastModified: 'BBBBBB',
          lastModifiedBy: 'BBBBBB',
          isDeleted: true,
          isActive: true,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Categories', () => {
      const patchObject = Object.assign(
        {
          lastModified: 'BBBBBB',
          isDeleted: true,
        },
        new Categories()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Categories', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          categoryName: 'BBBBBB',
          categoryDescription: 'BBBBBB',
          freeField1: 'BBBBBB',
          lastModified: 'BBBBBB',
          lastModifiedBy: 'BBBBBB',
          isDeleted: true,
          isActive: true,
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

    it('should delete a Categories', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addCategoriesToCollectionIfMissing', () => {
      it('should add a Categories to an empty array', () => {
        const categories: ICategories = { id: 123 };
        expectedResult = service.addCategoriesToCollectionIfMissing([], categories);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(categories);
      });

      it('should not add a Categories to an array that contains it', () => {
        const categories: ICategories = { id: 123 };
        const categoriesCollection: ICategories[] = [
          {
            ...categories,
          },
          { id: 456 },
        ];
        expectedResult = service.addCategoriesToCollectionIfMissing(categoriesCollection, categories);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Categories to an array that doesn't contain it", () => {
        const categories: ICategories = { id: 123 };
        const categoriesCollection: ICategories[] = [{ id: 456 }];
        expectedResult = service.addCategoriesToCollectionIfMissing(categoriesCollection, categories);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(categories);
      });

      it('should add only unique Categories to an array', () => {
        const categoriesArray: ICategories[] = [{ id: 123 }, { id: 456 }, { id: 92779 }];
        const categoriesCollection: ICategories[] = [{ id: 123 }];
        expectedResult = service.addCategoriesToCollectionIfMissing(categoriesCollection, ...categoriesArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const categories: ICategories = { id: 123 };
        const categories2: ICategories = { id: 456 };
        expectedResult = service.addCategoriesToCollectionIfMissing([], categories, categories2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(categories);
        expect(expectedResult).toContain(categories2);
      });

      it('should accept null and undefined values', () => {
        const categories: ICategories = { id: 123 };
        expectedResult = service.addCategoriesToCollectionIfMissing([], null, categories, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(categories);
      });

      it('should return initial array if no Categories is added', () => {
        const categoriesCollection: ICategories[] = [{ id: 123 }];
        expectedResult = service.addCategoriesToCollectionIfMissing(categoriesCollection, undefined, null);
        expect(expectedResult).toEqual(categoriesCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
