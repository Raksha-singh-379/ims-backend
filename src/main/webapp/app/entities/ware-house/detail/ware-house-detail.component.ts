import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWareHouse } from '../ware-house.model';

@Component({
  selector: 'jhi-ware-house-detail',
  templateUrl: './ware-house-detail.component.html',
})
export class WareHouseDetailComponent implements OnInit {
  wareHouse: IWareHouse | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ wareHouse }) => {
      this.wareHouse = wareHouse;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
