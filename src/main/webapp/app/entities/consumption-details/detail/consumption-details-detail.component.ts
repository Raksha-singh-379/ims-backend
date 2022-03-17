import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IConsumptionDetails } from '../consumption-details.model';

@Component({
  selector: 'jhi-consumption-details-detail',
  templateUrl: './consumption-details-detail.component.html',
})
export class ConsumptionDetailsDetailComponent implements OnInit {
  consumptionDetails: IConsumptionDetails | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ consumptionDetails }) => {
      this.consumptionDetails = consumptionDetails;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
