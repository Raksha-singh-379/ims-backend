import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITransferDetails } from '../transfer-details.model';

@Component({
  selector: 'jhi-transfer-details-detail',
  templateUrl: './transfer-details-detail.component.html',
})
export class TransferDetailsDetailComponent implements OnInit {
  transferDetails: ITransferDetails | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transferDetails }) => {
      this.transferDetails = transferDetails;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
