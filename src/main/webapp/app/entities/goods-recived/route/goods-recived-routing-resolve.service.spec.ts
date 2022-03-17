import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IGoodsRecived, GoodsRecived } from '../goods-recived.model';
import { GoodsRecivedService } from '../service/goods-recived.service';

import { GoodsRecivedRoutingResolveService } from './goods-recived-routing-resolve.service';

describe('GoodsRecived routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: GoodsRecivedRoutingResolveService;
  let service: GoodsRecivedService;
  let resultGoodsRecived: IGoodsRecived | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(GoodsRecivedRoutingResolveService);
    service = TestBed.inject(GoodsRecivedService);
    resultGoodsRecived = undefined;
  });

  describe('resolve', () => {
    it('should return IGoodsRecived returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultGoodsRecived = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultGoodsRecived).toEqual({ id: 123 });
    });

    it('should return new IGoodsRecived if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultGoodsRecived = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultGoodsRecived).toEqual(new GoodsRecived());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as GoodsRecived })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultGoodsRecived = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultGoodsRecived).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
