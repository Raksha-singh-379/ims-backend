import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IClientDetails } from '../client-details.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-client-details-detail',
  templateUrl: './client-details-detail.component.html',
})
export class ClientDetailsDetailComponent implements OnInit {
  clientDetails: IClientDetails | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ clientDetails }) => {
      this.clientDetails = clientDetails;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
}
