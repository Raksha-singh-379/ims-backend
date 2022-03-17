import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IPurchaseQuotation, PurchaseQuotation } from '../purchase-quotation.model';
import { PurchaseQuotationService } from '../service/purchase-quotation.service';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { SecurityUserService } from 'app/entities/security-user/service/security-user.service';
import { IProject } from 'app/entities/project/project.model';
import { ProjectService } from 'app/entities/project/service/project.service';
import { IClientDetails } from 'app/entities/client-details/client-details.model';
import { ClientDetailsService } from 'app/entities/client-details/service/client-details.service';
import { OrderType } from 'app/entities/enumerations/order-type.model';
import { Status } from 'app/entities/enumerations/status.model';

@Component({
  selector: 'jhi-purchase-quotation-update',
  templateUrl: './purchase-quotation-update.component.html',
})
export class PurchaseQuotationUpdateComponent implements OnInit {
  isSaving = false;
  orderTypeValues = Object.keys(OrderType);
  statusValues = Object.keys(Status);

  securityUsersSharedCollection: ISecurityUser[] = [];
  projectsSharedCollection: IProject[] = [];
  clientDetailsSharedCollection: IClientDetails[] = [];

  editForm = this.fb.group({
    id: [],
    refrenceNumber: [],
    totalPOAmount: [],
    totalGSTAmount: [],
    expectedDeliveryDate: [],
    poDate: [],
    orderType: [],
    orderStatus: [],
    termsAndCondition: [],
    notes: [],
    lastModified: [],
    lastModifiedBy: [],
    freeField1: [],
    freeField2: [],
    securityUser: [],
    project: [],
    clientDetails: [],
  });

  constructor(
    protected purchaseQuotationService: PurchaseQuotationService,
    protected securityUserService: SecurityUserService,
    protected projectService: ProjectService,
    protected clientDetailsService: ClientDetailsService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ purchaseQuotation }) => {
      if (purchaseQuotation.id === undefined) {
        const today = dayjs().startOf('day');
        purchaseQuotation.expectedDeliveryDate = today;
        purchaseQuotation.poDate = today;
      }

      this.updateForm(purchaseQuotation);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const purchaseQuotation = this.createFromForm();
    if (purchaseQuotation.id !== undefined) {
      this.subscribeToSaveResponse(this.purchaseQuotationService.update(purchaseQuotation));
    } else {
      this.subscribeToSaveResponse(this.purchaseQuotationService.create(purchaseQuotation));
    }
  }

  trackSecurityUserById(index: number, item: ISecurityUser): number {
    return item.id!;
  }

  trackProjectById(index: number, item: IProject): number {
    return item.id!;
  }

  trackClientDetailsById(index: number, item: IClientDetails): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPurchaseQuotation>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(purchaseQuotation: IPurchaseQuotation): void {
    this.editForm.patchValue({
      id: purchaseQuotation.id,
      refrenceNumber: purchaseQuotation.refrenceNumber,
      totalPOAmount: purchaseQuotation.totalPOAmount,
      totalGSTAmount: purchaseQuotation.totalGSTAmount,
      expectedDeliveryDate: purchaseQuotation.expectedDeliveryDate ? purchaseQuotation.expectedDeliveryDate.format(DATE_TIME_FORMAT) : null,
      poDate: purchaseQuotation.poDate ? purchaseQuotation.poDate.format(DATE_TIME_FORMAT) : null,
      orderType: purchaseQuotation.orderType,
      orderStatus: purchaseQuotation.orderStatus,
      termsAndCondition: purchaseQuotation.termsAndCondition,
      notes: purchaseQuotation.notes,
      lastModified: purchaseQuotation.lastModified,
      lastModifiedBy: purchaseQuotation.lastModifiedBy,
      freeField1: purchaseQuotation.freeField1,
      freeField2: purchaseQuotation.freeField2,
      securityUser: purchaseQuotation.securityUser,
      project: purchaseQuotation.project,
      clientDetails: purchaseQuotation.clientDetails,
    });

    this.securityUsersSharedCollection = this.securityUserService.addSecurityUserToCollectionIfMissing(
      this.securityUsersSharedCollection,
      purchaseQuotation.securityUser
    );
    this.projectsSharedCollection = this.projectService.addProjectToCollectionIfMissing(
      this.projectsSharedCollection,
      purchaseQuotation.project
    );
    this.clientDetailsSharedCollection = this.clientDetailsService.addClientDetailsToCollectionIfMissing(
      this.clientDetailsSharedCollection,
      purchaseQuotation.clientDetails
    );
  }

  protected loadRelationshipsOptions(): void {
    this.securityUserService
      .query()
      .pipe(map((res: HttpResponse<ISecurityUser[]>) => res.body ?? []))
      .pipe(
        map((securityUsers: ISecurityUser[]) =>
          this.securityUserService.addSecurityUserToCollectionIfMissing(securityUsers, this.editForm.get('securityUser')!.value)
        )
      )
      .subscribe((securityUsers: ISecurityUser[]) => (this.securityUsersSharedCollection = securityUsers));

    this.projectService
      .query()
      .pipe(map((res: HttpResponse<IProject[]>) => res.body ?? []))
      .pipe(
        map((projects: IProject[]) => this.projectService.addProjectToCollectionIfMissing(projects, this.editForm.get('project')!.value))
      )
      .subscribe((projects: IProject[]) => (this.projectsSharedCollection = projects));

    this.clientDetailsService
      .query()
      .pipe(map((res: HttpResponse<IClientDetails[]>) => res.body ?? []))
      .pipe(
        map((clientDetails: IClientDetails[]) =>
          this.clientDetailsService.addClientDetailsToCollectionIfMissing(clientDetails, this.editForm.get('clientDetails')!.value)
        )
      )
      .subscribe((clientDetails: IClientDetails[]) => (this.clientDetailsSharedCollection = clientDetails));
  }

  protected createFromForm(): IPurchaseQuotation {
    return {
      ...new PurchaseQuotation(),
      id: this.editForm.get(['id'])!.value,
      refrenceNumber: this.editForm.get(['refrenceNumber'])!.value,
      totalPOAmount: this.editForm.get(['totalPOAmount'])!.value,
      totalGSTAmount: this.editForm.get(['totalGSTAmount'])!.value,
      expectedDeliveryDate: this.editForm.get(['expectedDeliveryDate'])!.value
        ? dayjs(this.editForm.get(['expectedDeliveryDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      poDate: this.editForm.get(['poDate'])!.value ? dayjs(this.editForm.get(['poDate'])!.value, DATE_TIME_FORMAT) : undefined,
      orderType: this.editForm.get(['orderType'])!.value,
      orderStatus: this.editForm.get(['orderStatus'])!.value,
      termsAndCondition: this.editForm.get(['termsAndCondition'])!.value,
      notes: this.editForm.get(['notes'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      freeField1: this.editForm.get(['freeField1'])!.value,
      freeField2: this.editForm.get(['freeField2'])!.value,
      securityUser: this.editForm.get(['securityUser'])!.value,
      project: this.editForm.get(['project'])!.value,
      clientDetails: this.editForm.get(['clientDetails'])!.value,
    };
  }
}
