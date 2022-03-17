import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IConsumptionDetails, ConsumptionDetails } from '../consumption-details.model';
import { ConsumptionDetailsService } from '../service/consumption-details.service';
import { ISecurityUser } from 'app/entities/security-user/security-user.model';
import { SecurityUserService } from 'app/entities/security-user/service/security-user.service';
import { IProject } from 'app/entities/project/project.model';
import { ProjectService } from 'app/entities/project/service/project.service';
import { IProductInventory } from 'app/entities/product-inventory/product-inventory.model';
import { ProductInventoryService } from 'app/entities/product-inventory/service/product-inventory.service';

@Component({
  selector: 'jhi-consumption-details-update',
  templateUrl: './consumption-details-update.component.html',
})
export class ConsumptionDetailsUpdateComponent implements OnInit {
  isSaving = false;

  securityUsersSharedCollection: ISecurityUser[] = [];
  projectsSharedCollection: IProject[] = [];
  productInventoriesSharedCollection: IProductInventory[] = [];

  editForm = this.fb.group({
    id: [],
    comsumptionDate: [],
    qtyConsumed: [],
    freeField1: [],
    freeField2: [],
    lastModified: [],
    lastModifiedBy: [],
    securityUser: [],
    project: [],
    productInventory: [],
  });

  constructor(
    protected consumptionDetailsService: ConsumptionDetailsService,
    protected securityUserService: SecurityUserService,
    protected projectService: ProjectService,
    protected productInventoryService: ProductInventoryService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ consumptionDetails }) => {
      if (consumptionDetails.id === undefined) {
        const today = dayjs().startOf('day');
        consumptionDetails.comsumptionDate = today;
      }

      this.updateForm(consumptionDetails);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const consumptionDetails = this.createFromForm();
    if (consumptionDetails.id !== undefined) {
      this.subscribeToSaveResponse(this.consumptionDetailsService.update(consumptionDetails));
    } else {
      this.subscribeToSaveResponse(this.consumptionDetailsService.create(consumptionDetails));
    }
  }

  trackSecurityUserById(index: number, item: ISecurityUser): number {
    return item.id!;
  }

  trackProjectById(index: number, item: IProject): number {
    return item.id!;
  }

  trackProductInventoryById(index: number, item: IProductInventory): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConsumptionDetails>>): void {
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

  protected updateForm(consumptionDetails: IConsumptionDetails): void {
    this.editForm.patchValue({
      id: consumptionDetails.id,
      comsumptionDate: consumptionDetails.comsumptionDate ? consumptionDetails.comsumptionDate.format(DATE_TIME_FORMAT) : null,
      qtyConsumed: consumptionDetails.qtyConsumed,
      freeField1: consumptionDetails.freeField1,
      freeField2: consumptionDetails.freeField2,
      lastModified: consumptionDetails.lastModified,
      lastModifiedBy: consumptionDetails.lastModifiedBy,
      securityUser: consumptionDetails.securityUser,
      project: consumptionDetails.project,
      productInventory: consumptionDetails.productInventory,
    });

    this.securityUsersSharedCollection = this.securityUserService.addSecurityUserToCollectionIfMissing(
      this.securityUsersSharedCollection,
      consumptionDetails.securityUser
    );
    this.projectsSharedCollection = this.projectService.addProjectToCollectionIfMissing(
      this.projectsSharedCollection,
      consumptionDetails.project
    );
    this.productInventoriesSharedCollection = this.productInventoryService.addProductInventoryToCollectionIfMissing(
      this.productInventoriesSharedCollection,
      consumptionDetails.productInventory
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

    this.productInventoryService
      .query()
      .pipe(map((res: HttpResponse<IProductInventory[]>) => res.body ?? []))
      .pipe(
        map((productInventories: IProductInventory[]) =>
          this.productInventoryService.addProductInventoryToCollectionIfMissing(
            productInventories,
            this.editForm.get('productInventory')!.value
          )
        )
      )
      .subscribe((productInventories: IProductInventory[]) => (this.productInventoriesSharedCollection = productInventories));
  }

  protected createFromForm(): IConsumptionDetails {
    return {
      ...new ConsumptionDetails(),
      id: this.editForm.get(['id'])!.value,
      comsumptionDate: this.editForm.get(['comsumptionDate'])!.value
        ? dayjs(this.editForm.get(['comsumptionDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      qtyConsumed: this.editForm.get(['qtyConsumed'])!.value,
      freeField1: this.editForm.get(['freeField1'])!.value,
      freeField2: this.editForm.get(['freeField2'])!.value,
      lastModified: this.editForm.get(['lastModified'])!.value,
      lastModifiedBy: this.editForm.get(['lastModifiedBy'])!.value,
      securityUser: this.editForm.get(['securityUser'])!.value,
      project: this.editForm.get(['project'])!.value,
      productInventory: this.editForm.get(['productInventory'])!.value,
    };
  }
}
