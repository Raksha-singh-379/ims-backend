import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ITransferDetailsApprovals, TransferDetailsApprovals } from '../transfer-details-approvals.model';
import { TransferDetailsApprovalsService } from '../service/transfer-details-approvals.service';

import { TransferDetailsApprovalsRoutingResolveService } from './transfer-details-approvals-routing-resolve.service';

describe('TransferDetailsApprovals routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: TransferDetailsApprovalsRoutingResolveService;
  let service: TransferDetailsApprovalsService;
  let resultTransferDetailsApprovals: ITransferDetailsApprovals | undefined;

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
    routingResolveService = TestBed.inject(TransferDetailsApprovalsRoutingResolveService);
    service = TestBed.inject(TransferDetailsApprovalsService);
    resultTransferDetailsApprovals = undefined;
  });

  describe('resolve', () => {
    it('should return ITransferDetailsApprovals returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTransferDetailsApprovals = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTransferDetailsApprovals).toEqual({ id: 123 });
    });

    it('should return new ITransferDetailsApprovals if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTransferDetailsApprovals = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultTransferDetailsApprovals).toEqual(new TransferDetailsApprovals());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as TransferDetailsApprovals })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTransferDetailsApprovals = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTransferDetailsApprovals).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
