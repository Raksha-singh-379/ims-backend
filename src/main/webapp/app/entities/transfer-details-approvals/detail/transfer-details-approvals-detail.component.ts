import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITransferDetailsApprovals } from '../transfer-details-approvals.model';

@Component({
  selector: 'jhi-transfer-details-approvals-detail',
  templateUrl: './transfer-details-approvals-detail.component.html',
})
export class TransferDetailsApprovalsDetailComponent implements OnInit {
  transferDetailsApprovals: ITransferDetailsApprovals | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transferDetailsApprovals }) => {
      this.transferDetailsApprovals = transferDetailsApprovals;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
