import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITransferRecieved } from '../transfer-recieved.model';

@Component({
  selector: 'jhi-transfer-recieved-detail',
  templateUrl: './transfer-recieved-detail.component.html',
})
export class TransferRecievedDetailComponent implements OnInit {
  transferRecieved: ITransferRecieved | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transferRecieved }) => {
      this.transferRecieved = transferRecieved;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
