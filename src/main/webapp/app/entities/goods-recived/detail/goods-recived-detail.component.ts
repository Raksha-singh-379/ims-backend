import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGoodsRecived } from '../goods-recived.model';

@Component({
  selector: 'jhi-goods-recived-detail',
  templateUrl: './goods-recived-detail.component.html',
})
export class GoodsRecivedDetailComponent implements OnInit {
  goodsRecived: IGoodsRecived | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ goodsRecived }) => {
      this.goodsRecived = goodsRecived;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
