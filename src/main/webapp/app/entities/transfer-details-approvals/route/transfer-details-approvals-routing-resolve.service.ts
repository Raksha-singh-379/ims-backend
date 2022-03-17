import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITransferDetailsApprovals, TransferDetailsApprovals } from '../transfer-details-approvals.model';
import { TransferDetailsApprovalsService } from '../service/transfer-details-approvals.service';

@Injectable({ providedIn: 'root' })
export class TransferDetailsApprovalsRoutingResolveService implements Resolve<ITransferDetailsApprovals> {
  constructor(protected service: TransferDetailsApprovalsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITransferDetailsApprovals> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((transferDetailsApprovals: HttpResponse<TransferDetailsApprovals>) => {
          if (transferDetailsApprovals.body) {
            return of(transferDetailsApprovals.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TransferDetailsApprovals());
  }
}
